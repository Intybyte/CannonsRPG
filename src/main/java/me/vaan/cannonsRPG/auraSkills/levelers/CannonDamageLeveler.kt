package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.DamageType
import at.pavlov.cannons.event.CannonDamageEvent
import at.pavlov.cannons.event.CannonFireEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CannonDamageLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonDamageEvent) {
        val player = event.cannonball.shooterUID
        if (Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val firingSource = Utils.firstSource<CannonDamageSource>()
        var xp = firingSource.xp * event.damage * event.reduction

        xp *= if (event.type == DamageType.DIRECT)
                firingSource.getDirectMultiplier() else firingSource.getExplosionMultiplier()

        val skillPlayer = api.getUser(player)
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
