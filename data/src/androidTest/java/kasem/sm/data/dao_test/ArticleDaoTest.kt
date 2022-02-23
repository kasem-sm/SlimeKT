/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.dao_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import javax.inject.Named
import kasem.sm.article.datasource.cache.dao.ArticleDao
import kasem.sm.data.dao_test.utils.FakeData
import kasem.sm.data.dao_test.utils.getOrAwaitValue
import kasem.sm.data.db.SlimeDatabase
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
     * Inject at [kasem.sm.data.dao_test.inject.TestCacheModule]
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
        val getAllArticles = articleDao.getAllArticles().getOrAwaitValue().first()
        assertThat(getAllArticles).isEqualTo(sampleEntity)
    }

    @Test
    fun testInsertArticles() = runTest {
        val articles = listOf(sampleEntity.copy(id = 0), sampleEntity.copy(id = 1))
        articleDao.insert(articles)

        val getAllArticles = articleDao.getAllArticles().getOrAwaitValue()
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

    @Test
    fun testGetPagedArticleByTopic() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first"
            )
        )

        articleDao.insert(articles)

        val pageSize = 3

        val getPagedArticle = articleDao.getTopicPagedArticles(
            topic = "first",
            page = 0,
            pageSize = pageSize
        )

        assertThat(getPagedArticle).doesNotContain(articles[2])
        assertThat(getPagedArticle).contains(articles[0])
        assertThat(getPagedArticle.size).isEqualTo(pageSize)
    }

    @Test
    fun testGetPagedArticle() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first"
            )
        )

        articleDao.insert(articles)

        val pageSize = 3

        val getPagedArticle = articleDao.getPagedArticles(
            page = 0,
            pageSize = pageSize
        )

        assertThat(getPagedArticle).contains(articles[2])
        // As the pageSize is of 3, the third article in the list (starting from index 0)
        // should Not be in page number 0
        assertThat(getPagedArticle).doesNotContain(articles[3])
    }

    @Test
    fun testGetQueriedPagedArticle() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            )
        )

        articleDao.insert(articles)

        val getQueriedPagedArticle = articleDao.getQueriedPagedArticle(
            query = "third",
            page = 0,
            pageSize = 10
        )

        // Only 3 articles contains "third" in their title so the size
        // should be of 3
        assertThat(getQueriedPagedArticle.size).isEqualTo(3)
    }

    @Test
    fun testGetQueriedPagedArticleFromSingleTopic1() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            )
        )

        articleDao.insert(articles)

        val getQueriedPagedArticle = articleDao.getQueriedPagedArticles(
            topic = "first",
            query = "third",
            page = 0,
            pageSize = 10
        )

        // No article has any of the parameter containing "third"
        // where the article's topic is "first"
        assertThat(getQueriedPagedArticle.size).isEqualTo(0)
    }

    @Test
    fun testGetQueriedPagedArticleFromSingleTopic2() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            )
        )

        articleDao.insert(articles)

        val getQueriedPagedArticle = articleDao.getQueriedPagedArticles(
            topic = "first",
            query = "1st",
            page = 0,
            pageSize = 10
        )

        // Articles with topic "first" has a total
        // and title containing "1st" is of size 2
        assertThat(getQueriedPagedArticle.size).isEqualTo(2)
    }

    /**
     * After process death, we need to restore articles
     * till the last page number which was retrieved from
     * savedStateHandle, so we will test if we requested
     * Articles till page number 3 and page size is 3,
     * total articles size should be 9
     * (as we will retrieve all articles till page 3, so 3 (page number) x 3 (page size) = 9)
     */
    @Test
    fun testGetArticlesTillPageProvidedTopicAndSearchQueryIsEmpty() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 8,
                topic = "first",
                title = "at first"
            )
        )

        articleDao.insert(articles)

        val getArticlesTillPage = articleDao.getArticlesTillPage(
            3,
            3
        )

        assertThat(getArticlesTillPage.size).isEqualTo(9)
    }

    /**
     * Assuming that process death happens after user has
     * filtered the list to show articles only from "second" topic,
     * so we will be restoring articles till the retrieved page number from savedState
     * where the topic should be "second" (retrieved from savedState as well)
     */
    @Test
    fun testGetArticlesTillPageProvidedTopicIsNotEmptyAndSearchQueryIs() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 8,
                topic = "first",
                title = "at first"
            )
        )

        articleDao.insert(articles)

        val getArticlesTillPage = articleDao.getTopicArticlesTillPage(
            topic = "second",
            1,
            3
        )

        assertThat(getArticlesTillPage.size).isEqualTo(1)
    }

    /**
     * Assuming that process death happens after the search field
     * contains query "1st" and user has filtered the list to show articles
     * only from "first" topic, so we will be restoring
     * articles till the retrieved page number from savedState
     * where the topic should be "first" and the search query should be "1st"
     * (retrieved from savedState as well)
     */
    @Test
    fun testGetArticlesTillPageProvidedTopicAndSearchQueryIsNotEmpty() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 8,
                topic = "first",
                title = "at first"
            )
        )

        articleDao.insert(articles)

        val getArticlesTillPage = articleDao.getQueriedArticlesTillPage(
            topic = "first",
            query = "1st",
            1,
            3
        )

        assertThat(getArticlesTillPage.size).isEqualTo(2)
    }

    /**
     * Assuming that process death happens after the search field
     * contains query "third", so we will be restoring
     * articles till the retrieved page number from savedState
     * where the search query should be "third"
     * (retrieved from savedState as well)
     */
    @Test
    fun testGetArticlesTillPageProvidedTopicIsEmptyAndSearchQueryIsNot() = runTest {
        val articles = listOf(
            sampleEntity.copy(
                id = 0,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 1,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 2,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 3,
                topic = "second",
                title = "at second"
            ),
            sampleEntity.copy(
                id = 4,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 5,
                topic = "first",
                title = "at first"
            ),
            sampleEntity.copy(
                id = 6,
                topic = "first",
                title = "at 1st"
            ),
            sampleEntity.copy(
                id = 7,
                topic = "third",
                title = "at third"
            ),
            sampleEntity.copy(
                id = 8,
                topic = "first",
                title = "at first"
            )
        )

        articleDao.insert(articles)

        val getArticlesTillPage = articleDao.getQueriedArticlesTillPage(
            query = "third",
            1,
            10
        )

        assertThat(getArticlesTillPage.size).isEqualTo(3)
    }
}
