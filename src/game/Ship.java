package game;

public abstract class Ship {
	
	protected String name = "o";
	protected Vector2D pos = new Vector2D(0,0);
	protected double angle = 0;
	//protected Vector2D vel;
	
	public abstract void move();
	
	public void setPos(Vector2D pos){
		this.pos = pos;
	}

	public void setAngle(double angle){
		this.angle = angle;
	}

	public Vector2D getPos(){
		return pos;
	}

	public double getAngle(){
		return angle;
	}
	
	public void burn(int amount){
		//vel.add(dir);
	}

	public String toString(){
		return name;
	}
}