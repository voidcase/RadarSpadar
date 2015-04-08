package gui;

public class UIField {

	private int x, y;
	private String text;
	
	public UIField(int xProcent, int yProcent, String text) {
		x = xProcent;
		y = yProcent;
		this.text = text;
		System.out.println(x + ", " + y);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setText(String t){
		text = t;
	}
	
	public void append(String t){
		text = text + t;
	}
	
}
