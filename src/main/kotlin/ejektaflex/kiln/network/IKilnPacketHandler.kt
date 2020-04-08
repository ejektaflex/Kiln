package ejektaflex.kiln.network

import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

interface IKilnPacketHandler {

    /**
     * The default method that gets executed when the packet is handled.
     */
    fun execute()

    /**
     * Handles the given packet message. By default, calls [execute] on the main thread.
     */
    fun handle(ctx: Supplier<NetworkEvent.Context>) {
        ctx.get().apply {
            enqueueWork {
                execute()
            }
            packetHandled = true
        }
    }

}