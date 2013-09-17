package me.jeffmay.genomatrix;

import java.awt.*;

public class Cell implements Evaluatable {
	protected static Color defaultColor=Color.GRAY;
	protected int value;
	protected Color color;

	public Cell(int val,int rgb) {
		value=val;
		color=new Color(rgb);
	}
	public Cell(int val,int r,int g,int b) {
		value=val;
		color=new Color(r,g,b);
	}
	public Cell(int val,Color c) {
		this(val,c.getRGB());
	}
	public Cell(int val) {
		this(val,defaultColor);
	}
	public Cell() {
		this(0,defaultColor);
	}

	public int compareTo(Evaluatable o) {
		return value-o.getValue();
	}

	public int getValue() {
		return value;
	}
	
	public Color getColor() {
		return color;
	}

	public void setValue(int val) {
		value=val;
	}

	public void setColor(Color col) {
		color=col;
	}
}
