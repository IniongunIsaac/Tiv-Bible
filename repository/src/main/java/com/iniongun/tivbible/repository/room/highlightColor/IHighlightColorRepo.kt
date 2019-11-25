package com.iniongun.tivbible.repository.room.highlightColor

import com.iniongun.tivbible.entities.HighlightColor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IHighlightColorRepo {

    fun getAllHighlightColors(): Observable<List<HighlightColor>>

    fun getHighlightColorById(highlightColorId: String): Single<HighlightColor>

    fun insertHighlightColors(highlightColors: List<HighlightColor>): Completable

    fun deleteHighlightColors(highlightColors: List<HighlightColor>): Completable

}