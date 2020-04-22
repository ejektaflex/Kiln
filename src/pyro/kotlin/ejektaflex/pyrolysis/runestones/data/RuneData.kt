package ejektaflex.pyrolysis.runestones.data

import ejektaflex.kiln.nbt.KilnCheckableData
import net.minecraft.nbt.CompoundNBT
import kotlin.math.min

class RuneData : KilnCheckableData() {
    var currCharge = 0
    var charges = 0
    var level = 1

    val maxCharge: Int
        get() = 75 + (level * 25)

    override fun getCheckableKeys() = listOf(CHARGE, NUM_CHARGES, LEVEL)

    fun addCharge(amount: Int) {
        currCharge += amount
        while (currCharge >= maxCharge) {
            currCharge -= maxCharge
            charges = min(charges + 1, maxCharges)
        }
    }

    override fun serializeNBT(): CompoundNBT {
        return CompoundNBT().apply {
            putInt(CHARGE, currCharge)
            putInt(NUM_CHARGES, charges)
            putInt(LEVEL, level)
        }
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        nbt.run {
            currCharge = getInt(CHARGE)
            charges = getInt(NUM_CHARGES)
            level = getInt(LEVEL)
        }
    }

    companion object {
        private const val maxCharges = 3
        // NBT keys
        private const val CHARGE = "charge"
        private const val NUM_CHARGES = "num_charges"
        private const val LEVEL = "level"
    }

}