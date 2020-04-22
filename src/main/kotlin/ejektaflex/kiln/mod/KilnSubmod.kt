package ejektaflex.kiln.mod

import net.minecraftforge.eventbus.api.IEventBus

abstract class KilnSubmod(modId: String) : KilnAbstractMod() {

    override val ID = modId

}