package com.example.conexion;

public class RespuestaPeticionHTTP {
    private String resultado ;
    private String mensaje ;

    public RespuestaPeticionHTTP(String resultado, String mensaje) {
        this.resultado = resultado;
        this.mensaje = mensaje;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
