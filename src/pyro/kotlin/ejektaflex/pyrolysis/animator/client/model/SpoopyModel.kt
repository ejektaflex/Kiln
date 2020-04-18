package ejektaflex.pyrolysis.animator.client.model

import ejektaflex.kiln.anim.Animation
import ejektaflex.pyrolysis.Pyrolysis
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import ejektaflex.kiln.client.model.KilnSegmentedModel
import ejektaflex.pyrolysis.animator.PyroAnimator
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*

@OnlyIn(Dist.CLIENT)
class SpoopyModel : KilnSegmentedModel<SpoopyEntity>(PyroAnimator.locate("spoop.json")) {

    init {
        println("HMMM")
        animation = Animation(PyroAnimator.locate("spoop.anim.json"))
        println(animation!!.data)
    }

    override fun setRotationAngles(entityIn: SpoopyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {

        animation?.let {
            for (model in renderMap.values) {
                it.animate(model, ageInTicks)
            }
        }

        /*
        renderMap["leg"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

        renderMap["leg2"]!!.apply {
            rotateAngleZ = Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()

        }

        renderMap["leg3"]!!.apply {
            rotateAngleZ = -Math.toRadians(ageInTicks * 2.0 % 360.0).toFloat()
        }

        renderMap["loose_boxes"]!!.apply {
            xOff = (ageInTicks / 5.0) % 5.0
        }
         */

    }

}