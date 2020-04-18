package ejektaflex.kiln.client.model

import com.google.gson.annotations.SerializedName
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.vertex.IVertexBuilder
import ejektaflex.kiln.Kiln
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*


@OnlyIn(Dist.CLIENT)
open class KilnSegmentedModel<T : LivingEntity>(location: ResourceLocation) : EntityModel<T>() {

    val data: ModelGeometryFile = try {
        Kiln.loadAsset<ModelGeometryFile>(location)
    } catch (e: Exception) {
        throw Exception("Could not load model asset from '$location'")
    }

    val renderMap = mutableMapOf<String, ModelRenderer>()

    init {
        createGeometry()
    }

    private fun parseModel(model: BoneModel): ModelRenderer {
        println("Setting renderer for ${model.name}")
        val renderer = ModelRenderer(this)

        renderer.setRotationPoint(
                model.pivot[0],
                model.pivot[1],
                model.pivot[2]
        )

        renderer.apply {
            rotateAngleX = Math.toRadians(model.angle[0]).toFloat()
            rotateAngleY = Math.toRadians(model.angle[1]).toFloat()
            rotateAngleZ = Math.toRadians(model.angle[2]).toFloat()
        }

        for (box in model.boxes) {
            println("Adding bone to ${model.name} with name ${box.name}")
            renderer.addBox(
                    box.pos[0],
                    box.pos[1],
                    box.pos[2],
                    box.size[0],
                    box.size[1],
                    box.size[2],
                    0f,
                    box.uv[0].toFloat(),
                    box.uv[1].toFloat()
            )
        }

        for (subModel in model.subModels) {
            val newModel = parseModel(subModel)
            renderer.addChild(newModel)
        }

        renderMap[model.name] = renderer
        return renderer
    }


    private fun createGeometry() {
        for (model in data.models) {
            println("This is a model! ${model.name}")
            parseModel(model)
        }
    }

    override fun render(matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int, packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        GlStateManager.pushMatrix()
        for (model in renderMap.values) {
            model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        }
        GlStateManager.popMatrix()
    }

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        // default no rotation setting
    }

    class ModelGeometryFile {
        var scale = 1f
        @SerializedName("texture_width")
        var textureWidth: Int = 1
        @SerializedName("texture_height")
        var textureHeight: Int = 1
        var models: MutableList<BoneModel> = mutableListOf()
    }

    class BoneModel {
        var name = "NO_MODEL_NAME"
        var pivot = mutableListOf<Float>()
        var angle = mutableListOf<Double>()
        var boxes = mutableListOf<BoneBox>()
        @SerializedName("submodels")
        var subModels = mutableListOf<BoneModel>()
    }

    class BoneBox {
        var name = "NO_BOX_NAME"
        var uv = mutableListOf<Int>()
        var pos = mutableListOf<Float>()
        var size = mutableListOf<Float>()
    }

}