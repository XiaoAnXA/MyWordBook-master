package com.mask.mywordbook.event;

public class PoetryEvent {
    public PoetryEvent(String content) {
        this.content = content;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
