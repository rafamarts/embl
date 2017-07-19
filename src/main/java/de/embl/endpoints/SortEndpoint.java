package de.embl.endpoints;

import de.embl.utils.Sorting;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<String> accessionList = Pattern.compile(",")
                .splitAsStream(accessionNumbers)
                .map(String::trim)
                .collect(Collectors.toList());

        Sorting s = new Sorting();

        accessionList = s.sort(accessionList);

        accessionList.forEach(item->{

            if (result.isEmpty()) {
                result.add(item);
                return;
            }

            String lastItem = result.get(result.size()-1);
            if (lastItem.contains("-")){
                lastItem = Pattern.compile("-")
                        .splitAsStream(lastItem)
                        .map(String::trim)
                        .collect(Collectors.toList()).get(1);
            }

            if (s.isSequentialAssessions(lastItem, item) ){
                if (result.get(result.size()-1).contains("-")) {
                    String startValue = Pattern.compile("-")
                            .splitAsStream(result.get(result.size() - 1))
                            .map(String::trim)
                            .collect(Collectors.toList()).get(0);
                    result.remove(result.size() - 1);
                    result.add(startValue + "-" + item);
                }else {
                    result.remove(result.size() - 1);
                    result.add(lastItem + "-" + item);
                }
            }else {
                result.add(item);
            }

        });


        return String.join(",", result);
    }
}
