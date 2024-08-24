package me.meta4245.betterthanmodern

import com.chocohead.mm.api.ClassTinkerers

class EarlyRiser : Runnable {
    override fun run() {
        ClassTinkerers.addTransformation("me/meta4245/betterthanmodern/event/ItemRegistry", RegistryTransformer())
    }
}
