package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.Cannons
import at.pavlov.cannons.Enum.ProjectileCause
import at.pavlov.cannons.cannon.Cannon
import at.pavlov.cannons.projectile.ProjectileProperties
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Storage
import me.vaan.cannonsRPG.utils.Utils
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.projectiles.ProjectileSource
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.max

fun doubleShotHandler(ability: CustomAbility, player: Player, array: Array<out Any>?) {
    if (!ability.isEnabled) return
    val percentage = Utils.getSkillValue(player, ability) / 100.0

    if (Math.random() > percentage) return

    val cannon = array!![0] as Cannon
    val proj = cannon.loadedProjectile

    player.sendMessage(Storage.PREFIX + "Double shot activated!")

    object : BukkitRunnable() {
        override fun run() {
            fire(proj, player, cannon, ProjectileCause.PlayerFired)
        }
    }.runTaskLater(CannonsRPG.instance, 40L + (proj.automaticFiringDelay).toLong() * 20L)
}

private fun fire(projectile: at.pavlov.cannons.projectile.Projectile, onlinePlayer: Player, cannon: Cannon, projectileCause: ProjectileCause) {
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