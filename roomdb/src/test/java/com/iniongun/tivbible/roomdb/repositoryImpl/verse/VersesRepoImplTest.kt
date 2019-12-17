package com.iniongun.tivbible.roomdb.repositoryImpl.verse

import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.roomdb.dao.FakeVerseDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Created by Isaac Iniongun on 2019-12-16.
 * For Tiv Bible project.
 */

internal class VersesRepoImplTest {

    //Class under test
    private lateinit var versesRepoImpl: VersesRepoImpl

    private val testVerses = listOf(
        Verse("zg517najh816318asjh", 1, "Aondo gba sha man tar", true, "Kwagh u igbetar"),
        Verse("zg517najh25716dyuasjh", 2, "Aondo gba sha man tar", true, "Kwagh u igbetar"),
        Verse("zg128ghk7najh816318asjh", 3, "Aondo gba sha man tar", true, "Kwagh u igbetar"),
        Verse("zg517najh98hxja816318asjh", 4, "Aondo gba sha man tar", true, "Kwagh u igbetar")
    )

    @BeforeEach
    fun setUp() {

        val fakeVerseDao = FakeVerseDao(testVerses.toMutableList())
        versesRepoImpl = VersesRepoImpl(fakeVerseDao)

    }

    @Test
    fun addVerses_shouldAddVersesSuccessfully() {
        versesRepoImpl.addVerses(testVerses).test().assertComplete()
    }

    @Test
    fun getAllVerses_shouldReturnCorrectVerses() {

        versesRepoImpl.getAllVerses().test().assertValue {
            it.containsAll(testVerses) && it.count() == testVerses.count() && it[0].chapterId == testVerses[0].chapterId
        }

    }

    @Test
    fun getAllVerses_shouldReturnNoVerses_whenNoVersesAdded() {
        versesRepoImpl = VersesRepoImpl(FakeVerseDao())
        versesRepoImpl.getAllVerses().test().assertValue { it.isNullOrEmpty() }
    }

    @Test
    fun getVerseById_shouldReturnQueriedVerse() {
        val verse = testVerses[0]
        versesRepoImpl.getVerseById(verse.id).test().assertValue {
            it == verse && it.id == verse.id && it.chapterId == verse.chapterId && it.number == verse.number
        }
    }

    @Test
    fun getVerseById_shouldThrowException_whenVerseIdNotFound() {

        assertThrows<NoSuchElementException> { versesRepoImpl.getVerseById("123456").blockingGet() }

    }

    @Test
    fun getVersesByText_shouldReturnSearchedVerses() {
        versesRepoImpl.getVersesByText("Aondo").test().assertValue {
            it.count() == 4 && it[0] == testVerses[0]
        }
    }

    @Test
    fun getVersesByText_shouldReturnNoValues_whenSearchTextNotFound() {
        versesRepoImpl.getVersesByText("Aondoxx").test().assertValue {
            it.isNullOrEmpty()
        }
    }

    @Test
    fun getVersesByChapter_shouldReturnQueriedVerse() {
        val verse = testVerses[3]
        versesRepoImpl.getVersesByChapter(verse.chapterId).test().assertValue {
            it.contains(verse) && it.count() == 1
        }
    }

    @Test
    fun deleteVerses_shouldReturnNoValuesAfterDeleteOperation() {
        versesRepoImpl.deleteVerses(testVerses).test().assertComplete()

        versesRepoImpl.getAllVerses().test().assertValue { it.isNullOrEmpty() }
    }
}