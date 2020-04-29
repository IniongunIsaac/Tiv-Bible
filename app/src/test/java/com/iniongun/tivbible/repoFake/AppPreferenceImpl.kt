package com.iniongun.tivbible.repoFake

import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo

/**
 * Created by Isaac Iniongun on 2019-12-18.
 * For Tiv Bible project.
 */

class AppPreferenceImpl: IAppPreferencesRepo {
    override var isDBInitialized: Boolean
        get() = false
        set(value) {}
    override var currentVerse: Verse
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
}