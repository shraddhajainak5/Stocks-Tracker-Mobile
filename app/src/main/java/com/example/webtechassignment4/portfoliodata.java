package com.example.webtechassignment4;

import static androidx.core.content.ContextCompat.startActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.webtechassignment4.MainActivity.buysellcols;
import com.example.webtechassignment4.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;


public class portfoliodata extends RecyclerView.Adapter<portfoliodata.PortfolioViewHolder> {

    private Context context;
    private List<buysellcols> items;
    private RecyclerView recyclerview;

    public portfoliodata(Context context, List<buysellcols> items, RecyclerView recyclerview) {
        this.context = context;
        this.items = items;
        this.recyclerview = recyclerview;
    }

    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View portfoliolayoutview = LayoutInflater.from(context).inflate(R.layout.portfoliolayout, parent, false);
        return new PortfolioViewHolder(portfoliolayoutview);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        buysellcols item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder {
        private TextView portfolioticker;
        private TextView portfolioshare;
        private TextView portfoliocp;
        private TextView portfoliochange;
        private ImageView portfoliotrendingabove;
        private ImageView portfoliotrendingdown;
        private ImageButton portfoliorightarrow;
        private static final String TAG = "DetailsActivity";

        public PortfolioViewHolder(@NonNull View portfolioitemview) {
            super(portfolioitemview);
            portfolioticker = portfolioitemview.findViewById(R.id.portfolioticker);
            portfolioshare = portfolioitemview.findViewById(R.id.portfolioshare);
            portfoliocp = portfolioitemview.findViewById(R.id.portfoliocp);
            portfoliochange = portfolioitemview.findViewById(R.id.portfoliochange);
            portfoliotrendingabove = portfolioitemview.findViewById(R.id.portfoliotrendingabove);
            portfoliotrendingdown = portfolioitemview.findViewById(R.id.portfoliotrendingdown);
            portfoliorightarrow = portfolioitemview.findViewById(R.id.portfoliorightarrow);
        }

        public void bind(buysellcols item) {
            String marketvaltwodec = String.format("%.2f", item.marketvalcol());
            String totalchangetwodec = String.format("%.2f", item.totalchangecol());
            String changepercenttwodec = String.format("%.2f", item.changepercentcol());
            portfolioticker.setText(item.tickercol());
            portfolioshare.setText(String.valueOf(item.quantcol()) + " shares");
            portfoliocp.setText("$ " + marketvaltwodec);
            portfoliochange.setText("$"+totalchangetwodec+"("+changepercenttwodec+"%)");
            Log.d(TAG, item.tickercol());
            String symbol = item.tickercol();
            portfoliorightarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, detailsactivity.class);
                    intent.putExtra("symbol", symbol);
                    context.startActivity(intent);
                }
            });

            if(item.totalchangecol()>0){
                portfoliotrendingabove.setVisibility(View.VISIBLE);
                portfoliotrendingdown.setVisibility(View.INVISIBLE);
                portfoliochange.setTextColor(Color.GREEN);
            }else if (item.totalchangecol()<0){
                portfoliotrendingabove.setVisibility(View.INVISIBLE);
                portfoliotrendingdown.setVisibility(View.VISIBLE);
                portfoliochange.setTextColor(Color.RED);
            }else {
                portfoliotrendingabove.setVisibility(View.INVISIBLE);
                portfoliotrendingdown.setVisibility(View.INVISIBLE);
            }

        }
    }

    public void reorderitems(){
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

            }
        };
        ItemTouchHelper itemtouchhelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemtouchhelper.attachToRecyclerView(recyclerview);
    }
}


