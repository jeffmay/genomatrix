package me.jeffmay.genomatrix;

public class FitnessFunction implements Mappable2D {
	private Game game;
	private int games;

	public FitnessFunction(Game f,int numgames) {
		game=f;
		games=numgames;
	}
	public FitnessFunction(Game f) {
		this(f,20);
	}

	public Evaluatable function(ParameterSet2D params) {
		Evaluatable carry=params.getCarry();
		int total;
		if(carry==null) total=0;
		else total=carry.getValue();
		int p1=params.getLeftValue().getValue();
		int p2=params.getRightValue().getValue();
		int score=game.play(p1,p2,games)[0];
		return new EvaluatableInt(score+total);
	}
}
