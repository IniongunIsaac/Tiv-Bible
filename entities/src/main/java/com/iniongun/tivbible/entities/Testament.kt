package com.iniongun.tivbible.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
class Testament(@PrimaryKey val id: String = UUID.randomUUID().toString(), val name: String)