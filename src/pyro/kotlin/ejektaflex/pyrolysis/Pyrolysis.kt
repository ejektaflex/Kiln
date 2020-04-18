package ejektaflex.pyrolysis

import ejektaflex.kiln.config.KilnConfig
import ejektaflex.kiln.ext.invoke
import ejektaflex.kiln.mod.KilnMod
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.animator.PyroAnimator
import ejektaflex.pyrolysis.tiergear.PyroTierGear
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig


@Mod("pyrolysis")
object Pyrolysis : KilnMod() {

    init {
        //registerSubmod(PyroTierGear)
        registerSubmod(PyroAnimator)
    }

}