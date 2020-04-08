package ejektaflex.kiln.ext

import net.minecraftforge.common.ForgeConfigSpec


operator fun <T : Any> ForgeConfigSpec.ConfigValue<T>.invoke(): T = get()
