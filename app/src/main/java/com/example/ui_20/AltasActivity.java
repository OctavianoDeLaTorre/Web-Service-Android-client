package com.example.ui_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.controlador.ControladorAlumno;
import com.example.modelo.Alumno;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AltasActivity extends AppCompatActivity {

    /*Componentes de la vista*/
    private TextInputEditText txtNumeroControl;
    private TextInputEditText txtNombre;
    private TextInputEditText txtPrimerApellido;
    private TextInputEditText txtSegundoApellido;
    private AutoCompleteTextView acEdad;
    private AutoCompleteTextView acSemestre;
    private AutoCompleteTextView acCarrera;

    private Button btnLimpiar;
    private Button btnGuardar;

    private ControladorAlumno controladorAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        controladorAlumno = new ControladorAlumno();
        configViews();
    }

    private void configViews() {
        /*Instanciar elementos de la vista*/
        txtNumeroControl = findViewById(R.id.txtNumControl);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrimerApellido = findViewById(R.id.txtPrimerApellido);
        txtSegundoApellido = findViewById(R.id.txtSegundoPaellido);
        acEdad = findViewById(R.id.acEdad);
        acSemestre = findViewById(R.id.acSemestre);
        acCarrera = findViewById(R.id.acCarrera);

        btnLimpiar = findViewById(R.id.btnLimbiar);
        btnGuardar = findViewById(R.id.btnGuardar);

        /*Agregar eventos a botones*/
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarAlumno();
            }
        });

        /*Agregar adaptador a AutoCompletTextView*/
        acEdad.setAdapter(crearAdaptadorEdad());
        acSemestre.setAdapter(crearAdaptadorSemestre());
        acCarrera.setAdapter(crearAdaptadorCarrera());

    }

    /**
     * Metodo para registrar alumnos
     */
    private void registrarAlumno(){
        Alumno alumno = obtnerDatos();

        //revisar si existe conexion con wifi
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni =  cn.getActiveNetworkInfo();

        //condicio para ver si hay coneccion
        if (ni != null && ni.isConnected()){
            if (alumno != null){
                new RegistrarAlumno().execute(alumno);
            }else{
                Toast.makeText(
                        AltasActivity.this,
                        "Falta datos... perro!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(
                    AltasActivity.this,
                    "Revisa tu conexión a internet!",
                    Toast.LENGTH_SHORT)
                    .show();
        }


    }

    private Alumno obtnerDatos(){
        ArrayList<String> datosAlumno = new ArrayList<>();
        /* Obtner los datos de la vista*/
        datosAlumno.add(txtNumeroControl.getText().toString());
        datosAlumno.add(txtNombre.getText().toString());
        datosAlumno.add(txtPrimerApellido.getText().toString());
        datosAlumno.add(txtSegundoApellido.getText().toString());
        datosAlumno.add(acEdad.getText().toString());
        datosAlumno.add(acSemestre.getText().toString());
        datosAlumno.add(acCarrera.getText().toString());

        /* Validar que los datos no esten vacios */
        for (String dato : datosAlumno){
            if (dato.isEmpty()){
                return null;
            }
        }

        Alumno alumno = new Alumno(
                datosAlumno.get(0),
                datosAlumno.get(1),
                datosAlumno.get(2),
                datosAlumno.get(3),
                datosAlumno.get(4),
                datosAlumno.get(5),
                datosAlumno.get(6)
        );

        return alumno;
    }

    private ArrayAdapter<String> crearAdaptadorEdad(){
        String [] edades = new String[120];

        for (int i = 0; i < edades.length ; i++) {
            edades[i] = String.valueOf(i+1);
        }

        ArrayAdapter<String> adaptadorEdades= new ArrayAdapter<>(AltasActivity.this, R.layout.support_simple_spinner_dropdown_item,edades);

        return adaptadorEdades;
    }

    private ArrayAdapter<String> crearAdaptadorSemestre(){
        String [] semestres = new String[12];

        for (int i = 0; i < semestres.length ; i++) {
            semestres[i] = String.valueOf(i+1);
        }

        ArrayAdapter<String> adaptadorSemestres = new ArrayAdapter<>(AltasActivity.this, R.layout.support_simple_spinner_dropdown_item,semestres);

        return adaptadorSemestres;
    }

    private ArrayAdapter<String> crearAdaptadorCarrera(){
        String [] carreras = new String[]{"ISC","LA","IIA","CP","MC"};

        ArrayAdapter<String> adaptadorCarreras = new ArrayAdapter<>(AltasActivity.this, R.layout.support_simple_spinner_dropdown_item,carreras);

        return adaptadorCarreras;
    }

    /**
     * Case para registrar alumnos en segundo plano
     */
    class RegistrarAlumno extends AsyncTask<Alumno,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Alumno... alumnos) {
            return controladorAlumno.registrarAlumno(alumnos[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(AltasActivity.this,"Alumno registrado con Exito",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(AltasActivity.this,"Ocurrio un error al registrar el alumno",Toast.LENGTH_SHORT).show();
        }
    }
}
