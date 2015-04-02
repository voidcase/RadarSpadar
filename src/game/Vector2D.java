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

	public Vector2D add(Vector2D vector) {
		float rx = x + vector.x;
		float ry = y + vector.y;
		return new Vector2D(rx,ry);
	}
	
	public Vector2D scale(float scalar) {
		float rx = x*scalar;
		float ry = y*scalar;
		return new Vector2D(rx,ry);
	}
	
	public Vector2D normalize() {
		float rx = x/(float)Math.sqrt(x*x + y*y);
		float ry = y/(float)Math.sqrt(x*x + y*y);
		return new Vector2D(rx,ry);
	}
}
