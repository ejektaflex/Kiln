package ejektaflex.pyrolysis.tiergear

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands.argument
import net.minecraft.command.Commands.literal
import net.minecraft.enchantment.EnchantmentType
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.registries.ForgeRegistries

object TierGearCommand {

    fun generate(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(
                literal("tg")
                        .then(
                                argument("gType", string())
                                        .executes(makeItem())
                        )
        )
    }

    fun makeItem() = Command<CommandSource> { ctx ->

        if (ctx.source is PlayerEntity) {
            println("A player said this")

            val player = ctx.source.asPlayer()

            //PyroTierGear.genEnchants(player)

        }

        ForgeRegistries.ENCHANTMENTS.entries.toList().forEach {
            println("Ench ${it.key}")
        }

        val typeName = getString(ctx, "gType").toLowerCase()

        val types: Array<EnchantmentType> = enumValues<EnchantmentType>()

        types.forEach { type ->
            println("Enum value: ${type.name}")
        }

        val foundType = types.first { it.name.toLowerCase() == typeName }




        1
    }

}