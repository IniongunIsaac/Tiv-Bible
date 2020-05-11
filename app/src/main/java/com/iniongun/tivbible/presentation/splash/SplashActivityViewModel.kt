package com.iniongun.tivbible.presentation.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.Constants.COMMANDMENTS_TITLE
import com.iniongun.tivbible.common.utils.Constants.CREED_TITLE
import com.iniongun.tivbible.common.utils.Constants.LORDS_PRAYER_TITLE
import com.iniongun.tivbible.common.utils.ScreenSize.*
import com.iniongun.tivbible.common.utils.getDeviceScreenSize
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.common.utils.theme.ThemeConstants.*
import com.iniongun.tivbible.common.utils.theme.ThemeHelper
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.audioSpeed.IAudioSpeedRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.fontStyle.IFontStyleRepo
import com.iniongun.tivbible.repository.room.highlightColor.IHighlightColorRepo
import com.iniongun.tivbible.repository.room.other.IOtherRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.repository.room.theme.IThemeRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.repository.room.version.IVersionRepo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function3
import org.apache.commons.text.WordUtils
import timber.log.Timber
import java.io.BufferedReader
import java.util.*
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class SplashActivityViewModel @Inject constructor(
    private val context: Context,
    private val schedulerProvider: SchedulerProvider,
    private val preferencesRepo: IAppPreferencesRepo,
    private val versionRepo: IVersionRepo,
    private val testamentRepo: ITestamentRepo,
    private val bookRepo: IBookRepo,
    private val chapterRepo: IChapterRepo,
    private val versesRepo: IVersesRepo,
    private val audioSpeedRepo: IAudioSpeedRepo,
    private val themeRepo: IThemeRepo,
    private val fontStyleRepo: IFontStyleRepo,
    private val settingsRepo: ISettingsRepo,
    private val highlightColorRepo: IHighlightColorRepo,
    private val otherRepo: IOtherRepo
) : BaseViewModel() {

    private var oldTestamentId = ""
    private var newTestamentId = ""
    private var versionId = ""

    private val _startHomeLiveData = MutableLiveData<LiveDataEvent<Boolean>>()
    val startHomeLiveData = _startHomeLiveData as LiveData<LiveDataEvent<Boolean>>

    init {
        setUpDB()
    }

    private fun setUserSavedSettings() {
        compositeDisposable.add(
            settingsRepo.getAllSettings().subscribeOnIoObserveOnUi(schedulerProvider, {
                with(it.first()) {
                    val currentTheme = when(theme.name) {
                        LIGHT.name -> LIGHT
                        DARK.name -> DARK
                        BATTERY_SAVER.name -> BATTERY_SAVER
                        else -> SYSTEM_DEFAULT
                    }
                    ThemeHelper.changeTheme(currentTheme)
                }
            })
        )
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
            setUserSavedSettings()
            _startHomeLiveData.value = LiveDataEvent(true)
        } else {
            initializeDB()
        }

    }

    private fun initializeDB() {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.loading()))
        addFontStylesAndThemesAndAudioSpeeds()
    }

    private fun setupDefaultSettings(data: Triple<AudioSpeed, Theme, FontStyle>) {
        var fontSize = 0
        var lineSpacing = 0

        when(getDeviceScreenSize(context.resources)) {
            SMALL -> {
                fontSize = 12
                lineSpacing = 7
            }

            NORMAL, UNDEFINED -> {
                fontSize = 14
                lineSpacing = 8
            }

            LARGE -> {
                fontSize = 16
                lineSpacing = 9
            }

            XLARGE -> {
                fontSize = 18
                lineSpacing = 10
            }
        }

        val settings = listOf(Setting(fontSize = fontSize, lineSpacing = lineSpacing, fontStyle = data.third, theme = data.second, stayAwake = true, audioSpeed = data.first))
        compositeDisposable.add(
            settingsRepo.insertSettings(settings)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    setUserSavedSettings()
                    saveVersionsAndTestaments()
                })
        )
    }

    private fun addFontStylesAndThemesAndAudioSpeeds() {
        val audioSpeeds = listOf(AudioSpeed(name = "High"), AudioSpeed(name = "Low"), AudioSpeed(name = "Medium"))

        val themes = listOf(Theme(name = "SYSTEM_DEFAULT"), Theme(name = "LIGHT"), Theme(name = "DARK"), Theme(name = "BATTERY_SAVER"))

        val fontStyles = listOf(FontStyle(name = "comfortaa.ttf"), FontStyle(name = "happy_monkey.ttf"), FontStyle(name = "metamorphous.ttf"), FontStyle(name = "roboto.ttf"),
            FontStyle(name = "montserrat.ttf"), FontStyle(name = "amatic_sc.ttf"), FontStyle(name = "inconsolata_expanded.ttf"), FontStyle(name = "indie_flower.ttf"),
            FontStyle(name = "jost.ttf"), FontStyle(name = "lato.ttf"), FontStyle(name = "lobster.ttf"))

        val colors = listOf( HighlightColor(hexCode = android.R.color.transparent),
            HighlightColor(hexCode = R.color.color1), HighlightColor(hexCode = R.color.color2), HighlightColor(hexCode = R.color.color3), HighlightColor(hexCode = R.color.color4), HighlightColor(hexCode = R.color.color5),
            HighlightColor(hexCode = R.color.color6), HighlightColor(hexCode = R.color.color7), HighlightColor(hexCode = R.color.color8), HighlightColor(hexCode = R.color.color9), HighlightColor(hexCode = R.color.color10),
            HighlightColor(hexCode = R.color.color11), HighlightColor(hexCode = R.color.color12), HighlightColor(hexCode = R.color.color13), HighlightColor(hexCode = R.color.color14), HighlightColor(hexCode = R.color.color15),
            HighlightColor(hexCode = R.color.color16), HighlightColor(hexCode = R.color.color17), HighlightColor(hexCode = R.color.color18), HighlightColor(hexCode = R.color.color19), HighlightColor(hexCode = R.color.color20)
        )

        val others = listOf(Other(LORDS_PRAYER_TITLE, "Msen u Yesu tese mbahenev nav la (Mateu 6:9-13)", context.getString(com.iniongun.tivbible.common.R.string.msen_u_ter_wase)),
            Other(CREED_TITLE, "Akaa a Puekarahar a Mbakristu sha won cii ve ne a jighjigh la", context.getString(com.iniongun.tivbible.common.R.string.akaa_a_puekarahar)),
            Other(COMMANDMENTS_TITLE, "Atindi a Aondo a Pue (Ekesodu 20:1-17)", context.getString(com.iniongun.tivbible.common.R.string.atindi_a_pue))
        )

        compositeDisposable.add(
            Completable.mergeArray(
                audioSpeedRepo.insertAudioSpeeds(audioSpeeds),
                themeRepo.insertThemes(themes),
                fontStyleRepo.insertFontStyles(fontStyles),
                highlightColorRepo.insertHighlightColors(colors),
                otherRepo.insertOthers(others)
            ).subscribeOnIoObserveOnUi(schedulerProvider, { getDefaultFontStyleAndThemeAndAudioSpeedIds() })
        )
    }

    private fun getDefaultFontStyleAndThemeAndAudioSpeedIds() {
        compositeDisposable.add(
            Single.zip(
                audioSpeedRepo.getAudioSpeedByName("Medium"),
                themeRepo.getThemeByName("SYSTEM_DEFAULT"),
                fontStyleRepo.getFontStyleByName("comfortaa.ttf"),
                Function3<AudioSpeed, Theme, FontStyle, Triple<AudioSpeed, Theme, FontStyle>> { audioSpeed, theme, fontStyle -> Triple(audioSpeed, theme, fontStyle) }
            ).subscribeOnIoObserveOnUi(schedulerProvider, { setupDefaultSettings(it) })
        )
    }

    private fun saveVersionsAndTestaments() {

        val versions = listOf(Version(name = "Old"), Version(name = "New"))
        val testaments = listOf(Testament(name = "Old"), Testament(name = "New"))

        compositeDisposable.add(
            Completable.mergeArray(
                versionRepo.insertVersions(versions),
                testamentRepo.insertTestaments(testaments)
            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                getVersionAndTestamentId("Old", "Old", "New")
            }) {
                postFailureNotification("An error occurred while trying to populate versions and testaments.")
            }
        )
    }

    private fun getVersionAndTestamentId(versionName: String, vararg testamentIds: String) {

        compositeDisposable.add(
            Single.zip(
                versionRepo.getVersionIdByName(versionName),
                testamentRepo.getTestamentIdByName(testamentIds[0]),
                testamentRepo.getTestamentIdByName(testamentIds[1]),
                Function3<String, String, String, Triple<String, String, String>> { versionId, oldTestamentId, newTestamentId -> Triple(versionId, oldTestamentId, newTestamentId) }
            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                versionId = it.first
                oldTestamentId = it.second
                newTestamentId = it.third

                saveBooksAndChaptersAndVerses()

                }, {
                    postFailureNotification("An error occurred while trying to retrieve versions and testaments Ids.")
                })
        )

    }

    private fun saveBooksAndChaptersAndVerses() {
        compositeDisposable.add(
            Observable.just(getTivBibleJsonData())
                .subscribeOnIoObserveOnUi(schedulerProvider, { tivBibleDataList ->

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
                            name = WordUtils.capitalize(bibleBook.book.toLowerCase(Locale.getDefault()))
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
                                        text = it.text.replace("\t", " ").replace("\n", " "),
                                        hasTitle = it.title.isNotEmpty(),
                                        title = it.title.replace("\t", " ").replace("\n", " ")
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
                            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                                    Timber.e("Added book, chapters and verses sequence...")
                                }, {
                                postFailureNotification("An error occurred while trying to add book, chapters and verses sequence...")
                                })
                        )

                    }

                    //save boolean indicating that db has been setup
                    preferencesRepo.isDBInitialized = true

                    _notificationLiveData.postValue(LiveDataEvent(AppResult.success(message = null)))

                }, {
                    postFailureNotification("An error occurred while trying to populate books, chapters and verses.")
                })
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}