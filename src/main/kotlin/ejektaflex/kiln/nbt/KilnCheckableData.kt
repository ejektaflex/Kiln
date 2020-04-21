package ejektaflex.kiln.nbt

import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable

abstract class KilnCheckableData : INBTSerializable<CompoundNBT> {

    abstract fun getCheckableKeys(): List<String>

}