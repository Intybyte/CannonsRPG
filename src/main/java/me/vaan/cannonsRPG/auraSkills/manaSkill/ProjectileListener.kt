package me.vaan.cannonsRPG.auraSkills.manaSkill

import at.pavlov.cannons.event.ProjectileImpactEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ProjectileListener(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onImpact(event: ProjectileImpactEvent) {
        val player = Bukkit.getPlayer(event.shooterUID)
        player ?: return
    }
}