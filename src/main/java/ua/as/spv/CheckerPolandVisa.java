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
    final String statusID = "#ctl00_plhMain_lblMsg";

    public void run() {
	Map<String, String> param;
	String urlIframe = getFrameUrl();//получили url iframe грузим содержимое 

	param = toPost(urlIframe);

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

    //получаем данные из iframe в том числе ссылку на "Призначити дату подачі документів"
    protected Map<String, String> toPost(String url) {

	Document doc = JsoupSSL.post(url, null);

	Map<String, String> param = new HashMap<>(9);

	System.out.println(doc);
        String urlscr= doc.select("#ctl00_plhMain_lnkSchApp").attr("href").split("'")[1];
	System.out.println(urlscr);
	 
	param.put("__VIEWSTATE", doc.select("#__VIEWSTATE").attr("value"));
	param.put("__EVENTVALIDATION", doc.select("#__EVENTVALIDATION").attr("value"));
	param.put("__VIEWSTATEENCRYPTED", doc.select("#__VIEWSTATEENCRYPTED").attr("value"));
	param.put("____Ticket", doc.select("#____Ticket").attr("value"));
	param.put("ctl00$hidCSRF", doc.select("#ctl00_hidCSRF").attr("value"));

//	param.put("ctl00$plhMain$cboVAC", "12"); // Код города 	
//	param.put("ctl00$plhMain$cboPurpose","1"); // подача документов  
//	
//	param.put("ctl00$plhMain$btnSubmit", doc.select("#ctl00_plhMain_btnSubmit").attr("value"));//Підтвердити
	//System.out.println(param);
//	System.out.println("n\n\n\n\n\n\n\n");
//	System.out.println("tree");
//	System.out.println(new JsoupSSL().post(url,param));
	return param;
    }

    protected Map<String, String> getSity(String url, Map<String, String> par) {

	Document doc = JsoupSSL.post(url, par);

	Map<String, String> param = new HashMap<>(9);

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

//	param.put("ctl00$plhMain$cboVAC", "12"); // Код города 	
//	param.put("ctl00$plhMain$cboPurpose","1"); // подача документов  
//	
//	param.put("ctl00$plhMain$btnSubmit", doc.select("#ctl00_plhMain_btnSubmit").attr("value"));//Підтвердити
	//System.out.println(param);
//	System.out.println("n\n\n\n\n\n\n\n");
//	System.out.println("tree");
//	System.out.println(new JsoupSSL().post(url,param));
	return param;

    }

}
