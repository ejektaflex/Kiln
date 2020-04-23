package ejektaflex.pyrolysis.runestones

import ejektaflex.kiln.ext.edit
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.runestones.item.Runestone
import ejektaflex.pyrolysis.runestones.item.runes.HealRune
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
import kotlin.math.ceil
import kotlin.math.max

object Runestones : KilnSubmod("runestones") {

    val healing = HealRune()

    @SubscribeEvent
    fun onRegisterItems(event: RegistryEvent.Register<Item>) {
        event.registry.register(healing)
    }

    @SubscribeEvent
    fun onDrawRune(event: GuiScreenEvent.DrawScreenEvent.Post) {
        val gui = event.gui
        if (event.gui is ContainerScreen<*>)
            for (slot in event.gui.container.inventorySlots) {
                if (slot.hasStack && slot.stack.item is Runestone) {
                    println("Doot!")

                }
            }
    }

    fun drawRuneBar(x: Int, y: Int) {

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