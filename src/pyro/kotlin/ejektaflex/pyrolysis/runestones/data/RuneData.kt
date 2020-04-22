package ejektaflex.pyrolysis.runestones.data

import ejektaflex.kiln.nbt.KilnCheckableData
import net.minecraft.nbt.CompoundNBT
import kotlin.math.max
import kotlin.math.min

class RuneData : KilnCheckableData() {

    var points = 0
        private set

    var level = 1

    val charges: Int
        get() = points / pointsPerCharge


    var maxCharges = 3 // TODO change to 1

    val pointsPerCharge: Int
        get() = 75 + (level * 25)

    val isFullyCharged: Boolean
        get() = charges == maxCharges

    val maxPoints: Int
        get() = maxCharges * pointsPerCharge

    val chargePercent: Double
        get() = pointsAfterCharge.toDouble() / pointsPerCharge

    val pointsAfterCharge: Int
        get() = when (charges) {
            0 -> points
            else -> points % (pointsPerCharge * charges)
        }

    override fun getCheckableKeys() = listOf(POINTS, MAX_CHARGES, LEVEL)

    fun useCharges(amount: Int) {
        points = max(0, points - (pointsPerCharge * amount))
    }

    fun charge(amount: Int) {
        points = min(maxPoints, points + amount)
    }

    override fun serializeNBT(): CompoundNBT {
        return CompoundNBT().apply {
            putInt(POINTS, points)
            putInt(MAX_CHARGES, maxCharges)
            putInt(LEVEL, level)
        }
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        nbt.run {
            points = getInt(POINTS)
            maxCharges = getInt(MAX_CHARGES)
            level = getInt(LEVEL)
        }
    }

    companion object {
        // NBT keys
        private const val POINTS = "points"
        private const val MAX_CHARGES = "max_charges"
        private const val LEVEL = "level"
    }

}