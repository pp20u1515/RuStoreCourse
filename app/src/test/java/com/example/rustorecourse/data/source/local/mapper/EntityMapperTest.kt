package com.example.rustorecourse.data.source.local.mapper

import com.example.rustorecourse.data.source.local.entity.AppDetailsItemEntity
import com.example.rustorecourse.data.source.local.entity.AppEntity
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EntityMapperTest {
    // Тестовые данные
    private val testId = "1"
    private val testName = "QuickNote"
    private val testDescription = "Лёгкое приложение для заметок и напоминаний"
    private val testCategory = Category.APP
    private val testIconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg"
    private val testDeveloper = "QuickSoft Inc."
    private val testAgeRating = 3
    private val testSize = 8.2f
    private val testScreenshotUrlList = listOf(
        "https://fastly.picsum.photos/id/2/200/200.jpg",
        "https://fastly.picsum.photos/id/3/200/200.jpg",
        "https://fastly.picsum.photos/id/4/200/200.jpg"
    )

    private val testAppEntity = AppEntity(
        id = testId,
        name = testName,
        developer = testDeveloper,
        category = testCategory,
        ageRating = testAgeRating,
        size = testSize,
        screenshotUrlList = testScreenshotUrlList,
        iconUrl = testIconUrl,
        description = testDescription
    )

    private val testApp = App(
        id = testId,
        name = testName,
        developer = testDeveloper,
        category = testCategory,
        ageRating = testAgeRating,
        size = testSize,
        screenshotUrlList = testScreenshotUrlList,
        iconUrl = testIconUrl,
        description = testDescription
    )

    private val testAppDetailsItemEntity = AppDetailsItemEntity(
        id = testId,
        name = testName,
        description = testDescription,
        category = testCategory.name,
        iconUrl = testIconUrl
    )

    private val testAppDetailsItem = AppDetailsItem(
        id = testId,
        name = testName,
        description = testDescription,
        category = testCategory.name,
        iconUrl = testIconUrl
    )

    @Test
    fun `AppEntity toDomain should correctly map all fields`() {
        val result = testAppEntity.toDomain()

        assertEquals(testApp, result)
        assertEquals(testId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDeveloper, result.developer)
        assertEquals(testCategory, result.category)
        assertEquals(testAgeRating, result.ageRating)
        assertEquals(testSize, result.size)
        assertEquals(testScreenshotUrlList, result.screenshotUrlList)
        assertEquals(testIconUrl, result.iconUrl)
        assertEquals(testDescription, result.description)
    }

    @Test
    fun `AppEntity toDomain should handle large screenshot list`() {
        val largeScreenshotList = (1..100).map { "https://example.com/screen_$it.png" }
        val entity = testAppEntity.copy(screenshotUrlList = largeScreenshotList)
        val result = entity.toDomain()

        assertEquals(100, result.screenshotUrlList.size)
        assertEquals(largeScreenshotList, result.screenshotUrlList)
    }

    @Test
    fun `AppEntity toDomain should handle zero values`() {
        val entity = testAppEntity.copy(ageRating = 0, size = 0f)
        val result = entity.toDomain()

        assertEquals(0, result.ageRating)
        assertEquals(0f, result.size)
    }

    @Test
    fun `AppEntity toDomain should handle negative values`() {
        val entity = testAppEntity.copy(ageRating = -1, size = -5.5f)
        val result = entity.toDomain()

        assertEquals(-1, result.ageRating)
        assertEquals(-5.5f, result.size)
    }

    @Test
    fun `AppDetailsItemEntity toDomain should correctly map all fields`() {
        val result = testAppDetailsItemEntity.toDomain()

        assertEquals(testAppDetailsItem, result)
        assertEquals(testId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDescription, result.description)
        assertEquals(testCategory.name, result.category)
        assertEquals(testIconUrl, result.iconUrl)
    }

    @Test
    fun `AppDetailsItemEntity toDomain should preserve all fields without modification`() {
        val customId = "custom-id"
        val customName = "Custom App"
        val customDescription = "Custom description"
        val customCategory = "Custom Category"
        val customIconUrl = "https://custom.icon.url"

        val entity = AppDetailsItemEntity(
            id = customId,
            name = customName,
            description = customDescription,
            category = customCategory,
            iconUrl = customIconUrl
        )

        val result = entity.toDomain()

        assertEquals(customId, result.id)
        assertEquals(customName, result.name)
        assertEquals(customDescription, result.description)
        assertEquals(customCategory, result.category)
        assertEquals(customIconUrl, result.iconUrl)
    }

    @Test
    fun `App toEntity should correctly map all fields`() {
        val result = testApp.toEntity()

        assertEquals(testAppEntity, result)
        assertEquals(testId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDeveloper, result.developer)
        assertEquals(testCategory, result.category)
        assertEquals(testAgeRating, result.ageRating)
        assertEquals(testSize, result.size)
        assertEquals(testScreenshotUrlList, result.screenshotUrlList)
        assertEquals(testIconUrl, result.iconUrl)
        assertEquals(testDescription, result.description)
    }

    @Test
    fun `App toEntity should handle large screenshot list`() {
        val largeScreenshotList = (1..100).map { "https://example.com/screen_$it.png" }
        val app = testApp.copy(screenshotUrlList = largeScreenshotList)
        val result = app.toEntity()

        assertEquals(100, result.screenshotUrlList.size)
        assertEquals(largeScreenshotList, result.screenshotUrlList)
    }

    @Test
    fun `App toEntity should handle zero values`() {
        val app = testApp.copy(ageRating = 0, size = 0f)
        val result = app.toEntity()

        assertEquals(0, result.ageRating)
        assertEquals(0f, result.size)
    }

    @Test
    fun `AppDetailsItem toEntity should correctly map all fields`() {
        val result = testAppDetailsItem.toEntity()

        assertEquals(testAppDetailsItemEntity, result)
        assertEquals(testId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDescription, result.description)
        assertEquals(testCategory.name, result.category)
        assertEquals(testIconUrl, result.iconUrl)
    }

    @Test
    fun `AppEntity toDomain and back toEntity should preserve data`() {
        val domain = testAppEntity.toDomain()
        val entity = domain.toEntity()

        assertEquals(testAppEntity, entity)
    }

    @Test
    fun `App toEntity and back toDomain should preserve data`() {
        val entity = testApp.toEntity()
        val domain = entity.toDomain()

        assertEquals(testApp, domain)
    }

    @Test
    fun `AppDetailsItemEntity toDomain and back toEntity should preserve data`() {
        val domain = testAppDetailsItemEntity.toDomain()
        val entity = domain.toEntity()

        assertEquals(testAppDetailsItemEntity, entity)
    }

    @Test
    fun `AppDetailsItem toEntity and back toDomain should preserve data`() {
        val entity = testAppDetailsItem.toEntity()
        val domain = entity.toDomain()

        assertEquals(testAppDetailsItem, domain)
    }

    @Test
    fun `AppEntity toDomain should create new instance each time`() {
        val result1 = testAppEntity.toDomain()
        val result2 = testAppEntity.toDomain()

        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }

    @Test
    fun `App toEntity should create new instance each time`() {
        val result1 = testApp.toEntity()
        val result2 = testApp.toEntity()

        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }

    @Test
    fun `AppDetailsItemEntity toDomain should create new instance each time`() {
        val result1 = testAppDetailsItemEntity.toDomain()
        val result2 = testAppDetailsItemEntity.toDomain()

        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }

    @Test
    fun `AppDetailsItem toEntity should create new instance each time`() {
        val result1 = testAppDetailsItem.toEntity()
        val result2 = testAppDetailsItem.toEntity()

        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }
}