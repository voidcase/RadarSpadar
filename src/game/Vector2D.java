package game;

public class Vector2D {
    private float x, y;

    public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
    }
    
    public float getX() {
		return x;
    }
    
    public float getY() {
		return y;
    }

	public void add(Vector2D vector) {
		x = x + vector.x;
		y = y + vector.y;
	}
	
	public void scale(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public Vector2D normalize() {
		//TODO implement
		return this;
	}
}
