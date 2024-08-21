package edu.utez.utilerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;

@SuppressLint("NewApi")
public class ConexionServidor {
	public static String ip ="192.168.1.75:8084";
	public String conexionServlet(String url, List<NameValuePair> valores){	
		
		if(Build.VERSION.SDK_INT >=
				Build.VERSION_CODES.HONEYCOMB){
			StrictMode.setThreadPolicy(new StrictMode
					.ThreadPolicy.Builder().detectDiskReads()
					.detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			
			StrictMode.setVmPolicy(new StrictMode.VmPolicy
					.Builder().detectLeakedSqlLiteObjects()
					.detectLeakedClosableObjects()
					.penaltyDeath().build());
		}
		
		HttpClient cliente = new DefaultHttpClient(); // Cliente 
		HttpPost post = new HttpPost(url); // Mensajero y dirección de destino
		
		StringBuffer mensaje = new StringBuffer();
		
		try {
		
			post.setEntity(new UrlEncodedFormEntity(valores)); //Agrego el sobre a mis valores
			HttpResponse respuesta = cliente.execute(post); // Enviar y recibir 
			mensaje = formatoTexto(respuesta.getEntity().getContent());
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mensaje.toString();
	} 
	
	public JSONObject conexionServletJSON(String url, List<NameValuePair> valores){
		
		if(Build.VERSION.SDK_INT >=	Build.VERSION_CODES.HONEYCOMB){
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyDeath().build());
		}
		
		HttpClient cliente = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);	

		JSONObject objetoJSON = null;
		try {
			
			UrlEncodedFormEntity entidad = new UrlEncodedFormEntity(valores, HTTP.UTF_8);
			post.setEntity(entidad);
			
			HttpResponse respuesta = cliente.execute(post);	
			String cadenaJSON = formatoTexto(respuesta.getEntity().getContent()).toString();
			objetoJSON = new JSONObject(cadenaJSON);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
		return objetoJSON;
	}
	
	public StringBuffer formatoTexto(InputStream input) throws IOException{		
		StringBuffer cadena = new StringBuffer();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(input));
	
		String linea = "";

		while((linea = in.readLine())!=null){
			cadena.append(linea+"\n");
		}		
		
		in.close();		
		return cadena;
	}
}
