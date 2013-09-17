package me.jeffmay.genomatrix;

import java.awt.event.*;
import javax.swing.*;

public interface GameView {
	public JPanel getLeftPanel();
	public JPanel getTopPanel();
	public ActionListener createCellListener(int x,int y);
}
