/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.abondar.experimental.bigxmlparser;

import java.io.BufferedReader;
import org.abondar.bigxmlparser.ParseClasses.OfferBean;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author alex
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String oldFilepath = ""; //"/home/alex/NetBeansProjects/BigXMLParser/old.xml";
        String newFilepath = ""; //"/home/alex/NetBeansProjects/BigXMLParser/new.xml";

      
        System.out.println("Input an old file");
        oldFilepath = new Scanner(System.in).next();
        System.out.println(oldFilepath);

        System.out.println("Input a new file");
        newFilepath = new Scanner(System.in).next();
        System.out.println(newFilepath);

        System.out.println("...Processing files...");

        ParseOps parseNew = new ParseOps(newFilepath);
        ArrayList<OfferBean> newOffers = parseNew.getOfferList();

        ParseOps parseOld = new ParseOps(oldFilepath);
        ArrayList<OfferBean> oldOffers = parseOld.getOfferList();

        ArrayList<OfferBean> nonPictured = offersP(newOffers);
        System.out.println("List of offers with unavailable pictures");
        for (OfferBean ob : nonPictured) {
            System.out.println(ob.getId() + "p");
        }

        HashMap<Character, ArrayList<OfferBean>> offersMap = offersNMR(newOffers, oldOffers);
        System.out.println("Lists of new,modified or removed offers ");
        ArrayList<OfferBean> res1 = offersMap.get('N');
        for (OfferBean ob : res1) {
            System.out.println(ob.getId() + "n");
        }
        ArrayList<OfferBean> res2 = offersMap.get('M');
        for (OfferBean ob : res2) {
            System.out.println(ob.getId() + "m");
        }
        ArrayList<OfferBean> res3 = offersMap.get('R');
        for (OfferBean ob : res3) {
            System.out.println(ob.getId() + "r");
        }

    }

    //P - with bad pictures
    public static ArrayList<OfferBean> offersP(ArrayList<OfferBean> ofList) {
        ArrayList<OfferBean> nonPictured = new ArrayList<>();
        for (OfferBean ob : ofList) {
            try {
                URL url = new URL(ob.getPictureURL());
                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                uc.setRequestMethod("GET");

                uc.setConnectTimeout(30);
                uc.connect();

                if (uc.getResponseCode() != 200) {
                    nonPictured.add(ob);
                }

            } catch (java.net.SocketTimeoutException ex) {
                nonPictured.add(ob);
            } catch (IOException ex) {

                ex.printStackTrace();
            }

        }
        return nonPictured;
    }

    // N - new; M - modified; R -removed
    public static HashMap<Character, ArrayList<OfferBean>> offersNMR(ArrayList<OfferBean> newList, ArrayList<OfferBean> oldList) {

        HashMap<Character, ArrayList<OfferBean>> nmrMap = new HashMap<>();
        ArrayList<OfferBean> newOffers = new ArrayList<>();
        ArrayList<OfferBean> removedOffers = new ArrayList<>();
        ArrayList<OfferBean> modifiedOffers = new ArrayList<>();

        nmrMap.put('N', newOffers);
        nmrMap.put('M', modifiedOffers);
        nmrMap.put('R', removedOffers);

        HashMap<Integer, OfferBean> newMap = new HashMap<>();
        HashMap<Integer, OfferBean> oldMap = new HashMap<>();

        for (OfferBean ob : newList) {

            newMap.put(ob.getId(), ob);

        }

        for (OfferBean ob : oldList) {

            oldMap.put(ob.getId(), ob);

        }

        for (OfferBean ob : oldList) {
            if (newMap.containsKey(ob.getId()) == false) {
                removedOffers.add(ob);
            } else if (newMap.get(ob.getId()).equals(ob) == false) {
                modifiedOffers.add(newMap.get(ob.getId()));

            }
        }

        for (OfferBean ob : newList) {
            if (oldMap.containsKey(ob.getId()) == false) {
                newOffers.add(ob);

            }

        }

        return nmrMap;
    }
}
