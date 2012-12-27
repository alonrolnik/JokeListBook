package cse.bgu.ex5.jokelistbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XmlJokesParser {
    // We don't use namespaces
    private static final String ns = null;
   
    public List<Joke> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Joke> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List <Joke> entries = new ArrayList<Joke>();

        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser).entryToJoke());
            } else {
                skip(parser);
            }
        }  
        return entries;
    }

    public static class Entry {
        public final String title;
        public final String content_encoded;
        public final String pubDate;
        
        private Entry(String pubDate, String content_encoded, String title) {
            this.pubDate = pubDate;
            this.content_encoded = content_encoded;
            this.title = title;
        }
        
        private Joke entryToJoke(){
        	Joke joke = new Joke();
        	joke.setAuthor(title);
        	joke.setDate(pubDate);
        	joke.setJoke(content_encoded);
        	joke.setLikes("neither");
        	return joke;
        }
    }
      
    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String pubDate = null;
        String content_encoded = null;
        String title = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("pubDate")) {
            	pubDate = readpubDate(parser);
            } else if (name.equals("content_encoded")) {
            	content_encoded = readcontent_encoded(parser);
            } else if (name.equals("title")) {
            	title = readtitle(parser);
            } else {
                skip(parser);
            }
        }
        return new Entry(pubDate, content_encoded, title);
    }

    // Processes title tags in the feed.
    private String readcontent_encoded(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "content_encoded");
        String content_encoded = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "content_encoded");
        return content_encoded;
    }
      
    // Processes link tags in the feed.
    private String readtitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        String title;
        parser.require(XmlPullParser.START_TAG, ns, "title");
        title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes pubDate tags in the feed.
    private String readpubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
    if (parser.getEventType() != XmlPullParser.START_TAG) {
        throw new IllegalStateException();
    }
    int depth = 1;
    while (depth != 0) {
        switch (parser.next()) {
        case XmlPullParser.END_TAG:
            depth--;
            break;
        case XmlPullParser.START_TAG:
            depth++;
            break;
        }
    }
 }


}
