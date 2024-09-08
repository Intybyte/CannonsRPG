package me.vaan.cannonsRPG

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.registry.NamespacedRegistry
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonManaAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.CannonFireRefund
import me.vaan.cannonsRPG.auraSkills.manaSkill.StormBlastToggler
import me.vaan.cannonsRPG.auraSkills.levelers.AimingLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.CannonDamageLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.FiringLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.GunpowderLeveler
import me.vaan.cannonsRPG.auraSkills.manaSkill.StormImpactListener
import me.vaan.cannonsRPG.auraSkills.sources.AimingSource
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
import me.vaan.cannonsRPG.utils.Cooldowns
import me.vaan.cannonsRPG.utils.Storage
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger


class CannonsRPG : JavaPlugin() {

    companion object {
        @JvmStatic
        private lateinit var auraSkills: AuraSkillsApi
        @JvmStatic
        private lateinit var instance: CannonsRPG
        @JvmStatic
        private lateinit var registry: NamespacedRegistry
        @JvmStatic
        private lateinit var log: Logger
        @JvmStatic
        private var debug: Boolean = false

        fun instance(): CannonsRPG {
            return instance
        }

        fun registry(): NamespacedRegistry {
            return registry
        }

        fun debug(str: String) {
            if (debug)
                log.info(str)
        }

        fun logger(): Logger {
            return log
        }
    }

    override fun onEnable() {
        auraSkills = AuraSkillsApi.get()
        instance = this
        registry = auraSkills.useRegistry(Storage.PLUGIN_NAME, dataFolder)
        log = this.logger
        saveResources()

        CannonManaAbilities.loadManaAbilities()
        CannonAbilities.loadAbilities()
        registry.registerSkill(CannonSkill.GUNNERY)

        registerSourceTypes()
        registerListeners()
        Metrics(this,23294)
    }

    private fun saveResources() {
        saveResource("sources/gunnery.yml", false)
        saveResource("rewards/gunnery.yml", false)
        saveResource("abilities.yml", false)
        saveResource("skills.yml", false)
        saveResource("mana_abilities.yml", false)
        saveResource("config.yml", false)
        debug = config.getBoolean("debug")
    }

    private fun registerSourceTypes() {
        registry.registerSourceType("gunpowder") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            GunpowderSource(context.parseValues(source), multiplier)
        }

        registry.registerSourceType("aiming") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            AimingSource(context.parseValues(source), multiplier)
        }

        registry.registerSourceType("firing") { source, context ->
            val multiplier = source.node("multiplier").getDouble(1.0)
            FiringSource(context.parseValues(source), multiplier)
        }

        registry.registerSourceType("damage") { source, context ->
            val explosionMultiplier = source.node("explosion_multiplier").getDouble(1.0)
            val directMultiplier = source.node("direct_multiplier").getDouble(1.0)
            CannonDamageSource(context.parseValues(source), explosionMultiplier, directMultiplier)
        }
    }

    private fun registerListeners() {
        val pm = this.server.pluginManager
        Cooldowns.setCooldown(GunpowderLeveler::class,config.getInt("cooldowns.gunpowder_leveler", 10) * 1000L)
        Cooldowns.setCooldown(AimingLeveler::class,config.getInt("cooldowns.aiming_leveler", 30) * 1000L)
        Cooldowns.setCooldown(FiringLeveler::class,config.getInt("cooldowns.firing_leveler", 5) * 1000L)
        Cooldowns.setCooldown(CannonDamageLeveler::class, config.getInt("cooldowns.damage_leveler", 0) * 1000L)
        Cooldowns.printAllCooldowns()

        pm.registerEvents(GunpowderLeveler(auraSkills), this)
        pm.registerEvents(AimingLeveler(auraSkills), this)
        pm.registerEvents(FiringLeveler(auraSkills), this)
        pm.registerEvents(CannonDamageLeveler(auraSkills), this)

        pm.registerEvents(CannonFireRefund(auraSkills), this)

        pm.registerEvents(StormBlastToggler(auraSkills), this)
        pm.registerEvents(StormImpactListener(auraSkills), this)
    }
}
