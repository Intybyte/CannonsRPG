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
        .abilities(*(CannonAbilities.entries.map { it.ability }.toTypedArray()))
        .manaAbility(CannonManaAbilities.STORM_BLAST)
        .item(ItemContext
            .builder()
            .material("gunpowder")
            .pos("2,1")
            .build())
        .build()!!
}