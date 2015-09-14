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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author andr
 */
public class CheckerPolandVisa {
    final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    final String startUrl = "http://www.polandvisa-ukraine.com/scheduleappointment_2.html";

   

    public void run() {
	stepTwo(getFrameUrl());

    }

    protected String getFrameUrl() {

	String result = null;
	try {
	    Document doc = Jsoup.connect(startUrl).get();
	    Elements els = doc.select("iframe");
	    result = els.attr("src");
	} catch (IOException ex) {
	    Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    protected void stepTwo(String url) {
	
	Map<String, String> param1 = new HashMap<>(5);
	param1.put("__EVENTTARGET", "ctl00$plhMain$lnkSchApp");//__EVENTARGUMENT
	param1.put("__EVENTARGUMENT", "");

	Document doc = new JsoupSSL().post(url,param1);
	Map<String,String> param = new HashMap<>(9);

	System.out.println(doc);
	
	//получить список всех городов 
	Elements els = doc.select("#ctl00_plhMain_cboVAC");
	  // System.out.println(doc);
	
	
	for (Element el : els) {
	    System.out.println(el);
	}
	
	
	
	param.put("__VIEWSTATE", doc.select("#__VIEWSTATE").attr("value"));
	param.put("__EVENTVALIDATION", doc.select("#__EVENTVALIDATION").attr("value"));
	param.put("__VIEWSTATEENCRYPTED", doc.select("#__VIEWSTATEENCRYPTED").attr("value"));
	param.put("____Ticket", doc.select("#____Ticket").attr("value"));
	param.put("ctl00$hidCSRF", doc.select("#ctl00_hidCSRF").attr("value"));

	param.put("ctl00$plhMain$cboVAC", "12"); // Код города 	
	param.put("ctl00$plhMain$cboPurpose","1"); // подача документов  
	
	param.put("ctl00$plhMain$btnSubmit", doc.select("#ctl00_plhMain_btnSubmit").attr("value"));//Підтвердити
	
	
//	System.out.println(param);
//	System.out.println("n\n\n\n\n\n\n\n");
//	System.out.println(new JsoupSSL().post(url,param));

    }

}
