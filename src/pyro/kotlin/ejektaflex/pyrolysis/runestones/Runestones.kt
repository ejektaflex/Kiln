package ejektaflex.pyrolysis.runestones

import com.mojang.blaze3d.platform.GlStateManager
import ejektaflex.kiln.ext.edit
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.runestones.client.RuneRenderer
import ejektaflex.pyrolysis.runestones.item.Runestone
import ejektaflex.pyrolysis.runestones.item.runes.HealRune
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.lwjgl.opengl.GL11
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
        //Minecraft.getInstance().itemColors.register(healing, healing)



    }

    @SubscribeEvent
    fun onOverlay(event: RenderGameOverlayEvent.Post) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            //RuneRenderer.render()
        }
    }

    private fun drawHighlight(slot: Int, useable: Boolean) {
        GlStateManager.pushMatrix()
        GlStateManager.enableBlend()
        GlStateManager.depthMask(false)
        Minecraft.getInstance().textureManager.bindTexture(ResourceLocation("textures/item/apple"))
        GlStateManager.depthMask(true)
        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }

    @SubscribeEvent
    fun regModel(event: ModelRegistryEvent) {
        println("Registering healing")
        ModelLoader.addSpecialModel(locate("item/runestone"))
    }

    @SubscribeEvent
    fun onBake(event: ModelBakeEvent) {
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