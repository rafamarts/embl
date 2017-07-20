package de.embl.request;

/**
 * Created by rafaelmm on 18/07/17.
 */
public class SortBodyRequest{

    private String text;

    public SortBodyRequest() {
    }

    public SortBodyRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
