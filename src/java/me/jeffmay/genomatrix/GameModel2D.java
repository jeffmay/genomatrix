/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jeffmay.genomatrix;

import java.awt.Color;

/**
 *
 * @author Jeff
 */
public abstract class GameModel2D {
    protected int numbits;

	public abstract int getHeight();
	public abstract int getWidth();
    public abstract int getGenome(int x,int y);
    public abstract Color getColor(int x,int y);
	public abstract Color getGenomeColor(int genome);
	public abstract void step();
    protected int randomGenome() {
        return (int) (Math.random()*Math.pow(2,numbits));
    }
	protected int getBitLength() {
		return numbits;
	}
}
