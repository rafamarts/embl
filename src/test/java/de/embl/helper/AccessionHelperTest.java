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

        List<String> values = AccessionHelper.splitAccessions("A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1");

        assertTrue("Error spliting accessions.", values.size() == 14);
    }

    @Test
    public void isSequentialAssessions() throws Exception {

        assertTrue("Values is not sequential.", AccessionHelper.isSequentialAssessions("ERR000112", "ERR000113"));
        assertFalse("Values is sequential.", AccessionHelper.isSequentialAssessions("ERR000112", "ERR000115"));
    }

    @Test
    public void getLastItem() throws Exception {

        assertTrue("Error getting Last Item.", AccessionHelper.getLastItem("ERR000111-ERR000113").equals("ERR000113"));
        assertTrue("Error getting Last Item.", AccessionHelper.getLastItem("ERR000111").equals("ERR000111"));
    }

    @Test
    public void getFirstItem() throws Exception {

        assertTrue("Error getting First Item. Attempt 1", AccessionHelper.getFirstItem("ERR000111-ERR000113").equals("ERR000111"));
        assertTrue("Error getting First Item. Attempt 2", AccessionHelper.getFirstItem("ERR000111").equals("ERR000111"));
    }

}