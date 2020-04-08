package ejektaflex.kiln

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object KilnRegistration {

    inline fun <reified E : Entity> createEntityType(
            loc: ResourceLocation,
            classification: EntityClassification,
            noinline entity: (t: EntityType<out E>, w: World) -> E
    ): EntityType<E> {
        return EntityType.Builder.create(entity, classification)
                .setTrackingRange(32)
                .build(loc.toString()).apply {
                    setRegistryName(loc)
        }
    }

}