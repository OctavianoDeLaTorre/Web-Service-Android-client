package com.example.controlador;

import android.util.Log;

import com.example.conexion.ConexionServidor;
import com.example.conexion.RespuestaPeticionHTTP;
import com.example.modelo.Alumno;
import com.google.gson.Gson;

import java.net.MalformedURLException;

public class ControladorAlumno {
    private Gson gson;
    private ConexionServidor conexionServidor;
    private String urlServicio = "altas_alumnos.php";

    public ControladorAlumno() {
        gson = new Gson();
        try {
            conexionServidor = new ConexionServidor(urlServicio);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public boolean registrarAlumno(Alumno alumno){
        String cadenaJSON = gson.toJson(alumno,Alumno.class);
        String respuestaJSON = conexionServidor.peticionHTTP(cadenaJSON);

        RespuestaPeticionHTTP respuesta = gson.fromJson(respuestaJSON,RespuestaPeticionHTTP.class);

       return respuesta.getResultado().equals("true");
    }
}
