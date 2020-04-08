package ejektaflex.pyrolysis.animator.client.model

import ejektaflex.pyrolysis.Pyrolysis
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import ejektaflex.kiln.client.model.KilnSegmentedModel

class SpoopyModel : KilnSegmentedModel<SpoopyEntity>(Pyrolysis.locate("spoop.json")) {

    override fun setRotationAngles(entityIn: SpoopyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        renderMap["right_loog"]!!.apply {
            rotateAngleX = Math.toRadians(ageInTicks * 5.0 % 360.0).toFloat()
        }

    }

}