package com.iniongun.tivbible.roomdb.repositoryImpl.theme

import com.iniongun.tivbible.entities.Theme
import com.iniongun.tivbible.repository.room.theme.IThemeRepo
import com.iniongun.tivbible.roomdb.dao.ThemeDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class ThemeRepoImpl @Inject constructor(
    private val themeDao: ThemeDao
): IThemeRepo {

    override fun getAllThemes() = themeDao.getAllThemes()

    override fun getThemeById(themeId: String) = themeDao.getThemeById(themeId)

    override fun insertThemes(themes: List<Theme>) = themeDao.insertThemes(themes)

    override fun deleteThemes(themes: List<Theme>) = themeDao.deleteThemes(themes)

}