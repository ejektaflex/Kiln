package ejektaflex.pyrolysis.animator

import ejektaflex.kiln.KilnRegistration
import ejektaflex.kiln.ext.invoke
import ejektaflex.kiln.mod.KilnSubmod
import ejektaflex.pyrolysis.Pyrolysis
import ejektaflex.pyrolysis.animator.client.model.SpoopyModel
import ejektaflex.pyrolysis.animator.client.renderer.entity.SpoopyRenderer
import ejektaflex.pyrolysis.animator.entity.SpoopyEntity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

object PyroAnimator : KilnSubmod("pyroanimator") {

    @SubscribeEvent
    fun onDoot(event: FMLCommonSetupEvent) {
        println("Is active?:  yusss")
    }

    val type = KilnRegistration.createEntityType(
            Pyrolysis.locate("the_spooper"),
            EntityClassification.MONSTER,
            ::SpoopyEntity
    )

    @SubscribeEvent
    fun onClient(event: FMLClientSetupEvent) {
        println("Spoopy client")

        val model = SpoopyModel()

        println(model.renderMap.keys)

        RenderingRegistry.registerEntityRenderingHandler(type, ::SpoopyRenderer)

        //throw Exception("Time to crash; We don't need to load whole game yet anyway")
    }

    @SubscribeEvent
    fun registerEntity(event: RegistryEvent.Register<EntityType<*>>) {
        event.registry.register(type)
        event.registry.entries.forEach {
            println("Entity type: ${it.value.registryName} ${it.value.registryType}")
        }
    }

}