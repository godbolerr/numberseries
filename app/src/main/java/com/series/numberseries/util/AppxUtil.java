package com.series.numberseries.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.lang.math.RandomUtils;

public class AppxUtil {

	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();

	public static final String PLACEHOLDER = "?";
	public static final String SEPERATOR = " ";
	public static final String RULE_DEFINITION_FILE = "series_rules.csv";

	public static final String SCHEMA_TYPE_SOURCE = "s";

	public static int currentLevel = 1;

	public static final int TOTAL_QUESTIONS_IN_SESSION = 6;

	public static List<SeriesDefinitionTO> defListSimple = new ArrayList<>();
	public static List<SeriesDefinitionTO> defListMedium = new ArrayList<>();
	public static List<SeriesDefinitionTO> defListComplex = new ArrayList<>();

	public static long currentSession;

	public static Map<String, String> definitionMap = new HashMap<String, String>();
	public static Map<Long, List<String>> seriesMap = new HashMap<Long, List<String>>();
	public static Map<String, String> messageMap = new HashMap<String, String>();

	public static Map<Long, SeriesSessionTO> sessionMap = new HashMap<Long, SeriesSessionTO>();

	public static InteractionTO thisInteraction;

	public static SeriesSessionTO thisSession;

	public static int currentInteractionCount = 0;

	public AppxUtil() {

	}

	public static void finish() {

		// sessionMap = new HashMap<Long,SeriesSessionTO>();
	}

	public static SeriesSessionTO initiateSession(int totalQuestions, int level) {

		SeriesSessionTO session = new SeriesSessionTO();

		thisSession = session;

		currentInteractionCount = 0;

		session.setId(System.currentTimeMillis());
		List<InteractionTO> interactions = new ArrayList<InteractionTO>();
		for (int i = 0; i < totalQuestions; i++) {
			interactions.add(getNextSeries(level));
		}
		session.setInteractionTos(interactions);
		session.setStartTime(System.currentTimeMillis());

		sessionMap.put(session.getId(), session);

		currentSession = session.getId();

		// System.out.println("Current Session id " + session.getId());

		return session;
	}

	public static InteractionTO getNextSeries(int level) {

		InteractionTO ito = new InteractionTO();

		if (defListSimple.size() == 0) {
			loadSeries();
		}

		List<SeriesDefinitionTO> defList = null;

		if (level == 1) {
			defList = defListSimple;
		}
		if (level == 2) {
			defList = defListMedium;
		}
		if (level == 3) {
			defList = defListComplex;
		}

		// Get Random Definition

		int len = defList.size();

		int rand = RandomUtils.nextInt(len);

		SeriesDefinitionTO sto = defList.get(rand);

		// System.out.println("Working on :" + sto.getName());

		ito.setSto(generate(sto));

		thisInteraction = ito;

		return ito;
	}

	public static SeriesTO generate(SeriesDefinitionTO definition) {

		SeriesTO result = new SeriesTO();

		String sStartNumber = definition.getStartNumber();

		String sIncr = "" + definition.getIncrement();

		String resultString = getSeriesResult(definition);

		List<SeriesTO> seriesToList = getSeriesList(resultString, sStartNumber, sIncr);

		int length = seriesToList.size();

		int index = RandomUtils.nextInt(length);

		result = seriesToList.get(index);
		result.setDefinitionId(definition.getId());
		result.setExplanation(definition.getExplanation());

		String seriesText = result.getSeries();
		seriesText = seriesText.replace(",", ",   ");
		// seriesText = seriesText + " ";
		result.setSeries(seriesText);

		return result;
	}

	public static String getSeriesResult(SeriesDefinitionTO definition) {

		SeriesTO result = new SeriesTO();

		String sStartNumber = definition.getStartNumber();

		Object startNumber = null;

		Map<String, Object> sourceData = new HashMap<String, Object>();

		if (sStartNumber != null) {

			for (int j = 1; j <= 9; j++) {
				sourceData.put("x" + j, "");
			}

			String sIncr = "" + definition.getIncrement();
			Object incr = null;

			if (sIncr.contains(".")) {
				incr = new Double(sIncr);
			} else {
				incr = new Integer(sIncr);
			}

			if (sStartNumber.contains(".")) {
				startNumber = new Double(sStartNumber);
			} else {
				startNumber = new Integer(sStartNumber);
			}

			sourceData.put("n", incr);

			String rule = definition.getEncodedSeries();

			String[] rArray = rule.split(",");

			StringBuffer sb = new StringBuffer();

			for (int j = 0; j < rArray.length; j++) {

				String newRule = "x" + (j + 1) + "=" + rArray[j] + ";";
				sb.append(newRule);
			}

			rule = sb.toString();
			rule = rule.replaceAll("[a-z][0-9]*", "s.$0");
			sourceData.put("x", startNumber);
			sourceData.put("x1", startNumber);
			JexlContext jexlContext = new MapContext();
			jexlContext.set(SCHEMA_TYPE_SOURCE, sourceData);
			JexlScript e = jexl.createScript(rule);
			e.execute(jexlContext);

			String series = "";
			for (int j = 1; j <= rArray.length; j++) {

				Object value = sourceData.get("x" + j);

				if (value instanceof Double) {
					value = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}

				series = series + value + ",";
			}
			String resultString = series.substring(0, series.lastIndexOf(","));

			return resultString;
		}
		return null;

	}

