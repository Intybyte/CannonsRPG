package me.vaan.cannonsRPG.auraSkills.handlers

import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import me.vaan.cannonsRPG.utils.failsChecks
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun bonusShellHandler(ability: CustomAbility, player: Player, array: Array<out Any>?) {
    val refund = array!![0] as ItemStack

    if (ability.failsChecks(player)) return
    val percentage = Utils.getSkillValue(player, ability) / 100.0

    if (Math.random() > percentage) return
    CannonsRPG.debug("Refunded ammo to ${player.name}")
    player.inventory.addItem(refund)
}