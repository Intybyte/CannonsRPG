package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import me.vaan.cannonsRPG.utils.failsChecks
import org.bukkit.entity.Player

fun impactResistanceHandler(ability: CustomAbility, player: Player, array: Array<out Any>?) {
    val event = array!![0] as CannonDamageEvent

    if (event.target !is Player) return
    val target = event.target as Player

    if (ability.failsChecks(target)) return
    val value = Utils.getSkillValue(target, ability) / 100.0

    val damageDecrease = 1.0 - value

    CannonsRPG.debug("Damage Reduction: $damageDecrease")
    event.damage *= damageDecrease
}