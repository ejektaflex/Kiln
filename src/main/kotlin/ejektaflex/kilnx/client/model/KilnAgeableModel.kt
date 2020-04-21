package ejektaflex.kilnx.client.model

import net.minecraft.client.renderer.entity.model.AgeableModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
open class KilnAgeableModel<T : LivingEntity> : AgeableModel<T>() {

    private val bodyParts = mutableListOf<ModelRenderer>()
    private val headParts = mutableListOf<ModelRenderer>()

    private val boneReg = mutableMapOf<String, BoneGroup>()

    enum class BoneGroupType {
        HEAD,
        BODY
    }

    inner class BoneGroup(name: String) {

        var renderer = ModelRenderer(this@KilnAgeableModel)

        fun addGroup(func: BoneGroup.() -> Unit) {
            func(this)
        }

        fun edit(func: ModelRenderer.() -> Unit) {
            func(renderer)
        }

    }

    fun createSkeleton(func: BoneGroup.() -> Unit) {

    }

    inner class Part()

    protected fun addGroup(boneName: String, boneGroupType: BoneGroupType, texX: Int, texY: Int, func: ModelRenderer.() -> Unit): ModelRenderer {
        val renderer = ModelRenderer(this, texX, texY)

        func(renderer)

        when (boneGroupType) {
            BoneGroupType.BODY -> bodyParts.add(renderer)
            BoneGroupType.HEAD -> headParts.add(renderer)
        }
        
        return renderer
    }

    override fun getHeadParts() = headParts.toList()

    override fun getBodyParts() = bodyParts.toList()

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        // default no rotation setting
    }

}