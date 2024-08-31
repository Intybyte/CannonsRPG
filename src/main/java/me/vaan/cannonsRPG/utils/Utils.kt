package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.source.XpSource
import me.vaan.cannonsRPG.auraSkills.CannonSkill
import kotlin.reflect.KClass

object Utils {
    val sourceMap = HashMap<KClass<out XpSource>, XpSource>()

    inline fun <reified T : XpSource> firstSource(): T {
        return sourceMap.getOrPut(T::class) {
            CannonSkill.GUNNERY.sources.filterIsInstance<T>().first()
        } as T
    }
}