package me.meta4245.betterthanmodern.event

import net.minecraft.item.Item
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint
import net.modificationstation.stationapi.api.util.Identifier
import net.modificationstation.stationapi.api.util.Namespace
import net.modificationstation.stationapi.api.util.Null

class ItemRegistry {
    private fun item(clazz: Class<out Item>): Item {
        val name = StringBuilder()
        val class_name = clazz.simpleName
        val len = class_name.length

        for (i in 0 until len) {
            val c = class_name[i]
            if (Character.isUpperCase(c) && i != len - 1 && i != 0) {
                name.append("_")
            }
            name.append(c.lowercaseChar())
        }

        val key = name.toString()
        val i: Item

        try {
            i = clazz.getConstructor(Identifier::class.java).newInstance(
                NAMESPACE.id(key)
            ).setTranslationKey(NAMESPACE, key)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return i
    }

    companion object {
        @Entrypoint.Namespace
        val NAMESPACE: Namespace = Null.get()
    }
}
