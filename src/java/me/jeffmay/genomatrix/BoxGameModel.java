package me.jeffmay.genomatrix;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A 2 dimensional box model for playing a two player game. The default game
 * that is used for all BoxGameModels right now is PrisonerDilemma.
 * @author Jeff
 */
public class BoxGameModel extends GameModel2D {
	private Mappable2D fitnessFunction;
	private Mappable2D mateFunction;
	private Game game;
	private int games;
	private double cRate;
	private double mRate;
	private ColoringStrategy colorizer;
    private ActionListener updater;
    private int height;
    private int width;
    private Cell[][] board;

    public BoxGameModel(int height,int width,int numbits,int numgames,double crossoverRate,double mutationRate) {
		game=new PrisonerDilemma();
		colorizer=new EqualValueColoringStrategy(numbits);
        this.numbits=numbits;
        this.height=height;
        this.width=width;
        board=new Cell[height][width];
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
				int genome=randomGenome();
				Color color=colorizer.getColor(genome);
                board[y][x]=new Cell(genome,color);
            }
        }

		// Set function variables
		games=numgames;
		cRate=crossoverRate;
		mRate=mutationRate;

		// Set default mapping functions
		fitnessFunction=new FitnessFunction(game,games);
		mateFunction=new MateFunction(board,numbits,cRate,mRate);
    }

	public BoxGameModel(int numbits,int numgames,double crossoverRate, double mutationRate) {
		this(20,20,numbits,numgames,crossoverRate,mutationRate);
	}

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getGenome(int x,int y) {
        return board[y][x].getValue();
    }

    /**
     * Returns the color currently held by the cell on the board.
     * @param x The column number starting from the left at 0.
     * @param y The row number starting from the top at 0.
     * @return The unrefreshed version of the color displayed on the board.
     */
    @Override
	public Color getColor(int x,int y) {
		return board[y][x].getColor();
	}

    /**
     * Returns the color associated for the given genome, according to this
     * GameModel.
     * @param genome A bitstring of the length of a genome for the game.
     * @return The color associated with this genome.
     */
	@Override
	public Color getGenomeColor(int genome) {
		return colorizer.getColor(genome);
	}

    /**
     * A metric for measuring the "niceness" of a genome given a position on the
     * board. Equivalent to calling getNiceness(getGenome(x, y)).
     * @param x The column number starting from the left at 0.
     * @param y The row number starting from the top at 0.
     * @return The niceness of the genome.
     */
	public int getNiceness(int x,int y) {
		return getNiceness(board[y][x].getValue());
	}

    /**
     * A metric for measuring the "niceness" of a genome. This works by counting
     * the number of 1's in the genome. For the Game, PrisonerDilemma, this
     * is moderately useful.
     * @param genome The genome to measure the niceness of.
     * @return The niceness of the genome.
     */
	public int getNiceness(int genome) {
		return PrisonerDilemma.getNiceness(genome, numbits);
	}

    /**
     * Applies the niceness metric to the entire board.
     * @return The total niceness of all the genomes on the board.
     */
	public int getTotalNiceness() {
		int total = 0;
		for(int y=0; y<board.length; y++) {
			for(int x=0; x<board[y].length; x++) {
				total += getNiceness(x,y);
			}
		}
		return total;
	}

    public void setUpdateListener(ActionListener updateListener) {
        updater = updateListener;
    }

    /**
     * Refreshes the colors held by the board in this GameModel.
     */
	public void refreshColors() {
		for(Cell[] row:board) {
			for(Cell cell:row) {
				cell.setColor(colorizer.getColor(cell.getValue()));
			}
		}
	}

	protected Evaluatable[][] compete() {
		return Ops.mapPairs2D(fitnessFunction,board);
	}

	protected Evaluatable[][] mate(Evaluatable[][] fitnesses) {
		return Ops.mapPairs2D(mateFunction,fitnesses);
	}

	public void step() {
		Evaluatable[][] fitnesses=compete();
		Evaluatable[][] mates=mate(fitnesses);
		for(int y=0; y<board.length; y++) {
			for(int x=0; x<board[y].length; x++) {
				board[y][x].setValue(mates[y][x].getValue());
			}
		}
		refreshColors();
        updater.actionPerformed(new ActionEvent(board,board.hashCode(),
                "BoxGameModel updated."));
	}

	public double getCrossoverRate() {
		return cRate;
	}

	public double getMutationRate() {
		return mRate;
	}

	public String valueOf(int val) {
		return game.valueOf(val);
	}
}
