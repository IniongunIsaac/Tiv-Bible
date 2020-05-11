package com.iniongun.tivbible.common.utils

/**
 * Created by Isaac Iniongun on 04/05/2020.
 * For Tiv Bible project.
 */

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

object Constants {
    const val LORDS_PRAYER_TITLE = "Msen u Ter wase"
    const val CREED_TITLE = "Akaa a Puekarahar"
    const val COMMANDMENTS_TITLE = "Atindi a Pue"
    const val ABOUT_TITLE = "About"
}