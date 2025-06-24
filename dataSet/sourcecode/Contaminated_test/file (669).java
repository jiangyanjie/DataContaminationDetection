package com.alexaitken.gildedrose.inventory.categories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.alexaitken.gildedrose.inventory.InventoriedItem;
import com.alexaitken.gildedrose.inventory.InventoriedItem.Quality;
import com.alexaitken.gildedrose.inventory.InventoriedItem.SellIn;
import com.alexaitken.gildedrose.inventory.categories.DefaultCategory;
import com.alexaitken.gildedrose.inventory.categories.Category;

/**
 * Test suite for the {@link DefaultCategory} class
 */
public class DefaultCategoryTest extends SellDueCategoryTest {

	/**
	 * @see BaseCategoryTest#buildSubject(InventoriedItem...)
	 */
	@Override
	protected Category buildSubject(InventoriedItem... items) {
		return new DefaultCategory(items);
	}

	@Test
	public void shouldDecrementQuality() {
		InventoriedItem firstItem = new InventoriedItem("First item",
				SellIn.days(3), Quality.rate(5));

		InventoriedItem secondItem = new InventoriedItem("Second item",
				SellIn.days(4), Quality.rate(10));

		DefaultCategory subject = new DefaultCategory(firstItem,
				secondItem);

		subject.newDay();

		assertThat(firstItem.getQuality(), is(equalTo(4)));
		assertThat(secondItem.getQuality(), is(equalTo(9)));
	}

	@Test
	public void shouldDecrementQualityRapidlyWhenOverdue() {
		InventoriedItem firstItem = new InventoriedItem("First item",
				SellIn.days(1), Quality.rate(5));

		InventoriedItem secondItem = new InventoriedItem("Second item",
				SellIn.days(-1), Quality.rate(10));

		DefaultCategory subject = new DefaultCategory(firstItem,
				secondItem);

		subject.newDay();

		assertThat(firstItem.getQuality(), is(equalTo(3)));
		assertThat(secondItem.getQuality(), is(equalTo(8)));
	}

	@Test
	public void shouldNeverDecrementQualityBelowZero() {
		InventoriedItem firstItem = new InventoriedItem("First item",
				SellIn.days(3), Quality.rate(0));

		InventoriedItem secondItem = new InventoriedItem("Second item",
				SellIn.days(-1), Quality.rate(1));

		DefaultCategory subject = new DefaultCategory(firstItem,
				secondItem);

		subject.newDay();

		assertThat(firstItem.getQuality(), is(equalTo(0)));
		assertThat(secondItem.getQuality(), is(equalTo(0)));
	}

}
