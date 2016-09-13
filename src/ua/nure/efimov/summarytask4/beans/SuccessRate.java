package ua.nure.efimov.summarytask4.beans;

import java.io.Serializable;

public class SuccessRate implements Serializable {
	private static final long serialVersionUID = 406801238174290084L;

	private int difficulty;
	private double successRate;
	private int testsPassedAmount;

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the successRate
	 */
	public double getSuccessRate() {
		return successRate;
	}

	/**
	 * @param successRate
	 *            the successRate to set
	 */
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	/**
	 * @return the testsPassedAmount
	 */
	public int getTestsPassedAmount() {
		return testsPassedAmount;
	}

	/**
	 * @param testsPassedAmount
	 *            the testsPassedAmount to set
	 */
	public void setTestsPassedAmount(int testsPassedAmount) {
		this.testsPassedAmount = testsPassedAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SuccessRate [difficulty=" + difficulty + ", successRate=" + successRate + ", testsPassedAmount="
				+ testsPassedAmount + "]";
	}

}
