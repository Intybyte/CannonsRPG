package me.vaan.cannonsRPG.auraSkills

import dev.aurelium.auraskills.api.ability.CustomAbility
import dev.aurelium.auraskills.api.mana.CustomManaAbility
import dev.aurelium.auraskills.api.registry.NamespacedId
import me.vaan.cannonsRPG.utils.Storage

object CannonAbilities {
    val CANNON_PROFICIENCY = CustomAbility
        .builder(NamespacedId.of(Storage.PLUGIN_NAME, "cannon_proficiency"))
        .displayName("Cannon Proficiency")
        .description("Gain {value}% more XP when using cannons.")
        .info("+{value}% Gunnery XP ")
        .baseValue(10.0) // Value when at level 1
        .valuePerLevel(10.0) // Value added per ability level
        .unlock(2) // Skill level ability unlocks at
        .levelUp(5) // Skill level interval between ability level ups
        .maxLevel(0) // 0 = unlimited max level, but capper by the max skill level
        .build()!!

    val SHELL_MASTERY = CustomAbility
        .builder(NamespacedId.of(Storage.PLUGIN_NAME, "shell_mastery"))
        .displayName("Shell Mastery")
        .description("Deal {value}% more damage when using cannons.")
        .info("+{value}% Cannons Damage ")
        .baseValue(2.5)
        .valuePerLevel(2.5)
        .unlock(3)
        .levelUp(5)
        .maxLevel(0)
        .build()!!

    val STORM_BLAST = CustomManaAbility
        .builder(NamespacedId.of(Storage.PLUGIN_NAME, "storm_blast"))
        .displayName("Storm Blast")
        .description("Summons a lightning that deal increased damage in a 5x5 area of cannon hit [Right click gunpowder to activate]")!!
        .baseValue(1.0)
        .valuePerLevel(0.5)
        .unlock(6)
        .levelUp(6)
        .maxLevel(0)
        .build()!!
}