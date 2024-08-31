package me.vaan.cannonsRPG

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.registry.NamespacedRegistry
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import me.vaan.cannonsRPG.auraSkills.levelers.GunpowderLeveler
import me.vaan.cannonsRPG.auraSkills.sources.GunpowderSource
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
            GunpowderSource(context.parseValues(source), multiplier)
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
