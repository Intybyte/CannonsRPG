package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonGunpowderLoadEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GunpowderLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun gunpowderLoadEvent(event: CannonGunpowderLoadEvent) {
        val player = event.player ?: return
        if (!CannonsRPG.cooldownManager.check("GunpowderLeveler", player.name)) return

        val gunpowderSkillSources = Utils.source<GunpowderSource>()
        for (skillSource in gunpowderSkillSources) {
            val source = skillSource.source()
            val skill = skillSource.skill()
            if (!skill.isEnabled) continue

            val addedAmount = event.newAmount - event.startAmount
            val xp = addedAmount * source.xp * source.getMultiplier()

            val skillPlayer = api.getUser(player.uniqueId)
            skillPlayer.addSkillXp(skill, xp)
        }
    }
}