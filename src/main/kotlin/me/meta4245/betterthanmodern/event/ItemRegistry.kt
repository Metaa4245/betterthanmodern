package me.meta4245.betterthanmodern.event

import net.minecraft.item.Item
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint
import net.modificationstation.stationapi.api.util.Identifier
import net.modificationstation.stationapi.api.util.Namespace

class ItemRegistry {
    companion object {
        @Entrypoint.Namespace
        @JvmStatic
        val NAMESPACE: Namespace? = null
    }

    private fun item(clazz: Class<out Item>): Item {
        val name = StringBuilder()
        val className = clazz.simpleName
        val len = className.length

        for (i in 0 until len) {
            val c = className[i]
            if (Character.isUpperCase(c) && i != len - 1 && i != 0) {
                name.append("_")
            }
            name.append(c.lowercaseChar())
        }

        val key = name.toString()
        val i: Item

        try {
            i = clazz.getConstructor(Identifier::class.java).newInstance(
                NAMESPACE?.id(key)
            ).setTranslationKey(NAMESPACE, key)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return i
    }
}
