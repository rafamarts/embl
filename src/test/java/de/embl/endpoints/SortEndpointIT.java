package de.embl.endpoints;

import de.embl.endpoints.entity.validator.SortBodyValidator;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by rafaelmm on 20/07/17.
 */
public class SortEndpointIT {

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

        //EmblApplication.main(new String[0]);

    }

    @Test
    public void sort() throws Exception {

        Map<String, String> accessions = new HashMap<>();
        accessions.put("text", "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1");

        given()
                .contentType("application/json")
                .body(accessions)
                .when().post("/sort").then()
                .body("text", equalTo("A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001"));

    }

    @Test
    public void sortEmpty() throws Exception {

        Map<String, String> accessions = new HashMap<>();
        accessions.put("text", "");

        given()
                .contentType("application/json")
                .body(accessions)
                .when().post("/sort").then()
                .body("status", equalTo(400))
                .body("errors[0].code", equalTo(SortBodyValidator.ERROR_MESSAGE));

    }

    @Test
    public void sortInvalidJSON() throws Exception {

        Map<String, String> accessions = new HashMap<>();
        accessions.put("message", "");

        given()
                .contentType("application/json")
                .body(accessions)
                .when().post("/sort").then()
                .body("status", equalTo(400))
                .body("errors[0].code", equalTo(SortBodyValidator.ERROR_MESSAGE));

    }

    @Test
    public void sortInvalidAccessions() throws Exception {

        Map<String, String> accessions = new HashMap<>();
        accessions.put("text", "A00T00, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001");

        given()
                .contentType("application/json")
                .body(accessions)
                .when().post("/sort").then()
                .body("status", equalTo(400))
                .body("errors[0].code", equalTo("The accession(s) A00T00 is not a valid value."));

    }

}