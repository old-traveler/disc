package com.disc;

/**
 * Created by hyc on 2018/1/10 18:06
 */

public class DataItem {


    private int value;

    private String topText;

    private String bottomText;

    private int color;

    public DataItem(int value, String topText, String bottomText, int color) {
        this.value = value;
        this.topText = topText;
        this.bottomText = bottomText;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
