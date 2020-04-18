package ejektaflex.kiln.anim

data class AnimData(
        var name: String = "NO_ANIM_NAME",
        var length: Double,
        var bones: MutableList<AnimBone> = mutableListOf()
)