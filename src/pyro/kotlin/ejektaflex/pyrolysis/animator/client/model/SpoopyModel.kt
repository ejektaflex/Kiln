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

    override fun setRotationAngles(entityIn: SpoopyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        renderMap["leg"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

        renderMap["leg2"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

        renderMap["leg3"]!!.apply {
            rotateAngleZ = -Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

    }

}