package com.example.rustorecourse.data.source.local.database

import com.example.rustorecourse.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryConverterTest {
    private lateinit var converter: CategoryConverter

    @Before
    fun setUp() {
        converter = CategoryConverter()
    }

    @Test
    fun `fromStringList should convert List of Strings to JSON string`() {
        val inputList = listOf("item1", "item2", "item3")
        val result = converter.fromStringList(inputList)
        val expected = """["item1","item2","item3"]"""

        assertEquals(expected, result)
    }

    @Test
    fun `fromStringList should convert empty list to empty JSON array`() {
        val inputList = emptyList<String>()
        val result = converter.fromStringList(inputList)

        assertEquals("[]", result)
    }

    @Test
    fun `fromStringList should convert list with special characters`() {
        val inputList = listOf("item with spaces", "item-with-dash", "item_with_underscore")
        val result = converter.fromStringList(inputList)
        val expected = """["item with spaces","item-with-dash","item_with_underscore"]"""

        assertEquals(expected, result)
    }

    @Test
    fun `toStringList should convert JSON string to List of Strings`() {
        val json = """["item1","item2","item3"]"""
        val result = converter.toStringList(json)
        val expected = listOf("item1", "item2", "item3")

        assertEquals(expected, result)
    }

    @Test
    fun `toStringList should convert empty JSON array to empty list`() {
        val json = "[]"
        val result = converter.toStringList(json)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `toStringList should handle JSON with special characters`() {
        val json = """["item with spaces","item-with-dash","item_with_underscore"]"""
        val result = converter.toStringList(json)
        val expected = listOf("item with spaces", "item-with-dash", "item_with_underscore")

        assertEquals(expected, result)
    }

    @Test
    fun `fromStringList and toStringList should be inverse operations`() {
        val originalList = listOf("test1", "test2", "test3")
        val json = converter.fromStringList(originalList)
        val resultList = converter.toStringList(json)

        assertEquals(originalList, resultList)
    }

    @Test
    fun `fromCategory should convert Category enum to string name`() {
        val categories = listOf(
            Category.APP to "APP",
            Category.GAME to "GAME"
        )

        categories.forEach { (category, expectedName) ->
            val result = converter.fromCategory(category)

            assertEquals(expectedName, result)
        }
    }

    @Test
    fun `fromCategory should return empty string when category is null`() {
        val result = converter.fromCategory(null)

        assertEquals("", result)
    }

    @Test
    fun `toCategory should convert valid string name to Category enum`() {
        val categoryNames = listOf(
            "APP" to Category.APP,
            "GAME" to Category.GAME
        )

        categoryNames.forEach { (name, expectedCategory) ->
            val result = converter.toCategory(name)

            assertEquals(expectedCategory, result)
        }
    }

    @Test
    fun `toCategory should return null for empty string`() {
        val result = converter.toCategory("")

        assertNull(result)
    }

    @Test
    fun `fromStringList with large list should work efficiently`() {
        val largeList = (1..1000).map { "item_$it" }
        val result = converter.fromStringList(largeList)

        assertTrue(result.isNotEmpty())
        assertTrue(result.startsWith("["))
        assertTrue(result.endsWith("]"))
    }

    @Test
    fun `toStringList with large JSON should work efficiently`() {
        val items = (1..1000).map { "\"item_$it\"" }.joinToString(",")
        val json = "[$items]"
        val result = converter.toStringList(json)

        assertEquals(1000, result.size)
        assertEquals("item_1", result.first())
        assertEquals("item_1000", result.last())
    }
}