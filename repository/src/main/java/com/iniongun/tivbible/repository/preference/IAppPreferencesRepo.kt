package com.iniongun.tivbible.repository.preference

import com.iniongun.tivbible.entities.Verse

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IAppPreferencesRepo {

    var isDBInitialized: Boolean

    var currentTheme: String

    var testPreferenceVerse: Verse

}