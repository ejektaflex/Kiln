package ejektaflex.kiln.mod

abstract class KilnSubmod(modId: String, var isActive: Boolean = true) : KilnAbstractMod() {

    override val ID = modId

}