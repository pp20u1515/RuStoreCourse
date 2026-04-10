package com.example.rustorecourse.data.source.local.service

import com.example.rustorecourse.data.source.local.database.AppDatabase
import com.example.rustorecourse.data.source.local.database.AppDetailsDao
import com.example.rustorecourse.data.source.local.entity.AppEntity
import com.example.rustorecourse.domain.model.Category
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppDaoServiceTest {
    private val mockDatabase: AppDatabase = mockk()
    private val mockAppDetailsDao: AppDetailsDao = mockk()

    private lateinit var appDaoService: AppDaoService

    // Тестовые данные
    private val testAppId = "1"

    private val testAppEntity = AppEntity(
        id = testAppId,
        name = "QuickNote",
        description = "Test description",
        category = Category.APP,
        iconUrl = "https://example.com/icon.png",
        developer = "QuickSoft Inc.",
        ageRating = 3,
        size = 8.2f,
        screenshotUrlList = emptyList(),
        isInWishList = false
    )

    @Before
    fun setUp() {
        every { mockDatabase.appDetailsDao() } returns mockAppDetailsDao

        appDaoService = AppDaoService(mockDatabase)
    }

    @After
    fun tearDown() {
        clearMocks(mockDatabase, mockAppDetailsDao)
    }

    @Test
    fun `getLocalApp should return flow of AppEntity from database`() = runTest {
        val expectedFlow = flowOf(testAppEntity)
        every { mockAppDetailsDao.getAppDetails(testAppId) } returns expectedFlow

        val result = appDaoService.getLocalApp(testAppId)

        assert(result == expectedFlow)

        val entity = result.first()
        assert(entity == testAppEntity)

        verify(exactly = 1) { mockAppDetailsDao.getAppDetails(testAppId) }
    }

    @Test
    fun `getLocalApp should return flow of null when app not found`() = runTest {
        val expectedFlow = flowOf<AppEntity?>(null)
        every { mockAppDetailsDao.getAppDetails(testAppId) } returns expectedFlow

        val result = appDaoService.getLocalApp(testAppId)

        assert(result == expectedFlow)

        val entity = result.first()
        assert(entity == null)

        verify(exactly = 1) { mockAppDetailsDao.getAppDetails(testAppId) }
    }

    @Test
    fun `getLocalApp should return flow with correct app data`() = runTest {
        val expectedFlow = flowOf(testAppEntity)
        every { mockAppDetailsDao.getAppDetails(testAppId) } returns expectedFlow

        val result = appDaoService.getLocalApp(testAppId)
        val entity = result.first()

        assert(entity?.id == testAppId)
        assert(entity?.name == "QuickNote")
        assert(entity?.developer == "QuickSoft Inc.")

        verify(exactly = 1) { mockAppDetailsDao.getAppDetails(testAppId) }
    }

    @Test
    fun `insertAppDetails should call database insert with correct entity`() {
        every { mockAppDetailsDao.insertAppDetails(testAppEntity) } returns Unit

        appDaoService.insertAppDetails(testAppEntity)

        verify(exactly = 1) { mockAppDetailsDao.insertAppDetails(testAppEntity) }
    }

    @Test
    fun `insertAppDetails should work with different app entities`() {
        val anotherAppEntity = testAppEntity.copy(id = "another-id", name = "Another App")
        every { mockAppDetailsDao.insertAppDetails(any()) } returns Unit

        appDaoService.insertAppDetails(testAppEntity)
        appDaoService.insertAppDetails(anotherAppEntity)

        verify(exactly = 1) { mockAppDetailsDao.insertAppDetails(testAppEntity) }
        verify(exactly = 1) { mockAppDetailsDao.insertAppDetails(anotherAppEntity) }
    }

    @Test
    fun `insertAppDetails should handle multiple inserts correctly`() {
        every { mockAppDetailsDao.insertAppDetails(any()) } returns Unit

        repeat(5) { index ->
            val entity = testAppEntity.copy(id = "app-$index")
            appDaoService.insertAppDetails(entity)
        }

        verify(exactly = 5) { mockAppDetailsDao.insertAppDetails(any()) }
    }

    @Test
    fun `toggleWishlist should call database updateWishListStatus with correct parameters`() = runTest {
        val testStatus = true
        coEvery { mockAppDetailsDao.updateWishListStatus(testAppId, testStatus) } returns Unit

        appDaoService.toggleWishlist(testAppId, testStatus)

        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(testAppId, testStatus) }
    }

    @Test
    fun `toggleWishlist should work with false status`() = runTest {
        val testStatus = false
        coEvery { mockAppDetailsDao.updateWishListStatus(testAppId, testStatus) } returns Unit

        appDaoService.toggleWishlist(testAppId, testStatus)

        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(testAppId, testStatus) }
    }

    @Test
    fun `toggleWishlist should toggle status multiple times`() = runTest {
        coEvery { mockAppDetailsDao.updateWishListStatus(any(), any()) } returns Unit

        appDaoService.toggleWishlist(testAppId, true)
        appDaoService.toggleWishlist(testAppId, false)
        appDaoService.toggleWishlist(testAppId, true)

        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(testAppId, true) }
        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(testAppId, false) }
        coVerify(exactly = 2) { mockAppDetailsDao.updateWishListStatus(testAppId, true) }
    }

    @Test
    fun `toggleWishlist should work for different app ids`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"
        coEvery { mockAppDetailsDao.updateWishListStatus(any(), any()) } returns Unit

        appDaoService.toggleWishlist(appId1, true)
        appDaoService.toggleWishlist(appId2, true)

        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(appId1, true) }
        coVerify(exactly = 1) { mockAppDetailsDao.updateWishListStatus(appId2, true) }
    }

    @Test
    fun `getWishListStatus should return true when app is in wishlist`() = runTest {
        coEvery { mockAppDetailsDao.receiveWishList(testAppId) } returns true

        val result = appDaoService.getWishListStatus(testAppId)

        assert(result == true)
        coVerify(exactly = 1) { mockAppDetailsDao.receiveWishList(testAppId) }
    }

    @Test
    fun `getWishListStatus should return false when app is not in wishlist`() = runTest {
        coEvery { mockAppDetailsDao.receiveWishList(testAppId) } returns false

        val result = appDaoService.getWishListStatus(testAppId)

        assert(result == false)
        coVerify(exactly = 1) { mockAppDetailsDao.receiveWishList(testAppId) }
    }

    @Test
    fun `getWishListStatus should return correct status for different apps`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"

        coEvery { mockAppDetailsDao.receiveWishList(appId1) } returns true
        coEvery { mockAppDetailsDao.receiveWishList(appId2) } returns false

        val result1 = appDaoService.getWishListStatus(appId1)
        val result2 = appDaoService.getWishListStatus(appId2)

        assert(result1 == true)
        assert(result2 == false)

        coVerify(exactly = 1) { mockAppDetailsDao.receiveWishList(appId1) }
        coVerify(exactly = 1) { mockAppDetailsDao.receiveWishList(appId2) }
    }
}