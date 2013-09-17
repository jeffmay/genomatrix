package me.jeffmay.genomatrix;

import java.util.Arrays;

/**
 * This class represents how the genome to play the game, Prisoner's Dilemma is
 * organized, as well as how to play two genomes against each other.
 * 
 * The model for the genome is as follows:
 * 
 * First Move:  Regardless of last move,
 *               always   -----\
 * Second Move: On first move:  \
 *               they & you      \
 *               D      D ---\    \
 *               D      C ---\\    \
 *               C      D ---\\\    \
 *               C      C ---\\\\    \
 * Given Last 2 Moves:        \\\\    \
 * 2 move past:  Last move:    \\\\    \
 * They & You    They & You     \\\\    \
 * D      D      D      D -\     \\\\    \
 * D      D      D      C -\\     \\\\    \
 * D      D      C      D -\\\     \\\\    \
 * D      D      C      C -\\\\     \\\\    \
 * D      C      D      D -\\\\\     \\\\    \
 * D      C      D      C -\\\\\\     \\\\    \
 * D      C      C      D -\\\\\\\     \\\\    \
 * D      C      C      C -\\\\\\\\     \\\\    \
 * C      D      D      D -\\\\\\\\\     \\\\    \
 * C      D      D      C -\\\\\\\\\\     \\\\    \
 * C      D      C      D -\\\\\\\\\\\     \\\\    \
 * C      D      C      C -\\\\\\\\\\\\     \\\\   |
 * C      C      D      D -\\\\\\\\\\\\\     \\\\  |
 * C      C      D      C -\\\\\\\\\\\\\\    ||||  |
 * C      C      C      D -\\\\\\\\\\\\\\\   ||||  |
 * C      C      C      C -\\\\\\\\\\\\\\\\  ||||  |
 *                         ||||||||||||||||  ||||  |
 *                         vvvvvvvvvvvvvvvv  vvvv  v
 *            bitstring = [1100110011001100][1100][1]
 *         translation => [CCDDCCDDCCDDCCDD][CCDD][C] = Tit-for-Tat
 *                 loci =         L2          L1   L0
 *
 * The Prisoner's Dilemma is defined as by the following matrix:
 *      D     C
 *   /-----------\
 * D | P,P | T,S |
 *   |-----------|
 * C | S,T | R,R |
 *   \-----------/
 *
 * Where the payoffs must follow the constraint that:
 *     T > R > P > S
 *
 * The easy mnumonic is as follows:
 * T = Temptation to Defect
 * R = Reward for mutual Cooperation
 * P = Penalty for mutual Defection
 * S = Sucker's payoff (for being a sucker)
 *
 * The default payoff matrix for this class is:
 *      D     C
 *   /-----------\
 * D | 1,1 | 5,0 |
 *   |-----------|
 * C | 0,5 | 3,3 |
 *   \-----------/
 * 
 * @author Jeff
 */

public class PrisonerDilemma implements Game {
    private int numbits;
	private int[][] payoffs;
	private static int[][] defaultPayoffMatrix={{1,5},{0,3}};

	public PrisonerDilemma(int[][] payoffMatrix) {
		if(!setPayoffMatrix(payoffMatrix)) {
			payoffs = defaultPayoffMatrix;
            numbits = 21;
		}
	}
	public PrisonerDilemma() {
		this(defaultPayoffMatrix);
	}

	public int[] play(int g1,int g2,int games) {
		int[] totals={0,0};
        int L1=1,L2=5; // loci locations
		int p1,p2; // move played by player
		int p1m1=0,p1m2=0; // last two moves played by player in bits
        int p2m1=0,p2m2=0; // last two moves played by player in bits
		for(int game=0; game<games; game++) {
			if(game==0) {
				// first move:
				// select move from L0 locus (first bit)
				p1=g1 & 1;
				p2=g2 & 1;
			}
			else if(game==1) {
				// given last move:
				// select move from L1 locus (0 - 4) + L1
                // Algorithm (from right to left):
                // 1) Add L1 shift distance
                // 2) Add player 1's first last move (0 or 1)
                // 3) Add player 2's first last move * 2,
                //      because if it is a 1, then need to shift 2 bits left.
				p1=Ops.placeBit(g1,(p2m1*2)+p1m1+L1,true);
				p2=Ops.placeBit(g2,(p1m1*2)+p2m1+L1,true);
			}
			else {
				// given last 2 moves:
				// select move from L2 locus (0 - 16) + L2
                // Algorithm (from right to left):
                // 1) Add L2 shift distance
                // 2) Add player 1's first last move
                // 3) Add player 2's first last move * 2
                // 4) Add player 1's second last move * 4
                // 5) Add player 2's second last move * 8
				p1=Ops.placeBit(g1,(p2m2*8)+(p1m2*4)+(p2m1*2)+p1m1+L2,true);
				p2=Ops.placeBit(g2,(p1m2*8)+(p2m2*4)+(p1m1*2)+p2m1+L2,true);
			}

			// update payoffs:
			totals[0]+=payoffs[p1][p2];
			totals[1]+=payoffs[p2][p1];

			// set move memory
			p1m2=p1m1;
            p2m2=p2m1;
            p1m1=p1;
            p2m1=p2;
		}
		return totals;
	}

	public static String getMoveName(int play) {
		if(play==1) {
			return "cooperate";
		}
		else if(play==0) {
			return "defect";
		}
		else {
			return "invalid";
		}
	}

    public int getNiceness(int genome) {
        return getNiceness(genome, numbits);
    }
    
	public static int getNiceness(int genome, int numbits) {
		int niceness = 0;
		for(int i=0; i<numbits; i++) {
			niceness += Ops.placeBit(genome, i, true);
		}
		return niceness;
	}

	public boolean setPayoffMatrix(int[][] matrix) {
		if(isValidPayoffMatrix(matrix)) {
			payoffs=matrix;
			return true;
		}
		else return false;
	}

	public String valueOf(int val) {
		if(val == 1) {
			return "C";
		}
		else {
			return "D";
		}
	}

	public static boolean isValidPayoffMatrix(int[][] matrix) {
		return matrix.length==2 && matrix[0].length==2 && matrix[1].length==2;
	}
    
    public static void main(String args[]) {
        PrisonerDilemma game = new PrisonerDilemma();
        int AC = Integer.parseInt("1111111111111111111111", 2);
        int AD = Integer.parseInt("0000000000000000000000", 2);
        int T4T = Integer.parseInt("110011001100110011001", 2);
        int[] scores;
        scores = game.play(AC, AD, 4);
        System.out.println("AC vs AD:");
        System.out.println(Arrays.toString(scores));
        scores = game.play(T4T, AC, 4);
        System.out.println("T4T vs AC:");
        System.out.println(Arrays.toString(scores));
        scores = game.play(T4T, AD, 4);
        System.out.println("T4T vs AD:");
        System.out.println(Arrays.toString(scores));
    }
}
