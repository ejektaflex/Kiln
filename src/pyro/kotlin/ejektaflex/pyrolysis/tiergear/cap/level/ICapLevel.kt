package ejektaflex.pyrolysis.tiergear.cap.level

import ejektaflex.kilnx.cap.KilnSimpleSavingCapabilityProvider
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject

interface ICapLevel {

    var level: Int

    class Storage : Capability.IStorage<ICapLevel> {
        override fun readNBT(capability: Capability<ICapLevel>, instance: ICapLevel, side: Direction, nbt: INBT) {
            instance.level = (nbt as CompoundNBT).getInt(LEVEL)
        }

        override fun writeNBT(capability: Capability<ICapLevel>, instance: ICapLevel, side: Direction)= CompoundNBT().apply {
            putInt(LEVEL, instance.level)
        }
    }

    class LevelProvider : KilnSimpleSavingCapabilityProvider<ICapLevel>(LEVEL_CAPABILITY) {
        companion object {
            @JvmStatic @CapabilityInject(ICapLevel::class)
            lateinit var LEVEL_CAPABILITY: Capability<ICapLevel>
                //private set
        }
    }

    companion object {
        const val LEVEL = "level"
    }

}