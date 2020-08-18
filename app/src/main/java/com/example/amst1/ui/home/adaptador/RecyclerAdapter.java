package com.example.amst1.ui.home.adaptador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amst1.LoadImage;
import com.example.amst1.R;
import com.example.amst1.ui.home.ItemList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<ItemList> items;//Lista de items
    private List<ItemList> originalItems;//Para filtrado y busqueda de peliculas
    private RecyclerItemClick itemClick;

    public RecyclerAdapter(List<ItemList> items, RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }
    /*
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
    */
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aqui se usa el item_list_view.xml que creamos
        //tambien implementamos la view para el Holder creado
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        return new RecyclerHolder(view);
    }

    /**
     * Se asignan datos de cada elemento de la Vista
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, final int position) {
        final ItemList item = items.get(position);
        holder.tvTitulo.setText("Título: "+item.getTitulo());
        holder.tvAutor.setText("Autor: "+item.getAutor());
        holder.tvEditorial.setText("Editorial: "+item.getEditorial());
        //holder.imgItem.setImageResource(item.getURL());
        //holder.imgItem.setImageURL();
        //se asigna una imagen en base a su URL sacado de 000webhost
        LoadImage loadImage = new LoadImage(holder.imgItem);
        Bitmap bitm = null;
        try {
            bitm = loadImage.execute(item.getImgURL()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        holder.imgItem.setImageBitmap(bitm);

        //En caso de darle click, se ejecuta el metodo en el HomeFragment
        //                              itemClick(ItemList item)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(item);
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("itemDetail", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/

    }



    /**
     * Va a indicar la cantidad de items que se van a crear
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Filtro en base al Titulo de las peliculas, valores de comparacion
     * son dados por el SearchView
     * @param strSearch
     */
    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {//Si no hay nada, se deja la Vista en su forma original
            items.clear();
            items.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear();
                List<ItemList> collect = originalItems.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(strSearch.toLowerCase()))
                        .collect(Collectors.toList());

                items.addAll(collect);
            }
            else {
                items.clear();
                for (ItemList i : originalItems) {
                    if (i.getTitulo().toLowerCase().contains(strSearch.toLowerCase())) {
                        items.add(i);
                    }
                }
            }
            //Si no coinciden terminos, se deja la Vista queda vacia
        }
        notifyDataSetChanged();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imgItem;
        private TextView tvTitulo;
        private TextView tvAutor;
        private TextView tvEditorial;

        /**
         * Inicializamos los campos de la vista
         * @param itemView
         */
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            //activa las variables de item_list:_view
            imgItem = itemView.findViewById(R.id.imgItem);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvEditorial = itemView.findViewById(R.id.tvEditorial);
        }
    }

    public interface RecyclerItemClick {//Se lo implementa en la clase HOME
        void itemClick(ItemList item);
    }

}
