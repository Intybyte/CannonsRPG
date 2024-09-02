package me.vaan.cannonsRPG.auraSkills.levelers

import at.pavlov.cannons.Cannons
import at.pavlov.cannons.Enum.ProjectileCause
import at.pavlov.cannons.cannon.Cannon
import at.pavlov.cannons.event.CannonFireEvent
import at.pavlov.cannons.projectile.ProjectileProperties
import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.projectiles.ProjectileSource
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.max

class FiringLeveler(private val api: AuraSkillsApi) : Listener {

    @EventHandler
    fun onFire(event: CannonFireEvent) {
        if(!CannonSkill.GUNNERY.isEnabled) return
        val player = event.player

        val skillPlayer = api.getUser(player)

        val cannon = event.cannon
        if (CannonAbilities.DOUBLE_SHOT.ability.isEnabled) {
            val percentage = CannonAbilities.DOUBLE_SHOT.getValue(skillPlayer)

            if (Math.random() <= percentage) {
                val proj = cannon.loadedProjectile

                object : BukkitRunnable() {
                    override fun run() {
                        val bukkitPlayer = Bukkit.getPlayer(player)!!
                        fire(proj, bukkitPlayer, cannon, ProjectileCause.PlayerFired)
                    }
                }.runTaskLater(CannonsRPG.instance(), 40L + (proj.automaticFiringDelay).toLong() * 20L)
            }
        }

        if (!Cooldowns.checkCooldown(this::class, Bukkit.getPlayer(player)!!.name)) return

        val firingSource = Utils.firstSource<FiringSource>()
        val xp = firingSource.xp * firingSource.getMultiplier()
        skillPlayer.addSkillXp(CannonSkill.GUNNERY, xp)
    }

    fun fire(projectile: at.pavlov.cannons.projectile.Projectile, onlinePlayer: Player, cannon: Cannon, projectileCause: ProjectileCause) {
        //get firing location
        val firingLoc: Location = cannon.cannonDesign.getMuzzle(cannon)
        //for each bullet, but at least once
        for (i in 0 until max(projectile.numberOfBullets.toDouble(), 1.0).toInt()) {
            val source: ProjectileSource = onlinePlayer
            val playerLoc: Location = onlinePlayer.location

            val vect: Vector = cannon.getFiringVector(true, true)

            val projectileEntity: Projectile = Cannons.getPlugin().projectileManager.spawnProjectile(
                projectile,
                onlinePlayer.uniqueId,
                source,
                playerLoc,
                firingLoc,
                vect,
                cannon.uid,
                projectileCause
            )

            if (i == 0 && projectile.hasProperty(ProjectileProperties.SHOOTER_AS_PASSENGER)) projectileEntity.setPassenger(
                onlinePlayer
            )
        }
    }
}
