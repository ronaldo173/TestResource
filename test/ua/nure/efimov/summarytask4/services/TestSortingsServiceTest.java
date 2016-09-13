package ua.nure.efimov.summarytask4.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ua.nure.efimov.summarytask4.beans.SortingsBean;

public class TestSortingsServiceTest {

	@Test
	public void testSortTypesInit() {

		new TestSortingsService().sortTypesInit();

		List<SortingsBean> sortingsTypes = TestSortingsService.sortingsTypes;
		Assert.assertNotNull(sortingsTypes);
		Assert.assertTrue(sortingsTypes.size() > 0);
	}

}
