package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonFireEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
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
        val bukkitPlayer = Bukkit.getPlayer(player) ?: return

        val skillPlayer = api.getUser(player)

        CannonAbilities.DOUBLE_SHOT.callHandler(bukkitPlayer, event.cannon)

        if (!Cooldowns.checkCooldown(this::class, bukkitPlayer.name)) return

        val firingSource = Utils.firstSource<FiringSource>()
        val xp = firingSource.xp * firingSource.getMultiplier()
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
