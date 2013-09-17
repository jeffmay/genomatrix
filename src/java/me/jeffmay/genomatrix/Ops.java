package me.jeffmay.genomatrix;

public class Ops {

	/** Create a bit mask, a series of 1s, that is <tt>len</tt> bits long 
	 *  and is preceded by all 0s.
	 *
	 *  @param int len The length of the bit mask to return.
	 *  @return A bit mask with <tt>len</tt> 1s preceded by 32-len 0s.
	 **/
    public static int createMask(int len) {
        int mask=1;
        for(int i=1; i<len; i++) {
            mask=(mask<<1)+1;
        }
        return mask;
    }

	/** Truncate bits to the left of start or to the right of end
	 *  (referrenced by the number of bits from the right), and
	 *  move these bits all the way to the right or left.
	 *
	 *  @param int bitstring An int representation of a string of bits.
	 *  @param int start The number of bits from the rightmost bit where
     *  the bit range starts.
	 *  @param int end The number of bits from the rightmost bit where
     *  the bit range ends.
	 *  @param boolean right If true, move bits all the way to the right,
	 *  otherwise, if false, move bits all the way to the left.
	 *  @return An int representation of the bits.
	 **/
	public static int placeBits(int bitstring,int start,int end,boolean right) {
		int mask;
		if(right) {
			mask=createMask(end-start);
			return mask & (bitstring >> start);
		}
		else {
			int lstart=32-end;
			mask=createMask(end-start) << lstart;
			return mask & (bitstring << lstart);
		}
	}

    /**
     * Places the bit at the given location to the far right or left of an empty
     * bitstring. The result is either 1, Integer.MIN_VALUE, or 0 (if bit is 0).
     * @param bitstring The string of bits to select from.
     * @param loc The location of the bit to move.
     * @param right Whether to place the bit to the right or left of a new (all
     * zero) bitstring.
     * @return If bit is 1, then when right is true return 1 else return
     * Integer.MIN_VALUE (1 placed on left side). If bit is 0, return 0.
     */
	public static int placeBit(int bitstring,int loc,boolean right) {
		if(right)
			return (bitstring >> loc) & 1;
		else
			return (bitstring << (31-loc)) & 0x8000;
	}

	public static Mappable1D flatten(Mappable2D function) {
		final Mappable2D f=function;
		return new Mappable1D() {
			public Evaluatable function(ParameterSet1D params) {
				ParameterSet2D params2D=new ParameterSet2D(params.getLeftX(),0,params.getRightX(),0,params.getLeftValue(),params.getRightValue(),params.getCarry());
				return f.function(params2D);
			}
		};
	}

	public static Evaluatable[] mapPairs1D(Mappable1D function,Evaluatable[] values) {
		Evaluatable[] results=new Evaluatable[values.length];
		for(int col=0; col<values.length; col++) {
			int val=values[col].getValue();
			Evaluatable result=null;
			if(col>0) {
				result=function.function(new ParameterSet1D(col,col-1,values,result));
			}
			if(col<values.length) {
				result=function.function(new ParameterSet1D(col,col+1,values,result));
			}
			results[col]=result;
		}
		return results;
	}

    /**
     * Maps the function that takes a pair of inputs over a two dimensional
     * array, excluding invalid pairs at the edges of the array.
     * @param function
     * @param values
     * @return A two dimensional Evaluatable array whose values are set to the
     * return value of the function.
     */
	public static Evaluatable[][] mapPairs2D(Mappable2D function,Evaluatable[][] values) {
		Evaluatable[][] results;
		if(values.length<1) {
			results=new Evaluatable[1][];
			results[0]=Ops.mapPairs1D(flatten(function),values[0]);
			return results;
		}
		results=new Evaluatable[values.length][];
		for(int row=0; row<results.length; row++) {
			results[row]=new Evaluatable[values[row].length];
			for(int col=0; col<results[row].length; col++) {
				int val=values[row][col].getValue();
				Evaluatable result=null;
				if(row==0) {
					if(col>0) {
						result=function.function(new ParameterSet2D(col,row,col-1,row,values,result));
						result=function.function(new ParameterSet2D(col,row,col-1,row+1,values,result));
					}
					if(col<values[row].length-1) {
						result=function.function(new ParameterSet2D(col,row,col+1,row,values,result));
						result=function.function(new ParameterSet2D(col,row,col+1,row+1,values,result));
					}
					result=function.function(new ParameterSet2D(col,row,col,row+1,values,result));
				}
				else if(row==values.length-1) {
					if(col>0) {
						result=function.function(new ParameterSet2D(col,row,col-1,row-1,values,result));
						result=function.function(new ParameterSet2D(col,row,col-1,row,values,result));
					}
					if(col<values[row].length-1) {
						result=function.function(new ParameterSet2D(col,row,col+1,row-1,values,result));
						result=function.function(new ParameterSet2D(col,row,col+1,row,values,result));
					}
					result=function.function(new ParameterSet2D(col,row,col,row-1,values,result));
				}
				else {
					if(col>0) {
						result=function.function(new ParameterSet2D(col,row,col-1,row-1,values,result));
						result=function.function(new ParameterSet2D(col,row,col-1,row,values,result));
						result=function.function(new ParameterSet2D(col,row,col-1,row+1,values,result));
					}
					if(col<values[row].length-1) {
						result=function.function(new ParameterSet2D(col,row,col+1,row-1,values,result));
						result=function.function(new ParameterSet2D(col,row,col+1,row,values,result));
						result=function.function(new ParameterSet2D(col,row,col+1,row+1,values,result));
					}
					result=function.function(new ParameterSet2D(col,row,col,row-1,values,result));
					result=function.function(new ParameterSet2D(col,row,col,row+1,values,result));
				}
				results[row][col]=result;
			}
		}
		return results;
	}
}
