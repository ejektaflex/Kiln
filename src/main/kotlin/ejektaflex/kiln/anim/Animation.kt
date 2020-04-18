package ejektaflex.pyrolysis.animator.client.anim

import com.google.gson.JsonDeserializer
import net.minecraft.util.ResourceLocation
import java.io.File

class Animation(val location: ResourceLocation) {

    import com.google.gson.GsonBuilder
    import com.google.gson.JsonArray
    import com.google.gson.JsonDeserializer
    import com.google.gson.JsonObject
    import com.google.gson.annotations.SerializedName
    import java.io.File
    import java.lang.Exception
    import java.util.function.Supplier

    class Car

/*
// Java SAM in CarMakerFactory.java
public interface CarMakerFactory {
    public Car create();
}
 */

    fun main() {

        val deserializer: JsonDeserializer<AnimKeyframe> = JsonDeserializer { json, typeOfT, context ->
            val arr = json.asJsonArray
            AnimKeyframe(
                    arr[0].asFloat,
                    listOf(arr[1].asFloat, arr[2].asFloat, arr[3].asFloat)
            )
        }

        JsonAdapter.register(deserializer)

        val text = File("spoop.anim.json").readText()

        val data = JsonAdapter.fromJsonExp(text, AnimFile::class)



        val doot = data.animations.first().bones.first()
        println(doot)

    }

    data class AnimFile(var animations: MutableList<AnimData> = mutableListOf()) {

    }

    data class AnimData(
            var name: String = "NO_ANIM_NAME",
            var bones: MutableList<AnimBone> = mutableListOf()
    )

    data class AnimBone(
            var rot: List<AnimKeyframe>,
            var pos: List<AnimKeyframe>,
            var name: String
    )


    data class AnimKeyframe(val time: Float, val nums: List<Float>)


}