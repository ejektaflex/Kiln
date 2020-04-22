package ejektaflex.pyrolysis.runestones.item

import ejektaflex.kiln.nbt.KilnNBT
import ejektaflex.pyrolysis.runestones.Runestones
import ejektaflex.pyrolysis.runestones.data.RuneData
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

abstract class Runestone(val runeId: String) : Item(
        Properties().defaultMaxDamage(100)
) {

    init {
        registryName = Runestones.locate(runeId)
    }

    val descriptionKey: String
        get() = "item.${Runestones.ID}.$runeId.desc"

    abstract fun onRuneUsed(world: World, player: PlayerEntity, level: Int, chargesUsed: Int)

    private var ItemStack.runeData: RuneData by KilnNBT(this.asItem(), ::RuneData)

    // Automagically utilizing setters and getters
    private fun ItemStack.initRuneData(func: RuneData.() -> Unit = {}) {
        stack.runeData = stack.runeData.apply(func)
    }

    fun addCharge(stack: ItemStack, chargeAmount: Int) {
        stack.initRuneData {
            addCharge(chargeAmount)
            setDamage(stack, currCharge)
        }
    }

    override fun getMaxDamage(stack: ItemStack): Int {
        val data = stack.runeData
        return data.maxCharge
    }

    @OnlyIn(Dist.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        val data = stack.runeData
        tooltip.addAll(listOf(
                StringTextComponent("Level: ${data.level}"),
                StringTextComponent("Charge: ${data.currCharge}/${data.maxCharge}"),
                StringTextComponent("Charges: ${data.charges}"),
                TranslationTextComponent(descriptionKey)
        ))
    }

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)
        stack.initRuneData()
        val data = stack.runeData

        return if (data.charges > 0) {
            data.charges--
            onRuneUsed(worldIn, playerIn, data.level, 1)
            stack.runeData = data
            ActionResult.resultSuccess(stack)
        } else {
            ActionResult.resultFail(stack)
        }
    }

}


