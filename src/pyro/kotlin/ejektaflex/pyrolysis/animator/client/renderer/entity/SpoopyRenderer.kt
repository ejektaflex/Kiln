package ejektaflex.pyrolysis.animator.client.renderer.entity

import ejektaflex.pyrolysis.animator.PyroAnimator
import ejektaflex.pyrolysis.animator.client.model.SpoopyModel
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class SpoopyRenderer(renderManagerIn: EntityRendererManager) : MobRenderer<SpoopyEntity, SpoopyModel>(renderManagerIn, SpoopyModel(), 0.5f) {

    override fun getEntityTexture(entity: SpoopyEntity): ResourceLocation {
        return TEXTURES
    }

    companion object {
        private val TEXTURES = PyroAnimator.locate("texture.png")
    }
}