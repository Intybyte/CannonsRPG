package me.vaan.cannonsRPG.auraSkills

import dev.aurelium.auraskills.api.item.ItemContext
import dev.aurelium.auraskills.api.skill.CustomSkill
import me.vaan.cannonsRPG.utils.Storage

object CannonSkill {
    @JvmStatic
    val GUNNERY = CustomSkill
        .builder(Storage.GUNNERY_KEY)
        .displayName("Gunnery")
        .description("Gunnery increases the proficiency with cannons")
        .item(ItemContext
            .builder()
            .material("gunpowder")
            .pos("1,2")
            .build())
        .build()!!
}