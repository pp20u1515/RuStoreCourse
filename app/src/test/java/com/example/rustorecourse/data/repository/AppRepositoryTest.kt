package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.local.mapper.toEntity
import com.example.rustorecourse.data.source.local.service.AppDaoService
import com.example.rustorecourse.data.source.remote.INetworkOperations
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.model.Category
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryTest {
    private val mockNetwork: INetworkOperations = mockk()
    private val mockLocalDaoService: AppDaoService = mockk()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var repository: AppRepository

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
        screenshotUrlList = emptyList()
    )

    private val testAppDetailsItem = AppDetailsItem(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = "App",
        iconUrl = "https://example.com/icon.png"
    )

    @Before
    fun setUp() {
        repository = AppRepository(
            appNetwork = mockNetwork,
            localDaoService = mockLocalDaoService,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        clearMocks(mockNetwork, mockLocalDaoService)
    }

    @Test
    fun `getRemoteListOfApps should return success result from network`() = runTest {
        val expectedResult = Result.success(listOf(testAppDetailsItem))
        coEvery { mockNetwork.getListOfApps() } returns expectedResult

        val result = repository.getRemoteListOfApps()

        assert(result.isSuccess)
        assert(result.getOrNull() == expectedResult.getOrNull())
        coVerify(exactly = 1) { mockNetwork.getListOfApps() }
    }

    @Test
    fun `getRemoteListOfApps should return failure result when network fails`() = runTest {
        val expectedError = Exception("Network error")
        val expectedResult = Result.failure<List<AppDetailsItem>>(expectedError)
        coEvery { mockNetwork.getListOfApps() } returns expectedResult

        val result = repository.getRemoteListOfApps()

        assert(result.isFailure)
        assert(result.exceptionOrNull() == expectedError)
        coVerify(exactly = 1) { mockNetwork.getListOfApps() }
    }

    @Test
    fun `getRemoteApp should return success result from network`() = runTest {
        val expectedResult = Result.success(testApp)
        coEvery { mockNetwork.getAppDetails(testAppId) } returns expectedResult

        val result = repository.getRemoteApp(testAppId)

        assert(result.isSuccess)
        assert(result.getOrNull() == testApp)
        coVerify(exactly = 1) { mockNetwork.getAppDetails(testAppId) }
    }

    @Test
    fun `getRemoteApp should return failure result when network fails`() = runTest {
        val expectedError = Exception("Network error")
        val expectedResult = Result.failure<App>(expectedError)
        coEvery { mockNetwork.getAppDetails(testAppId) } returns expectedResult

        val result = repository.getRemoteApp(testAppId)

        assert(result.isFailure)
        assert(result.exceptionOrNull() == expectedError)
        coVerify(exactly = 1) { mockNetwork.getAppDetails(testAppId) }
    }

    @Test
    fun `getApp should return app from local database when available`() = runTest {
        val localEntity = testApp.toEntity()
        coEvery { mockLocalDaoService.getLocalApp(testAppId) } returns flowOf(localEntity)

        coEvery { mockNetwork.getAppDetails(any()) } returns Result.success(testApp)

        val result = repository.getApp(testAppId)

        assert(result.isSuccess)
        assert(result.getOrNull() == testApp)

        coVerify(exactly = 0) { mockNetwork.getAppDetails(any()) }
        coVerify(exactly = 1) { mockLocalDaoService.getLocalApp(testAppId) }
    }

    @Test
    fun `getApp should fetch from network when app not in local database`() = runTest {
        coEvery { mockLocalDaoService.getLocalApp(testAppId) } returns flowOf(null)

        val networkResult = Result.success(testApp)
        coEvery { mockNetwork.getAppDetails(testAppId) } returns networkResult

        coEvery { mockLocalDaoService.insertAppDetails(any()) } returns Unit

        val result = repository.getApp(testAppId)

        assert(result.isSuccess)
        assert(result.getOrNull() == testApp)

        coVerify(exactly = 1) { mockNetwork.getAppDetails(testAppId) }
        coVerify(exactly = 1) { mockLocalDaoService.getLocalApp(testAppId) }
        coVerify(exactly = 1) { mockLocalDaoService.insertAppDetails(any()) }
    }

    @Test
    fun `getApp should not save to database when network returns error`() = runTest {
        coEvery { mockLocalDaoService.getLocalApp(testAppId) } returns flowOf(null)

        val networkError = Exception("Network error")
        val networkResult = Result.failure<App>(networkError)
        coEvery { mockNetwork.getAppDetails(testAppId) } returns networkResult

        val result = repository.getApp(testAppId)

        assert(result.isFailure)
        assert(result.exceptionOrNull() == networkError)

        coVerify(exactly = 0) { mockLocalDaoService.insertAppDetails(any()) }
        coVerify(exactly = 1) { mockNetwork.getAppDetails(testAppId) }
    }

    @Test
    fun `getApp should use ioDispatcher when saving to database`() = runTest {
        coEvery { mockLocalDaoService.getLocalApp(testAppId) } returns flowOf(null)
        coEvery { mockNetwork.getAppDetails(testAppId) } returns Result.success(testApp)
        coEvery { mockLocalDaoService.insertAppDetails(any()) } returns Unit

        repository.getApp(testAppId)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { mockLocalDaoService.insertAppDetails(any()) }
    }
}