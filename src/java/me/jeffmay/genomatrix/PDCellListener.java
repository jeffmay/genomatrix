package me.jeffmay.genomatrix;

import java.awt.event.*;

public class PDCellListener implements ActionListener {
	PDView view;
	final int x;
	final int y;

	public PDCellListener(PDView v,int x,int y) {
		view=v;
		this.x=x;
		this.y=y;
	}

	public void actionPerformed(ActionEvent e) {
		view.displayBox(x,y);
	}
}
