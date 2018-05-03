package com.disc;

/**
 * Created by hyc on 2018/1/10 18:06
 */

public class DataItem {

    /**
     * 所占值
     */
    private int value;
    /**
     * 顶部文本
     */
    private String topText;
    /**
     * 底部文本
     */
    private String bottomText;
    /**
     * 颜色
     */
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
