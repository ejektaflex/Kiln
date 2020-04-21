package ejektaflex.kilnx.cap

import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional
import java.lang.IllegalArgumentException

open class KilnSimpleSavingCapabilityProvider<C: Any>(val capability: Capability<C>?) : ICapabilitySerializable<INBT> {

    val instance = LazyOptional.of { capability!!.defaultInstance!! }

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if (cap == capability) instance.cast() else LazyOptional.empty()
    }

    override fun deserializeNBT(nbt: INBT?) {
        capability!!.storage.readNBT(capability, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null, nbt)
    }

    override fun serializeNBT(): INBT {
        return capability!!.storage.writeNBT(capability, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null) ?: throw Exception("Serializing capability of type ${capability.name} did not work!")
    }

}