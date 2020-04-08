package ejektaflex.kiln.network

import ejektaflex.kiln.Kiln
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import java.util.function.Supplier
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@Suppress("INACCESSIBLE_TYPE")
open class KilnChannel(var protocolVersion: String = "0.0.1") {

    private var msgId: Int = 0

    val channel = NetworkRegistry.ChannelBuilder
            .named(Kiln.locate("main"))
            .networkProtocolVersion { protocolVersion }
            .clientAcceptedVersions { isAcceptedVersion(false, it) }
            .serverAcceptedVersions { isAcceptedVersion(true, it) }
            .simpleChannel()

    /*
        Override to determine which versions are acceptable for packet transmissions
     */
    open fun isAcceptedVersion(isServer: Boolean, string: String) = true

    /**
     * Used to register a packet with a separate handler.
     */
    fun <D : IKilnPacketData, H: IKilnPacketHandler> registerPacket(dataClazz: KClass<D>, handler: H) {
        channel.registerMessage(
                msgId++,
                dataClazz.java,
                { msg: D, buff: PacketBuffer -> msg.encode(buff) },
                { buff: PacketBuffer -> dataClazz.createInstance().apply { decode(buff) }},
                { _: D, ctx: Supplier<NetworkEvent.Context> -> handler.handle(ctx) }
        )
    }

    /**
     * Used to register a packet with it's own handler.
     */
    fun <M : IKilnMessage> registerPacket(dataClazz: KClass<M>) {
        channel.registerMessage(
                msgId++,
                dataClazz.java,
                { msg: M, buff: PacketBuffer -> msg.encode(buff) },
                { buff: PacketBuffer -> dataClazz.createInstance().apply { decode(buff) }},
                { msg: M, ctx: Supplier<NetworkEvent.Context> -> msg.handle(ctx) }
        )
    }

}