package me.meta4245.betterthanmodern

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.ClassNode
import java.util.function.Consumer

class RegistryTransformer : Consumer<ClassNode> {
    private var classes = listOf(
        "disc/BlocksDisc",
    )

    override fun accept(classNode: ClassNode) {
        // TODO: this could be simpler right?
        val labels: MutableList<Label> = ArrayList()

        classes = classes.map { s -> "me/meta4245/betterthanmodern/item/$s" }
        val typeNames = classes.map { s -> "L$s;" }
        val simpleNames = classes.map { s ->
            val split = s.split("\\/")
            split[split.size - 1]
        }

        for (simpleName in simpleNames) {
            val idName = "${simpleName}Id"

            val itemVisitor = classNode.visitField(
                Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
                simpleName,
                "Lnet/minecraft/item/Item;",
                null,
                null
            )
            itemVisitor.visitEnd()
            val idVisitor = classNode.visitField(Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC, idName, "I", null, null)
            idVisitor.visitEnd()
        }

        val methodVisitor = classNode.visitMethod(
            Opcodes.ACC_PUBLIC,
            "registerItems",
            "(Lnet/modificationstation/stationapi/api/event/registry/ItemRegistryEvent;)V",
            null,
            null
        )
        val annotationVisitor =
            methodVisitor.visitAnnotation("Lnet/mine_diver/unsafeevents/listener/EventListener;", true)
        annotationVisitor.visitEnd()
        methodVisitor.visitCode()
        for (i in classes.indices) {
            val typeName = typeNames[i]
            val simpleName = simpleNames[i]
            val idName = simpleName + "Id"

            val l = Label()
            methodVisitor.visitLabel(l)
            labels.add(l)

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitLdcInsn(Type.getType(typeName))
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "me/meta4245/betterthanmodern/event/ItemRegistry",
                "item",
                "(Ljava/lang/Class;)Lnet/minecraft/item/Item;",
                false
            )
            methodVisitor.visitFieldInsn(
                Opcodes.PUTSTATIC,
                "me/meta4245/betterthanmodern/event/ItemRegistry",
                simpleName,
                "Lnet/minecraft/item/Item;"
            )

            //            Label l1 = new Label();
//            methodVisitor.visitLabel(l1);
//            labels.add(l1);
//
//            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "me/meta4245/betterthanmodern/event/ItemRegistry", simple_name, "Lnet/minecraft/item/Item;");
//            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/item/Item", "id", "I");
//            methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, "me/meta4245/betterthanmodern/event/ItemRegistry", id_name, "I");
        }
        val label1 = Label()
        methodVisitor.visitLabel(label1)
        labels.add(label1)
        methodVisitor.visitInsn(Opcodes.RETURN)
        val label2 = Label()
        methodVisitor.visitLabel(label2)
        labels.add(label2)
        val first = labels[0]
        val last = labels[labels.size - 1]
        methodVisitor.visitLocalVariable(
            "this",
            "Lme/meta4245/betterthanmodern/event/ItemRegistry;",
            null,
            first,
            last,
            0
        )
        methodVisitor.visitLocalVariable(
            "event",
            "Lnet/modificationstation/stationapi/api/event/registry/ItemRegistryEvent;",
            null,
            first,
            last,
            1
        )
        methodVisitor.visitMaxs(2, 2 + classes.size)
        methodVisitor.visitEnd()

        classNode.visitEnd()
    }
}
