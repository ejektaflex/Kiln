package ejektaflex.pyrolysis.runestones.model

import com.mojang.blaze3d.matrix.MatrixStack
import ejektaflex.pyrolysis.runestones.Runestones
import ejektaflex.pyrolysis.runestones.item.Runestone.Companion.runeData
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.model.ModelManager
import net.minecraft.client.renderer.model.ModelResourceLocation
import net.minecraft.client.renderer.model.SimpleBakedModel
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.model.ModelLoaderRegistry
import java.util.*

class RuneRenderer : ItemStackTileEntityRenderer() {


    override fun render(itemStackIn: ItemStack, matrixStackIn: MatrixStack, bufferIn: IRenderTypeBuffer, combinedLightIn: Int, combinedOverlayIn: Int) {

        //val baked = Minecraft.getInstance().modelManager.getModel(ModelResourceLocation(ResourceLocation("diamond_sword"), "inventory"))
        //val bake2 = baked

        val bake2 = Minecraft.getInstance().modelManager.getModel(Runestones.locate("item/runestone"))

        val r = Random(42L)

        val itemRenderer = Minecraft.getInstance().itemRenderer

        val buff = ItemRenderer.getBuffer(bufferIn, RenderTypeLookup.getRenderType(itemStackIn), true, itemStackIn.hasEffect())

        //println(quads.size)

        //println(quads.size)
        val dirs = listOf(Direction.UP, Direction.EAST, Direction.NORTH, Direction.DOWN, Direction.SOUTH, Direction.WEST)

        //ModelLoaderRegistry.registerLoader()

        for (dir in dirs) {
            val quads = bake2.getQuads(null, dir, r)
            itemRenderer.renderQuads(matrixStackIn, buff, quads, itemStackIn, combinedLightIn, combinedOverlayIn)
        }

        itemRenderer.renderQuads(matrixStackIn, buff, bake2.getQuads(null, null, r), itemStackIn, combinedLightIn, combinedOverlayIn)


        Items.TORCH.itemStackTileEntityRenderer.render(
                itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn
        )



        2
    }

}