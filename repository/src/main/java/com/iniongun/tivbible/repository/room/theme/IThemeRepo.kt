package com.iniongun.tivbible.repository.room.theme

import com.iniongun.tivbible.entities.Theme
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IThemeRepo {

    fun getAllThemes(): Observable<List<Theme>>

    fun getThemeById(themeId: String): Single<Theme>

    fun insertThemes(themes: List<Theme>): Completable

    fun deleteThemes(themes: List<Theme>): Completable

    fun getThemeByName(name: String): Single<Theme>

}