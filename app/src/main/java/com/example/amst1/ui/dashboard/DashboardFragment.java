package com.example.amst1.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.amst1.AsyncQuery;
import com.example.amst1.LoginActivity;
import com.example.amst1.NavegationActivity;
import com.example.amst1.R;
import com.example.amst1.ui.home.HomeFragment;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import static android.view.Gravity.CENTER;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String query;
    private String server = "https://flightsregister.000webhostapp.com/queriTA5.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        /*final TextView textView = root.findViewById(R.id.textView4);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        final TableLayout tabla = root.findViewById(R.id.table);

        llenarTabla(tabla);

        return root;
    }

    public void llenarTabla(TableLayout tabla){
        String[] datos = consulta();
        String[] lineas = datos[0].split("\\r?\\n");
        Random rnd = new Random();
        for (int i = 1; i <lineas.length; i++){
            final String[] cate = lineas[i].split("--");
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tv.setText(cate[1]);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(CENTER);
            tv.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            tv.setPadding(100,100,100,100);
            TextView tvs = new TextView(getContext());
            tvs.setPadding(10, 100, 10,100);
            tr.addView(tv);
            tr.addView(tvs);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), LoginActivity.class );
                    i.putExtra("id", cate[0]);
                    i.putExtra("nombre",cate[1]);
                    startActivity(i);
                }
            });
            i++;
            final String[] cate2 = lineas[i].split("--");
            TextView tv2 = new TextView(getContext());
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tv2.setText(cate2[1]);
            tv2.setTextColor(Color.WHITE);
            tv2.setGravity(CENTER);
            tv2.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            tv2.setPadding(100,100,100,100);
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("id", cate2[0]);
                    i.putExtra("nombre",cate2[1]);
                    startActivity(i);
                }
            });
            tr.addView(tv2);
            tr.setPadding(100,30,100,30);
            tabla.addView(tr);
        }

    }
    public String[] consulta() {
        String[] resultado = null;
        query = "SELECT * FROM Categoria";
        try {
            String[] datos = new String[]{
                    "query",
                    server,
                    query
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}