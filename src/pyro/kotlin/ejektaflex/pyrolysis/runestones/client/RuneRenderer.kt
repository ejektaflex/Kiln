package ejektaflex.pyrolysis.runestones.client

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import ejektaflex.pyrolysis.runestones.Runestones
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import org.lwjgl.opengl.GL11

object RuneRenderer {

    val mc = Minecraft.getInstance()

    val blank = Runestones.locate("textures/item/rune-base.png")

    var xScale = 1f
    var yScale = 1f

    var b1 = 0
    var b2 = 0
    var b3 = 256
    var b4 = 256

    fun render() {

        val gui = mc.ingameGUI

        mc.renderManager.textureManager.bindTexture(blank)

        var x: Int = mc.mainWindow.scaledWidth / 2
        var y: Int = mc.mainWindow.scaledHeight / 2

        //RenderSystem.enableRescaleNormal()
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.enableAlphaTest()
        RenderSystem.defaultAlphaFunc()

        GlStateManager.color4f(1f, 1f, 1f, 1f)

        mc.ingameGUI.blit(x, y, b1, b2, b3, b4)



        mc.ingameGUI.drawCenteredString(
                mc.fontRenderer, "HELLO, WORLD! :D",
                (x * xScale).toInt(), (y * yScale).toInt(), 0x170e0b)




    }
}