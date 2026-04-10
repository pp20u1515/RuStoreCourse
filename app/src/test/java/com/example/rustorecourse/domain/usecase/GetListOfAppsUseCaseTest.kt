package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.AppDetailsItem
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
class GetListOfAppsUseCaseTest {
    private val mockAppRepository: IAppRepository = mockk()

    private lateinit var getListOfAppsUseCase: GetListOfAppsUseCase

    // Тестовые данные
    private val testApp1 = AppDetailsItem(
        id = "1",
        name = "QuickNote",
        description = "Лёгкое приложение для заметок и напоминаний",
        category = "Производительность",
        iconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg"
    )

    private val testApp2 = AppDetailsItem(
        id = "2",
        name = "Calmalist",
        description = "Списки дел с расслабляющими напоминаниями",
        category = "Производительность",
        iconUrl = "https://fastly.picsum.photos/id/238/200/200.jpg"
    )

    private val testApp3 = AppDetailsItem(
        id = "3",
        name = "FitRiver",
        description = "Трекер прогулок и пробежек",
        category = "Здоровье и фитнес",
        iconUrl = "https://fastly.picsum.photos/id/239/200/200.jpg"
    )

    private val testAppList = listOf(testApp1, testApp2, testApp3)

    @Before
    fun setUp() {
        getListOfAppsUseCase = GetListOfAppsUseCase(mockAppRepository)
    }

    @After
    fun tearDown() {
        clearMocks(mockAppRepository)
    }

    @Test
    fun `invoke should return success result with list of apps when repository returns success`() = runTest {
        val expectedResult = Result.success(testAppList)
        coEvery { mockAppRepository.getRemoteListOfApps() } returns expectedResult

        val result = getListOfAppsUseCase.invoke()

        assertTrue(result.isSuccess)
        assertEquals(testAppList, result.getOrNull())
        assertEquals(3, result.getOrNull()?.size)
        coVerify(exactly = 1) { mockAppRepository.getRemoteListOfApps() }
    }

    @Test
    fun `invoke should return success with correct app data`() = runTest {
        coEvery { mockAppRepository.getRemoteListOfApps() } returns Result.success(testAppList)

        val result = getListOfAppsUseCase.invoke()

        assertTrue(result.isSuccess)
        val apps = result.getOrNull()
        assertEquals("QuickNote", apps?.get(0)?.name)
        assertEquals("Производительность", apps?.get(0)?.category)
        assertEquals("Calmalist", apps?.get(1)?.name)
        assertEquals("FitRiver", apps?.get(2)?.name)
        assertEquals("Здоровье и фитнес", apps?.get(2)?.category)
    }

    @Test
    fun `invoke should preserve all app fields correctly`() = runTest {
        val completeApp = AppDetailsItem(
            id = "test-id",
            name = "Complete App",
            description = "Full description with details",
            category = "Games",
            iconUrl = "https://example.com/icon.png"
        )
        coEvery { mockAppRepository.getRemoteListOfApps() } returns Result.success(listOf(completeApp))

        val result = getListOfAppsUseCase.invoke()

        assertTrue(result.isSuccess)
        val app = result.getOrNull()?.first()
        assertEquals("test-id", app?.id)
        assertEquals("Complete App", app?.name)
        assertEquals("Full description with details", app?.description)
        assertEquals("Games", app?.category)
        assertEquals("https://example.com/icon.png", app?.iconUrl)
    }

    @Test
    fun `invoke should return failure result when repository returns failure`() = runTest {
        val expectedError = Exception("Network error")
        val expectedResult = Result.failure<List<AppDetailsItem>>(expectedError)
        coEvery { mockAppRepository.getRemoteListOfApps() } returns expectedResult

        val result = getListOfAppsUseCase.invoke()

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
        coVerify(exactly = 1) { mockAppRepository.getRemoteListOfApps() }
    }
}