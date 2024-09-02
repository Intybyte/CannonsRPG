package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonGunpowderLoadEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.utils.Utils
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
import me.vaan.cannonsRPG.utils.Cooldowns
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GunpowderLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun gunpowderLoadEvent(event: CannonGunpowderLoadEvent) {
        if(!CannonSkill.GUNNERY.isEnabled) return
        val player = event.player ?: return
        if (!Cooldowns.checkCooldown(this::class, player.name)) return

        val gunpowderSource = Utils.firstSource<GunpowderSource>()

        val addedAmount = event.newAmount - event.startAmount
        val xp = addedAmount * gunpowderSource.xp * gunpowderSource.getMultiplier()

        val skillPlayer = api.getUser(player.uniqueId)
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}