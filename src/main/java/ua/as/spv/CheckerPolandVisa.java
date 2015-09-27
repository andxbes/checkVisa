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
    
    private String frameUrl,
	    formUrl,
	    rootUrl;
    
    public void run() {
	
	Map<String, String> param = new HashMap<>(10);
	
	extractFrameUrl();//получили url iframe грузим содержимое 

	extractTheRootAddress();
	
	param = extractDataFromFirstPage(param);
	
	viewParams(param);
	
	stepTwo(param);
	
    }
    
    private void extractTheRootAddress() {
	String arr[] = frameUrl.split("/");
	rootUrl = frameUrl.replace(arr[arr.length - 1], "");
	log.info(rootUrl);
    }

    // получаем адрес iframe из стартовой страницы 
    protected void extractFrameUrl() {
	
	try {
	    Document doc = Jsoup.connect(startUrl).get();
	    Elements els = doc.select("iframe");
	    frameUrl = els.first().attr("src");
	} catch (IOException ex) {
	    Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }

    //получаем все input в form
    private void extractInputAtributes(Document doc, Map<String, String> map) {
	Elements els = doc.select("form input");
	
	for (Element el : els) {
	    String elName = el.attr("name");
	    String elValue = el.attr("value");
	    map.put(elName, elValue);
	}
    }
    
    protected void extractUrlFromForm(Document doc) {

	Elements els = doc.select("form");
	if (els.size() > 1) {
	    log.warning("А форм то...  = " + els.size());
	}
	Element el = els.first();
	formUrl = rootUrl + el.attr("action");
	log.info(formUrl);
    }

    //получаем данные из iframe в том числе ссылку на "Призначити дату подачі документів"
    protected Map<String, String> extractDataFromFirstPage(Map<String, String> param) {
	
	Document doc = JsoupSSL.post(frameUrl, null);
	
	extractInputAtributes(doc, param);

	//команда перехода 
	String target = doc.select("#ctl00_plhMain_lnkSchApp").attr("href").split("'")[1];//Призначити дату подачі документів
	
	param.put("__EVENTTARGET", target);
	
	extractUrlFromForm(doc);
	//System.out.println(doc);
	param.remove("");
	return param;
    }
    
    protected Map<String, String> stepTwo( Map<String, String> param) {
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException ex) {
	    Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
	}
	log.info(formUrl);
	Document doc = JsoupSSL.post(formUrl, param);

	
	param.clear();
	
	System.out.println(doc);
	
	
	return param;
    }
    
    protected Map<String, String> getSity(String url,Map<String, String> par) {
	
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
    
    private void  viewParams(Map<String,String> map){
         StringBuilder sb = new StringBuilder(11);
	 for (Map.Entry<String, String> entrySet : map.entrySet()) {
	    sb.append( entrySet.getKey()).append(" = ")
		    .append(entrySet.getValue()).append("\n");
	}
    
	 log.info(sb.toString());
    
    }
    
}
