package ejektaflex.pyrolysis.runestones.data

import ejektaflex.kiln.nbt.KilnCheckableData
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable
import kotlin.math.max

class RuneData : KilnCheckableData() {
    var charge = 0
    var charges = 0
    var level = 1

    private val maxCharge: Int
        get() = 75 + (level * 25)

    override fun getCheckableKeys() = listOf(CHARGE, NUM_CHARGES, LEVEL)

    fun addCharge(amount: Int) {
        charge += amount
        if (charge >= maxCharge && charges != maxCharges) {
            charge -= maxCharge
            charges = max(charges + 1, maxCharges)
        }
    }

    override fun serializeNBT(): CompoundNBT {
        return CompoundNBT().apply {
            putInt(CHARGE, charge)
            putInt(NUM_CHARGES, charges)
            putInt(LEVEL, level)
        }
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        nbt.run {
            charge = getInt(CHARGE)
            charges = getInt(NUM_CHARGES)
            level = getInt(LEVEL)
        }
    }

    companion object {
        private const val maxCharges = 3

        private const val CHARGE = "charge"
        private const val NUM_CHARGES = "num_charges"
        private const val LEVEL = "level"
    }

}