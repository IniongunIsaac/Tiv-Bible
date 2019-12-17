package com.iniongun.tivbible.roomdb.suite

import com.iniongun.tivbible.roomdb.repositoryImpl.audioSpeed.AudioSpeedRepoImplTest
import com.iniongun.tivbible.roomdb.repositoryImpl.book.BookRepoImplTest
import com.iniongun.tivbible.roomdb.repositoryImpl.verse.VersesRepoImplTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Isaac Iniongun on 2019-12-17.
 * For Tiv Bible project.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(AudioSpeedRepoImplTest::class, BookRepoImplTest::class, VersesRepoImplTest::class)
class RepoImplSuite