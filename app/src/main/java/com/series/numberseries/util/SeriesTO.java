
package com.series.numberseries.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SeriesTO implements Serializable {
	
	private Long id;

	private String series;

	private String answer;

	private String start;

	private String increment;

	private List<String> options = new ArrayList<String>();

	private Long definitionId;
	
	private String explanation;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the increment
	 */
	public String getIncrement() {
		return increment;
	}

	/**
	 * @param increment the increment to set
	 */
	public void setIncrement(String increment) {
		this.increment = increment;
	}

	/**
	 * @return the options
	 */
	public List<String> getOptions() {
		return options;
	}



	/**
	 * @param options the options to set
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	
	/**
	 * @return the definitionId
	 */
	public Long getDefinitionId() {
		return definitionId;
	}

	/**
	 * @param definitionId the definitionId to set
	 */
	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	/**
	 * @return the explanation
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
	 * @param explanation the explanation to set
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	

}
