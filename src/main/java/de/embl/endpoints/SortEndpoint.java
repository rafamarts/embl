package de.embl.endpoints;

import de.embl.helper.AccessionHelper;
import de.embl.request.SortBodyRequest;
import de.embl.request.validator.SortBodyValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelmm on 18/07/17.
 */
@RestController
@RequestMapping("/sort")
public class SortEndpoint {

    private static final Logger logger = Logger.getLogger(SortEndpoint.class);

    @Autowired
    private SortBodyValidator sortBodyValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(sortBodyValidator);
    }

    /**
     *
     * @param String to be sorted
     * @return String sorted
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces="application/json")
    public SortBodyRequest sort(@RequestBody @Valid SortBodyRequest body ){

        logger.debug("Request: " + body.getText());
        List<String> result = new ArrayList<String>();

        List<String> accessionList = AccessionHelper.splitAccessions(body.getText());
        accessionList = AccessionHelper.sort(accessionList);

        accessionList.forEach(item->{
             if (result.isEmpty()) {
                 result.add(item);
                 return;
             }

            String lastItem = AccessionHelper.getLastItem(result.get(result.size()-1));
            if (AccessionHelper.isSequentialAssessions(lastItem, item) ){
                String startValue = AccessionHelper.getFirstItem(result.get(result.size()-1));
                result.remove(result.size() - 1);
                result.add(startValue + "-" + item);
            }else {
                result.add(item);
            }
        });


        return new SortBodyRequest(String.join(",", result));
    }

}

