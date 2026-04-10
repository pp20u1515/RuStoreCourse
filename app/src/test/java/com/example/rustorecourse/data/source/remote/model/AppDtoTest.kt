package com.example.rustorecourse.data.source.remote.model

import com.example.rustorecourse.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppDtoTest {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val testId = "1"
    private val testName = "QuickNote"
    private val testDeveloper = "QuickSoft Inc."
    private val testCategory = Category.APP
    private val testAgeRating = 3
    private val testSize = 8.2f
    private val testIconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg"
    private val testScreenshotUrlList = listOf(
        "https://fastly.picsum.photos/id/2/200/200.jpg",
        "https://fastly.picsum.photos/id/3/200/200.jpg",
        "https://fastly.picsum.photos/id/4/200/200.jpg"
    )
    private val testDescription = "QuickNote — практичное приложение для хранения заметок"

    @Test
    fun `should serialize AppDto to JSON correctly with all fields`() {
        val dto = AppDto(
            id = testId,
            name = testName,
            developer = testDeveloper,
            category = testCategory,
            ageRating = testAgeRating,
            size = testSize,
            iconUrl = testIconUrl,
            screenshotUrlList = testScreenshotUrlList,
            description = testDescription
        )
        val jsonString = json.encodeToString(dto)

        assertTrue(jsonString.contains("\"id\":\"$testId\""))
        assertTrue(jsonString.contains("\"name\":\"$testName\""))
        assertTrue(jsonString.contains("\"developer\":\"$testDeveloper\""))
        assertTrue(jsonString.contains("\"category\":\"${testCategory.name}\""))
        assertTrue(jsonString.contains("\"ageRating\":$testAgeRating"))
        assertTrue(jsonString.contains("\"size\":$testSize"))
        assertTrue(jsonString.contains("\"iconUrl\":\"$testIconUrl\""))
        assertTrue(jsonString.contains("\"screenshotUrlList\":[\"${testScreenshotUrlList[0]}\",\"${testScreenshotUrlList[1]}\",\"${testScreenshotUrlList[2]}\"]"))
        assertTrue(jsonString.contains("\"description\":\"$testDescription\""))
    }

    @Test
    fun `should deserialize JSON to AppDto with all fields`() {
        val jsonString = """
            {
                "id": "$testId",
                "name": "$testName",
                "developer": "$testDeveloper",
                "category": "${testCategory.name}",
                "ageRating": $testAgeRating,
                "size": $testSize,
                "iconUrl": "$testIconUrl",
                "screenshotUrlList": ["${testScreenshotUrlList[0]}", "${testScreenshotUrlList[1]}", "${testScreenshotUrlList[2]}"],
                "description": "$testDescription"
            }
        """.trimIndent()

        val dto = json.decodeFromString<AppDto>(jsonString)

        assertEquals(testId, dto.id)
        assertEquals(testName, dto.name)
        assertEquals(testDeveloper, dto.developer)
        assertEquals(testCategory, dto.category)
        assertEquals(testAgeRating, dto.ageRating)
        assertEquals(testSize, dto.size)
        assertEquals(testIconUrl, dto.iconUrl)
        assertEquals(testScreenshotUrlList, dto.screenshotUrlList)
        assertEquals(testDescription, dto.description)
    }

    @Test
    fun `should serialize and deserialize back to same object with all fields`() {
        val original = AppDto(
            id = testId,
            name = testName,
            developer = testDeveloper,
            category = testCategory,
            ageRating = testAgeRating,
            size = testSize,
            iconUrl = testIconUrl,
            screenshotUrlList = testScreenshotUrlList,
            description = testDescription
        )

        val jsonString = json.encodeToString(original)
        val deserialized = json.decodeFromString<AppDto>(jsonString)

        assertEquals(original, deserialized)
    }
}