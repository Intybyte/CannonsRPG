package me.vaan.cannonsRPG.auraSkills

import dev.aurelium.auraskills.api.ability.CustomAbility
import dev.aurelium.auraskills.api.registry.NamespacedId
import dev.aurelium.auraskills.api.user.SkillsUser
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Storage

enum class CannonAbilities(val ability: CustomAbility) {
    DOUBLE_SHOT(
        CustomAbility.builder(NamespacedId.of(Storage.PLUGIN_NAME, "double_shot"))
            .displayName("Double Shot")
            .description("There is a {value}% chance of shooting another projectile")
            .info("{value}% Double Shot ")
            .baseValue(1.0)
            .valuePerLevel(1.0)
            .unlock(1)
            .levelUp(5)
            .maxLevel(0)
            .build()!!
    ),
    CANNON_PROFICIENCY(
        CustomAbility.builder(NamespacedId.of(Storage.PLUGIN_NAME, "cannon_proficiency"))
            .displayName("Cannon Proficiency")
            .description("Gain {value}% more XP when using cannons.")
            .info("+{value}% Gunnery XP ")
            .baseValue(10.0)
            .valuePerLevel(10.0)
            .unlock(2)
            .levelUp(5)
            .maxLevel(0)
            .build()!!
    ),
    SHELL_MASTERY(
        CustomAbility.builder(NamespacedId.of(Storage.PLUGIN_NAME, "shell_mastery"))
            .displayName("Shell Mastery")
            .description("Deal {value}% more damage when using cannons.")
            .info("+{value}% Cannons Damage ")
            .baseValue(2.0)
            .valuePerLevel(2.0)
            .unlock(3)
            .levelUp(5)
            .maxLevel(0)
            .build()!!
    ),
    BONUS_SHELL(
        CustomAbility.builder(NamespacedId.of(Storage.PLUGIN_NAME, "bonus_shell"))
            .displayName("Bonus Shell")
            .description("Once the shell has exploded {value}% of getting another shell")
            .info("{value}% Shell Retrieval ")
            .baseValue(1.0)
            .valuePerLevel(1.0)
            .unlock(4)
            .levelUp(5)
            .maxLevel(0)
            .build()!!
    ),
    IMPACT_RESISTANCE(
        CustomAbility.builder(NamespacedId.of(Storage.PLUGIN_NAME, "impact_resistance"))
            .displayName("Impact Resistance")
            .description("When being hit by a cannon reduce damage by {value}%")
            .info("-{value}% Cannon Received Damage ")
            .baseValue(1.0)
            .valuePerLevel(1.0)
            .unlock(5)
            .levelUp(5)
            .maxLevel(0)
            .build()!!
    );

    fun getValue(user: SkillsUser): Double {
        return this.ability.getValue(user.getAbilityLevel(this.ability))
    }

    companion object {
        fun loadAbilities() {
            val reg = CannonsRPG.registry()
            entries.forEach { abilityEnum ->
                reg.registerAbility(abilityEnum.ability)
            }
        }
    }
}