package com.series.numberseries;

import com.series.numberseries.util.AppxUtil;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void loadRuleFile() throws Exception {
        Map<String,String> defMap = AppxUtil.loadDefinitions();
        assertNotNull(defMap);
        assertTrue(defMap.size() > 0 );
    }
}