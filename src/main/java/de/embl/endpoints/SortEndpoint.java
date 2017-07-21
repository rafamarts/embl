package de.embl.endpoints;

import de.embl.handler.AccessionHandlerImpl;
import de.embl.endpoints.entity.SortBodyEntity;
import de.embl.endpoints.entity.validator.SortBodyValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by rafaelmm on 18/07/17.
 */
@RestController
@RequestMapping("/sort")
public class SortEndpoint {

    private static final Logger logger = Logger.getLogger(SortEndpoint.class);

    @Inject
    private AccessionHandlerImpl implementation;

    @Autowired
    private SortBodyValidator sortBodyValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(sortBodyValidator);
    }

    /**
     * Endpoint Sort
     *
     * JSON Request:
     *      {
     *          "text" : ""
     *      }
     *
     * @param SortBodyEntity to be sorted
     * @return SortBodyEntity Json formated with accession sorted
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces="application/json")
    public SortBodyEntity sort(@RequestBody @Valid SortBodyEntity body ){

        logger.debug("Request: " + body.getText());
        return new SortBodyEntity(implementation.execute(body.getText()));
    }

}

