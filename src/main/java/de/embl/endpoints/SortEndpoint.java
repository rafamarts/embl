package de.embl.endpoints;

import de.embl.helper.AccessionHelper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rafaelmm on 18/07/17.
 */
@RestController
@RequestMapping("/sort")
public class SortEndpoint {

    private static final Logger logger = Logger.getLogger(SortEndpoint.class);

    /**
     *
     * @param String to be sorted
     * @return String sorted
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "text/plain",
            produces="text/plain")
    public String sort(@RequestBody String accessionNumbers ){
        List<String> result = new ArrayList<String>();

        List<String> accessionList = AccessionHelper.splitAccessions(accessionNumbers);
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


        return String.join(",", result);
    }
}
