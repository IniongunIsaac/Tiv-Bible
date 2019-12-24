package com.iniongun.tivbible.roomdb.dao

/**
 * Created by Isaac Iniongun on 2019-12-13.
 * For Tiv Bible project.
 */

//@RunWith(AndroidJUnit4::class)
//class HighlightDaoTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var mTivBibleDatabase: TivBibleDatabase
//
//    private lateinit var highlightDao: HighlightDao
//
//    private val testHighlightColor = HighlightColor("000000")
//    private val testVerse = Verse("123", 1, "Ter ngurumun mo", false, "")
//    private val testHighlight = Highlight(OffsetDateTime.now(), testHighlightColor, testVerse)
//
//    @Before
//    internal fun setUp() {
//
//        AndroidThreeTen.init(getApplicationContext())
//
//        mTivBibleDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), TivBibleDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
//
//        highlightDao = mTivBibleDatabase.highlightDao()
//
//    }
//
//    @After
//    internal fun tearDown() {
//        mTivBibleDatabase.close()
//    }
//
//    @Test
//    internal fun getAllHighlights_shouldReturnNoValues_whenNoValuesInserted() {
//
//        val highlights = highlightDao.getAllHighlights().blockingFirst()
//
//        assertTrue(highlights.isNullOrEmpty())
//
//    }
//
//    @Test
//    internal fun getAllHighlights_shouldReturnValues_whenValuesInserted() {
//
//        highlightDao.insertHighlights(testHighlight).blockingAwait()
//
//        val highlights = highlightDao.getAllHighlights().blockingFirst()
//
//        assertTrue(highlights.isNotEmpty())
//        assertEquals(highlights[0].id, testHighlight.id)
//
//    }
//
//    @Test
//    internal fun getHighlightById_shouldReturnNoValue_whenNoValueInserted() {
//
//        assertThrows<EmptyResultSetException> { highlightDao.getHighlightById(testHighlight.id).blockingGet() }
//
//    }
//
//    @Test
//    internal fun getHighlightById_shouldReturnValue_whenValueInserted() {
//
//        highlightDao.insertHighlights(testHighlight).blockingAwait()
//
//        val highlight = highlightDao.getHighlightById(testHighlight.id).blockingGet()
//
//        assertNotNull(highlight)
//        assertEquals(highlight.id, testHighlight.id)
//
//    }
//
//    @Test
//    internal fun deleteHighlights_shouldReturnNoValue_whenValuesInserted_andThenDeleted_andThenRetrieved() {
//
//        highlightDao.insertHighlights(testHighlight).blockingAwait()
//
//        highlightDao.deleteAllHighlights().blockingAwait()
//
//        assertThrows<EmptyResultSetException> { highlightDao.getHighlightById(testHighlight.id).blockingGet() }
//
//    }
//
//}