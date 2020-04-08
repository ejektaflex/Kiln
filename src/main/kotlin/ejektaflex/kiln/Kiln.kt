package ejektaflex.kiln

import ejektaflex.kiln.mod.KilnMod
import net.minecraftforge.fml.common.Mod

@Mod("kiln")
object Kiln : KilnMod() {


    init {
        println("My mod name is: $ID")
    }

}

