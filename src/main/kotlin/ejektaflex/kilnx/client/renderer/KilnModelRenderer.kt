package ejektaflex.kilnx.client.renderer

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class KilnModelRenderer(model: Model, val name: String) : ModelRenderer(model) {
    var xOff: Double = 0.0
    var yOff: Double = 0.0
    var zOff: Double = 0.0

    var animRef = AnimRef()

    override fun render(matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int, packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        if (showModel) {
            if (cubeList.isNotEmpty() || childModels.isNotEmpty()) {
                matrixStackIn.push()

                // Added position translation
                matrixStackIn.translate(xOff, yOff, zOff)

                translateRotate(matrixStackIn)

                doRender(matrixStackIn.last, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)

                for (model in childModels) {
                    model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
                }

                matrixStackIn.pop()
            }

        }
    }


    inner class AnimRef {
        var xPos = 0.0
        var yPos = 0.0
        var zPos = 0.0
        var xAngle = 0f
        var yAngle = 0f
        var zAngle = 0f

        fun save() {
            xPos = xOff
            yPos = yOff
            zPos = zOff
            xAngle = rotateAngleX
            yAngle = rotateAngleY
            zAngle = rotateAngleZ
        }
    }

}