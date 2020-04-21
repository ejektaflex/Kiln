package ejektaflex.pyrolysis.animator.client.model

import ejektaflex.kilnx.anim.Animation
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import ejektaflex.kilnx.client.model.KilnSegmentedModel
import ejektaflex.pyrolysis.animator.PyroAnimator
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class SpoopyModel : KilnSegmentedModel<SpoopyEntity>(PyroAnimator.locate("spoop.json")) {

    init {
        animation = Animation(PyroAnimator.locate("spoop.anim.json"))
        println(animation!!.data)
    }

    override fun setRotationAngles(entityIn: SpoopyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        animation?.let {
            for (model in renderMap.values) {
                it.animate(model, ageInTicks)
            }
        }
    }

}