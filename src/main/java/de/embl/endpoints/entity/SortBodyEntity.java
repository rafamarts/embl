package de.embl.endpoints.entity;

import de.embl.endpoints.entity.validator.SortBodyValidator;

import javax.validation.constraints.NotNull;

/**
 * Created by rafaelmm on 18/07/17.
 */
public class SortBodyEntity {

    @NotNull(message = SortBodyValidator.ERROR_MESSAGE)
    private String text;

    public SortBodyEntity() {
    }

    public SortBodyEntity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
