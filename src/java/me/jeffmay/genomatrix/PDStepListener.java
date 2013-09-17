/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jeffmay.genomatrix;

import java.awt.event.*;

/**
 *
 * @author Jeff
 */
public class PDStepListener implements ActionListener {
	PDView view;

	public PDStepListener(PDView v) {
		view=v;
	}

	public void actionPerformed(ActionEvent e) {
		view.refreshMetrics();
	}
}

