package me.vaan.cannonsRPG.auraSkills

import at.pavlov.cannons.event.CannonFireEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CannonFireRefund(private val api: AuraSkillsApi) : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun cannonFireRefund(event: CannonFireEvent) {
        val playerUUID = event.player
        val player = Bukkit.getPlayer(playerUUID) ?: return
        val skillUser = api.getUser(playerUUID)

        if (CannonAbilities.BONUS_SHELL.ability.isEnabled) {
            val percentage = CannonAbilities.BONUS_SHELL.getValue(skillUser) / 100.0
            if (Math.random() <= percentage) {
                CannonsRPG.debug("Refunded ammo to ${player.name}")

                val stack = event.cannon.loadedProjectile.loadingItem.toItemStack(1)
                player.inventory.addItem(stack)
            }
        }
    }
}
