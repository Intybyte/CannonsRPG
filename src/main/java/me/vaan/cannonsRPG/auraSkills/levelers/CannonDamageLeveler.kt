package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Enum.DamageType
import at.pavlov.cannons.event.CannonDamageEvent
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CannonDamageLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonDamageEvent) {
        val player = event.cannonball.shooterUID
        if (!CannonSkill.GUNNERY.isEnabled) return
        val bukkitPlayer = Bukkit.getPlayer(player) ?: return

        if (CannonAbilities.IMPACT_RESISTANCE.ability.isEnabled && event.target is Player) {
            val targetSkill = api.getUser((event.target as Player).uniqueId)
            val damageDecrease = 1.0 - CannonAbilities.IMPACT_RESISTANCE.getValue(targetSkill) / 100.0
            CannonsRPG.debug("Damage Reduction: $damageDecrease")
            event.damage *= damageDecrease
        }

        val skillPlayer = api.getUser(player)
        if (CannonAbilities.SHELL_MASTERY.ability.isEnabled) {
            val damageIncrease = 1.0 + CannonAbilities.SHELL_MASTERY.getValue(skillPlayer) / 100.0
            CannonsRPG.debug("Damage Increase: $damageIncrease")
            event.damage *= damageIncrease
        }

        if (!Cooldowns.checkCooldown(this::class, bukkitPlayer.name)) return
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
