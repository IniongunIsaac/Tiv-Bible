package com.iniongun.tivbible.preferences.utils

import com.google.gson.reflect.TypeToken

/**
 * Created by Isaac Iniongun on 2019-12-08.
 * For Tiv Bible project.
 */


fun <T>getTypeToken() = object : TypeToken<T>(){}.type