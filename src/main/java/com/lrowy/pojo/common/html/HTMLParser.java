package com.lrowy.pojo.common.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
    private Document document;

    public HTMLParser(String entityString, String charset) {
        this.document = Jsoup.parse(entityString, charset);
    }

    public String getTitle() {
        Elements head = this.document.head().children();
        String result = "";
        for (Element e : head) {
            if (e.tagName().equals("title") && e.text().length() > 0) {
                result = e.text();
                break;
            }
        }
        return result;
    }

    public String getFavicon() {
        Elements head = this.document.head().children();
        Elements icons = new Elements();
        for (Element e : head) if (e.tagName().equals("link") && (e.attr("rel").equalsIgnoreCase("shortcut icon") || e.attr("rel").equalsIgnoreCase("icon"))) icons.add(e);
        boolean target = true;
        int size, maxSize = 0;
        String sizeString, result = "";
        for (Element icon : icons) {
            sizeString = icon.attr("sizes");
            if (sizeString != null && !sizeString.equals("")) {
                target = false;
                size = Integer.parseInt(sizeString.substring(sizeString.lastIndexOf("x") + 1));
                if (size > maxSize) {
                    result = icon.attr("href");
                    maxSize = size;
                }
            } else {
                if (target) result = icon.attr("href");
            }
        }
        if (result.equals("")) result = "/favicon.ico";
        return result;
    }
}
