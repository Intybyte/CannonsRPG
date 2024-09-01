package me.vaan.cannonsRPG.auraSkills

import dev.aurelium.auraskills.api.mana.CustomManaAbility
import dev.aurelium.auraskills.api.registry.NamespacedId
import me.vaan.cannonsRPG.CannonsRPG
import me.vaan.cannonsRPG.utils.Storage

object CannonManaAbilities {
    val STORM_BLAST = CustomManaAbility
        .builder(NamespacedId.of(Storage.PLUGIN_NAME, "storm_blast"))
        .displayName("Storm Blast")
        .description("Summons a lightning that deal increased damage in a 5x5 area of cannon hit [Right click gunpowder to activate]")!!
        .baseValue(1.0)
        .valuePerLevel(0.5)
        .unlock(6)
        .levelUp(6)
        .maxLevel(0)
        .build()!!

    fun loadManaAbilities() {
        val reg = CannonsRPG.registry()
        reg.registerManaAbility(STORM_BLAST)
    }
}