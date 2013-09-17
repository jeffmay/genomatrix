package me.jeffmay.genomatrix;

public class ParameterSet1D {
	protected int x1;
	protected int x2;
	protected Evaluatable val1;
	protected Evaluatable val2;
	protected Evaluatable carry;

	public ParameterSet1D(int x1,int x2,Evaluatable val1,Evaluatable val2,Evaluatable carry) {
		this.x1=x1;
		this.x2=x2;
		this.val1=val1;
		this.val2=val2;
		this.carry=carry;
	}
	
	public ParameterSet1D(int x1,int x2,Evaluatable[] array,Evaluatable carry) {
		this(x1,x2,array[x1],array[x2],carry);
	}

	public int getLeftX() { return x1; }
	public int getRightX() { return x2; }
	public Evaluatable getLeftValue() { return val1; }
	public Evaluatable getRightValue() { return val2; }
	public Evaluatable getCarry() { return carry; }
}
