package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonFireEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class FiringLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonFireEvent) {
        val player = event.player
        val bukkitPlayer = Bukkit.getPlayer(player) ?: return

        val skillPlayer = api.getUser(player)

        CannonAbilities.DOUBLE_SHOT.callHandler(bukkitPlayer, event.cannon)

        if (!CannonsRPG.cooldownManager.check("FiringLeveler", bukkitPlayer.name)) return

        val firingSkillSources = Utils.source<FiringSource>()
        for (skillSource in firingSkillSources) {
            val source = skillSource.source()
            val skill = skillSource.skill()
            if (!skill.isEnabled) continue

            val xp = source.xp * source.getMultiplier()
            skillPlayer.addSkillXp(skill, xp)
        }
    }
}
