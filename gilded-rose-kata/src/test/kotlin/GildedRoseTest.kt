import org.junit.Test
import kotlin.test.assertEquals

internal class GildedRoseTest {

    @Test
    fun `updateQuality degrades conjured items twice as fast as normal items`() {
        val conjuredItem = Item(name = "conjured", sellIn = 2, quality = 20)

        GildedRose(listOf(conjuredItem)).runFor(days = 3)

        assertEquals(12, conjuredItem.quality)
    }

    @Test
    fun `updateQuality never degrades sulfuras items`() {
        val sulfurasItem = Item(name = "sulfuras", sellIn = 2, quality = 80)

        GildedRose(listOf(sulfurasItem)).runFor(days = 3)

        assertEquals(80, sulfurasItem.quality)
    }

    @Test
    fun `updateQuality degrades just normal item and do not degrade sulfura`() {
        val sulfurasItem = Item(name = "sulfuras", sellIn = 2, quality = 80)
        val normalItem = Item(name = "normal item", sellIn = 2, quality = 50)

        GildedRose(listOf(sulfurasItem, normalItem)).runFor(days = 3)

        assertEquals(80, sulfurasItem.quality)
        assertEquals(46, normalItem.quality)
    }

    @Test
    fun `updateQuality degrades normal items twice as faster when sell date has passed`() {
        val normalItem = Item(name = "normal item", sellIn = 2, quality = 10)

        GildedRose(listOf(normalItem)).runFor(days = 4)

        assertEquals(4, normalItem.quality)
    }


    @Test
    fun `updateQuality never degrades items when quality is equal to 0`() {
        val normalItem = Item(name = "normal item", sellIn = 2, quality = 2)

        GildedRose(listOf(normalItem)).runFor(days = 4)

        assertEquals(0, normalItem.quality)
        assertEquals(-2, normalItem.sellIn)
    }

    @Test
    fun `updateQuality increase aged brie quality`() {
        val normalItem = Item(name = "aged brie", sellIn = 2, quality = 2)

        GildedRose(listOf(normalItem)).runFor(days = 4)

        assertEquals(6, normalItem.quality)
        assertEquals(-2, normalItem.sellIn)
    }

    @Test
    fun `updateQuality never increase aged brie quality more than 50`() {
        val agedBriItem = Item(name = "aged brie", sellIn = 2, quality = 47)

        GildedRose(listOf(agedBriItem)).runFor(days = 4)

        assertEquals(50, agedBriItem.quality)
        assertEquals(-2, agedBriItem.sellIn)
    }

    @Test
    fun `updateQuality sulfuras never has to be sold`() {
        val sulfurasItem = Item(name = "sulfuras", sellIn = 2, quality = 80)

        GildedRose(listOf(sulfurasItem)).runFor(days = 3)

        assertEquals(80, sulfurasItem.quality)
        assertEquals(2, sulfurasItem.sellIn)
    }

    @Test
    fun `updateQuality increase passes backstage items by 3 when sellin between 0 and 5 days`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 5, quality = 20)

        GildedRose(listOf(passesItem)).runFor(days = 3)

        assertEquals(29, passesItem.quality)
        assertEquals(2, passesItem.sellIn)
    }

    @Test
    fun `updateQuality increase passes backstage items by 2 when sellin between 10 and 6 days`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 10, quality = 20)

        GildedRose(listOf(passesItem)).runFor(days = 2)

        assertEquals(24, passesItem.quality)
        assertEquals(8, passesItem.sellIn)
    }

    @Test
    fun `updateQuality backstage passes quality never increase more than 50`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 10, quality = 50)

        GildedRose(listOf(passesItem)).runFor(days = 2)

        assertEquals(50, passesItem.quality)
        assertEquals(8, passesItem.sellIn)
    }

    @Test
    fun `updateQuality backstage passes drops to zero after concert day`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 2, quality = 50)

        GildedRose(listOf(passesItem)).runFor(days = 3)

        assertEquals(-1, passesItem.sellIn)
        assertEquals(0, passesItem.quality)
    }

    @Test
    fun `updateQuality backstage passes when is concert get higher value`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 10, quality = 10)

        GildedRose(listOf(passesItem)).runFor(days = 10)

        assertEquals(0, passesItem.sellIn)
        assertEquals(36, passesItem.quality)
    }

    @Test
    fun `updateQuality backstage passes increase quality by one when there are more than 10 day until the concert`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 12, quality = 10)

        GildedRose(listOf(passesItem)).runFor(days = 11)

        assertEquals(1, passesItem.sellIn)
        assertEquals(36, passesItem.quality)
    }

    @Test
    fun `updateQuality conjured items quality is never negative`() {
        val conjuredItem = Item(name = "conjured", sellIn = 2, quality = 1)

        GildedRose(listOf(conjuredItem)).runFor(days = 2)

        assertEquals(0, conjuredItem.quality)
    }

    @Test
    fun `updateQuality change value for all kind of items respecting each specific rule`() {
        val passesItem = Item(name = "Backstage passes", sellIn = 12, quality = 10)
        val conjuredItem = Item(name = "conjured", sellIn = 3, quality = 50)
        val sulfurasItem = Item(name = "sulfuras", sellIn = 2, quality = 80)
        val normalItem = Item(name = "normal item", sellIn = 2, quality = 10)
        val agedBriItem = Item(name = "aged brie", sellIn = 2, quality = 40)

        GildedRose(listOf(passesItem, conjuredItem, sulfurasItem, normalItem, agedBriItem)).runFor(days = 5)

        assertEquals(19, passesItem.quality)
        assertEquals(36, conjuredItem.quality)
        assertEquals(80, sulfurasItem.quality)
        assertEquals(2, normalItem.quality)
        assertEquals(45, agedBriItem.quality)
    }
}