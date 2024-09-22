package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.DamageType
import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CannonDamageLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonDamageEvent) {
        val player = event.cannonball.shooterUID
        if (!CannonSkill.GUNNERY.isEnabled) return
        val bukkitPlayer = Bukkit.getPlayer(player) ?: return

        CannonAbilities.IMPACT_RESISTANCE.callHandler(bukkitPlayer, event)

        val skillPlayer = api.getUser(player)
        CannonAbilities.SHELL_MASTERY.callHandler(bukkitPlayer, event)

        if (!CannonsRPG.cooldown().check("CannonDamageLeveler", bukkitPlayer.name)) return
        val firingSource = Utils.firstSource<CannonDamageSource>()
        var xp = firingSource.xp * event.damage * event.reduction

        xp *= when (event.type) {
            DamageType.DIRECT -> firingSource.getDirectMultiplier()
            DamageType.EXPLOSION -> firingSource.getExplosionMultiplier()
            else -> 1.0
        }

        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }
}
