package ejektaflex.kiln.mod

import com.google.gson.GsonBuilder
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

    protected val basicGson = GsonBuilder()
            .setPrettyPrinting()
            .create()

    fun locate(place: String) = ResourceLocation(ID, place)

    // Client side asset loading
    fun loadAsset(res: ResourceLocation): String {
        return Minecraft.getInstance().resourceManager.getResource(res).inputStream.reader().readText()
    }

    // Client side asset loading
    fun loadAsset(path: String): String = loadAsset(locate(path))

    // Client side asset loading
    fun <T : Any> loadAsset(path: ResourceLocation, klazz: KClass<T>): T {
        return basicGson.fromJson(loadAsset(path), klazz.java)
    }

    // Client side asset loading
    fun <T : Any> loadAsset(path: String, klazz: KClass<T>): T {
        return basicGson.fromJson(loadAsset(path), klazz.java)
    }

}