package com.iniongun.tivbible.repository.room.highlight

import com.iniongun.tivbible.entities.Highlight
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.threeten.bp.OffsetDateTime

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IHighlightRepo {

    fun getAllHighlights(): Observable<List<Highlight>>

    fun getHighlightById(highlightId: String): Single<Highlight>

    fun getHighlightByDate(highlightDate: OffsetDateTime): Single<Highlight>

    fun insertHighlights(highlights: List<Highlight>): Completable

    fun deleteHighlights(highlightColors: List<Highlight>): Completable

}