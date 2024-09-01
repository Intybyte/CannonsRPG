package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.DamageType
import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CannonDamageLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonDamageEvent) {
        val player = event.cannonball.shooterUID
        if (!CannonSkill.GUNNERY.isEnabled) return
        if (Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val skillPlayer = api.getUser(player)

        if (CannonAbilities.CANNON_PROFICIENCY.ability.isEnabled) {
            val damageIncrease = 1.0 + CannonAbilities.CANNON_PROFICIENCY.getValue(skillPlayer)
            event.damage *= damageIncrease
        }

        val firingSource = Utils.firstSource<CannonDamageSource>()
        var xp = firingSource.xp * event.damage * event.reduction

        xp *= if (event.type == DamageType.DIRECT)
                firingSource.getDirectMultiplier() else firingSource.getExplosionMultiplier()


        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
