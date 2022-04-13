/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.database.dao_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import javax.inject.Named
import kasem.sm.article.datasource_impl.cache.dao.ArticleDao
import kasem.sm.database.dao_test.utils.FakeData
import kasem.sm.database.dao_test.utils.getOrAwaitValue
import kasem.sm.database.db.SlimeDatabase
import kotlin.random.Random
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@SmallTest
class ArticleDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Inject at [kasem.sm.database.dao_test.inject.TestCacheModule]
     */
    @Inject
    @Named("slime_test_db")
    lateinit var database: SlimeDatabase

    private lateinit var articleDao: ArticleDao

    private val sampleEntity = FakeData.sampleEntity

    @Before
    fun setup() {
        hiltRule.inject()
        articleDao = database.articleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertArticle() = runTest {
        articleDao.insert(sampleEntity)
        val getAllArticles = articleDao.getAllArticles("").getOrAwaitValue().first()
        assertThat(getAllArticles).isEqualTo(sampleEntity)
    }

    @Test
    fun testInsertArticles() = runTest {
        val articles = listOf(sampleEntity.copy(id = 0), sampleEntity.copy(id = 1))
        articleDao.insert(articles)

        val getAllArticles = articleDao.getAllArticles("").getOrAwaitValue()
        assertThat(articles[Random.nextInt(0, 1)]).isIn(getAllArticles)
    }

    @Test
    fun testInsertListAndFindOne() = runTest {
        val articles = listOf(sampleEntity.copy(id = 0), sampleEntity.copy(id = 1))
        articleDao.insert(articles)

        val getArticleOfId1 = articleDao.getArticleById(1).getOrAwaitValue()
        assertThat(getArticleOfId1).isEqualTo(articles[1])
    }

    @Test
    fun testGetActiveArticleFromList() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                isActiveInDailyRead = true
            ),
            sampleEntity.copy(id = 1)
        )
        articleDao.insert(articles)

        articleDao.updateIsActiveInDailyReadStatus(active = true, id = 0)

        val getActiveArticle = articleDao.getAllActiveArticles().getOrAwaitValue()
        assertThat(getActiveArticle[0].isActiveInDailyRead).isEqualTo(true)
    }

    @Test
    fun testActiveArticleIsNotToBeShownAgain() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                isShownInDailyRead = true
            ),
            sampleEntity.copy(id = 1)
        )
        articleDao.insert(articles)

        val getActiveArticle = articleDao.getAllArticlesShowInDailyRead().first()
        assertThat(getActiveArticle).isEqualTo(articles[0])
    }
}
