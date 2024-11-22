package me.vaan.cannonsRPG.auraSkills.manaSkill

import dev.aurelium.auraskills.api.AuraSkillsApi
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonManaAbilities
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class StormBlastToggler(private val api: AuraSkillsApi) : Listener {
    companion object {
        @JvmStatic
        val activatedStormBlast = HashMap<String, Boolean>()
    }

    @EventHandler
    fun onGunpowderShiftLMB(event: PlayerInteractEvent) {
        if (!event.action.isLeftClick) return
        if(!CannonManaAbilities.STORM_BLAST.isEnabled) return
        if(!CannonSkill.GUNNERY.isEnabled) return

        val player = event.player
        if (!player.isSneaking) return
        if (player.inventory.itemInMainHand.type != Material.GUNPOWDER) return

        val skillPlayer = api.getUser(player.uniqueId)
        val level = skillPlayer.getManaAbilityLevel(CannonManaAbilities.STORM_BLAST)
        if (level == 0) return

        activatedStormBlast[player.name] = !(activatedStormBlast[player.name] ?: false)
        val status = if(activatedStormBlast[player.name]!!) "enabled" else "disabled"
        player.sendMessage(CannonsRPG.messages["prefix"] + CannonsRPG.messages["storm_blast"]!!.format(status))
    }
}
