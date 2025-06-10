package me.vaan.cannonsRPG.auraSkills.sources

import dev.aurelium.auraskills.api.source.CustomSource
import dev.aurelium.auraskills.api.source.SourceValues
import org.bukkit.entity.EntityType

class CannonKillSource : CustomSource {
    private val sourceValues: SourceValues
    private val type: EntityType

    constructor(sourceValues: SourceValues, type: EntityType) : super(sourceValues) {
        this.sourceValues = sourceValues
        this.type = type
    }

    fun getEntityType() : EntityType = type
}