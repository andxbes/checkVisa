/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.as.spv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author andr
 */
public class CheckerPolandVisa {

    final String startUrl = "http://www.polandvisa-ukraine.com/scheduleappointment_2.html";

    public void run() {

        Map<String, String> post = new HashMap<>(5);
        post.put("__EVENTTARGET", "ctl00$plhMain$lnkSchApp");

        stepTwo(getFrameUrl());

    }

    protected String getFrameUrl() {
        Document doc;
        Elements els = null;
        try {
            doc = Jsoup.connect(startUrl).get();
            els = doc.select("iframe");

        } catch (IOException ex) {
            Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return els.attr("src");
    }

    protected void stepTwo(String url) {

        Document doc;
        try {
            doc = Jsoup.connect(url).post();
            System.out.println(doc.toString());

        } catch (IOException ex) {
            Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
