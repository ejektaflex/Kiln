package ejektaflex.pyrolysis.runestones

import ejektaflex.kiln.ext.toDataOrNull
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.runestones.data.RuneData
import ejektaflex.pyrolysis.runestones.item.runes.HealRune
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object Runestones : KilnSubmod("runestones") {

    val healing = HealRune()

    @SubscribeEvent
    fun onRegisterItems(event: RegistryEvent.Register<Item>) {
        event.registry.register(healing)
    }

}