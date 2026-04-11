package com.example.rustorecourse.data.source.remote.mapper

import com.example.rustorecourse.data.source.remote.model.AppDetailsItemDto
import com.example.rustorecourse.data.source.remote.model.AppDto
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkMapperTest {
    private val testAppId = "1"
    private val testName = "QuickNote"
    private val testDescription = "Лёгкое приложение для заметок и напоминаний"
    private val testCategory = "GAME"
    private val testIconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg"
    private val testDeveloper = "QuickSoft Inc."
    private val testAgeRating = 3
    private val testSize = 8.2f
    private val testScreenshotUrlList = listOf(
        "https://fastly.picsum.photos/id/2/200/200.jpg",
        "https://fastly.picsum.photos/id/3/200/200.jpg",
        "https://fastly.picsum.photos/id/4/200/200.jpg"
    )

    private val testAppDetailsItemDto = AppDetailsItemDto(
        id = testAppId,
        name = testName,
        description = testDescription,
        category = testCategory,
        iconUrl = testIconUrl
    )

    private val testAppDto = AppDto(
        id = testAppId,
        name = testName,
        description = testDescription,
        category = Category.GAME,
        iconUrl = testIconUrl,
        developer = testDeveloper,
        ageRating = testAgeRating,
        size = testSize,
        screenshotUrlList = testScreenshotUrlList
    )

    private val expectedAppDetailsItem = AppDetailsItem(
        id = testAppId,
        name = testName,
        description = testDescription,
        category = testCategory,
        iconUrl = testIconUrl
    )

    private val expectedApp = App(
        id = testAppId,
        name = testName,
        description = testDescription,
        category = Category.GAME,
        iconUrl = testIconUrl,
        developer = testDeveloper,
        ageRating = testAgeRating,
        size = testSize,
        screenshotUrlList = testScreenshotUrlList
    )

    @Test
    fun `toDomain should correctly map AppDetailsItemDto to AppDetailsItem`() {
        val result = testAppDetailsItemDto.toDomain()

        assertEquals(expectedAppDetailsItem, result)
        assertEquals(testAppId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDescription, result.description)
        assertEquals(testCategory, result.category)
        assertEquals(testIconUrl, result.iconUrl)
    }

    @Test
    fun `toDomain should preserve all fields without modification`() {
        val dto = AppDetailsItemDto(
            id = "custom-id",
            name = "Custom Name",
            description = "Custom Description",
            category = "Custom Category",
            iconUrl = "https://custom.icon.url"
        )

        val result = dto.toDomain()

        assertEquals("custom-id", result.id)
        assertEquals("Custom Name", result.name)
        assertEquals("Custom Description", result.description)
        assertEquals("Custom Category", result.category)
        assertEquals("https://custom.icon.url", result.iconUrl)
    }

    @Test
    fun `toDomain should handle long strings`() {
        val longString = "a".repeat(10000)
        val dto = AppDetailsItemDto(
            id = longString,
            name = longString,
            description = longString,
            category = longString.take(100),
            iconUrl = "https://example.com/$longString.png"
        )
        val result = dto.toDomain()

        assertEquals(longString, result.id)
        assertEquals(longString, result.name)
        assertEquals(longString, result.description)
        assertEquals(longString.take(100), result.category)
    }

    @Test
    fun `toDomain should correctly map AppDto to App`() {
        val result = testAppDto.toDomain()

        assertEquals(expectedApp, result)
        assertEquals(testAppId, result.id)
        assertEquals(testName, result.name)
        assertEquals(testDescription, result.description)
        assertEquals(testCategory, result.category)
        assertEquals(testIconUrl, result.iconUrl)
        assertEquals(testDeveloper, result.developer)
        assertEquals(testAgeRating, result.ageRating)
        assertEquals(testSize, result.size)
        assertEquals(testScreenshotUrlList, result.screenshotUrlList)
    }

    @Test
    fun `toDomain should handle large screenshot list`() {
        val largeScreenshotList = (1..100).map { "https://example.com/screen_$it.png" }
        val dto = testAppDto.copy(screenshotUrlList = largeScreenshotList)

        val result = dto.toDomain()

        assertEquals(100, result.screenshotUrlList.size)
        assertEquals(largeScreenshotList, result.screenshotUrlList)
    }

    @Test
    fun `toDomain should handle zero age rating`() {
        val dto = testAppDto.copy(ageRating = 0)
        val result = dto.toDomain()

        assertEquals(0, result.ageRating)
    }

    @Test
    fun `toDomain should handle negative age rating`() {
        val dto = testAppDto.copy(ageRating = -1)
        val result = dto.toDomain()

        assertEquals(-1, result.ageRating)
    }

    @Test
    fun `toDomainList should correctly map list of AppDetailsItemDto to list of AppDetailsItem`() {
        val dtoList = listOf(testAppDetailsItemDto, testAppDetailsItemDto.copy(id = "another-id", name = "Another App"))
        val result = dtoList.toDomainList()

        assertEquals(2, result.size)
        assertEquals(testAppId, result[0].id)
        assertEquals(testName, result[0].name)
        assertEquals("another-id", result[1].id)
        assertEquals("Another App", result[1].name)
    }

    @Test
    fun `toDomainList should return empty list for empty input`() {
        val emptyList = emptyList<AppDetailsItemDto>()
        val result = emptyList.toDomainList()

        assertTrue(result.isEmpty())
        assertEquals(0, result.size)
    }

    @Test
    fun `toDomainList should preserve order of elements`() {
        val dtoList = listOf(
            AppDetailsItemDto("1", "First", "Desc1", "Cat1", "url1"),
            AppDetailsItemDto("2", "Second", "Desc2", "Cat2", "url2"),
            AppDetailsItemDto("3", "Third", "Desc3", "Cat3", "url3")
        )

        val result = dtoList.toDomainList()

        assertEquals("1", result[0].id)
        assertEquals("2", result[1].id)
        assertEquals("3", result[2].id)
        assertEquals("First", result[0].name)
        assertEquals("Second", result[1].name)
        assertEquals("Third", result[2].name)
    }

    @Test
    fun `toDomainList should not modify original list`() {
        val originalList = listOf(testAppDetailsItemDto)
        val originalListCopy = originalList.toList()

        originalList.toDomainList()

        assertEquals(originalListCopy, originalList)
    }

    @Test
    fun `toDomain should create new instance each time`() {
        val result1 = testAppDetailsItemDto.toDomain()
        val result2 = testAppDetailsItemDto.toDomain()

        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }

    @Test
    fun `AppDto toDomain should create new instance each time`() {
        // Act
        val result1 = testAppDto.toDomain()
        val result2 = testAppDto.toDomain()

        // Assert
        assertTrue(result1 !== result2)
        assertEquals(result1, result2)
    }
}