package com.iniongun.tivbible.preferences.utils

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by Isaac Iniongun on 2019-12-08.
 * For Tiv Bible project.
 */


fun <T>getTypeToken(): Type = object : TypeToken<T>(){}.type