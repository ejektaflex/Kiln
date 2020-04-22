package ejektaflex.pyrolysis.runestones.item.runes

import ejektaflex.pyrolysis.runestones.data.RuneData
import ejektaflex.pyrolysis.runestones.item.Runestone
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.World

class HealRune : Runestone("healing") {

    override fun onRuneUsed(world: World, player: PlayerEntity, level: Int, chargesUsed: Int) {
        println("Hello, World!")
    }

}