package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.ability.Ability
import org.bukkit.entity.Player

fun Ability.failsChecks(player: Player) : Boolean {
    return Utils.abilityContext.failsChecks(player, this)
}