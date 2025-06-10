package me.vaan.cannonsRPG

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.registry.NamespacedRegistry
import me.vaan.CooldownManager
import me.vaan.cannonsRPG.auraSkills.CannonAbilities
import me.vaan.cannonsRPG.auraSkills.CannonManaAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.CannonFireRefund
import me.vaan.cannonsRPG.auraSkills.manaSkill.StormBlastToggler
import me.vaan.cannonsRPG.auraSkills.levelers.AimingLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.CannonDamageLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.CannonKillLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.FiringLeveler
import me.vaan.cannonsRPG.auraSkills.levelers.GunpowderLeveler
import me.vaan.cannonsRPG.auraSkills.manaSkill.StormImpactListener
import me.vaan.cannonsRPG.auraSkills.sources.AimingSource
import me.vaan.cannonsRPG.auraSkills.sources.CannonDamageSource
import me.vaan.cannonsRPG.auraSkills.sources.CannonKillSource
import me.vaan.cannonsRPG.auraSkills.sources.FiringSource
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
import me.vaan.cannonsRPG.utils.Storage
import me.vaan.interfaces.SimpleDebugger
import org.bstats.bukkit.Metrics
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger


class CannonsRPG : JavaPlugin() {

    companion object StaticStuff : SimpleDebugger {
        val messages = HashMap<String, String>()

        lateinit var auraSkills: AuraSkillsApi
            private set

        lateinit var instance: CannonsRPG
            private set

        lateinit var registry: NamespacedRegistry
            private set

        lateinit var log: Logger
            private set

        var debug: Boolean = false
            private set

        lateinit var cooldownManager: CooldownManager<String>
            private set

        override fun debug(s: String) {
            if (debug)
                log.info(s)
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
        registerCooldowns()
        registerListeners()
        Metrics(this,23294)
    }

    private fun saveResources() {
        genResource("sources/gunnery.yml")
        genResource("rewards/gunnery.yml")
        genResource("abilities.yml")
        genResource("skills.yml")
        genResource("mana_abilities.yml")
        genResource("config.yml")
        debug = config.getBoolean("debug")

        val entries = config.getConfigurationSection("messages")
        entries ?: throw RuntimeException("No messages entries found: maybe you are using an old config, delete it to generate a new one")
        for (message in entries.getValues(false)) {
            messages[message.key] = message.value as String
        }
    }

    private fun genResource(path: String) {
        val configFile = File(dataFolder, path)
        if (!configFile.exists()) saveResource(path, false)
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

        registry.registerSourceType("kill") { source, context ->
            val typeString = source.node("entity_type").string?.uppercase() ?: "PLAYER"
            val type = EntityType.valueOf(typeString)
            CannonKillSource(context.parseValues(source), type)
        }
    }

    private fun registerCooldowns() {
        cooldownManager = CooldownManager(StaticStuff)
        cooldownManager.setCooldown("GunpowderLeveler",config.getInt("cooldowns.gunpowder_leveler", 10) * 1000L)
        cooldownManager.setCooldown("AimingLeveler",config.getInt("cooldowns.aiming_leveler", 30) * 1000L)
        cooldownManager.setCooldown("FiringLeveler",config.getInt("cooldowns.firing_leveler", 5) * 1000L)
        cooldownManager.setCooldown("CannonDamageLeveler", config.getInt("cooldowns.damage_leveler", 0) * 1000L)
        cooldownManager.setCooldown("CannonKillLeveler", config.getInt("cooldowns.kill_leveler", 0) * 1000L)

        cooldownManager.printAllCooldowns()
    }

    private fun registerListeners() {
        val pm = this.server.pluginManager

        pm.registerEvents(GunpowderLeveler(auraSkills), this)
        pm.registerEvents(AimingLeveler(auraSkills), this)
        pm.registerEvents(FiringLeveler(auraSkills), this)
        pm.registerEvents(CannonDamageLeveler(auraSkills), this)
        pm.registerEvents(CannonKillLeveler(auraSkills), this)

        pm.registerEvents(CannonFireRefund(auraSkills), this)

        pm.registerEvents(StormBlastToggler(auraSkills), this)
        pm.registerEvents(StormImpactListener(auraSkills), this)
    }
}
