package de.embl.handler;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rafaelmm on 20/07/17.
 */
public class AccessionHandlerImplTest {

    @Test
    public void execute() throws Exception {
        AccessionHandlerImpl ahi = new AccessionHandlerImpl();

        String input = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
        String output = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001";

        assertTrue("Invalid value for 'text'", ahi.execute(input).equals(output) );
    }

    @Test
    public void executeNull() throws Exception {
        AccessionHandlerImpl ahi = new AccessionHandlerImpl();

        assertTrue("Invalid value for 'text'", ahi.execute(null).equals("") );
    }

    @Test
    public void executeInvalidText() throws Exception {
        AccessionHandlerImpl ahi = new AccessionHandlerImpl();

        String invalidText = "accession invalid";

        assertTrue("Invalid value for 'text'", ahi.execute(invalidText).equals(invalidText) );
    }

}