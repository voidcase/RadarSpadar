package game;

public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
    }
    
    public void setPos(Vector2D newPos) {
    	this.x = newPos.x;
		this.y = newPos.y;
    }
    
    public void setX(double x) {
		this.x = x;
    }
    
    public void setY(double y) {
		this.y = y;
    }

    public double getX() {
		return x;
    }
    
    public double getY() {
		return y;
    }
    
    public double distance(Vector2D other){
    	return Math.sqrt(Math.pow(x-other.x,2) + Math.pow(y-other.y,2));
    }

	public Vector2D add(Vector2D vector) {
		double rx = x + vector.x;
		double ry = y + vector.y;
		return new Vector2D(rx,ry);
	}
	
	public Vector2D scale(double scalar) {
		double rx = x*scalar;
		double ry = y*scalar;
		return new Vector2D(rx,ry);
	}
	
	public Vector2D normalize() {
		double rx = x/Math.sqrt(x*x + y*y);
		double ry = y/Math.sqrt(x*x + y*y);
		return new Vector2D(rx,ry);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector2D) {
			Vector2D v = (Vector2D) o;
			return x == v.x && y == v.y;
		}
		return false;
	}
}
