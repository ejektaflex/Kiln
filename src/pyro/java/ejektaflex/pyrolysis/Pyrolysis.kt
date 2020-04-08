package ejektaflex.pyrolysis

import ejektaflex.kiln.config.KilnConfig
import ejektaflex.kiln.ext.invoke
import ejektaflex.kiln.mod.KilnMod
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.animator.PyroAnimator
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig


@Mod("pyrolysis")
object Pyrolysis : KilnMod() {

    val configLoadSubmods = mutableListOf<KilnSubmod>(
            PyroAnimator
    )

    object Config : KilnConfig<Config>(ModConfig.Type.COMMON) {

        init {
            for (configLoadable in configLoadSubmods) {
                registerConfig(configLoadable, configLoadable.isActive)
            }
        }

        private val configMap = mutableMapOf<KilnSubmod, ForgeConfigSpec.BooleanValue>()

        private fun registerConfig(submod: KilnSubmod, default: Boolean) {
            val spec = builder
                    .comment("Whether the submod '${submod::class.simpleName}' should be active.")
                    .define(submod::class.simpleName, default)
            println("SPEC: $spec")
            //configMap[submod] = spec
            println("IN: ${configMap[submod]}")
        }

        fun changeActiveStatus() {
            for (submod in configMap.keys) {
                submod.isActive = configMap[submod]!!()
            }
        }

    }

    init {
        Config.register()
        Config.changeActiveStatus()

        configLoadSubmods.filter { it.isActive }.forEach {
            registerSubmod(it)
        }
    }



}