package pl.edu.agh.serializers.google.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextAndValue {

    private String text;
    private int value;

    public TextAndValue() {
    }

    public TextAndValue(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
