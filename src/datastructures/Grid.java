package datastructures;

import game.Vector2D;

public class Grid {
	private Vector2D[] horizontalStartVectors;
	private Vector2D[] horizontalEndVectors;
	private Vector2D[] verticalStartVectors;
	private Vector2D[] verticalEndVectors;
	private int gridSpace;
	private int buffer = 1;
	private float xStart;
	private float xEnd;
	private float yStart;
	private float yEnd;
	private int nrOfHoriLines;
	private int nrOfVertLines;
	
	public Grid(int gridSpace, int screenWidth, int screenHeight) {
		int nrOfHoriLines = screenWidth/gridSpace + 2*buffer;
		int nrOfVertLines = screenHeight/gridSpace + 2*buffer;
		this.gridSpace = gridSpace;
		
		horizontalStartVectors = new Vector2D[nrOfHoriLines];
		horizontalEndVectors = new Vector2D[nrOfHoriLines];
		verticalStartVectors = new Vector2D[nrOfVertLines];
		verticalEndVectors = new Vector2D[nrOfVertLines];

		xStart = -gridSpace;
		xEnd = (nrOfVertLines - buffer)*gridSpace;
		for (int i = 0; i < nrOfHoriLines; i++) {
			float y = (i - buffer)*gridSpace;
			horizontalStartVectors[i] = new Vector2D(xStart, y);
			horizontalEndVectors[i] = new Vector2D(xEnd, y);
		}
		
		yStart = -gridSpace;
		yEnd = (nrOfHoriLines - buffer)*gridSpace;
		for (int i = 0; i < nrOfVertLines; i++) {
			float x = (i - buffer)*gridSpace;
			verticalStartVectors[i] = new Vector2D(x, yStart);
			verticalEndVectors[i] = new Vector2D(x, yEnd);
		}
	}
	
	public void translate(float dx, float dy) {
		float newXStart = (xStart + dx) % (-gridSpace);
		float newYStart = (yStart + dy) % (-gridSpace);
		dx = newXStart - xStart;
		dy = newYStart - yStart;

		xStart += dx;
		xEnd += dx;
		Vector2D shiftVector = new Vector2D(dx, dy);
		for (int i = 0; i < nrOfHoriLines; i++) {
			horizontalStartVectors[i].add(shiftVector);
			horizontalEndVectors[i].add(shiftVector);
		}
		
		yStart += dy;
		yEnd += dy;
		for (int i = 0; i < nrOfVertLines; i++) {
			verticalStartVectors[i].add(shiftVector);
			verticalEndVectors[i].add(shiftVector);
		}
	}
	
	public Vector2D[] getHorizontalStartVectors() {
		return horizontalStartVectors;
	}

	public Vector2D[] getHorizontalEndVectors() {
		return horizontalEndVectors;
	}

	public Vector2D[] getVerticalStartVectors() {
		return verticalStartVectors;
	}

	public Vector2D[] getVerticalEndVectors() {
		return verticalEndVectors;
	}



}