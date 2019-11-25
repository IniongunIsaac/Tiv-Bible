package com.iniongun.tivbible.roomdb.repositoryImpl.highlight

import com.iniongun.tivbible.entities.Highlight
import com.iniongun.tivbible.repository.room.highlight.IHighlightRepo
import com.iniongun.tivbible.roomdb.dao.HighlightDao
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class HighlightRepoImpl @Inject constructor(
    private val highlightDao: HighlightDao
): IHighlightRepo {

    override fun getAllHighlights() = highlightDao.getAllHighlights()

    override fun getHighlightById(highlightId: String) = highlightDao.getHighlightById(highlightId)

    override fun getHighlightByDate(highlightDate: OffsetDateTime) = highlightDao.getHighlightByDate(highlightDate)

    override fun insertHighlights(highlights: List<Highlight>) = highlightDao.insertHighlights(highlights)

    override fun deleteHighlights(highlights: List<Highlight>) = highlightDao.deleteHighlights(highlights)

}