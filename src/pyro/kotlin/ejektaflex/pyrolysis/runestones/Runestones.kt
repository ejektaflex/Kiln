package ejektaflex.pyrolysis.runestones

import ejektaflex.kiln.ext.edit
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.runestones.item.Runestone
import ejektaflex.pyrolysis.runestones.item.runes.HealRune
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import kotlin.math.ceil
import kotlin.math.max

object Runestones : KilnSubmod("runestones") {

    val healing = HealRune()

    @SubscribeEvent
    fun onRegisterItems(event: RegistryEvent.Register<Item>) {
        event.registry.register(healing)
    }

    @SubscribeEvent
    fun onClient(event: FMLClientSetupEvent) {
        Minecraft.getInstance().itemColors.register(healing, healing)
    }

    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        if (event.entity.world.isRemote) { return }

        val source = event.source.trueSource
        println("Src: ${event.source}, ${event.source.trueSource}, ${event.source.immediateSource}")
        if (source is PlayerEntity) {
            val charge = 30
            val stones = source.inventory.mainInventory.filter { it.item is Runestone }
            stones.forEach { stone ->
                stone.edit<Runestone> {
                    println("Adding charge")
                    addCharge(it, max(1, ceil(charge.toDouble() / stones.size).toInt()))
                }
            }
        }
    }


}