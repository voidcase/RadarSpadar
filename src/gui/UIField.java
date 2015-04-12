package gui;

public class UIField {
	private String text;
	
	public UIField(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public int getX(RenderPanel parent){
		return 0;
	}
	
	public int getY(RenderPanel parent){
		return 24;
	}
	
	public void setText(String t){
		text = t;
	}
	
	public void append(String t){
		text = text + t;
	}
}
