package me.jeffmay.genomatrix;

import java.awt.*;

public class SplitBitColoringStrategy implements ColoringStrategy {

	private int numbits;

	public SplitBitColoringStrategy(int bitlength) {
		numbits=bitlength;
	}
	public SplitBitColoringStrategy() {
		this(24);
	}

	@Override
    public Color getColor(int val) {
        int colorbits=numbits/3;
        int red,green,blue;
        if(colorbits==8) {
            return new Color(val);
        }
        else if(colorbits<8) {
			int dist=8-colorbits;
            int mask=Ops.createMask(colorbits);
            red=(mask&val)<<dist;
			val>>=colorbits;
            green=(mask&val)<<dist;
			val>>=colorbits;
            blue=(mask&val)<<dist;
			val>>=colorbits;
            return new Color(red,green,blue);
        }
        else {
            return Color.BLACK;
        }
    }
}
