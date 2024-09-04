package me.vaan.cannonsRPG.auraSkills.sources

import dev.aurelium.auraskills.api.source.CustomSource
import dev.aurelium.auraskills.api.source.SourceValues

class CannonDamageSource : CustomSource {
    private val sourceValues: SourceValues
    private val explosionMultiplier: Double
    private val directMultiplier: Double

    constructor(sourceValues: SourceValues, explosionMultiplier: Double, directMultiplier: Double) : super(sourceValues) {
        this.sourceValues = sourceValues
        this.explosionMultiplier = explosionMultiplier
        this.directMultiplier = directMultiplier
    }

    fun getExplosionMultiplier() : Double {
        return explosionMultiplier
    }

    fun getDirectMultiplier() : Double {
        return directMultiplier
    }
}
