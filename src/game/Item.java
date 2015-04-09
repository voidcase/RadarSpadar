package game;

public abstract class Item {
	protected String name;
	
	public Item(String name) {
		this.name = name;
	}
	
	public abstract void action();

}
