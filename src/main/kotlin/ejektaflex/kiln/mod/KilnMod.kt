package ejektaflex.kiln.mod

import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import kotlin.reflect.full.findAnnotation


@Suppress("LeakingThis")
abstract class KilnMod() : KilnAbstractMod() {

    private val submods = mutableListOf<KilnSubmod>()

    init {
        FMLKotlinModLoadingContext.get().modEventBus.register(this)
    }

    fun registerSubmod(submod: KilnSubmod) {
        LOGGER.info("Registering submod with ID: '${submod.ID}'..")
        submods += submod
        // non lifecycle events
        MinecraftForge.EVENT_BUS.register(submod)
        // lifecycle events
        FMLKotlinModLoadingContext.get().modEventBus.register(submod)
    }

    override val ID: String by lazy {
        this::class.findAnnotation<Mod>()?.value ?: throw Exception("Mod not annotated! Classes extending KilnMod must have the '@Mod' annotation with a valid ID.")
    }

}