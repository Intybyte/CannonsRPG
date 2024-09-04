package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.entity.Player

fun impactResistanceHandler(ability: CustomAbility, player: Player, array: Array<Any>?) {
    val event = array!![0] as CannonDamageEvent

    if (!ability.isEnabled || event.target !is Player) return

    val target = event.target as Player
    val value = Utils.getSkillValue(target, ability) / 100.0

    val damageDecrease = 1.0 - value

    CannonsRPG.debug("Damage Reduction: $damageDecrease")
    event.damage *= damageDecrease
}