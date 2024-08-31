package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.InteractAction
import at.pavlov.cannons.event.CannonUseEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.AimingSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AimingLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun aimingChange(event: CannonUseEvent) {
        if (event.action != InteractAction.adjustPlayer) return
        val player = event.player
        if (Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val aimingSource = Utils.firstSource<AimingSource>()
        val xp = aimingSource.xp * aimingSource.getMultiplier()

        val skillPlayer = api.getUser(player)
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
