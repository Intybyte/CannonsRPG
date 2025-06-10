package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonsEntityDeathEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.sources.CannonKillSource
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CannonKillLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onKill(event: CannonsEntityDeathEvent) {
        val player = Bukkit.getPlayer(event.shooter) ?: return
        if (!CannonsRPG.cooldownManager.check("AimingLeveler", player.name)) return

        val cannonKillSkillSources = Utils.source<CannonKillSource>()
        val skillPlayer = api.getUser(event.shooter)

        for (skillSource in cannonKillSkillSources) {
            val source = skillSource.source()
            val skill = skillSource.skill()
            if (!skill.isEnabled) continue

            if (event.killedEntity.type != source.getEntityType()) continue
            skillPlayer.addSkillXp(skill, source.xp)
        }
    }
}