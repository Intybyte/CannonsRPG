package me.vaan.cannonsRPG.utils

import dev.aurelium.auraskills.api.registry.NamespacedId

object Storage {
    const val PLUGIN_NAME = "cannonsrpg"
    const val PREFIX = "§6[§fCannons§bRPG§6]§f "
    val GUNNERY_KEY = NamespacedId.of(PLUGIN_NAME, "gunnery")!!
}