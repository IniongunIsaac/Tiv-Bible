package com.iniongun.tivbible.common.utils

/**
 * Created by Isaac Iniongun on 04/05/2020.
 * For Tiv Bible project.
 */

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }