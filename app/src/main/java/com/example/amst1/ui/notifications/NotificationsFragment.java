package com.example.amst1.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.amst1.AsyncQuery;
import com.example.amst1.LoginActivity;
import com.example.amst1.R;

import java.util.concurrent.ExecutionException;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private String query;
    private String server = "https://flightsregister.000webhostapp.com/queriTA5.php";
    private String[] results;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView user = root.findViewById(R.id.text_notifications);
        final TextView name = root.findViewById(R.id.textView5);
        final TextView lname = root.findViewById(R.id.textView6);
        final TextView mail = root.findViewById(R.id.textView7);
        final TextView cel = root.findViewById(R.id.textView8);
        final TextView cat = root.findViewById(R.id.textView9);

        results= consulta();

        user.setText(user.getText()+results[0]);
        name.setText(name.getText()+results[2]);
        lname.setText(lname.getText()+results[3]);
        mail.setText(mail.getText()+results[4]);
        cel.setText(cel.getText()+results[5]);
        cat.setText(cat.getText()+consultaCat(results[6]));

        /*notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }
    public String[] consulta(){
        query="Select * From Cliente Where user= \""+ LoginActivity.usuario+"\" ";
        String[] resultado = null;
        try {
            String[] datos = new String[]{
                    "query",
                    server,
                    query
            };
            AsyncQuery async = new AsyncQuery();
            String[] res = async.execute(datos).get();
            resultado = res[0].split("\\r?\\n")[1].split("--");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public String consultaCat(String id){
        query="Select * From Categoria Where id= \""+ id+"\" ";
        String resultado = null;
        try {
            String[] datos = new String[]{
                    "query",
                    server,
                    query
            };
            AsyncQuery async = new AsyncQuery();
            String[] res = async.execute(datos).get();
            resultado = res[0].split("\\r?\\n")[1].split("--")[1];
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}