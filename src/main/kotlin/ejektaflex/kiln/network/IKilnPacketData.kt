package ejektaflex.kiln.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

interface IKilnPacketData {

    fun encode(buff: PacketBuffer)

    fun decode(buff: PacketBuffer)

}