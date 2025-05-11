package com.example.webtechassignment4;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.webtechassignment4.MainActivity.favoritecols;
import com.example.webtechassignment4.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.List;


public class favoritedata extends RecyclerView.Adapter<favoritedata.favoriteViewHolder> {

    private Context context;
    private List<favoritecols> items;
    private RequestQueue requestqueue;
    private RecyclerView recyclerview;

    public favoritedata(Context context, List<favoritecols> items, RecyclerView recyclerview) {
        this.context = context;
        this.items = items;
        this.recyclerview = recyclerview;
    }

    @NonNull
    @Override
    public favoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View favoritelayoutview = LayoutInflater.from(context).inflate(R.layout.favorite, parent, false);
        return new favoriteViewHolder(favoritelayoutview);
    }

    @Override
    public void onBindViewHolder(@NonNull favoriteViewHolder holder, int position) {
        favoritecols item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public class favoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView favoriteticker;
        private TextView favoritecompany;
        private TextView favoritecp;
        private TextView favoritechange;
        private ImageView favoritetrendingabove;
        private ImageView favoritetrendingdown;
        private ImageView favoriterightarrow;
        private LinearLayout deleteview;

        public favoriteViewHolder(@NonNull View favoriteitemview) {
            super(favoriteitemview);
            favoriteticker = favoriteitemview.findViewById(R.id.favoriteticker);
            favoritecompany = favoriteitemview.findViewById(R.id.favoritecompany);
            favoritecp = favoriteitemview.findViewById(R.id.favoritecp);
            favoritechange = favoriteitemview.findViewById(R.id.favoritechange);
            favoritetrendingabove = favoriteitemview.findViewById(R.id.favoritetrendingabove);
            favoritetrendingdown = favoriteitemview.findViewById(R.id.favoritetrendingdown);
            favoriterightarrow = favoriteitemview.findViewById(R.id.favoriterightarrow);
         //   deleteview = favoriteitemview.findViewById(R.id.deleteview);
        }

        public void bind(favoritecols item) {
            //deleteview.setVisibility(item.deleteviewvisible()? View.VISIBLE : View.GONE);
            favoriteticker.setText(item.tickercol());
            favoritecompany.setText(item.namecol());
            String currpricetwodec = String.format("%.2f", item.currpricecol());
            String changevaltwodec = String.format("%.2f", item.changevalcol());
            String changepercenttwodec = String.format("%.2f", item.changepercentcol());
            favoritecp.setText("$ " + currpricetwodec);
            favoritechange.setText("$"+changevaltwodec+"("+changepercenttwodec+"%)");
            if(item.changevalcol()>=0){
                favoritetrendingabove.setVisibility(View.VISIBLE);
                favoritechange.setTextColor(Color.GREEN);
                favoritetrendingdown.setVisibility(View.INVISIBLE);
            }else{
                favoritetrendingabove.setVisibility(View.INVISIBLE);
                favoritechange.setTextColor(Color.RED);
                favoritetrendingdown.setVisibility(View.VISIBLE);
            }
        }
    }

    public void swipetodelete(){
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int movefrom = viewHolder.getAdapterPosition();
                int moveto = target.getAdapterPosition();
                Collections.swap(items, movefrom, moveto);
                notifyItemMoved(movefrom, moveto);
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                removefromfavorites(position);
               //deleteviewonswipe(true,position);
            }
        };
        ItemTouchHelper itemtouchhelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemtouchhelper.attachToRecyclerView(recyclerview);
    }

    private void removefromfavorites(int position){
        if (position >= 0 && position < items.size()) {
            favoritecols itemtodelete = items.get(position);
            String symbol = itemtodelete.tickercol().toUpperCase();
            String uppersymbol = symbol.toUpperCase();
            String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/deletewatchlist/"+uppersymbol;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            items.remove(position);
                            notifyItemRemoved(position);
                            updateitemsposition(position);
                            if (items.size() > 0 && position == 0) {
                                notifyItemChanged(0);
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });
        requestqueue = Volley.newRequestQueue(context);
        requestqueue.add(jsonObjectRequest);}
    }
    private void updateitemsposition(int position){
        for (int i = position; i < items.size(); i++) {
            items.get(i).updateposition(i);
            notifyItemChanged(i);
        }
    }
    private void deleteviewonswipe(boolean yes, int position){
        if (position >= 0 && position < items.size()) {
            items.get(position).deleteviewvisibility(yes);
            notifyItemChanged(position);
        }
    }

}