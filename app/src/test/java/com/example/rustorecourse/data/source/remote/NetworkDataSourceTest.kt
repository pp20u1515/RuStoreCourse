package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.model.AppDetailsItemDto
import com.example.rustorecourse.data.source.remote.model.AppDto
import com.example.rustorecourse.domain.model.Category
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkDataSourceTest {
    private val mockApi: INetworkDataSource = mockk()

    private lateinit var networkDataSource: NetworkDataSource

    // Тестовые данные
    private val testAppId = "1"

    private val testAppDetailsItemDto = AppDetailsItemDto(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = "GAME",
        iconUrl = "https://example.com/icon.png"
    )

    private val testAppDto = AppDto(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = Category.GAME,
        iconUrl = "https://example.com/icon.png",
        developer = "QuickSoft Inc.",
        ageRating = 3,
        size = 8.2f,
        screenshotUrlList = listOf(
            "https://example.com/screen1.png",
            "https://example.com/screen2.png"
        )
    )

    private val testAppList = listOf(
        testAppDetailsItemDto,
        testAppDetailsItemDto.copy(
            id = "another-id",
            name = "Another App"
        )
    )

    @Before
    fun setUp() {
        networkDataSource = NetworkDataSource(mockApi)
    }

    @After
    fun tearDown() {
        clearMocks(mockApi)
    }

    @Test
    fun `getListOfApps should return success result when API call succeeds`() = runTest {
        coEvery { mockApi.getListOfApps() } returns testAppList

        val result = networkDataSource.getListOfApps()

        assertTrue(result.isSuccess)
        assertEquals(testAppList, result.getOrNull())
        coVerify(exactly = 1) { mockApi.getListOfApps() }
    }

    @Test
    fun `getListOfApps should return failure result when API throws IOException`() = runTest {
        val ioException = java.io.IOException("Network error")
        coEvery { mockApi.getListOfApps() } throws ioException

        val result = networkDataSource.getListOfApps()

        assertTrue(result.isFailure)
        assertEquals(ioException, result.exceptionOrNull())
        coVerify(exactly = 1) { mockApi.getListOfApps() }
    }

    @Test
    fun `getListOfApps should handle large list of apps correctly`() = runTest {
        val largeList = (1..100).map { index ->
            testAppDetailsItemDto.copy(
                id = "app-$index",
                name = "App $index"
            )
        }
        coEvery { mockApi.getListOfApps() } returns largeList

        val result = networkDataSource.getListOfApps()

        assertTrue(result.isSuccess)
        assertEquals(100, result.getOrNull()?.size)
        coVerify(exactly = 1) { mockApi.getListOfApps() }
    }

    @Test
    fun `getAppDetails should return success result when API call succeeds`() = runTest {
        coEvery { mockApi.getAppDetails(testAppId) } returns testAppDto

        val result = networkDataSource.getAppDetails(testAppId)

        assertTrue(result.isSuccess)
        assertEquals(testAppDto, result.getOrNull())
        coVerify(exactly = 1) { mockApi.getAppDetails(testAppId) }
    }

    @Test
    fun `getAppDetails should return failure result when API throws IOException`() = runTest {
        val ioException = java.io.IOException("Network error")
        coEvery { mockApi.getAppDetails(testAppId) } throws ioException

        val result = networkDataSource.getAppDetails(testAppId)

        assertTrue(result.isFailure)
        assertEquals(ioException, result.exceptionOrNull())
        coVerify(exactly = 1) { mockApi.getAppDetails(testAppId) }
    }

    @Test
    fun `getAppDetails should work with different app ids`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"
        val app1 = testAppDto.copy(id = appId1, name = "App 1")
        val app2 = testAppDto.copy(id = appId2, name = "App 2")

        coEvery { mockApi.getAppDetails(appId1) } returns app1
        coEvery { mockApi.getAppDetails(appId2) } returns app2

        val result1 = networkDataSource.getAppDetails(appId1)
        val result2 = networkDataSource.getAppDetails(appId2)

        assertTrue(result1.isSuccess && result2.isSuccess)
        assertEquals(app1, result1.getOrNull())
        assertEquals(app2, result2.getOrNull())

        coVerify(exactly = 1) { mockApi.getAppDetails(appId1) }
        coVerify(exactly = 1) { mockApi.getAppDetails(appId2) }
    }

    @Test
    fun `getListOfApps and getAppDetails should be independent calls`() = runTest {
        coEvery { mockApi.getListOfApps() } returns testAppList
        coEvery { mockApi.getAppDetails(testAppId) } returns testAppDto

        val listResult = networkDataSource.getListOfApps()
        val detailsResult = networkDataSource.getAppDetails(testAppId)

        assertTrue(listResult.isSuccess && detailsResult.isSuccess)

        coVerify(exactly = 1) { mockApi.getListOfApps() }
        coVerify(exactly = 1) { mockApi.getAppDetails(testAppId) }
    }
}