package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.event.CannonFireEvent
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CannonDamageLeveler : Listener {

    /* Wait for 3.0.4
    @EventHandler
    fun onFire(event: CannonDa) {
        val player = event.player
        if (Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val firingSource = Utils.firstSource<FiringSource>()
        val xp = firingSource.xp * firingSource.getMultiplier()

        val skillPlayer = api.getUser(player)
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }

     */
}
