package com.series.numberseries.util;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SeriesDefinitionTO implements Serializable {
	private Long id;
	private String name;
	private String description;
	private String category;
	private String explanation;
	private String encodedSeries;
	String startNumber;
	String varValues;
	String increment = "1";
	int level;
	Set<SeriesTO> series;

	public SeriesDefinitionTO(String name,String desc,String explanation,String encodedSeries,String startNumber,String increment,int level){
		this.name = name;
		this.startNumber = startNumber;
		this.description = desc;
		this.explanation = explanation;
		this.encodedSeries = encodedSeries;
		this.increment = increment;
		this.level = level;
	}

	public SeriesDefinitionTO(){}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	/**
	 * @return the encodedSeries
	 */
	public String getEncodedSeries() {
		return encodedSeries;
	}
	/**
	 * @param encodedSeries the encodedSeries to set
	 */
	public void setEncodedSeries(String encodedSeries) {
		this.encodedSeries = encodedSeries;
	}
	/**
	 * @return the startNumber
	 */
	public String getStartNumber() {
		return startNumber;
	}
	/**
	 * @param startNumber the startNumber to set
	 */
	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}
	/**
	 * @return the varValues
	 */
	public String getVarValues() {
		return varValues;
	}
	/**
	 * @param varValues the varValues to set
	 */
	public void setVarValues(String varValues) {
		this.varValues = varValues;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the series
	 */
	public Set<SeriesTO> getSeries() {
		return series;
	}
	/**
	 * @param series the series to set
	 */
	public void setSeries(Set<SeriesTO> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
	

}
