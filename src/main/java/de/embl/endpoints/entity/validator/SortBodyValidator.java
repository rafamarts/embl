package de.embl.endpoints.entity.validator;

import de.embl.helper.AccessionHelper;
import de.embl.endpoints.entity.SortBodyEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rafaelmm on 19/07/17.
 */
@Component
public class SortBodyValidator implements Validator {

    private static final Logger logger = Logger.getLogger(SortBodyValidator.class);
    private static final String PATTERN = "([A-Z])+([0-9])+";
    private static Pattern pattern = Pattern.compile(PATTERN);
    public static final String ERROR_MESSAGE = "Field 'text' is mandatory";

    @Override
    public boolean supports(Class aClass) {
        return SortBodyEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //Checking if the value is null or empty
        ValidationUtils.rejectIfEmpty(errors, "text", ERROR_MESSAGE);

        //Casting the object to SortBoddyEntity
        SortBodyEntity body = (SortBodyEntity) o;

        logger.debug("Validating Request: " + body.getText());

        //Comma split the content in text json field.
        List<String> accessions = AccessionHelper.splitAccessions(body.getText());
        //Created to keep the invalid accessions
        List<String> accessionsInvalid = new ArrayList<String>();

        //For each value checking if match with regex
        accessions.forEach(item->{
            Matcher matcher = pattern.matcher(item);
            if (!matcher.matches()){
                accessionsInvalid.add(item);
            }
        });

        //If exists invalid value reject the request
        if (!accessionsInvalid.isEmpty())
            errors.rejectValue("text", "The accession(s) "+ String.join(",", accessionsInvalid) +" is not a valid value.");

    }
}
