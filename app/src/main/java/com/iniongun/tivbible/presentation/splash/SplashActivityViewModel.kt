package com.iniongun.tivbible.presentation.splash

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.state.AppResource
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.repository.room.version.IVersionRepo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.text.WordUtils
import java.io.BufferedReader
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class SplashActivityViewModel @Inject constructor(
    private val context: Context,
    private val preferencesRepo: IAppPreferencesRepo,
    private val versionRepo: IVersionRepo,
    private val testamentRepo: ITestamentRepo,
    private val bookRepo: IBookRepo,
    private val chapterRepo: IChapterRepo,
    private val versesRepo: IVersesRepo
) : BaseViewModel() {

    var oldTestamentId = ""
    var newTestamentId = ""
    var versionId = ""

    init {
        setUpDB()
    }

    private fun getTivBibleJsonData(): List<TivBibleData> {

        val bufferedReader: BufferedReader =
            context.assets.open("tivBibleData.json").bufferedReader()
        val dataString = bufferedReader.use { it.readText() }

        val tivBibleDataListType = object : TypeToken<List<TivBibleData>>() {}.type

        return Gson().fromJson(dataString, tivBibleDataListType)
    }

    private fun setUpDB() {

        if (preferencesRepo.isDBInitialized) {

        } else {
            initializeDB()
        }

    }

    private fun initializeDB() {

        _notificationLiveData.postValue(LiveDataEvent(AppResource.loading()))

        saveVersionsAndTestaments()

    }

    private fun saveVersionsAndTestaments() {

        val versions = listOf(Version(name = "Old"), Version(name = "New"))
        val testaments = listOf(Testament(name = "Old"), Testament(name = "New"))

        compositeDisposable.add(
            Completable.mergeArray(
                versionRepo.insertVersions(versions),
                testamentRepo.insertTestaments(testaments)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getVersionAndTestamentId("Old", "Old", "New")
                }, {
                    _notificationLiveData.postValue(LiveDataEvent(AppResource.failed("An error occurred while trying to populate versions and testaments.")))
                })
        )

    }

    private fun getVersionAndTestamentId(versionName: String, vararg testamentIds: String) {

        compositeDisposable.add(
            Single.zip(versionRepo.getVersionIdByName(versionName),
                testamentRepo.getTestamentIdByName(testamentIds[0]),
                testamentRepo.getTestamentIdByName(testamentIds[1]),
                Function3<String, String, String, Triple<String, String, String>> { versionId, oldTestamentId, newTestamentId ->

                    return@Function3 Triple(versionId, oldTestamentId, newTestamentId)

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    versionId = it.first
                    oldTestamentId = it.second
                    newTestamentId = it.third

                    saveBooksAndChaptersAndVerses()

                }, {
                    _notificationLiveData.postValue(LiveDataEvent(AppResource.failed("An error occurred while trying to retrieve versions and testaments Ids.")))
                })
        )

    }

    private fun saveBooksAndChaptersAndVerses() {
        compositeDisposable.add(
            Observable.just(getTivBibleJsonData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tivBibleDataList ->

                    val bibleBooks = tivBibleDataList.distinctBy { it.book }.sortedBy { it.orderNo }

                    bibleBooks.map { bibleBook ->
                        //get all occurrences of bibleBook
                        val bookOccurrences =
                            tivBibleDataList.filter { it.book.contains(bibleBook.book) }

                        //Get number of verses for book
                        val numOfBookVerses = bookOccurrences.size

                        //get book chapters
                        val bookChapters = bookOccurrences.distinctBy { it.chapter }

                        //get number of chapters for book
                        val numOfChapters = bookChapters.size

                        //get book testament
                        val testamentId = if (bibleBook.testament.equals(
                                "old",
                                true
                            )
                        ) oldTestamentId else newTestamentId

                        //create book object for saving into the database
                        val book = Book(
                            testamentId = testamentId,
                            versionId = versionId,
                            orderNo = bibleBook.orderNo,
                            numberOfChapters = numOfChapters,
                            numberOfVerses = numOfBookVerses,
                            name = WordUtils.capitalize(bibleBook.book.toLowerCase())
                        )

                        //create chapters list for saving into the database
                        val chapters: ArrayList<Chapter> = arrayListOf()

                        //create verses list for saving into the database
                        val verses: ArrayList<Verse> = arrayListOf()

                        bookChapters.map { bookChapter ->

                            val bookChapterVerses =
                                bookOccurrences.filter { it.chapter == bookChapter.chapter }
                                    .distinctBy { it.verse }

                            //get number of book's chapter's verses
                            val numOfBookChapterVerses = bookChapterVerses.size

                            //create chapter object
                            val chapter = Chapter(
                                bookId = book.id,
                                chapterNumber = bookChapter.chapter,
                                numberOfVerses = numOfBookChapterVerses
                            )

                            //add chapter to chapters list
                            chapters.add(chapter)

                            bookChapterVerses.map {
                                //create and add verse to verses list
                                verses.add(
                                    Verse(
                                        chapterId = chapter.id,
                                        number = it.verse,
                                        text = it.text,
                                        hasTitle = it.title.isNotEmpty(),
                                        title = it.title
                                    )
                                )
                            }

                        }

                        //save book, chapters and verses
                        compositeDisposable.add(
                            Completable.mergeArray(
                                bookRepo.insertBooks(listOf(book)),
                                chapterRepo.insertChapters(chapters),
                                versesRepo.addVerses(verses)
                            )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    print("Added book, chapters and verses sequence...")
                                }, {
                                    _notificationLiveData.postValue(
                                        LiveDataEvent(
                                            AppResource.failed(
                                                "An error occurred while trying to add book, chapters and verses sequence..."
                                            )
                                        )
                                    )
                                })
                        )

                    }

                    //save boolean indicating that db has been setup
                    preferencesRepo.isDBInitialized = true

                    _notificationLiveData.postValue(LiveDataEvent(AppResource.success(message = null)))

                }, {
                    _notificationLiveData.postValue(LiveDataEvent(AppResource.failed("An error occurred while trying to populate books, chapters and verses.")))
                })
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResource.failed(throwable.message)))
    }

}