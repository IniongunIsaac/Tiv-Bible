package com.iniongun.tivbible.roomdb.repositoryImpl.fontStyle

import com.iniongun.tivbible.entities.FontStyle
import com.iniongun.tivbible.repository.room.fontStyle.IFontStyleRoom
import com.iniongun.tivbible.roomdb.dao.FontStyleDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class FontStyleRoomImpl @Inject constructor(
    private val fontStyleDao: FontStyleDao
): IFontStyleRoom {

    override fun getAllFontStyles() = fontStyleDao.getAllFontStyles()

    override fun getFontStyleByName(fontStyleName: String) = fontStyleDao.getFontStyleByName(fontStyleName)

    override fun getFontStyleById(fontStyleId: String) = fontStyleDao.getFontStyleById(fontStyleId)

    override fun insertFontStyles(fontStyles: List<FontStyle>) = fontStyleDao.insertFontStyles(fontStyles)

    override fun deleteFontStyles(fontStyles: List<FontStyle>) = fontStyleDao.deleteFontStyles(fontStyles)

}