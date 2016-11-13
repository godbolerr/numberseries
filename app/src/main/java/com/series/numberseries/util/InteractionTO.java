package com.series.numberseries.util;

import java.io.Serializable;

public class InteractionTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	long id;

	long startTime;

	long endTime;

	SeriesTO sto;

	boolean status;

	String input;

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the sto
	 */
	public SeriesTO getSto() {
		return sto;
	}

	/**
	 * @param sto
	 *            the sto to set
	 */
	public void setSto(SeriesTO sto) {
		this.sto = sto;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		if ( input != null ){
			return input.equalsIgnoreCase(sto.getAnswer());
		} else {
			return false;
		}
	}


	public String getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}
	

	public void start(){
		startTime = System.currentTimeMillis();
	}

	public void end(){
		endTime = System.currentTimeMillis();
	}

	public long timeTaken(){
		return endTime - startTime;
	}

}
