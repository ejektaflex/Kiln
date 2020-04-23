package ejektaflex.pyrolysis.runestones.item

import ejektaflex.kiln.nbt.KilnNBT
import ejektaflex.pyrolysis.runestones.Runestones
import ejektaflex.pyrolysis.runestones.data.RuneData
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

abstract class Runestone(val runeId: String) : Item(
        Properties()
), IItemColor {

    private var ItemStack.runeData: RuneData by KilnNBT(this.asItem(), ::RuneData)

    init {
        registryName = Runestones.locate(runeId)
    }

    enum class RuneRenderLayer {
        BASE,
        LABEL,
        DOT1,
        DOT2,
        DOT3
    }

    private fun shouldDrawDot(dotNum: Int, chargeNum: Int): Boolean {
        return chargeNum >= dotNum
    }

    @OnlyIn(Dist.CLIENT)
    override fun getColor(stack: ItemStack, layer: Int): Int {
        val renderOn = RuneRenderLayer.values()[layer]
        val data = stack.runeData
        return when (renderOn) {
            RuneRenderLayer.BASE -> 0xFFFFFF
            RuneRenderLayer.LABEL -> MathHelper.hsvToRGB(0f, 0f,
                    (data.charges.toFloat() / data.maxCharges))
            else -> {
                if (shouldDrawDot(layer - 1, data.charges)) {
                    0xFFFFFF
                } else {
                    0x000000
                }
            }
        }
    }

    class OhNo {
        fun light(): Int {
            return -0x1
        }

        fun dark(): Int {
            return -0x100
        }
    }


    val descriptionKey: String
        get() = "item.${Runestones.ID}.$runeId.desc"

    abstract fun onRuneUsed(world: World, player: PlayerEntity, level: Int, chargesUsed: Int)


    // Automagically utilizing setters and getters
    private fun ItemStack.initRuneData(func: RuneData.() -> Unit = {}) {
        stack.runeData = stack.runeData.apply(func)
    }

    fun addCharge(stack: ItemStack, chargeAmount: Int) {
        stack.initRuneData {
            charge(chargeAmount)
        }
    }

    override fun showDurabilityBar(stack: ItemStack): Boolean {
        return true
    }

    override fun getDurabilityForDisplay(stack: ItemStack): Double {
        val data = stack.runeData
        return if (data.isFullyCharged) {
            0.0 // full bar
        } else {
            1.0 - data.remainderPercent
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        val data = stack.runeData
        tooltip.addAll(listOf(
                StringTextComponent("Level: ${data.level}"),
                StringTextComponent("Charge: ${data.pointsAfterCharge}/${data.pointsPerCharge}"),
                StringTextComponent("Charges: ${data.charges}"),
                TranslationTextComponent(descriptionKey)
        ))
    }

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)
        stack.initRuneData()
        val data = stack.runeData

        return if (data.charges > 0) {
            data.useCharges(1)
            onRuneUsed(worldIn, playerIn, data.level, 1)
            stack.runeData = data
            ActionResult.resultSuccess(stack)
        } else {
            ActionResult.resultFail(stack)
        }
    }

}


