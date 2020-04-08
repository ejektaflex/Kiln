package ejektaflex.pyrolysis.dungeoning.world.gen.structure

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraft.world.gen.feature.template.TemplateManager


class DungeonPiece(
        templateManagerIn: TemplateManager, nbt: CompoundNBT? = null
) : AbstractVillagePiece(templateManagerIn, nbt, Type) {

    companion object {
        val Type = Registry.register(Registry.STRUCTURE_PIECE, "bigdungeon", IStructurePieceType { t, n -> DungeonPiece(t, n) })
    }
}