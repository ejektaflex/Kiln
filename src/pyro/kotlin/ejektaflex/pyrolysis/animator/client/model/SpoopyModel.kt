package ejektaflex.pyrolysis.animator.client.model

import ejektaflex.pyrolysis.Pyrolysis
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import ejektaflex.kiln.client.model.KilnSegmentedModel
import ejektaflex.pyrolysis.animator.PyroAnimator
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class SpoopyModel : KilnSegmentedModel<SpoopyEntity>(PyroAnimator.locate("spoop.json")) {

    init {

        val head = ModelRenderer(this)

        //head.addBox()


    }

    override fun setRotationAngles(entityIn: SpoopyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        renderMap["nose_bone"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

        renderMap["drip_bone"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

    }

}