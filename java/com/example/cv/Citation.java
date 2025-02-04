package com.example.cv;

public class Citation {
    private String label;
    private String url;

    // Empty constructor needed for Firebase
    public Citation() {}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
