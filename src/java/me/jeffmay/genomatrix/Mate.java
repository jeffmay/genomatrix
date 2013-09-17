package me.jeffmay.genomatrix;

public class Mate {
	private int genome;
	private int fitness;

	public Mate(int genome,int fitness) {
		this.genome=genome;
		this.fitness=fitness;
	}

	public int getGenome() {
		return genome;
	}

	public int getFitness() {
		return fitness;
	}
}
