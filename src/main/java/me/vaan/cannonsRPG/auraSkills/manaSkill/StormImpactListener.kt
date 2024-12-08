package me.vaan.cannonsRPG.auraSkills.manaSkill

import at.pavlov.cannons.event.ProjectileImpactEvent
import at.pavlov.cannons.utils.ArmorCalculationUtil
import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.event.mana.ManaAbilityActivateEvent
import me.vaan.cannonsRPG.auraSkills.CannonManaAbilities
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import kotlin.math.max

class StormImpactListener(private val api: AuraSkillsApi) : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun stormImpact(event: ProjectileImpactEvent) {
        val player = Bukkit.getPlayer(event.shooterUID)
        player ?: return

        if(StormBlastToggler.activatedStormBlast[player.name] != true) return

        val skillPlayer = api.getUser(player.uniqueId)
        val level = skillPlayer.getManaAbilityLevel(CannonManaAbilities.STORM_BLAST)
        val manaCost = CannonManaAbilities.STORM_BLAST.getManaCost(level)
        val skillDamage = CannonManaAbilities.STORM_BLAST.getValue(level)

        val impact = event.impactLocation
        val entityList = impact.world?.getNearbyEntities(impact, 5.0, 20.0, 5.0)
        entityList ?: return
        if (entityList.isEmpty()) return
        val livingEntityList = entityList.mapNotNull { it as? LivingEntity }

        if (!skillPlayer.consumeMana(manaCost)) return

        val callEvent = ManaAbilityActivateEvent(player, skillPlayer, CannonManaAbilities.STORM_BLAST, 0, manaCost)
        Bukkit.getServer().pluginManager.callEvent(callEvent)

        impact.world?.strikeLightningEffect(impact)

        for (entity in livingEntityList) {
            val damage: Double
            if (entity is HumanEntity) {
                ArmorCalculationUtil.reduceArmorDurability(entity)
                val reduction = max(event.projectile.penetration, 0.0)
                damage = ArmorCalculationUtil.getDirectHitReduction(entity, reduction) * skillDamage
            } else {
                damage = skillDamage
            }

            entity.damage(damage)
        }
    }
}