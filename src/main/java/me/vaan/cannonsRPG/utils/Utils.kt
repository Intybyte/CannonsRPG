package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.ability.AbilityContext
import dev.aurelium.auraskills.api.ability.CustomAbility
import dev.aurelium.auraskills.api.source.XpSource
import me.vaan.cannonsRPG.CannonsRPG
import org.bukkit.entity.Player
import org.bukkit.event.block.Action

object Utils {
    val abilityContext = AbilityContext(CannonsRPG.auraSkills)

    inline fun <reified T : XpSource> source() = CannonsRPG.auraSkills.sourceManager.getSourcesOfType(T::class.java)

    fun getSkillValue(player: Player, ability: CustomAbility): Double {
        val api = AuraSkillsApi.get()
        val skillPlayer = api.getUser(player.uniqueId)
        val level = skillPlayer.getAbilityLevel(ability)

        return ability.getValue(level)
    }

    val Action.isLeftClick : Boolean get()  {
        return this == Action.LEFT_CLICK_AIR || this == Action.LEFT_CLICK_BLOCK
    }

    val Action.isRightClick : Boolean get()  {
        return this == Action.RIGHT_CLICK_AIR || this == Action.RIGHT_CLICK_BLOCK
    }


}