package me.jeffmay.genomatrix;

import java.util.*;

public class MateFunction implements Mappable2D {
	private Cell[][] board;
	private int numbits;
	private int trim;
	private double cRate;
	private double mRate;

	public MateFunction(Cell[][] board,int bitlength,double crossoverRate,double mutationRate) {
		this.board=board;
		numbits=bitlength;
		trim=Ops.createMask(numbits);
		cRate=crossoverRate;
		mRate=mutationRate;
	}

	public Evaluatable function(ParameterSet2D params) {
		MatingGroup group=(MatingGroup) params.getCarry();
		Location2D next=params.getRightLocation();
		if(group==null) {
			Location2D loc=params.getLeftLocation();
			group=new MatingGroup(board[loc.y][loc.x].getValue());
		}
		Mate nextMate=new Mate(board[next.y][next.x].getValue(),params.getRightValue().getValue());
		group.add(nextMate);
		return group;
	}

	class MatingGroup implements Evaluatable {
		private int nextgen;
		private Mate mate;
		private int genome;
		private Vector<Mate> mates;

		public MatingGroup(int genome) {
			mate=null;
			nextgen=0;
			mates=new Vector<Mate>(8);
			this.genome=genome;
		}

		public void add(Mate mate) {
			mates.add(mate);
		}

		public int getValue() {
			if(mate==null) {
				calcNextGen();
			}
			return nextgen;
		}

		public int getNextGen() {
			return getValue();
		}

		private void calcNextGen() {
			mate=chooseMate();
			nextgen=mate.getGenome();
			if(Math.random()<cRate) {
				nextgen=crossover(genome,nextgen);
			}
			if(Math.random()<mRate) {
				nextgen=mutate(nextgen);
			}
		}

		private Mate chooseMate() {
			// Set up roulette wheel, with size proportional to fitness
			int total=0;
			int[] wheel=new int[mates.size()];
			int i=0;
			for(Mate mate:mates) {
				total+=mate.getFitness();
				wheel[i++]=total;
			}

			// Roll the wheel to chose mate
			int choice=(int) (Math.random()*total);
			for(i=0;wheel[i]<=choice;i++);
			return mates.get(i);
		}

		public int crossover(int g1,int g2) {
			int pivot=(int) (Math.random()*(numbits-1)+1);
			int mask=Ops.createMask(pivot);
			if((int) (Math.random()+.5)==1) {
				int t=g2;
				g2=g1;
				g1=t;
			}
			return ((g1 & mask)+(g2 & (~mask))) & trim;
		}

		public int mutate(int g) {
			return g ^ (1 << (int) (Math.random()*numbits));
		}

		public Mate getSelectedMate() {
			if(mate==null) {
				calcNextGen();
			}
			return mate;
		}

		public int compareTo(Evaluatable o) {
			int f=getSelectedMate().getFitness();
			if(o instanceof MatingGroup) {
				return f-((MatingGroup)o).getSelectedMate().getFitness();
			}
			else {
				return f-o.getValue();
			}
		}

		public String toString() {
			return Integer.toBinaryString(getValue());
		}
	}
}
