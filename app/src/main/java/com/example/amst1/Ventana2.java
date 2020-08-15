package com.example.amst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Ventana2 extends AppCompatActivity {

    private String server = "https://flightsregister.000webhostapp.com/queriTA5.php";


    private String query;
    private EditText user;
    private EditText password;
    private ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana2);

        user= (EditText) findViewById(R.id.user);
        password= (EditText) findViewById(R.id.passw);
    }


    public void consulta(View view){
        String[] resultado = null;
        query="Select nombres From Cliente Where user= \""+user.getText().toString()+"\" And contrasenia= \""+password.getText().toString()+"\"";

        try {
            String[] datos = new String[]{
                    "query",
                    server,
                    query
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            //consulta.setText(resultado[0]);
            if (resultado.length > 0) {
                Toast toast = Toast.makeText(this, "Conexión Exitosa <3", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}