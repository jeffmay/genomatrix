package me.jeffmay.genomatrix;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import me.jeffmay.genomatrix.util.*;

public class PDView implements GameView {
    protected JPanel _TopPanel;
	protected JPanel _LeftPanel;
    // Listener
    private ActionListener stepListener;
	// Top panel
	private JPanel topPanel;
	private BoxIcon icon;
	private JLabel niceLbl;
    private JLabel totalNiceLbl;
	// 3 Table Sets
	private SimpleTable m0Table;
	private SimpleTable m1Table;
	private SimpleTable m2TableDD;
	private SimpleTable m2TableDC;
	private SimpleTable m2TableCD;
	private SimpleTable m2TableCC;
	// A Panel for the last table set
	private JPanel m2Panel;
	// The model
	private BoxGameModel model;

	private int x;
	private int y;
	private Component box;

	public PDView(final GameWindow parent, BoxGameModel activeModel) {
		// Set Model
		model = activeModel;

        stepListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.refresh();
                refreshMetrics();
            }
        };
        model.setUpdateListener(stepListener);

		niceLbl = new JLabel();
        totalNiceLbl = new JLabel(String.valueOf(model.getTotalNiceness()));

        _TopPanel = new JPanel();
		_TopPanel.add(new JLabel("Niceness Index ="));
		_TopPanel.add(totalNiceLbl);
        _TopPanel.add(new JLabel("Current cell's niceness ="));
        _TopPanel.add(niceLbl);

		// Initialize Tables
		String[] single = {"Always"};
		String[] options = {"D","C"};

		m0Table = new SimpleTable("First Move", single);

		m1Table = new SimpleTable("Last Move:", options);

		m2TableDD = new SimpleTable("Defect", "Defect", options, options);
		m2TableDC = new SimpleTable("Defect", "Cooperate", options, options);
		m2TableCD = new SimpleTable("Cooperate", "Defect", options, options);
		m2TableCC = new SimpleTable("Cooperate", "Cooperate", options, options);

		// Construct Top Table
		m0Table.setBorder(BorderFactory.createTitledBorder("First Move"));

		// Construct Middle Table
		m1Table.setBorder(BorderFactory.createTitledBorder("1 Move Memory"));

		// Build Low Panel
		m2Panel = new JPanel(new BorderLayout());
        JPanel lblPanel = new JPanel(new GridLayout(3,2));
        lblPanel.add(new JLabel("Opponent plays:"));
        lblPanel.add(new JLabel());
        lblPanel.add(new JLabel());
        lblPanel.add(new JLabel("Genome plays:"));
        lblPanel.add(new JLabel());
        lblPanel.add(new JLabel());
        m2Panel.add(lblPanel,BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridLayout(5,1));
		tablePanel.add(m2TableDD);
		tablePanel.add(m2TableDC);
		tablePanel.add(m2TableCD);
		tablePanel.add(m2TableCC);
        m2Panel.add(tablePanel,BorderLayout.CENTER);
		m2Panel.setBorder(BorderFactory.createTitledBorder("2 Move Memory"));

        // Add Edit Button
        JButton updateBtn = new JButton("Update");
//        updateBtn.addActionListener(

        //icon = new BoxIcon();
        //topPanel.add(icon);

        // Add panels to view
        _LeftPanel = new JPanel();
		//_LeftPanel.add(topPanel);
		_LeftPanel.add(m0Table);
		_LeftPanel.add(m1Table);
		_LeftPanel.add(m2Panel);
        _LeftPanel.add(updateBtn);

		_LeftPanel.setPreferredSize(new Dimension(250,
				(int) parent.getPreferredSize().getHeight()));
	}

//    private boolean updateCell()

	public JPanel getLeftPanel() {
		return _LeftPanel;
	}

	public JPanel getTopPanel() {
		return _TopPanel;
	}

	public ActionListener createCellListener(int x,int y) {
		return new PDCellListener(this,x,y);
	}

	public void displayBox(int x, int y) {
		display(model.getGenome(x,y));
	}

    public void refreshMetrics() {
        totalNiceLbl.setText(String.valueOf(model.getTotalNiceness()));
    }

	public void display(int genome) {
		Color col=model.getGenomeColor(genome);
		//icon.setColor(col);

		int niceness = model.getNiceness(genome);
		niceLbl.setText(String.valueOf(niceness));

		String[] values = new String[21];
		for(int i=0; i<model.getBitLength(); i++) {
			values[i] = model.valueOf(Ops.placeBit(genome, i, true));
		}

		m0Table.setValueAt(values[0], 0, 0);
		for(int x=0; x<2; x++) {
			m1Table.setValueAt(values[x+1], 0, x);
		}
		for(int y=0; y<2; y++) {
			for(int x=0; x<2; x++) {
				m2TableDD.setValueAt(values[2*y+x+5], y, x);
			}
		}
		for(int y=0; y<2; y++) {
			for(int x=0; x<2; x++) {
				m2TableDC.setValueAt(values[2*y+x+9], y, x);
			}
		}
		for(int y=0; y<2; y++) {
			for(int x=0; x<2; x++) {
				m2TableCD.setValueAt(values[2*y+x+13], y, x);
			}
		}
		for(int y=0; y<2; y++) {
			for(int x=0; x<2; x++) {
				m2TableCC.setValueAt(values[2*y+x+17], y, x);
			}
		}
	}
}