	private static List<SeriesTO> getSeriesList(String sequence, String startNo, String increment) {

		List<SeriesTO> seriesList = new ArrayList<SeriesTO>();

		String[] item = sequence.split(",");

		String newSeries = "";

		List<String> options = null;

		for (int i = 0; i < item.length; i++) {
			String answer = "";
			StringBuffer sb = new StringBuffer();
			item = sequence.split(",");
			for (int j = 0; j < item.length; j++) {
				if (i == j) {
					answer = item[j];
					item[j] = PLACEHOLDER;
					options = new ArrayList<String>();
					options.add(answer + "");
					options.add((Integer.parseInt(answer) - 1) + "");
					options.add((Integer.parseInt(answer) + 1) + "");
					options.add((Integer.parseInt(answer) * 2) + "");

				}
				sb.append(item[j]);
				sb.append(",");
			}

			SeriesTO series = new SeriesTO();
			series.setAnswer(answer);

			long seed = System.nanoTime();
			Collections.shuffle(options, new Random(seed));

			series.setOptions(options);

			String newSequence = sb.toString();
			newSequence = newSequence.substring(0, newSequence.lastIndexOf(","));

			series.setSeries(newSequence);
			series.setStart(startNo);
			series.setIncrement(increment);

			seriesList.add(series);
		}

		return seriesList;

	}

	public static void changeLevel() {
		int thisLevel = currentLevel++;
		if (thisLevel >= 3) {
			currentLevel = 1;
		}
	}

	public static void loadSeries() {

		loadDefinitions();

//		if (defListSimple.size() > 0) {
//			return;
//		}
		InputStream is = AppxUtil.class.getClassLoader().getResourceAsStream("dataLoad.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String thisLine = null;

		try {
			while ((thisLine = br.readLine()) != null) {
				String defn = thisLine.substring(0, thisLine.indexOf(","));

				if (definitionMap.containsKey(defn)) {
					String series = definitionMap.get(defn);

					if (messageMap.containsKey(defn)) {

					}
					// Get the data for the same and then load the definition
					// and series.

					String[] sArray = thisLine.split(",");
					String start = sArray[1];
					String incr = sArray[2];
					String level = sArray[3];

					String name = "IncrementWithOne";
					String desc = "Description of " + defn;
					String explanation = defn + ".MESSAGE";

					// System.out.println(defn + ":" + series +":" + start +":"
					// + incr +":"+ level);

					if ("1".equals(level)) {
						defListSimple.add(new SeriesDefinitionTO(defn, desc, explanation, series, start, incr,
								Integer.parseInt(level)));
					}

					if ("2".equals(level)) {
						defListMedium.add(new SeriesDefinitionTO(defn, desc, explanation, series, start, incr,
								Integer.parseInt(level)));
					}

					if ("3".equals(level)) {
						defListComplex.add(new SeriesDefinitionTO(defn, desc, explanation, series, start, incr,
								Integer.parseInt(level)));
					}

					// System.out.println(defn + ":" + series +":" + start +":"
					// + incr +":"+ level);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Loaded series :" + defList.size());

	}

	public static Map<String, String> loadDefinitions() {

//		if (definitionMap.size() > 0) {
//			return definitionMap;
//		}
		InputStream is = AppxUtil.class.getClassLoader().getResourceAsStream(RULE_DEFINITION_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String thisLine = null;

		try {
			while ((thisLine = br.readLine()) != null) {
				// Handle comments
				if (thisLine.startsWith("#")) {
					continue;
				}
				String defn = thisLine.substring(0, thisLine.indexOf(","));
				String series = thisLine.substring(thisLine.indexOf(",") + 1);
				definitionMap.put(defn, series);
				// System.out.println(defn + ":" + series);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return definitionMap;
	}

	// TODO Close streams

	public static List<String> loadLevel(int level) {

		InputStream is = AppxUtil.class.getClassLoader().getResourceAsStream(level + ".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		List<String> list = new ArrayList<String>();

		String thisLine = null;

		try {
			while ((thisLine = br.readLine()) != null) {
				list.add(thisLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
