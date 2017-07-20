package de.embl.request.validator;

import de.embl.helper.AccessionHelper;
import de.embl.request.SortBodyRequest;
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

    private static final String PATTERN = "([A-Z])+([0-9])+";

    @Override
    public boolean supports(Class aClass) {
        return SortBodyRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "text", "Text is Mandatory");

        SortBodyRequest body = (SortBodyRequest) o;

        List<String> accessions = AccessionHelper.splitAccessions(body.getText());
        List<String> accessionsError = new ArrayList<String>();

        Pattern pattern = Pattern.compile(PATTERN);
        accessions.forEach(item->{
            Matcher matcher = pattern.matcher(item);
            if (!matcher.matches()){
                accessionsError.add(item);
            }
        });

        if (!accessionsError.isEmpty())
            errors.rejectValue("text", "The values "+ String.join(",", accessionsError) +" is not a valid value. ");

    }
}
