package com.alexaitken.gildedrose.inventory.categories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.alexaitken.gildedrose.inventory.InventoriedItem;
import com.alexaitken.gildedrose.inventory.InventoriedItem.Quality;
import com.alexaitken.gildedrose.inventory.InventoriedItem.SellIn;
import com.alexaitken.gildedrose.inventory.categories.ConjuredCategory;
import com.alexaitken.gildedrose.inventory.categories.Category;

/**
 * Test suite for the {@link ConjuredCategory} class
 */
public class ConjuredCategoryTest extends SellDueCategoryTest {

	/**
	 * @see BaseCategoryTest#buildSubject(InventoriedItem...)
	 */
	@Override
	protected Category buildSubject(InventoriedItem... items) {
		return new ConjuredCategory(items);
	}

	@Test
	public void shouldDecreaseQuality() {
		InventoriedItem firstItem = new InventoriedItem("First item",
				SellIn.days(3), Quality.rate(5));

		InventoriedItem secondItem = new InventoriedItem("Second item",
				SellIn.days(4), Quality.rate(10));

		ConjuredCategory subject = new ConjuredCategory(firstItem,
				secondItem);

		subject.newDay();

		assertThat(firstItem.getQuality(), is(equalTo(3)));
		assertThat(secondItem.getQuality(), is(equalTo(8)));
	}
	
	@Test
	public void shouldDecreaseQualityFasterWhenOverdue() {
		InventoriedItem firstItem = new InventoriedItem("First item",
				SellIn.days(1), Quality.rate(5));

		InventoriedItem secondItem = new InventoriedItem("Second item",
				SellIn.days(-5), Quality.rate(10));

		ConjuredCategory subject = new ConjuredCategory(firstItem,
				secondItem);

		subject.newDay();

		assertThat(firstItem.getQuality(), is(equalTo(1)));
		assertThat(secondItem.getQuality(), is(equalTo(6)));
	}

}
