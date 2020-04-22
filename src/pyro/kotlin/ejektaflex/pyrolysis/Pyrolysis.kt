package ejektaflex.pyrolysis

import ejektaflex.kiln.config.KilnConfig
import ejektaflex.kiln.ext.edit
import ejektaflex.kiln.ext.invoke
import ejektaflex.kiln.mod.KilnMod
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.animator.PyroAnimator
import ejektaflex.pyrolysis.runestones.Runestones
import ejektaflex.pyrolysis.runestones.item.Runestone
import ejektaflex.pyrolysis.tiergear.PyroTierGear
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import kotlin.math.ceil


@Mod("pyrolysis")
object Pyrolysis : KilnMod() {

    init {
        //registerSubmod(PyroTierGear)
        //registerSubmod(PyroAnimator)
        registerSubmod(Runestones)
    }





}