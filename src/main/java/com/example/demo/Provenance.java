package com.example.demo;

import org.jsoup.select.Elements;

public class Provenance {
    String web_page;
    String html;

    public Provenance(String web_page, String html) {
        this.web_page = web_page;
        this.html = html;
    }

    @Override
    public String toString() {
        return "Provenance{" +
                "web_page='" + web_page + '\'' +
                ", html=" + html +
                '}';
    }

    public Provenance() {
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}

