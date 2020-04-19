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

    fun animate(rend: KilnModelRenderer, ticks: Float) {
        val bone = boneMap[rend.name] ?: return

        // Actual frame time in seconds that we are through the entire animation
        val frameLoc = (ticks / 20) % data.length

        animateRot(rend, bone, frameLoc)
        animatePos(rend, bone, frameLoc)
    }

    private fun animateRot(rend: KilnModelRenderer, bone: AnimBone, frameLoc: Double, frames: List<Keyframe> = bone.rot) {
        val currIndex = frames.indexOfLast { frameLoc >= it.time }

        val ref = rend.animRef

        when (currIndex) {
            -1 -> {
                // no keyframes have been passed
                if (frames.isNotEmpty()) { // No keyframes necessarily exist
                    rend.rotateAngleX = ref.xAngle + angle(frames.first().nums[0])
                    rend.rotateAngleY = ref.yAngle + angle(frames.first().nums[1])
                    rend.rotateAngleZ = ref.zAngle + angle(frames.first().nums[2])
                }
            }
            frames.lastIndex -> {
                // all keyframes have been passed
                rend.rotateAngleX = ref.xAngle + angle(frames.last().nums[0])
                rend.rotateAngleY = ref.yAngle + angle(frames.last().nums[1])
                rend.rotateAngleZ = ref.zAngle + angle(frames.last().nums[2])
            }
            else -> {
                // we have a valid keyframe that is not the last
                val curr = frames[currIndex]
                val next = frames[currIndex+1]

                // Avoid divide-by-zero for instances where frames have same frame time
                if (curr.time == next.time) { return }

                // Lerp % of way through current keyframe
                val lerpPos = (frameLoc - curr.time) / (next.time - curr.time)

                rend.rotateAngleX = lerp(ref.xAngle + angle(curr.nums[0]), ref.xAngle + angle(next.nums[0]), lerpPos.toFloat())
                rend.rotateAngleY = lerp(ref.yAngle + angle(curr.nums[1]), ref.yAngle + angle(next.nums[1]), lerpPos.toFloat())
                rend.rotateAngleZ = lerp(ref.zAngle + angle(curr.nums[2]), ref.zAngle + angle(next.nums[2]), lerpPos.toFloat())
            }
        }
    }

    private fun animatePos(rend: KilnModelRenderer, bone: AnimBone, frameLoc: Double, frames: List<Keyframe> = bone.pos) {
        val currIndex = frames.indexOfLast { frameLoc >= it.time }

        val ref = rend.animRef

        when (currIndex) {
            -1 -> {
                // no keyframes have been passed
                if (frames.isNotEmpty()) { // No keyframes necessarily exist
                    rend.xOff = ref.xPos + frames.first().nums[0]
                    rend.yOff = ref.yPos + frames.first().nums[1]
                    rend.zOff = ref.zPos + frames.first().nums[2]
                }
            }
            frames.lastIndex -> {
                // all keyframes have been passed
                rend.xOff = ref.xPos + frames.last().nums[0]
                rend.yOff = ref.yPos + frames.last().nums[1]
                rend.zOff = ref.zPos + frames.last().nums[2]
            }
            else -> {
                // we have a valid keyframe that is not the last
                val curr = frames[currIndex]
                val next = frames[currIndex+1]

                // Avoid divide-by-zero for instances where frames have same frame time
                if (curr.time == next.time) { return }

                // Lerp % of way through current keyframe
                val lerpPos = (frameLoc - curr.time) / (next.time - curr.time)

                rend.xOff = lerp(curr.nums[0], next.nums[0], lerpPos.toFloat()).toDouble()
                rend.yOff = lerp(curr.nums[1], next.nums[1], lerpPos.toFloat()).toDouble()
                rend.zOff = lerp(curr.nums[2], next.nums[2], lerpPos.toFloat()).toDouble()
            }
        }
    }


}