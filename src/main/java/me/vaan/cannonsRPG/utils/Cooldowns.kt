package me.vaan.cannonsRPG.utils

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

        if (playerTime == null || playerTime + cooldown <= current) {
            setPlayer(clazz, playerName)
            return true
        }

        return false
    }

    private fun getPlayer(clazz: KClass<out Listener>, playerName: String) : Long? {
        val playerMap = timeMap.putIfAbsent(clazz, HashMap())!!
        return playerMap[playerName]
    }

    private fun setPlayer(clazz: KClass<out Listener>, playerName: String) {
        val playerMap = timeMap.putIfAbsent(clazz, HashMap())!!
        playerMap[playerName] = System.currentTimeMillis()
    }
}