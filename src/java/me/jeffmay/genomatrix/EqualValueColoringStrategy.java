package me.jeffmay.genomatrix;

import java.awt.*;

public class EqualValueColoringStrategy implements ColoringStrategy {

	private int numbits;
	private int sinlen;
	private int dublen;
	private float dubval;
	private float sinval;

	public EqualValueColoringStrategy(int bitlength) {
		setBitLength(bitlength);
	}

	@Override
    public Color getColor(int val) {
        int red=0,green=0,blue=0;
		for(int i=0; i<dublen; i++) {
			red+=dubval*(val&1);
			val>>=1;
		}
		for(int i=0; i<dublen; i++) {
			green+=dubval*(val&1);
			val>>=1;
		}
		for(int i=0; i<sinlen; i++) {
			blue+=sinval*(val&1);
			val>>=1;
		}
		return new Color(red,green,blue);
    }

	// TODO: Add error checking
	public void setBitLength(int bitlength) {
		if(numbits==bitlength) return;
		if(bitlength % 3 != 0) {
			dublen=bitlength/3;
			sinlen=bitlength-2*dublen;
			dubval=255.0f/dublen;
			sinval=255.0f/sinlen;
		}
		else {
			dublen=sinlen=bitlength/3;
			dubval=sinval=255.0f/dublen;
		}
		numbits=bitlength;
	}

	public int getBitLength() {
		return numbits;
	}
}
