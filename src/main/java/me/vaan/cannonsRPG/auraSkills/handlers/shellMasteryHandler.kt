package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import me.vaan.cannonsRPG.utils.failsChecks
import org.bukkit.entity.Player

fun shellMasteryHandler(ability: CustomAbility, player: Player, array: Array<out Any>?) {
    if (ability.failsChecks(player)) return
    val event = array!![0] as CannonDamageEvent

    val value = Utils.getSkillValue(player, ability)/ 100.0
    val damageIncrease = 1.0 + value

    CannonsRPG.debug("Damage Increase: $damageIncrease")
    event.damage *= damageIncrease
}