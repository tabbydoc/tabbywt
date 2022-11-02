package com.example.demo;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//public class CountElementHandlerSax extends DefaultHandler {
//
//    private final String elementName;
//    private Integer count = 0;
//
//    public String getElementName() {
//        return elementName;
//    }
//
//    public Integer getCount() {
//        return count;
//    }
//
//    public CountElementHandlerSax(String elementName) {
//        this.elementName = elementName;
//    }
//
//    @Override
//    public void startElement(String uri, String localName,
//                             String qName, Attributes attributes)
//            throws SAXException {
//        System.out.println("qname = " + qName);
//        if (qName.equalsIgnoreCase(getElementName())) {
//            count++;
//        }
//    }
//
//}

// ЭТОТ КОД, ВОЗМОЖНО, НЕ БУДЕТ ИСПОЛЬЗОВАТЬСЯ.