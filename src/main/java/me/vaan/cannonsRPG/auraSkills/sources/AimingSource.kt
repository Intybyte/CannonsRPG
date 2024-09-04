package me.vaan.cannonsRPG.auraSkills.sources

import dev.aurelium.auraskills.api.source.CustomSource
import dev.aurelium.auraskills.api.source.SourceValues

class AimingSource : CustomSource {
    private val sourceValues: SourceValues
    private val multiplier: Double

    constructor(sourceValues: SourceValues, multiplier: Double) : super(sourceValues) {
        this.sourceValues = sourceValues
        this.multiplier = multiplier
    }

    fun getMultiplier(): Double {
        return multiplier
    }
}