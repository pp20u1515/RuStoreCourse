package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.model.AppDetailsItemDto
import com.example.rustorecourse.data.source.remote.model.AppDto
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
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
class NetworkOperationsTest {
    private val mockNetworkDataSource: NetworkDataSource = mockk()

    private lateinit var networkOperations: NetworkOperations

    // Тестовые данные
    private val testAppId = "1"

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

    private val testAppDetailsItemDto = AppDetailsItemDto(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = "GAME",
        iconUrl = "https://example.com/icon.png"
    )

    private val testAppDetailsItemDtoList = listOf(
        testAppDetailsItemDto,
        testAppDetailsItemDto.copy(
            id = "another-id",
            name = "Another App"
        )
    )

    private val expectedApp = App(
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

    private val expectedAppDetailsItem = AppDetailsItem(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = "GAME",
        iconUrl = "https://example.com/icon.png"
    )

    private val expectedAppDetailsItemList = listOf(
        expectedAppDetailsItem,
        expectedAppDetailsItem.copy(id = "another-id", name = "Another App")
    )

    @Before
    fun setUp() {
        networkOperations = NetworkOperations(mockNetworkDataSource)
    }

    @After
    fun tearDown() {
        clearMocks(mockNetworkDataSource)
    }

    @Test
    fun `getAppDetails should return success result with mapped App when network call succeeds`() = runTest {
        val networkResult = Result.success(testAppDto)
        coEvery { mockNetworkDataSource.getAppDetails(testAppId) } returns networkResult

        val result = networkOperations.getAppDetails(testAppId)

        assertTrue(result.isSuccess)
        assertEquals(expectedApp, result.getOrNull())
        coVerify(exactly = 1) { mockNetworkDataSource.getAppDetails(testAppId) }
    }

    @Test
    fun `getAppDetails should return failure result when network call fails`() = runTest {
        val expectedError = Exception("Network error")
        val networkResult = Result.failure<AppDto>(expectedError)
        coEvery { mockNetworkDataSource.getAppDetails(testAppId) } returns networkResult

        val result = networkOperations.getAppDetails(testAppId)

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
        coVerify(exactly = 1) { mockNetworkDataSource.getAppDetails(testAppId) }
    }

    @Test
    fun `getAppDetails should handle different app ids correctly`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"
        val appDto1 = testAppDto.copy(id = appId1, name = "App 1")
        val appDto2 = testAppDto.copy(id = appId2, name = "App 2")
        val expectedApp1 = expectedApp.copy(id = appId1, name = "App 1")
        val expectedApp2 = expectedApp.copy(id = appId2, name = "App 2")

        coEvery { mockNetworkDataSource.getAppDetails(appId1) } returns Result.success(appDto1)
        coEvery { mockNetworkDataSource.getAppDetails(appId2) } returns Result.success(appDto2)

        val result1 = networkOperations.getAppDetails(appId1)
        val result2 = networkOperations.getAppDetails(appId2)

        assertTrue(result1.isSuccess && result2.isSuccess)
        assertEquals(expectedApp1, result1.getOrNull())
        assertEquals(expectedApp2, result2.getOrNull())

        coVerify(exactly = 1) { mockNetworkDataSource.getAppDetails(appId1) }
        coVerify(exactly = 1) { mockNetworkDataSource.getAppDetails(appId2) }
    }

    @Test
    fun `getListOfApps should return success result with mapped list when network call succeeds`() = runTest {
        val networkResult = Result.success(testAppDetailsItemDtoList)
        coEvery { mockNetworkDataSource.getListOfApps() } returns networkResult

        val result = networkOperations.getListOfApps()

        assertTrue(result.isSuccess)
        assertEquals(expectedAppDetailsItemList, result.getOrNull())
        coVerify(exactly = 1) { mockNetworkDataSource.getListOfApps() }
    }

    @Test
    fun `getListOfApps should return failure result when network call fails`() = runTest {
        val expectedError = Exception("Network error")
        val networkResult = Result.failure<List<AppDetailsItemDto>>(expectedError)
        coEvery { mockNetworkDataSource.getListOfApps() } returns networkResult

        val result = networkOperations.getListOfApps()

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
        coVerify(exactly = 1) { mockNetworkDataSource.getListOfApps() }
    }

    @Test
    fun `getAppDetails should correctly map all fields from DTO to Domain`() = runTest {
        val completeAppDto = AppDto(
            id = "test-id",
            name = "Complete App",
            description = "Full description",
            category = Category.GAME,
            iconUrl = "https://example.com/icon.png",
            developer = "Developer Name",
            ageRating = 12,
            size = 45.5f,
            screenshotUrlList = listOf("url1", "url2", "url3")
        )
        val expectedCompleteApp = App(
            id = "test-id",
            name = "Complete App",
            description = "Full description",
            category = Category.GAME,
            iconUrl = "https://example.com/icon.png",
            developer = "Developer Name",
            ageRating = 12,
            size = 45.5f,
            screenshotUrlList = listOf("url1", "url2", "url3")
        )

        coEvery { mockNetworkDataSource.getAppDetails("test-id") } returns Result.success(completeAppDto)

        val result = networkOperations.getAppDetails("test-id")

        assertTrue(result.isSuccess)
        assertEquals(expectedCompleteApp, result.getOrNull())
    }

    @Test
    fun `getListOfApps should correctly map all fields from DTO to Domain`() = runTest {
        val completeAppDtoItem = AppDetailsItemDto(
            id = "test-id",
            name = "Complete App",
            description = "Full description",
            category = "GAME",
            iconUrl = "https://example.com/icon.png"
        )
        val expectedCompleteItem = AppDetailsItem(
            id = "test-id",
            name = "Complete App",
            description = "Full description",
            category = "GAME",
            iconUrl = "https://example.com/icon.png"
        )

        coEvery { mockNetworkDataSource.getListOfApps() } returns Result.success(listOf(completeAppDtoItem))

        val result = networkOperations.getListOfApps()

        assertTrue(result.isSuccess)
        assertEquals(listOf(expectedCompleteItem), result.getOrNull())
    }
}