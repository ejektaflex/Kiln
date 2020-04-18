package ejektaflex.kiln.client.renderer

import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class KilnModelRenderer(model: Model, val name: String) : ModelRenderer(model) {
    var xOff: Double = 0.0
    var yOff: Double = 0.0
    var zOff: Double = 0.0
}