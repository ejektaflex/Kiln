package ejektaflex.kiln.client.model

import com.google.gson.annotations.SerializedName
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.vertex.IVertexBuilder
import ejektaflex.kiln.Kiln
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.SegmentedModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn


@OnlyIn(Dist.CLIENT)
open class KilnSegmentedModel<T : LivingEntity>(location: ResourceLocation) : EntityModel<T>() {


    val data: ModelData = try {
        Kiln.loadAsset(location, ModelData::class)
    } catch (e: Exception) {
        throw Exception("Could not load model asset from '$location'")
    }

    val renderMap = mutableMapOf<String, ModelRenderer>()

    @Suppress("LeakingThis")
    val renderer = data.createRenderer(this)

    /*
    override fun getParts(): MutableIterable<ModelRenderer> {
        return renderMap.values
    }

     */

    override fun render(matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int, packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        //GlStateManager.pushMatrix()
        for (model in renderMap.values) {
            model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        }
        //GlStateManager.popMatrix()
    }


    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        // default no rotation setting
    }


    class ModelData {
        var type = "UNDEFINED"
        var scale = 1.0
        @SerializedName("texture_width")
        var texWidth = 16
        @SerializedName("texture_height")
        var texHeight = 16
        var cubes = mutableListOf<ModelCube>()

        val rootCube: ModelCube
            get() = cubes.first()

        fun createRenderer(model: KilnSegmentedModel<*>): ModelRenderer {
            val renderer = ModelRenderer(model)
            addRenderer(model, renderer, rootCube)
            return renderer
        }



        private fun addRenderer(model: KilnSegmentedModel<*>, parent: ModelRenderer, cube: ModelCube) {

            val currRender = ModelRenderer(model)
            println("Setting cube rotpoint of '${cube.name} to ${cube.rotPoint}")
            currRender.apply {
                mirror = cube.mirror
                rotationPointX = cube.rotPoint[0]
                rotationPointY = cube.rotPoint[1]
                rotationPointZ = cube.rotPoint[2]
                rotateAngleX = Math.toRadians(cube.rotation[0]).toFloat()
                rotateAngleY = Math.toRadians(cube.rotation[1]).toFloat()
                rotateAngleZ = Math.toRadians(cube.rotation[2]).toFloat()
            }

            model.renderMap[cube.name] = currRender
            parent.addChild(currRender)

            if (cube.isFolder) {

                for (subCube in cube.children) {
                    addRenderer(model, currRender, subCube)
                }

            } else {

                println("Adding cube at ${cube.offset} with size of ${cube.size} for renderer ${cube.name}")

                parent.addBox(
                        cube.offset[0],
                        cube.offset[1],
                        cube.offset[2],
                        cube.size[0],
                        cube.size[1],
                        cube.size[2]
                )
            }

        }

    }

    class ModelCube {
        var name = "UNDEFINED_CUBE"
        var scale = 1f
        var mirror = false
        @SerializedName("tex_off")
        var texOffset = mutableListOf<Int>()
        @SerializedName("off")
        var offset = mutableListOf<Float>()
        @SerializedName("rot_point")
        var rotPoint = mutableListOf<Float>()
        var size = mutableListOf<Float>()
        @SerializedName("rot")
        var rotation = mutableListOf<Double>()
        var children = mutableListOf<ModelCube>()

        val isFolder: Boolean
            get() = children.isNotEmpty()

    }

}