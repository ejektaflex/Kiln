package ejektaflex.kiln.ext

import ejektaflex.kiln.Kiln
import ejektaflex.kiln.nbt.KilnCheckableData

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fml.ModList
import net.minecraftforge.registries.ForgeRegistries

val ItemStack.toNBT: CompoundNBT
    get() {
        return CompoundNBT().apply {
            putString("e_item", toPretty)
            putInt("e_amt", count)
            put("e_nbt", serializeNBT())
        }
    }

val CompoundNBT.toItemStack: ItemStack?
    get() {
        val istack = getString("e_item").toItemStack
        return istack?.apply {
            count = getInt("e_amt")
            tag = get("e_nbt") as CompoundNBT
        }
    }

val String.toItemStack: ItemStack?
    get() {
        val sect = split(":").toMutableList()
        val item = ForgeRegistries.ITEMS.getValue(ResourceLocation("${sect[0]}:${sect[1]}"))
        //val item = Item.getByNameOrId("${sect[0]}:${sect[1]}")
        return if (item != null) {
            ItemStack(item, 1)
        } else {
            null
        }
    }

val ItemStack.toPretty: String
    get() {
        var proto = item.registryName.toString()

        return proto
    }

inline fun <reified T : Item> ItemStack.edit(func: T.(stack: ItemStack) -> Unit) {
    if (item is T) {
        func(item as T, this)
    } else {
        throw Exception("Tried to edit stack ${stack.count}x ${stack.item.registryName} as if it was a ${T::class}")
    }
}

/*
inline fun <reified T : INBTSerializable<CompoundNBT>> ItemStack.editNbt(func: T.() -> Unit) {
    if ()
}

 */

inline fun <reified T : KilnCheckableData> ItemStack.toData(func: () -> T): T {
    return func().apply {
        if (stack.hasTag()) {
            if (stack.tag!!.keySet().containsAll(getCheckableKeys())) {
                deserializeNBT(stack.tag)
            } else {
                stack.tag!!.merge(serializeNBT())
            }
        } else {
            stack.tag = serializeNBT()
        }
    }
}

inline fun <reified T : INBTSerializable<CompoundNBT>> ItemStack.toSafeData(func: () -> T): T {
    return func().apply {
        deserializeNBT(stack.tag)
    }
}

inline fun <reified T : INBTSerializable<CompoundNBT>> ItemStack.toDataOrNull(func: () -> T): T? {
    return try {
        func().apply {
            deserializeNBT(stack.tag)
        }
    } catch (e: Exception) {
        Kiln.LOGGER.severe(e.message)
        null
    }
}

val ItemStack.modOriginName: String?
    get() {
        val modid = item.registryName?.namespace
        return if (modid != null) {
            ModList.get().mods.find { it.modId == modid }?.displayName
        } else {
            null
        }
    }
