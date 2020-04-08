package ejektaflex.kiln.ext

import net.minecraftforge.fml.ModList


object ExtMods {

    fun isPresent(modId: String) = ModList.get().isLoaded(modId)

}
