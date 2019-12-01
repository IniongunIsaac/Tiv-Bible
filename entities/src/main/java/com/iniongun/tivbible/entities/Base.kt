package com.iniongun.tivbible.entities

import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-11-16
 * For Tiv Bible project
 */

open class Base(@PrimaryKey val id: String = UUID.randomUUID().toString())