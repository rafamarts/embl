package de.embl.endpoints;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rafaelmm on 18/07/17.
 */
@RestController
@RequestMapping("/sort")
public class Sort {

    /**
     *
     * @param String to be sorted
     * @return String sorted
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "text/plain",
            produces="text/plain")
    public String sort(@RequestBody String string){
        return string;
    }
}
