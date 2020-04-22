package ejektaflex.kiln.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig
import org.apache.commons.lang3.tuple.Pair as ApachePair


open class KilnConfig<T : KilnConfig<T>>(private val configType: ModConfig.Type, val fileName: String? = null) {

    private var builder = ForgeConfigSpec.Builder()

    @Suppress("UNCHECKED_CAST")
    fun register(): T {
        println("Registering Kiln config")
        if (fileName != null) {
            ModLoadingContext.get().registerConfig(configType, builder.build(), fileName)
        } else {
            ModLoadingContext.get().registerConfig(configType, builder.build())
        }
        return this as T
    }


}