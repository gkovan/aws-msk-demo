package com.gk.aws.msk.demo.pipe.model;

public class KafkaBody {
    private String text;
    private String sourceLang;
    private String targetLang;
    boolean translate;
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getSourceLang() {
        return sourceLang;
    }
    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }
    public String getTargetLang() {
        return targetLang;
    }
    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }
    public boolean isTranslate() {
        return translate;
    }
    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    
}
