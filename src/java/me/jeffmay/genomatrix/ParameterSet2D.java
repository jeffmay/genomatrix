package me.jeffmay.genomatrix;

public class ParameterSet2D extends ParameterSet1D {
	protected int y1;
	protected int y2;

	public ParameterSet2D(int x1,int y1,int x2,int y2,Evaluatable val1,Evaluatable val2,Evaluatable carry) {
		super(x1,x2,val1,val2,carry);
		this.y1=y1;
		this.y2=y2;
	}

	public ParameterSet2D(Location2D loc1,Location2D loc2,Evaluatable val1,Evaluatable val2,Evaluatable carry) {
		this(loc1.x,loc1.y,loc2.x,loc2.y,val1,val2,carry);
	}

	public ParameterSet2D(int x1,int y1,int x2,int y2,Evaluatable[][] matrix,Evaluatable carry) {
		this(x1,y1,x2,y2,matrix[y1][x1],matrix[y2][x2],carry);
	}

	public ParameterSet2D(Location2D loc1,Location2D loc2,Evaluatable[][] matrix,Evaluatable carry) {
		this(loc1.x,loc1.y,loc2.x,loc2.y,matrix,carry);
	}

	public int getLeftY() { return y1; }
	public int getRightY() { return y2; }
	public Location2D getLeftLocation() { return new Location2D(x1,y1); }
	public Location2D getRightLocation() { return new Location2D(x2,y2); }
}
