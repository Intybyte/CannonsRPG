package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.registry.NamespacedId

object Storage {
    const val PLUGIN_NAME = "cannonsrpg"
    const val PREFIX = "§g[§fCannons§bRPG§g]§f "
    val GUNNERY_KEY = NamespacedId.of(PLUGIN_NAME, "gunnery")!!
}