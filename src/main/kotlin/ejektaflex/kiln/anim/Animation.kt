package ejektaflex.kiln.anim

import ejektaflex.kiln.Kiln
import ejektaflex.kiln.client.model.KilnSegmentedModel
import ejektaflex.kiln.client.renderer.KilnModelRenderer
import ejektaflex.kiln.json.JsonAdapter
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import java.io.File
import kotlin.math.max

class Animation(location: ResourceLocation) {

    val file: AnimFile = try {
        Kiln.loadAsset(location, AnimFile::class)
    } catch (e: Exception) {
        throw Exception("Could not load animation asset from '$location'")
    }


    val data = file.also {
        println("Anim is $it")
    }.animations.first()

    val boneMap = data.bones.map { it.name to it }.toMap()

    private fun lerp(a: Float, b: Float, n: Float): Float {
        return (1 - n) * a + n * b
    }

    private fun angle(n: Float): Float {
        return Math.toRadians(n.toDouble()).toFloat()
    }

    //lerp(10, 20, 0.5) => 15
    //lerp(10, 5, 0.5) => 7.5

    fun animate(rend: KilnModelRenderer, ticks: Float) {
        val bone = boneMap[rend.name] ?: return
        val frameLoc = (ticks / 20) % data.length
        //println("Frameloc $frameLoc on bone ${bone.name}")

        val currIndex = bone.rot.indexOfLast { frameLoc >= it.time }



        // Given times.. 0.8 / 1.48 / 3 / 4 and AnimTime of 4.52
        // at FL 0, I = -1 (none exist with 0 >= the value)
        // at FL 1, I = 0 (val 0)
        // at FL 2, I = 1 (val 1.48)
        // at FL 4.25, I = 3 (lastIndex, 4)

        //println("Curr index $currIndex")

        var ref = rend.animRef

        when (currIndex) {
            -1 -> {
                // no keyframes have been passed
                rend.rotateAngleZ = ref.zAngle + angle(bone.rot.first().nums[2])
            }
            bone.rot.lastIndex -> {
                // all keyframes have been passed
                rend.rotateAngleZ = ref.zAngle + angle(bone.rot.last().nums[2])
            }
            else -> {
                // we have a valid keyframe that is not the last
                val curr = bone.rot[currIndex]
                val next = bone.rot[currIndex+1]
                val lerpPos = (frameLoc - curr.time) / next.time
                println("LerpPos $lerpPos")
                rend.rotateAngleZ = lerp(
                        ref.zAngle + angle(curr.nums[2]),
                        ref.zAngle + angle(next.nums[2]),
                        lerpPos.toFloat()
                )
            }
        }


    }


}