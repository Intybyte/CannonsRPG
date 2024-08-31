package me.vaan.cannonsRPG

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.registry.NamespacedRegistry
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.levelers.AimingLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.FiringLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.GunpowderLeveler
import me.vaan.cannonsRPG.auraSkills.sources.AimingSource
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Storage
import org.bukkit.plugin.java.JavaPlugin


class CannonsRPG : JavaPlugin() {

    companion object {
        @JvmStatic
        private lateinit var auraSkills: AuraSkillsApi
        @JvmStatic
        private lateinit var instance: CannonsRPG
        @JvmStatic
        private lateinit var registry: NamespacedRegistry
    }

    override fun onEnable() {
        auraSkills = AuraSkillsApi.get()
        instance = this
        registry = auraSkills.useRegistry(Storage.PLUGIN_NAME, dataFolder)
        saveResource("sources/gunnery.yml", false)

        registry.registerSkill(CannonSkill.GUNNERY)

        registerSourceTypes()
        registerListeners()
    }

    private fun registerSourceTypes() {
        registry.registerSourceType("gunpowder") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            val cooldown = source.node("cooldown").getLong(10L) * 1000L
            Cooldowns.setCooldown(GunpowderLeveler::class, cooldown)
            GunpowderSource(context.parseValues(source), multiplier)
        }

        registry.registerSourceType("aiming") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            val cooldown = source.node("cooldown").getLong(30L) * 1000L
            Cooldowns.setCooldown(AimingLeveler::class, cooldown)
            AimingSource(context.parseValues(source), multiplier)
        }

        registry.registerSourceType("firing") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            val cooldown = source.node("cooldown").getLong(5L) * 1000L
            Cooldowns.setCooldown(FiringLeveler::class, cooldown)
            FiringSource(context.parseValues(source), multiplier)
        }
    }

    private fun registerListeners() {
        val pm = this.server.pluginManager

        pm.registerEvents(GunpowderLeveler(auraSkills), this)

    }

    fun instance(): CannonsRPG {
        return instance
    }
}
