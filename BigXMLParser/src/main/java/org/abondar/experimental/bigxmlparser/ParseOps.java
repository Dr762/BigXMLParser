/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.abondar.experimental.bigxmlparser;

import org.abondar.bigxmlparser.ParseClasses.OfferBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alex
 */
public class ParseOps {

    private ArrayList<OfferBean> offerList;

    public ParseOps(String filePath) {
        offerList = new ArrayList<>();

      
        try {
            FileInputStream fis = new FileInputStream(filePath);
            try {

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler dh = new DefaultHandler() {
                    OfferBean ob;
                    boolean price = false;
                    boolean pictureURL = false;

                    @Override
                    public void startElement(String uri, String localName, String qName,
                            Attributes attributes) throws SAXException {

                        if (qName.equals("offer")) {

                            ob = new OfferBean();
                            offerList.add(ob);
                            int len = attributes.getLength();

                            for (int i = 0; i < len; i++) {

                                String sAttrName = attributes.getLocalName(i);
                                if (sAttrName.compareTo("id") == 0) {
                                    String sVal = attributes.getValue(i);

                                    ob.setId(Integer.valueOf(sVal));

                                }

                                if (sAttrName.compareTo("available") == 0) {
                                    String sVal = attributes.getValue(i);
                                    ob.setAvailable(Boolean.valueOf(sVal));

                                }
                            }

                        }
                        if (qName.equals("price")) {
                                price = true;

                            }

                            if (qName.equals("picture")) {
                                pictureURL = true;

                            }
                    }

                    @Override
                    public void characters(char ch[], int start, int length) throws SAXException {
                        if (price) {

                            ob.setPrice(Float.valueOf(new String(ch, start, length)));
                            price = false;

                        }

                        if (pictureURL) {

                            ob.setPictureURL(new String(ch, start, length));
                            pictureURL = false;
                        }
                    }

                };

                saxParser.parse(fis, dh);

            } catch (SAXException | ParserConfigurationException | IOException ex) {

                ex.printStackTrace();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        }

    }

    public ArrayList<OfferBean> getOfferList() {
        return offerList;
    }
    
  
    

}
