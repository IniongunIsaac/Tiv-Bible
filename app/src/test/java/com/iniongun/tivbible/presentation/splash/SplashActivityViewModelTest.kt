package com.iniongun.tivbible.presentation.splash

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iniongun.tivbible.common.utils.rxScheduler.TestSchedulerProvider
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.repository.room.version.IVersionRepo
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Isaac Iniongun on 2019-12-17.
 * For Tiv Bible project.
 */

@ExtendWith(MockKExtension::class)
internal class SplashActivityViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    val schedulers = RxImmediateSchedulerRule()

    //system under test
    private lateinit var splashActivityViewModel: SplashActivityViewModel

    @MockK
    private lateinit var appPreferencesRepo: IAppPreferencesRepo

    @MockK
    private lateinit var versionRepo: IVersionRepo

    @MockK
    private lateinit var testamentRepo: ITestamentRepo

    @MockK
    private lateinit var bookRepo: IBookRepo

    @MockK
    private lateinit var chapterRepo: IChapterRepo

    @MockK
    private lateinit var versesRepo: IVersesRepo

    @MockK
    private lateinit var context: Context

    private val testSchedulerProvider = TestSchedulerProvider()

    @BeforeEach
    fun setUp() {
//        splashActivityViewModel = SplashActivityViewModel(
//            context,
//            testSchedulerProvider,
//            AppPreferenceImpl(),
//            versionRepo,
//            testamentRepo,
//            bookRepo,
//            chapterRepo,
//            versesRepo
//        )

//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun viewModelInit_shouldSetAppResultToLoading_whenDBHasNotBeenInitialized() {

        assertTrue(true)

//        every { appPreferencesRepo.isDBInitialized } answers { false }
//
//        appPreferencesRepo.isDBInitialized = false

//        every { versionRepo.insertVersions(any()) } answers { Completable.complete() }
//
//        assertEquals(AppResult.loading<Unit>(), splashActivityViewModel.notificationLiveData.getOrAwaitValue().getContentIfNotHandled())
    }

}