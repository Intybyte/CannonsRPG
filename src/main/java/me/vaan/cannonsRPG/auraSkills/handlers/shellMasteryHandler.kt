package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import org.bukkit.entity.Player

fun shellMasteryHandler(ability: CustomAbility, player: Player, array: Array<Any>?) {
    if (!ability.isEnabled) return
    val event = array!![0] as CannonDamageEvent

    val skillPlayer = AuraSkillsApi.get().getUser(player.uniqueId)
    val value = ability.getValue(skillPlayer.getAbilityLevel(ability))
    val damageIncrease = 1.0 + value / 100.0
    CannonsRPG.debug("Damage Increase: $damageIncrease")
    event.damage *= damageIncrease
}