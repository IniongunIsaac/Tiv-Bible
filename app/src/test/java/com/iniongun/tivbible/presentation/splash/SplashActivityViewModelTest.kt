package com.iniongun.tivbible.presentation.splash

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iniongun.tivbible.common.utils.rxScheduler.TestSchedulerProvider
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.repoFake.AppPreferenceImpl
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.testament.ITestamentRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import com.iniongun.tivbible.repository.room.version.IVersionRepo
import com.iniongun.tivbible.utils.getOrAwaitValue
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
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
        splashActivityViewModel = SplashActivityViewModel(
            context,
            testSchedulerProvider,
            AppPreferenceImpl(),
            versionRepo,
            testamentRepo,
            bookRepo,
            chapterRepo,
            versesRepo
        )

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun viewModelInit_shouldSetAppResultToLoading_whenDBHasNotBeenInitialized() {

//        every { appPreferencesRepo.isDBInitialized } returns false
//
//        appPreferencesRepo.isDBInitialized = false

        assertEquals(AppResult.loading<Unit>(), splashActivityViewModel.notificationLiveData.getOrAwaitValue().getContentIfNotHandled())
    }

}