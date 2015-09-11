/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.as.spv;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andr
 */
public class CheckerPolandVisa {

    final String startUrl = "http://www.polandvisa-ukraine.com/scheduleappointment_2.html",
	    realUrl = "https://polandonline.vfsglobal.com/poland-ukraine-appointment/%28S%28gu3yez55vqab1dzzc5ya3455%29%29/AppScheduling/AppWelcome.aspx?P=s2x6znRcBRv7WQQK7h4MTjZiPRbOsXKqJzddYBh3qCA=";

    public void run(){
    
    
    
    }
    
    private void  stepOne(){
	Map<String,String> post = new HashMap<>(5);
             post.put("__EVENTTARGET", "ctl00$plhMain$lnkSchApp");
	
    }
    
    
    
    
}
