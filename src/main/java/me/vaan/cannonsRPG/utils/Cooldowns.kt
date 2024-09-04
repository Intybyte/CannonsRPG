package me.vaan.cannonsRPG.utils

import me.vaan.cannonsRPG.CannonsRPG
import org.bukkit.event.Listener
import kotlin.reflect.KClass

object Cooldowns {
    private val timeMap = HashMap<KClass<out Listener>, HashMap<String, Long>>()
    private val cooldowns = HashMap<KClass<out Listener>, Long>()

    fun setCooldown(clazz: KClass<out Listener>, cooldown: Long) {
        cooldowns[clazz] = cooldown
    }

    fun checkCooldown(clazz: KClass<out Listener>, playerName: String) : Boolean {
        val cooldown = cooldowns[clazz]!!
        val playerTime = getPlayer(clazz, playerName)
        val current = System.currentTimeMillis()
        CannonsRPG.debug("PlayerTime: $playerTime Cooldown: $cooldown Current: $current")

        if (playerTime == null || playerTime + cooldown <= current) {
            setPlayer(clazz, playerName)
            return true
        }

        return false
    }

    fun printAllCooldowns() {
        for (entry in cooldowns) {
            CannonsRPG.debug("KClass: " + entry.key + " Value: " + entry.value)
        }
    }

    private fun getPlayer(clazz: KClass<out Listener>, playerName: String) : Long? {
        timeMap.putIfAbsent(clazz, HashMap())
        val playerMap = timeMap[clazz]!!
        return playerMap[playerName]
    }

    private fun setPlayer(clazz: KClass<out Listener>, playerName: String) {
        timeMap.putIfAbsent(clazz, HashMap())
        val playerMap = timeMap[clazz]!!
        playerMap[playerName] = System.currentTimeMillis()
        CannonsRPG.debug("Set player time for $playerName in class ${clazz.simpleName} to ${playerMap[playerName]}")
    }
}