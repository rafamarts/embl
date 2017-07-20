package de.embl.helper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rafaelmm on 20/07/17.
 */
public class AccessionHelperTest {
    @Test
    public void sort() throws Exception {
        List<String> values = new ArrayList<>();
        values.add("ERR000111");
        values.add("ERR000151");
        values.add("ABC000110");
        values.add("ERR000100");

        values = AccessionHelper.sort(values);

        assertFalse("Result list can't be empty.", values.isEmpty());

        assertNotNull("Result list can't be null.", values);

        assertTrue("Result list is not sorted.", values.get(0).equals("ABC000110"));
        assertTrue("Result list is not sorted.", values.get(1).equals("ERR000100"));
        assertTrue("Result list is not sorted.", values.get(2).equals("ERR000111"));
        assertTrue("Result list is not sorted.", values.get(3).equals("ERR000151"));
        
    }

    @Test
    public void splitAccessions() throws Exception {

    }

    @Test
    public void isSequentialAssessions() throws Exception {

    }

    @Test
    public void getLastItem() throws Exception {

    }

    @Test
    public void getFirstItem() throws Exception {

    }

}