class GildedRose(
    val items: List<Item>
) {


    fun updateQuality() {
        for (i in 0..items.size - 1) {
            val item = items[i]

            if (item.name.contains("sulfuras", ignoreCase = true)) {
                continue
            }

            if (item.name.contains("aged brie", ignoreCase = true)) {
                item.sellIn--
                if (item.quality < 50) item.quality++
                continue
            }

            if (item.name.contains("backstage passes", ignoreCase = true)) {
                item.sellIn--

                if (item.sellIn > 10) item.quality++
                else if (item.sellIn < 0) item.quality = 0
                else if (item.sellIn in 6..10) if (item.quality > 48) item.quality = 50 else item.quality += 2
                else if (item.quality > 47) item.quality = 50 else item.quality += 3

                continue
            }

            if (item.name.contains("conjured", ignoreCase = true)) {
                item.sellIn--
                when (item.sellIn >= 0) {
                    true -> if (item.quality <= 2) item.quality = 0 else item.quality -= 2
                    false -> if (item.quality <= 4) item.quality = 0 else item.quality -= 4
                }
                continue
            } else if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                if (items[i].quality > 0) {
                    items[i].quality = items[i].quality - 1
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            items[i].sellIn = items[i].sellIn - 1

            if (items[i].sellIn < 0 && item.name != "conjured") {
                if (items[i].name != "Aged Brie") {
                    if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].quality > 0) {
                            items[i].quality = items[i].quality - 1
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality
                    }
                }
            }
        }
    }
}