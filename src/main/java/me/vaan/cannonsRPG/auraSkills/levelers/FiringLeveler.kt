package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonFireEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class FiringLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonFireEvent) {
        if(!CannonSkill.GUNNERY.isEnabled) return
        val player = event.player
        if (!Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val firingSource = Utils.firstSource<FiringSource>()
        val xp = firingSource.xp * firingSource.getMultiplier()

        val skillPlayer = api.getUser(player)
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
