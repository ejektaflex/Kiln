package ejektaflex.pyrolysis.tiergear

import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.tiergear.cap.level.CapLevel
import ejektaflex.pyrolysis.tiergear.cap.level.ICapLevel
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.SwordItem
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.registries.ForgeRegistries

object PyroTierGear : KilnSubmod("tiergear") {

    val Enchantment.compatibleItems: List<Item>
        get() = ForgeRegistries.ITEMS.values.toList().filter { type?.canEnchantItem(it) == true }

    val EnchantmentType.compatibleItems: List<Item>
        get() = ForgeRegistries.ITEMS.values.toList().filter { canEnchantItem(it) }

    val Item.compatibleEnchantments: List<Enchantment>
        get() = ForgeRegistries.ENCHANTMENTS.values.toList().filter { it.type?.canEnchantItem(this) == true }

    @SubscribeEvent
    fun onServerStarting(event: FMLServerStartingEvent) {
        TierGearCommand.generate(event.commandDispatcher)
    }

    fun genEnchants(player: ServerPlayerEntity, level: Int) {

        var levelsToGive = level




    }

    @SubscribeEvent
    fun onAttach(event: AttachCapabilitiesEvent<ItemStack>) {
        println("Attempting attach to itemstack")

        if (event.`object`.item is SwordItem) {
            println("Attaching..")
            event.addCapability(PyroTierGear.locate("level_cap"), ICapLevel.LevelProvider())
            println("Attached.")
        }

    }

    @SubscribeEvent
    fun onCommon(event: FMLCommonSetupEvent) {
        println("PyroTierGear onCommon")

        /*
        val enchant = ForgeRegistries.ENCHANTMENTS.values.toList().first()

        println("Grabbed enchantment: ${enchant.name}")

        val items = ForgeRegistries.ITEMS.values.toList()

        for (item in items) {
            val canEnchant = enchant.type!!.canEnchantItem(item)
            println("Can ${item.registryName} be enchanted with this?: $canEnchant")
        }
         */

        CapabilityManager.INSTANCE.register(
                ICapLevel::class.java,
                ICapLevel.Storage()
        ) {
            CapLevel()
        }





    }

}