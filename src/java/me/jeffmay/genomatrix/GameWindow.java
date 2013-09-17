package me.jeffmay.genomatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Jeff
 */
public class GameWindow extends JFrame {
	private static final int INIT_DELAY=0;
	private static Dimension defaultBoxSize=new Dimension(25,25);
	private GameModel2D model;
	private int height,width;
    private JPanel mainPanel;
	private GameView view;
    private JScrollPane scrollPanel;
    private JPanel modelPanel;
    private JPanel buttonPanel;
	private JPanel viewPanel;
    private JButton[][] boxes;
    private JButton stepButton;
	private JButton playButton;
	private JTextField delayField;
	private ActionListener run;
	private Timer timer;

    public static void main(String[] args) {
        try {
			GameWindow window=new GameWindow();
			window.setLocationRelativeTo(null);
			window.setVisible(true);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

	public GameWindow() {
		super();
		initialize(null, null);
	}

	public GameWindow(GameView view) {
		super();
		initialize(null, view);
	}

	private void initialize(BoxGameModel m, GameView v) {
		if(m == null) {
			m=new BoxGameModel(21,100,.7,.001);
		}
		if(v == null) {
			v=new PDView(this, m);
		}
		initComponents(m, v);
		pack();
	}

    private void initComponents(GameModel2D m, GameView v) {

		// Set variables
		model=m;
		view=v;
		height=model.getHeight();
		width=model.getWidth();

		// Set up Model Panel
        modelPanel=new JPanel();
        modelPanel.setLayout(new GridLayout(width,height));
		boxes=new JButton[height][width];
		for(int row=0; row<boxes.length; row++) {
			for(int col=0; col<boxes[row].length; col++) {
				boxes[row][col]=new JButton();
				JButton box=boxes[row][col];
				box.addActionListener(view.createCellListener(col,row));
				box.setBackground(model.getColor(col,row));
				box.setPreferredSize(defaultBoxSize);
        		modelPanel.add(box);
			}
		}

		// Set up Run Action
		run=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.step();
			}
		};

		// Set up Timer and Delay Field
		timer=new Timer(INIT_DELAY,run);
		delayField=new JTextField(String.valueOf(INIT_DELAY),5);

		// Set up Step Button
		stepButton=new JButton("Step");
		stepButton.addActionListener(run);

		// Set up Play Button
		playButton=new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) {
					timer.stop();
					playButton.setText("Play");
				}
				else {
					timer.setDelay(Integer.parseInt(delayField.getText()));
					timer.start();
					playButton.setText("Stop");
				}
			}
		});

		// Add buttons and delay to Button Panel
		buttonPanel=new JPanel(new GridLayout(3,1,5,20));
		buttonPanel.add(stepButton);
		buttonPanel.add(playButton);
		buttonPanel.add(delayField);

		// Set Content Pane 
		Container p=getContentPane();
        p.setLayout(new BorderLayout());
        p.add(modelPanel,BorderLayout.CENTER);
		p.add(buttonPanel,BorderLayout.EAST);
        p.add(view.getTopPanel(),BorderLayout.NORTH);
		p.add(view.getLeftPanel(),BorderLayout.WEST);

		// Set window properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public void refresh() {
		for(int row=0; row<boxes.length; row++) {
			for(int col=0; col<boxes[row].length; col++) {
				JButton cell=boxes[row][col];
				Color color=model.getColor(col,row);
				cell.setBackground(color);
			}
		}
	}

	public void setView(GameView view) {
		this.view = view;
	}

	public GameView getViewPanel() {
		return view;
	}

}
