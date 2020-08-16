package com.example.amst1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.amst1.ui.home.ItemList;
import com.example.amst1.ui.home.adaptador.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {
    private RecyclerView rvLista;
    private SearchView svSearch;
    private RecyclerAdapter adapter;
    private List<ItemList> items;
    //private TextView txtLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initValues();
        initListener();
    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svBuscador);
        //txtLibros = (TextView)findViewById(R.id.txt);
    }

    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);

        items = getItems();
        adapter = new RecyclerAdapter(items, this);
        rvLista.setAdapter(adapter);
    }

    private String serverConsulta = "https://flightsregister.000webhostapp.com/queriTA5.php";
    private List<ItemList> getItems() {
        List<ItemList> itemLists = new ArrayList<>();

        //Obtener Info de la base de datos
        String[] resultLibros = null;
        String queryLibro = "SELECT * FROM Libro";
        try {
            String[] datos = new String[]{//ahora van 3 variables
                    "query",//parametro diferenciador con la anterior funcion
                    serverConsulta,
                    queryLibro//es el query que el usuario esta ingresando
            };
            AsyncQuery async1 = new AsyncQuery();
            //text.setText(async1.execute(datos).get()[0].split("\\n")[3].split("--")[3]);
            resultLibros = async1.execute(datos).get()[0].split("\\n");
            String queryImg = "SELECT * FROM Imagen WHERE";
            String[] ord_por_idImg = new String[resultLibros.length];
            for(int i=1; i<resultLibros.length; i++){//desde 1 para evitar el encabezado
                String[] infoLibro = resultLibros[i].split("--");
                //Libro: id,titulo,autor,editorial,idImagen,cantidad,resumen
                if(i>1){
                    queryImg += " OR";
                }
                queryImg += " id="+infoLibro[4];

                ord_por_idImg[Integer.parseInt(infoLibro[4])] = resultLibros[i];
            }

            String[] datoImg = new String[]{//ahora van 3 variables
                    "query",//parametro diferenciador con la anterior funcion
                    serverConsulta,
                    queryImg//es el query que el usuario esta ingresando
            };
            String[] imagenes;
            AsyncQuery async2 = new AsyncQuery();
            imagenes = async2.execute(datoImg).get()[0]
                    .split("\\n");
            String valorTXT = "";
            for(int i=1; i<imagenes.length; i++) {//desde 1 para evitar el encabezado
                String[] ingLine = imagenes[i].split("--");
                String nomImagen = ingLine[1];
                int indice = Integer.parseInt(ingLine[0]);
                String[] infoLibro = ord_por_idImg[indice].split("--");
                itemLists.add(new ItemList(infoLibro[1], infoLibro[2], infoLibro[3], nomImagen, infoLibro[6]));
                valorTXT+=nomImagen+"\n";
            }


            //txtLibros.setText(valorTXT);

            //Crear los Objetos ItemList que contendrán la info de los libros
            //itemLists.add(new ItemList(resultLibros[1], resultLibros[2], resultLibros[3], imgLibro, resultLibros[6]));
            //itemLists.add(new ItemList(resultLibros[1], resultLibros[2], resultLibros[3], R.drawable.carrie, resultLibros[6]));

        } catch (ExecutionException e) {
            e.printStackTrace();
            //txtLibros.setText(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            //txtLibros.setText(e.getMessage());
        }

        //itemLists.add(new ItemList("Saga de Broly", "Ultima pelicula de DB, peleas epicas.", R.drawable.saga_broly));

        return itemLists;
    }

    /**
     * Ejecuta acciones de cuando se da click al item
     * el item tiene todos los valores del Libro
     * @param item
     */
    @Override
    public void itemClick(ItemList item) {
        /**Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("itemDetail", item);
        startActivity(intent);**/
    }

    /////////////////////////////////////////////////////
    //Métodos para la busqueda de items con el SearchView

    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }


}