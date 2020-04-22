package ejektaflex.kiln

import com.google.gson.JsonDeserializer
import ejektaflex.kilnx.anim.Keyframe
import ejektaflex.kiln.json.JsonAdapter
import ejektaflex.kiln.mod.KilnMod
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod("kiln")
object Kiln : KilnMod() {

    private val animationDeserializer: JsonDeserializer<Keyframe> = JsonDeserializer { json, typeOfT, context ->
        val arr = json.asJsonArray
        Keyframe(
                arr[0].asFloat,
                listOf(arr[1].asFloat, arr[2].asFloat, arr[3].asFloat)
        )
    }

    init {
        println("My mod name is: $ID")
        JsonAdapter.register(animationDeserializer)
    }



}

