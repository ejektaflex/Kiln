package ejektaflex.pyrolysis.dungeoning.world.gen.structure

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeManager
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.structure.Structure
import java.util.*

class DungeonStructure : Structure<NoFeatureConfig>({ _ -> IFeatureConfig.NO_FEATURE_CONFIG}) {

    override fun getSize() = 100

    override fun getStartFactory() = DungeonStart.Companion.Factory()

    override fun getStructureName() = "DogonoDungeon"

    //override fun getSeedModifier() = 1337



    override fun func_225558_a_(biomeManager: BiomeManager, chunkGen: ChunkGenerator<*>, rand: Random, x: Int, y: Int, biomeIn: Biome): Boolean {
        TODO("Not yet implemented")
    }


}