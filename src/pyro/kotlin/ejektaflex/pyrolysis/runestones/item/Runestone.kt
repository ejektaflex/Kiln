package ejektaflex.pyrolysis.runestones.item

import ejektaflex.kiln.ext.toData
import ejektaflex.pyrolysis.runestones.Runestones
import ejektaflex.pyrolysis.runestones.data.RuneData
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MobEntity
import net.minecraft.entity.item.ItemEntity
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
import net.minecraftforge.items.ItemStackHandler

abstract class Runestone(val runeId: String) : Item(
        Properties().maxDamage(100)
) {

    init {
        registryName = Runestones.locate(runeId)
    }

    val descriptionKey: String
        get() = "item.${Runestones.ID}.$runeId.desc"

    abstract fun onRuneUsed(world: World, player: PlayerEntity)

    private fun ItemStack.initRuneData() {
        stack.runeData = stack.runeData
    }

    private var ItemStack.runeData: RuneData
        get() = stack.toData(::RuneData)
        set(value) {
            if (item is Runestone) {
                stack.tag = value.serializeNBT()
            } else {
                throw Exception("Cannot set a Rune's stack NBT to a non-Rune!")
            }
        }


    fun addCharge(stack: ItemStack, chargeAmount: Int) {
        stack.runeData = stack.runeData.apply {
            addCharge(chargeAmount)
        }
    }

    private fun tryUseRune(world: World, player: PlayerEntity, stack: ItemStack, rune: RuneData): Boolean {
        return if (rune.charges > 0) {
            rune.charges--
            onRuneUsed(world, player)
            true
        } else {
            false
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        val rune = stack.runeData
        println("Adding to tooltip: ${rune.level}")
        println("It had the tag: ${stack.tag}")
        tooltip.addAll(listOf(
                StringTextComponent("Level: ${rune.level}"),
                StringTextComponent("Charge: ${rune.charge}"),
                StringTextComponent("Charges: ${rune.charges}"),
                TranslationTextComponent(descriptionKey)
        ))
    }

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)
        stack.initRuneData()
        val result = tryUseRune(worldIn, playerIn, stack, stack.runeData)
        return when (result) {
            true -> ActionResult.resultSuccess(stack)
            false -> ActionResult.resultFail(stack)
        }
    }

}