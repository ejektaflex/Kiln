package ejektaflex.pyrolysis.dungeoning.world.gen.structure

import ejektaflex.pyrolysis.Pyrolysis
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.jigsaw.JigsawManager
import net.minecraft.world.gen.feature.structure.MarginedStructureStart
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.feature.structure.StructureStart
import net.minecraft.world.gen.feature.template.TemplateManager


class DungeonStart(
        structureIn: Structure<*>,
        chunkX: Int,
        chunkZ: Int,
        boundsIn: MutableBoundingBox,
        referenceIn: Int,
        seed: Long
) : MarginedStructureStart(structureIn, chunkX, chunkZ, boundsIn, referenceIn, seed) {

    override fun init(generator: ChunkGenerator<*>, templateManagerIn: TemplateManager, chunkX: Int, chunkZ: Int, biomeIn: Biome) {

        val pos = BlockPos(chunkX * 16, 40, chunkZ * 16)


        JigsawManager.func_214889_a(
                Pyrolysis.locate("dungeon/starts"),
                100, { t, _, _, _, _, _ -> DungeonPiece(t) },
                generator,
                templateManagerIn,
                pos,
                components,
                rand
        )

        recalculateStructureSize()


    }

    companion object {
        class Factory : Structure.IStartFactory {
            override fun create(p_create_1_: Structure<*>, p_create_2_: Int, p_create_3_: Int, p_create_4_: MutableBoundingBox, p_create_5_: Int, p_create_6_: Long): StructureStart {
                return DungeonStart(p_create_1_, p_create_2_, p_create_3_, p_create_4_, p_create_5_, p_create_6_)
            }
        }
    }

}