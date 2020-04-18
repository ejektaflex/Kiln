package ejektaflex.kiln.mod

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ejektaflex.kiln.json.JsonAdapter
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import java.util.logging.Logger
import kotlin.reflect.KClass

@Suppress("PropertyName", "LeakingThis")
abstract class KilnAbstractMod {

    abstract val ID: String

    val LOGGER: Logger by lazy {
        Logger.getLogger(ID)
    }


    fun locate(place: String) = ResourceLocation(ID, place)

    // Client side asset loading
    fun loadAsset(res: ResourceLocation): String {
        return Minecraft.getInstance().resourceManager.getResource(res).inputStream.reader().readText()
    }

    // Client side asset loading
    fun loadAsset(path: String): String = loadAsset(locate(path))

    // Client side asset loading
    fun <T : Any> loadAsset(path: ResourceLocation, klazz: KClass<T>): T {
        return JsonAdapter.gson.fromJson(loadAsset(path), klazz.java)
    }

    // Client side asset loading
    fun <T : Any> loadAsset(path: String, klazz: KClass<T>): T {
        return JsonAdapter.gson.fromJson(loadAsset(path), klazz.java)
    }

    @PublishedApi
    internal val `access$basicGson`: Gson?
        get() = JsonAdapter.gson

    inline fun <reified T : Any> loadAsset(path: String): T {
        return `access$basicGson`!!.fromJson(loadAsset(path), T::class.java)
    }

    inline fun <reified T : Any> loadAsset(path: ResourceLocation): T {
        return `access$basicGson`!!.fromJson(loadAsset(path), T::class.java)
    }


}