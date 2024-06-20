package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase GildedRose")
class GildedRoseTest {
	private Item[] items;
	
	@Nested
	@DisplayName("Item genérico")
	class ItemGenerico {
		/*
		 * GIVEN: un elemento fuera de tiempo para venderlo y una calidad = 5
		 * WHEN: se llama al método updatequality() 
		 * THEN: su calidad se degrada 2 unidades
		 */
		@Test
		void testDegrada2Unidades() {
			items = new Item[] { new Item("+5 Dexterity Vest", -1, 5) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(3, app.items[0].quality);
			
			//Comprobación toString()
			assertEquals("+5 Dexterity Vest, -2, 3", app.items[0].toString());
		}

		/*
		 * GIVEN: un item con 5 días para venderlo y calidad = 0 
		 * WHEN: se decrementan ambos valores con el método updateQuality 
		 * THEN: la calidad se mantiene a 0 porque no puede ser un número negativo
		 */
		@Test
		void testCalidadNegativa() {
			items = new Item[] { new Item("+5 Dexterity Vest", 5, 0) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(0, app.items[0].quality);
		}

	}
	
	@Nested
	@DisplayName("Item Aged Brie")
	class AgedBrie {
		/*
		 * GIVEN: un "Aged brie" con 5 días para venderlo y una calidad = 0 
		 * WHEN: se llama al método updatequality() 
		 * THEN: su calidad aumenta 1 unidad
		 */
		@Test
		void testAgedBrie() {
			items = new Item[] { new Item("Aged Brie", 5, 1) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(2, app.items[0].quality);
		}

		/*
		 * GIVEN: un "Aged brie" fuera de tiempo para venderlo y una calidad = 0 
		 * WHEN: se llama al método updatequality() 
		 * THEN: su calidad aumenta 2 unidad
		 */
		@Test
		void testAgedBrieFueraFechaVenta() {
			items = new Item[] { new Item("Aged Brie", -1, 1) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(3, app.items[0].quality);
		}
		
		/*
		 * GIVEN: un "Aged brie" con calidad 50
		 * WHEN: se llama al método updatequality() 
		 * THEN: su calidad no puede aumentar, se queda en 50
		 */
		@Test
		void testCalidadMayor50() {
			items = new Item[] { new Item("Aged Brie", 7 , 50) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(50, app.items[0].quality);
		}
	}
	
	
	@Nested
	@DisplayName("Item Sulfuras")
	class Sulfuras {
		/*
		 * GIVEN: un producto legendario Sulfuras, Hand of Ragnaros
		 * WHEN: se llama al método updatequality() 
		 * THEN: no se modifica su fecha de venta ni se degrada la calidad
		 */
		@Test
		void testSulfuras() {
			items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 7 , 80) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(80, app.items[0].quality);
			assertEquals(7, app.items[0].sellIn);
		}
	}
	
	@Nested
	@DisplayName("Item Conjurados")
	class Conjurados {
		/*
		 * GIVEN: un producto conjurado
		 * WHEN: se llama al método updatequality() 
		 * THEN: la calidad decrementa en 2ud y el día decrementa en 1 ud
		 */
		@Test
		void testSulfuras() {
			items = new Item[] { new Item("Conjured Mana Cake", 7 , 7) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(5, app.items[0].quality);
			assertEquals(6, app.items[0].sellIn);

		}
	}
	
	@Nested
	@DisplayName("Item Entrada al Backstage")
	class EntradaAlBackstage {
		/*
		 * GIVEN: una Entrada al Backstage con 10 días para el concierto
		 * WHEN: se llama al método updatequality() 
		 * THEN: la calidad aumenta en 2 ud
		 */
		@Test
		void testEntradaAlBackstage10días() {
			items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10 , 43) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(45, app.items[0].quality);
		}
		
		/*
		 * GIVEN: una Entrada al Backstage con 5 días para el concierto
		 * WHEN: se llama al método updatequality() 
		 * THEN: la calidad aumenta en 3 ud
		 */
		@Test
		void testEntradaAlBackstage5días() {
			items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5 , 43) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(46, app.items[0].quality);
		}
		
		/*
		 * GIVEN: una Entrada al Backstage tras pasar la fecha del concierto
		 * WHEN: se llama al método updatequality() 
		 * THEN: la calidad es 0
		 */
		@Test
		void testEntradaAlBackstageFueraDeFecha() {
			Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", -1 , 43) };
			GildedRose app = new GildedRose(items);
			app.updateQuality();
			assertEquals(0, app.items[0].quality);
		}

	}
	
	
}
