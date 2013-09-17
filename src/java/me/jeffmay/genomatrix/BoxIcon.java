package me.jeffmay.genomatrix;

import java.awt.*;
import javax.swing.*;

public class BoxIcon implements Icon {

	static int defaultArc=12;
	static int defaultSize=44;
	private int arc;
	private int size;
	private Color color;

	public BoxIcon() {
		this(Color.GRAY);
	}

	public BoxIcon(Color col) {
		size=defaultSize;
		arc=defaultArc;
		color=col;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.fillRoundRect(x,y,size,size,arc,arc);
        g2d.dispose();
    }
    
    public int getIconWidth() {
        return size;
    }
    
    public int getIconHeight() {
        return size;
    }

	public Color getColor() {
		return color;
	}

	public void setColor(Color col) {
		color = col;
	}
}
