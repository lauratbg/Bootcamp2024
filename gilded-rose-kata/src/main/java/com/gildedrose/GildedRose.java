package com.gildedrose;

class GildedRose {
	Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			
			calculateQuality(item);

			calculateSellIn(item);

			
		}
	}

	private void calculateSellIn(Item item) {
		String name = item.name;
		if (!name.equals("Sulfuras, Hand of Ragnaros")) {
			item.sellIn = item.sellIn - 1;
		}
	}

	private void calculateQuality(Item item) {
		String name = item.name;
		int quality = item.quality;
		
		//conjurados
		if(name.contains("Conjured")) {
			item.quality = quality - 2;
		}
		else if (!name.equals("Aged Brie")
				&& !name.equals("Backstage passes to a TAFKAL80ETC concert")
				) {
			if (quality > 0) {
				if (!name.equals("Sulfuras, Hand of Ragnaros")) {
					item.quality = quality - 1;
				}
			}
		} else {
			if (quality < 50) {
				item.quality = quality + 1;

				if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
					if (item.sellIn < 11) {
						int quality2 = item.quality;
						if (quality2 < 50) {
							item.quality = quality2 + 1;
						}
					}

					if (item.sellIn < 6) {
						int quality3 = item.quality;
						if (quality3 < 50) {
							item.quality = quality3 + 1;
						}
					}
				}
			}
		}
		if (item.sellIn < 0) {
			int quality4 = item.quality;
			if (!name.equals("Aged Brie")) {
				if (!name.equals("Backstage passes to a TAFKAL80ETC concert")) {
					if (quality4 > 0) {
						if (!name.equals("Sulfuras, Hand of Ragnaros")) {
							item.quality = quality4 - 1;
						}
					}
				} else {
					item.quality = quality4 - quality4;
				}
			} else {
				if (quality4 < 50) {
					item.quality = quality4 + 1;
				}
			}
		}
	}
}
