package com.iniongun.tivbible.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Isaac Iniongun on 2019-07-21.
 * For TivBible project.
 */

@Entity
data class RecentSearch(
    @PrimaryKey val text: String
    //@PrimaryKey val id: String = UUID.randomUUID().toString()
)