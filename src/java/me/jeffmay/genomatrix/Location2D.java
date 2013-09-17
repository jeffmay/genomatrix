package me.jeffmay.genomatrix;

/**
 *
 * @author Jeff
 */
public class Location2D {
    protected int x;
    protected int y;

    public Location2D(int x,int y) {
        this.x=x;
        this.y=y;
    }
    public Location2D() {
        this(0,0);
    }

	public String toString() {
		return "("+x+","+y+")";
	}
}
