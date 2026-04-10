package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.Category
import com.example.rustorecourse.domain.repository.IAppRepository
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
class GetAppDetailsUseCaseTest {
    private val mockAppRepository: IAppRepository = mockk()

    private lateinit var getAppDetailsUseCase: GetAppDetailsUseCase

    // Тестовые данные
    private val testAppId = "1"

    private val testApp = App(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = Category.APP,
        iconUrl = "https://example.com/icon.png",
        developer = "QuickSoft Inc.",
        ageRating = 3,
        size = 8.2f,
        screenshotUrlList = listOf(
            "https://example.com/screen1.png",
            "https://example.com/screen2.png"
        )
    )

    @Before
    fun setUp() {
        getAppDetailsUseCase = GetAppDetailsUseCase(mockAppRepository)
    }

    @After
    fun tearDown() {
        clearMocks(mockAppRepository)
    }

    @Test
    fun `invoke should return success result with app when repository returns success`() = runTest {
        val expectedResult = Result.success(testApp)
        coEvery { mockAppRepository.getApp(testAppId) } returns expectedResult

        val result = getAppDetailsUseCase.invoke(testAppId)

        assertTrue(result.isSuccess)
        assertEquals(testApp, result.getOrNull())
        coVerify(exactly = 1) { mockAppRepository.getApp(testAppId) }
    }

    @Test
    fun `invoke should return correct app data for different app ids`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"
        val app1 = testApp.copy(id = appId1, name = "App 1")
        val app2 = testApp.copy(id = appId2, name = "App 2")

        coEvery { mockAppRepository.getApp(appId1) } returns Result.success(app1)
        coEvery { mockAppRepository.getApp(appId2) } returns Result.success(app2)

        val result1 = getAppDetailsUseCase.invoke(appId1)
        val result2 = getAppDetailsUseCase.invoke(appId2)

        assertTrue(result1.isSuccess && result2.isSuccess)
        assertEquals(app1, result1.getOrNull())
        assertEquals(app2, result2.getOrNull())

        coVerify(exactly = 1) { mockAppRepository.getApp(appId1) }
        coVerify(exactly = 1) { mockAppRepository.getApp(appId2) }
    }

    @Test
    fun `invoke should preserve all app fields correctly`() = runTest {
        val completeApp = App(
            id = "test-id",
            name = "Complete App",
            description = "Full description with details",
            category = Category.GAME,
            iconUrl = "https://example.com/icon.png",
            developer = "Developer Name",
            ageRating = 12,
            size = 45.5f,
            screenshotUrlList = listOf("url1", "url2", "url3")
        )
        coEvery { mockAppRepository.getApp("test-id") } returns Result.success(completeApp)

        val result = getAppDetailsUseCase.invoke("test-id")

        assertTrue(result.isSuccess)
        val app = result.getOrNull()
        assertEquals("test-id", app?.id)
        assertEquals("Complete App", app?.name)
        assertEquals("Full description with details", app?.description)
        assertEquals("GAMES", app?.category)
        assertEquals("https://example.com/icon.png", app?.iconUrl)
        assertEquals("Developer Name", app?.developer)
        assertEquals(12, app?.ageRating)
        assertEquals(45.5f, app?.size)
        assertEquals(3, app?.screenshotUrlList?.size)
    }

    @Test
    fun `invoke should return failure result when repository returns failure`() = runTest {
        val expectedError = Exception("App not found")
        val expectedResult = Result.failure<App>(expectedError)
        coEvery { mockAppRepository.getApp(testAppId) } returns expectedResult

        val result = getAppDetailsUseCase.invoke(testAppId)

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
        coVerify(exactly = 1) { mockAppRepository.getApp(testAppId) }
    }

    @Test
    fun `invoke should handle empty string as app id`() = runTest {
        val emptyId = ""
        val error = Exception("Invalid app id")
        coEvery { mockAppRepository.getApp(emptyId) } returns Result.failure(error)

        val result = getAppDetailsUseCase.invoke(emptyId)

        assertTrue(result.isFailure)
        coVerify(exactly = 1) { mockAppRepository.getApp(emptyId) }
    }
}