package javajunittests;
import static org.junit.Assert.*;

import org.junit.Test;

import programmingstructures.YouSuck;

public class JavaTest {

	@Test
	public void test1() {
		fail("Not yet implemented");
	}

	@Test
	public void test2() {
		
		String name[] = {"Tony", "Romo"};
		
		YouSuck ys = new YouSuck();
		ys.main(name);
		
		assertTrue(true);
	}

	@Test
	public void test3() {
		assertTrue(1 == 2);
	}

}
