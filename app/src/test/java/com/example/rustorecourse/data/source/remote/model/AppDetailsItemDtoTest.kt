package com.example.rustorecourse.data.source.remote.model

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.encodeToString
import org.junit.Test
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
class AppDetailsItemDtoTest {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val testId = "1"
    private val testName = "QuickNote"
    private val testDescription = "Лёгкое приложение для заметок и напоминаний"
    private val testCategory = "GAME"
    private val testIconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg"

    @Test
    fun `should serialize AppDetailsItemDto to JSON correctly with all fields`() {
        val dto = AppDetailsItemDto(
            id = testId,
            name = testName,
            description = testDescription,
            category = testCategory,
            iconUrl = testIconUrl
        )

        val jsonString = json.encodeToString(dto)

        assertTrue(jsonString.contains("\"id\":\"$testId\""))
        assertTrue(jsonString.contains("\"name\":\"$testName\""))
        assertTrue(jsonString.contains("\"description\":\"$testDescription\""))
        assertTrue(jsonString.contains("\"category\":\"$testCategory\""))
        assertTrue(jsonString.contains("\"iconUrl\":\"$testIconUrl\""))
    }

    @Test
    fun `should deserialize JSON to AppDetailsItemDto with all fields`() {
        val jsonString = """
            {
                "id": "$testId",
                "name": "$testName",
                "description": "$testDescription",
                "category": "$testCategory",
                "iconUrl": "$testIconUrl"
            }
        """.trimIndent()

        val dto = json.decodeFromString<AppDetailsItemDto>(jsonString)

        assertEquals(testId, dto.id)
        assertEquals(testName, dto.name)
        assertEquals(testDescription, dto.description)
        assertEquals(testCategory, dto.category)
        assertEquals(testIconUrl, dto.iconUrl)
    }

    @Test
    fun `should serialize and deserialize back to same object with all fields`() {
        val original = AppDetailsItemDto(
            id = testId,
            name = testName,
            description = testDescription,
            category = testCategory,
            iconUrl = testIconUrl
        )

        val jsonString = json.encodeToString(original)
        val deserialized = json.decodeFromString<AppDetailsItemDto>(jsonString)

        assertEquals(original, deserialized)
    }
}