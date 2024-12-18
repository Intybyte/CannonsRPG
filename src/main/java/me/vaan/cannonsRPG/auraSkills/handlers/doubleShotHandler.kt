package me.vaan.cannonsRPG.auraSkills.handlers

import at.pavlov.cannons.Enum.ProjectileCause
import at.pavlov.cannons.cannon.Cannon
import at.pavlov.cannons.projectile.ProjectileManager
import dev.aurelium.auraskills.api.ability.CustomAbility
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Utils
import me.vaan.cannonsRPG.utils.failsChecks
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.projectiles.ProjectileSource
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.max

fun doubleShotHandler(ability: CustomAbility, player: Player, array: Array<out Any>?) {
    if (ability.failsChecks(player)) return
    val percentage = Utils.getSkillValue(player, ability) / 100.0

    if (Math.random() > percentage) return

    val cannon = array!![0] as Cannon
    val proj = cannon.loadedProjectile

    player.sendMessage(CannonsRPG.messages["prefix"] + CannonsRPG.messages["double_shot"])

    object : BukkitRunnable() {
        override fun run() {
            fire(proj, player, cannon)
        }
    }.runTaskLater(CannonsRPG.instance, 40L + (proj.automaticFiringDelay).toLong() * 20L)
}

private fun fire(projectile: at.pavlov.cannons.projectile.Projectile, onlinePlayer: Player, cannon: Cannon) {
    //get firing location
    val firingLoc: Location = cannon.cannonDesign.getMuzzle(cannon)
    //for each bullet, but at least once
    for (i in 0 until max(projectile.numberOfBullets.toDouble(), 1.0).toInt()) {
        val source: ProjectileSource = onlinePlayer
        val playerLoc: Location = onlinePlayer.location

        val vect: Vector = cannon.getFiringVector(true, true)

        ProjectileManager.getInstance().spawnProjectile(
            projectile,
            onlinePlayer.uniqueId,
            source,
            playerLoc,
            firingLoc,
            vect,
            cannon.uid,
            ProjectileCause.PlayerFired
        )
    }
}