class GildedRose(
    val items: List<Item>
) {
    fun updateQuality() {
        for (item in items) {
            if (item.name.contains("sulfuras", ignoreCase = true)) {
                continue
            }

            item.sellIn--

            if (item.name.contains("aged brie", ignoreCase = true)) {
                if (item.quality < 50) item.quality++
                continue
            }

            if (item.name.contains("backstage passes", ignoreCase = true)) {

                if (item.sellIn > 10) item.quality++
                else if (item.sellIn < 0) item.quality = 0
                else if (item.sellIn in 6..10) if (item.quality > 48) item.quality = 50 else item.quality += 2
                else if (item.quality > 47) item.quality = 50 else item.quality += 3

                continue
            }

            if (item.name.contains("conjured", ignoreCase = true)) {
                when (item.sellIn >= 0) {
                    true -> if (item.quality <= 2) item.quality = 0 else item.quality -= 2
                    false -> if (item.quality <= 4) item.quality = 0 else item.quality -= 4
                }
                continue
            }

            if (item.quality > 0) {
                item.quality = item.quality - 1
            }

            if (item.sellIn < 0 && item.quality > 0) {
                item.quality = item.quality - 1
            }
        }
    }
}