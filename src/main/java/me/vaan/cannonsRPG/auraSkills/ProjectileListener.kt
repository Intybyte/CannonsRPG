package me.vaan.cannonsRPG.auraSkills

import at.pavlov.cannons.event.ProjectileImpactEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ProjectileListener(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun projectileImpact(event: ProjectileImpactEvent) {
        val playerUUID = event.shooterUID
        val player = Bukkit.getPlayer(playerUUID) ?: return
        val skillUser = api.getUser(playerUUID)

        if (CannonAbilities.BONUS_SHELL.ability.isEnabled) {
            val percentage = CannonAbilities.BONUS_SHELL.getValue(skillUser)
            if (Math.random() <= percentage) {
                val stack = event.projectile.loadingItem.toItemStack(1)
                player.inventory.addItem(stack)
            }
        }

        if (CannonAbilities.AMMUNITION_ENGINEER.ability.isEnabled) {
            val tempNew = event.projectile.clone()
            tempNew.explosionPower *= (1.0 + CannonAbilities.AMMUNITION_ENGINEER.getValue(skillUser)).toFloat()
        }
    }
}
