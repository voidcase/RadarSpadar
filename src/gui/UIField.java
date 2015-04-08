package gui;

public class UIField {

	private int x, y;
	private String text;
	
	public UIField(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
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
