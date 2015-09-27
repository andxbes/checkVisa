/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.as.spv;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Andrey
 */
public class JsoupSSL {

    final static Logger log = Logger.getLogger("JsoupSSL");

    public static void enableSSLSocket() throws NoSuchAlgorithmException, KeyManagementException {
	HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

	    @Override
	    public boolean verify(String string, SSLSession ssls) {
		return true;
	    }
	});

	SSLContext context = SSLContext.getInstance("TLS");
	context.init(null, new X509TrustManager[]{new X509TrustManager() {

	    @Override
	    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		log.info("checkClientTrusted");
	    }

	    @Override
	    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		log.info("checkServerTrusted");
	    }

	    @Override
	    public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	    }
	}}, new SecureRandom());

	HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

    }

    public static Document post2(String url, Map<String, String> param) {
	
	
	try {
	    enableSSLSocket();

	    Connection con = Jsoup.connect(url)
		    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0")
		    .referrer(url)
		    .header("Connection", "keep-alive")
		    .method(Connection.Method.POST);

	    if (param != null) {
		param.remove("");
		con.data(param);
	    }

	    Connection.Response response = con.execute();
	    
	    switch (response.statusCode()) {

		case 200:
		    log.info("Хорошо");
		    return response.parse();

		default:
		    log.info(Integer.toString(response.statusCode()));
		    break;

	    }

	} catch (IOException ex) {
	    Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NoSuchAlgorithmException | KeyManagementException ex) {
	    Logger.getLogger(JsoupSSL.class.getName()).log(Level.SEVERE, null, ex);
	}

	return null;
    }

    public static Document post(String url, Map<String, String> param) {

	try {
	    enableSSLSocket();

	    Connection con = Jsoup.connect(url)
		    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0")
		    .referrer(url)
		    .header("Connection", "keep-alive")
		    .method(Connection.Method.POST);

	    if (param != null) {
		param.remove("");
		con.data(param);
	    }

	    Connection.Response response = con.execute();

	    switch (response.statusCode()) {

		case 200:
		    log.info("Хорошо");
		    return response.parse();

		default:
		    log.info(Integer.toString(response.statusCode()));
		    break;

	    }

	} catch (IOException ex) {
	    Logger.getLogger(CheckerPolandVisa.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NoSuchAlgorithmException | KeyManagementException ex) {
	    Logger.getLogger(JsoupSSL.class.getName()).log(Level.SEVERE, null, ex);
	}

	return null;
    }

}
