package com.example.conexion;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ConexionServidor {

    private static final String urlServidor  = "http://10.0.2.2/tutorias/";
    private String metodo = "POST";
    private String urlServicio;

    private HttpURLConnection conexion = null;
    private URL mUrl = null;

    private OutputStream os;
    private InputStream is = null;


    public ConexionServidor(String urlServicio) throws MalformedURLException {
        mUrl = new URL(urlServidor+urlServicio);
    }

    public String peticionHTTP(String cadenaJSON){
        try {
            conexion = (HttpURLConnection) mUrl.openConnection();

            conexion.setDoOutput(true);
            conexion.setRequestMethod(metodo);

            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes());
            os.flush();
            os.close();

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cadena = new StringBuilder();

            String linea;

            while ((linea = br.readLine()) != null){
                cadena.append(linea+"\n");
            }
            is.close();

            String json = cadena.toString();

            Log.i("Mensaje servidor:", "Respuesta JSON: " + json);

            return json;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
