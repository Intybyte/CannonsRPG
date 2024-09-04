package me.vaan.cannonsRPG.auraSkills.handlers

import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun bonusShellHandler(ability: CustomAbility, player: Player, array: Array<Any>?) {
    val refund = array!![0] as ItemStack

    if (!ability.isEnabled) return
    val percentage = Utils.getSkillValue(player, ability) / 100.0

    if (Math.random() > percentage) return
    CannonsRPG.debug("Refunded ammo to ${player.name}")
    player.inventory.addItem(refund)
}