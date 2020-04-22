package ejektaflex.kiln.nbt

import ejektaflex.kiln.ext.toData
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class KilnNBT<D : KilnCheckableData>(val item: Item, val func: () -> D) : ReadWriteProperty<ItemStack, D> {
    override fun getValue(thisRef: ItemStack, property: KProperty<*>): D {
        return thisRef.toData(func)
    }

    override fun setValue(thisRef: ItemStack, property: KProperty<*>, value: D) {
        if (thisRef.item == item) {
            if (thisRef.hasTag()) {
                thisRef.tag!!.merge(value.serializeNBT())
            } else {
                thisRef.tag = value.serializeNBT()
            }
        } else {
            throw Exception("Cannot set value of NBT binding ${property.name} to $value, as item is not a ${thisRef.item.name}")
        }
    }
}