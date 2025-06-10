package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.InteractAction
import at.pavlov.cannons.event.CannonUseEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.sources.AimingSource
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AimingLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun aimingChange(event: CannonUseEvent) {
        if (event.action != InteractAction.adjustPlayer) return
        val player = event.player
        val bukkitPlayer = Bukkit.getPlayer(player) ?: return
        if (!CannonsRPG.cooldownManager.check("AimingLeveler", bukkitPlayer.name)) return

        val aimingSkillSources = Utils.source<AimingSource>()
        for (skillSource in aimingSkillSources) {
            val source = skillSource.source()
            val skill = skillSource.skill()
            if (!skill.isEnabled) continue

            val xp = source.xp * source.getMultiplier()

            val skillPlayer = api.getUser(player)
            skillPlayer.addSkillXp(skill, xp)
        }
    }
}
