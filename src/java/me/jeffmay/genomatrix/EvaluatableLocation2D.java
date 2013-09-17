package me.jeffmay.genomatrix;

public class EvaluatableLocation2D extends Location2D implements Evaluatable {
	private int value;

	public EvaluatableLocation2D(int x,int y,int val) {
		super(x,y);
		value=val;
	}
	
	public EvaluatableLocation2D(Location2D loc,int val) {
		this(loc.x,loc.y,val);
	}

	public EvaluatableLocation2D(int x,int y,Evaluatable val) {
		this(x,y,val.getValue());
	}

	public EvaluatableLocation2D(Location2D loc,Evaluatable val) {
		this(loc.x,loc.y,val.getValue());
	}

	public int compareTo(Evaluatable o) {
		return value-o.getValue();
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return super.toString()+"="+value;
	}
}
