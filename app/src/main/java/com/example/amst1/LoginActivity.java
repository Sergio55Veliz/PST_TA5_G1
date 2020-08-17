package com.example.amst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amst1.ui.notifications.NotificationsFragment;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private String server = "https://flightsregister.000webhostapp.com/queriTA5.php";


    private String query;
    private EditText user;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user= (EditText) findViewById(R.id.user);
        password= (EditText) findViewById(R.id.passw);
    }


    public void ingreso(View view){
        String[] resultado = null;
        query="Select * From Cliente Where user= \""+user.getText().toString()+"\" And contrasenia= \""+password.getText().toString()+"\"";

        try {
            String[] datos = new String[]{
                    "query",
                    server,
                    query
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            if (resultado.length > 0) {
                Toast toast = Toast.makeText(this, "Conexión Exitosa <3", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(this, NavegationActivity.class);
                startActivity(i);
                this.finish();
            } else{
                Toast toast = Toast.makeText(this, "Ingreso Fallido", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}