import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.junit.Test;

public class MockHello {

	@SuppressWarnings("rawtypes")
	@Test
	public void testMockito() {
		Iterator iterator = mock(Iterator.class);
		when(iterator.next()).thenReturn("hello ").thenReturn("world");
		String result = iterator.next() + " " + iterator.next();

		System.out.println(result);
		assertNotNull(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testArgMock() {
		Comparable c = mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		assertEquals(-1, c.compareTo(5));
	}

}
