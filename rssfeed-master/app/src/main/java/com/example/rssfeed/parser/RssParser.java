package com.example.rssfeed.parser;

import android.util.Xml;
import com.example.rssfeed.model.RssItem;
import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RssParser {

    public static List<RssItem> parse(String xmlString) {
        List<RssItem> items = new ArrayList<>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlString));

            String title = "", description = "", link = "", pubDate = "";
            String imageUrl = null;
            boolean inItem = false;
            String currentTag = "";

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        currentTag = parser.getName() != null ? parser.getName() : "";

                        if (currentTag.equals("item")) {
                            inItem = true;
                            title = ""; description = "";
                            link = ""; pubDate = ""; imageUrl = null;
                        }

                        if (inItem && (currentTag.equals("media:content")
                                || currentTag.equals("enclosure"))) {
                            imageUrl = parser.getAttributeValue(null, "url");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (inItem) {
                            String text = parser.getText() != null
                                    ? parser.getText().trim() : "";
                            switch (currentTag) {
                                case "title":       title       += text; break;
                                case "description": description += text; break;
                                case "link":        link        += text; break;
                                case "pubDate":     pubDate     += text; break;
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName()) && inItem) {
                            items.add(new RssItem(
                                    title, description, link, pubDate, imageUrl));
                            inItem = false;
                        }
                        currentTag = "";
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}