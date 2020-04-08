package ejektaflex.pyrolysis.animator.entity

import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.Pose
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.monster.MonsterEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

class SpoopyEntity(type: EntityType<out SpoopyEntity>, world: World) : MonsterEntity(type, world) {

    override fun registerAttributes() {
        super.registerAttributes()
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 8.0
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.16
    }

    override fun getExperiencePoints(player: PlayerEntity) = 10

    override fun getStandingEyeHeight(poseIn: Pose, sizeIn: EntitySize): Float {
        return 0.9f
    }

}