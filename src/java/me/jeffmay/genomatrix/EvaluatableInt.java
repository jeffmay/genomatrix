package me.jeffmay.genomatrix;

public class EvaluatableInt implements Evaluatable {
	private final int value;

	public EvaluatableInt() {
		value=0;
	}
	public EvaluatableInt(int val) {
		value=val;
	}

	public int getValue() {
		return value;
	}

	public int compareTo(Evaluatable o) {
		return value-o.getValue();
	}

	public String toString() {
		return String.valueOf(value);
	}
}
