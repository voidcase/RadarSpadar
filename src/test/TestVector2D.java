package test;

import static org.junit.Assert.assertEquals;
import game.Vector2D;

import org.junit.Test;

public class TestVector2D {

	@Test
	public void testNormalize() {
		Vector2D v = new Vector2D(3, 4).normalize();
		Vector2D expected = new Vector2D(3/5, 4/5);
		assertEquals(expected, v);
	}
	
	@Test
	public void testEquals() {
		Vector2D v = new Vector2D(1,1);
		Vector2D expected = new Vector2D(1,1);
		assertEquals(expected, v);
	}

}
