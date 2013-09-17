package me.jeffmay.genomatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoxListener implements ActionListener {
	final int x;
	final int y;
	GameModel2D model;
	Component box;

	public BoxListener(Component source,int x,int y,GameModel2D m) {
		model=m;
		box=source;
		this.x=x;
		this.y=y;
	}

	public void actionPerformed(ActionEvent e) {
		int genome=model.getGenome(x,y);
		Color col=model.getGenomeColor(genome);
		int niceness = 0;
		for(int i=0; i<model.getBitLength(); i++) {
			niceness += Ops.placeBit(genome, i, true);
		}
		String bin=Integer.toBinaryString(genome);
		String hex=Integer.toHexString(genome);
		String msg="x="+x+", y="+y+", genome='"+bin+"' ("+hex+")\n"
					   +"Color="+col+"\nniceness="+niceness;
		BoxIcon icon=new BoxIcon(col);
		JOptionPane.showMessageDialog(box,msg,"Genome Reading:",JOptionPane.INFORMATION_MESSAGE,icon);
	}
}
