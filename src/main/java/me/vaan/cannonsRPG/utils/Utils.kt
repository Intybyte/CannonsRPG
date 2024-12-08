package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.AuraSkillsApi
import dev.aurelium.auraskills.api.ability.AbilityContext
import dev.aurelium.auraskills.api.ability.CustomAbility
import dev.aurelium.auraskills.api.source.XpSource
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import kotlin.reflect.KClass

object Utils {
    val sourceMap = HashMap<KClass<out XpSource>, XpSource>()
    val abilityContext = AbilityContext(CannonsRPG.auraSkills)

    inline fun <reified T : XpSource> firstSource(): T {
        return sourceMap.getOrPut(T::class) {
            CannonSkill.GUNNERY.sources.filterIsInstance<T>().first()
        } as T
    }

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