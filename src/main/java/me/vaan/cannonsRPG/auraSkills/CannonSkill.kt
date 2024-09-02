package me.vaan.cannonsRPG.auraSkills

import dev.aurelium.auraskills.api.item.ItemContext
import dev.aurelium.auraskills.api.skill.CustomSkill
import me.vaan.cannonsRPG.utils.Storage

object CannonSkill {
    val GUNNERY = CustomSkill
        .builder(Storage.GUNNERY_KEY)
        .displayName("Gunnery")
        .description("Gunnery increases the proficiency with cannons")
        .abilities(*(CannonAbilities.entries.filter { it != CannonAbilities.CANNON_PROFICIENCY }.map{ it.ability }.toTypedArray()))
        .manaAbility(CannonManaAbilities.STORM_BLAST)
        .xpMultiplierAbility(CannonAbilities.CANNON_PROFICIENCY.ability)
        .item(ItemContext
            .builder()
            .material("gunpowder")
            .pos("1,2")
            .build())
        .build()!!
}