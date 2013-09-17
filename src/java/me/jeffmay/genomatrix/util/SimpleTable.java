package me.jeffmay.genomatrix.util;

import java.awt.*;
import javax.swing.*;

public class SimpleTable extends JPanel {
	private int numcols;
	private int numrows;
	private JLabel topLabel;
	private JLabel leftLabel;
	private JLabel[] rowLabels;
	private JLabel[] colLabels;
	private GridBagLayout layout;
	protected JTable table;
	protected JScrollPane tablePane;

	public SimpleTable(String rowtitle,
					   String coltitle,
					   String[] rownames,
					   String[] colnames) {
		super();
		if(rowtitle == null) rowtitle = "";
		if(coltitle == null) coltitle = "";
		if(rownames == null) {
			rownames = new String[1];
			rownames[0] = "";
		}
		if(colnames == null) {
			colnames = new String[1];
			colnames[0] = "";
		}
		initialize(rowtitle, coltitle, rownames, colnames);
	}

	public SimpleTable(String coltitle, String[] colnames) {
		this(null,coltitle,null,colnames);
	}

	public SimpleTable(String coltitle) {
		this(null,coltitle,null,null);
	}

	public SimpleTable() {
		this(null,null,null,null);
	}

	private void initialize(String rt,String ct,String[] rows,String[] cols) {
		numcols = cols.length;
		numrows = rows.length;

		// Init column title
		topLabel = new JLabel(ct);

		// Init row title
		leftLabel = new JLabel(rt);

		// Init row labels
		rowLabels = new JLabel[numrows];
		for(int i=0; i<rows.length; i++) {
			rowLabels[i] = new JLabel(rows[i]);
		}

		// Init column labels
		colLabels = new JLabel[numcols];
		for(int i=0; i<cols.length; i++) {
			colLabels[i] = new JLabel(cols[i]);
		}

		// Init Table
		table = new JTable(numrows, numcols);
		tablePane = new JScrollPane(table,
			JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		GridBagConstraints c = new GridBagConstraints();
		layout = new GridBagLayout();
		setLayout(layout);

		// Top label
		c.gridheight = 1;
		c.gridwidth = c.REMAINDER;
		c.gridx = 2;
		c.gridy = 0;
		layout.addLayoutComponent(topLabel, c);
		add(topLabel);

		// Left label
		c.gridwidth = 1;
		c.gridheight = c.REMAINDER;
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 5;
		layout.addLayoutComponent(leftLabel, c);
		add(leftLabel);
		c.ipadx = 0;

		// Col Labels
		c.gridheight = 1;
		c.gridy = 1;
		for(int x=0; x<numcols; x++) {
			c.gridx = x+2;
			layout.addLayoutComponent(colLabels[x], c);
			add(colLabels[x]);
		}

		// Row Labels
		c.fill = c.EAST;
		c.gridx = 1;
		for(int y=0; y<numrows; y++) {
			c.gridy = y+2;
			layout.addLayoutComponent(rowLabels[y], c);
			add(rowLabels[y]);
		}

		// Table
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = c.REMAINDER;
		c.gridwidth = c.REMAINDER;
		layout.addLayoutComponent(table, c);
		add(table);
	}

	public void setValueAt(Object o, int row, int col) {
		table.setValueAt(o,row,col);
	}
}
