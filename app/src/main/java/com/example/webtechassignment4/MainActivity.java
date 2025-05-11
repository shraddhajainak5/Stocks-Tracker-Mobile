package com.example.webtechassignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import com.android.volley.toolbox.StringRequest;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private View progressstocktext;
    private ImageView iconimage;
    private RequestQueue requestqueue;
    private AutoCompleteTextView inputbar;
    private ImageButton search;
    private ImageButton backbutton;
    private ImageButton clearbutton;
    private TextView toolbarstocktext;
    private TextView date;
    private TextView portfoliotext;
    private TextView networthtext;
    private TextView networthvalue;
    private TextView cashtext;
    private TextView cashvalue;
    private Toolbar toolbar;
    private TextView favouritetext;
    private TextView finhublink;
    private RecyclerView portfoliosection;
    private RecyclerView favoritesection;
    private portfoliodata portfolioadapter;
    private favoritedata favoriteadapter;
    public View detailviewprogress;
    public ProgressBar detailprogressbar;
    List<buysellcols> buysellcols = new ArrayList<>();
    List<favoritecols> favoritecols = new ArrayList<>();
    Double cp;
    Double d;
    Double dpercent;
    Double walletAmount;
    Double networth;
    public static boolean openedfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        requestqueue = Volley.newRequestQueue(this);
        progressbar = findViewById(R.id.progressbar);
        progressstocktext = findViewById(R.id.progressstocktext);
        iconimage = findViewById(R.id.iconimage);
        progressbar.setVisibility(View.INVISIBLE);
        iconimage.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarstocktext = findViewById(R.id.toolbarstocktext);
        inputbar = findViewById(R.id.inputbar);
        search = findViewById(R.id.searchbutton);
        backbutton = findViewById(R.id.backbutton);
        clearbutton = findViewById(R.id.clearbutton);
        date = findViewById(R.id.date);
        portfoliotext = findViewById(R.id.portfoliotext);
        networthtext = findViewById(R.id.networthtext);
        networthvalue = findViewById(R.id.networthvalue);
        cashtext = findViewById(R.id.cashtext);
        cashvalue = findViewById(R.id.cashvalue);
        favouritetext = findViewById(R.id.favouritetext);
        finhublink = findViewById(R.id.finhublink);
        portfoliosection = findViewById(R.id.portfoliosection);
        favoritesection = findViewById(R.id.favouritesection);
        detailviewprogress = findViewById(R.id.detailviewprogress);
        detailprogressbar = findViewById(R.id.detailprogressbar);

        if (openedfirst) {
            hideiconimage();
        }else{
            progressbar.setVisibility(View.INVISIBLE);
            iconimage.setVisibility(View.INVISIBLE);

        }
        openedfirst = false;

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputbar.setVisibility(View.VISIBLE);
                backbutton.setVisibility(View.VISIBLE);
                toolbarstocktext.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputbar.setVisibility(View.GONE);
                backbutton.setVisibility(View.GONE);
                toolbarstocktext.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                clearbutton.setVisibility(View.GONE);
            }
        });
        inputbar.setVisibility(View.GONE);
        inputbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearbutton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            public void afterTextChanged(Editable s) {
                clearbutton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputbar.setText("");
                clearbutton.setVisibility(View.GONE);
            }
        });
        fetchwalletamount();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat datetoday = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        String formattedDate = datetoday.format(calendar.getTime());
        date.setText(formattedDate);
        finhublink.setOnClickListener(v -> {
            String url = "https://finnhub.io/";
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
           // intent.setPackage("com.android.chrome");
                startActivity(intent);
        });
        inputbar.setThreshold(1);
        inputbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String symbol = s.toString().trim();
                if (symbol.length()>=1) {
                    autocompletesuggestion(symbol);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        portfoliosection.setLayoutManager(new LinearLayoutManager(this));
        favoritesection.setLayoutManager(new LinearLayoutManager(this));
        }

        private void hideiconimage(){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    iconimage.setVisibility(View.INVISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    hideprogressbar();
                }
            }, 2000);
        }

    private void hideprogressbar(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iconimage.setVisibility(View.INVISIBLE);
                progressbar.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

        private void fetchwalletamount(){

            String url ="https://webassignment3-service-idpstnzwpq-wl.a.run.app/walletamount";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.has("walletamount")) {
                                    walletAmount = response.getDouble("walletamount");
                                    String formattedWalletAmount = String.format("%.2f", walletAmount);
                                    cashvalue.setText("$ " + formattedWalletAmount);
                                    progressbar.setVisibility(View.INVISIBLE);
                                    networth = walletAmount;
                                    String networthtwodec = String.format("%.2f", networth);
                                    networthvalue.setText("   $"+networthtwodec);
                                    displaydata();
                                }
                                portfoliodata();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            requestqueue.add(jsonObjectRequest);
        }

        private void displaydata(){
        progressbar.setVisibility(View.INVISIBLE);
        progressstocktext.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        portfoliotext.setVisibility(View.VISIBLE);
        networthtext.setVisibility(View.VISIBLE);
        networthvalue.setVisibility(View.VISIBLE);
        cashtext.setVisibility(View.VISIBLE);
        cashvalue.setVisibility(View.VISIBLE);
        finhublink.setVisibility(View.VISIBLE);
        favouritetext.setVisibility(View.VISIBLE);
    }

    private void autocompletesuggestion(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/autocomplete?q="+uppersymbol;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("result");
                            List<String> suggestions = new ArrayList<>();
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject item = result.getJSONObject(i);
                                String suggestion = item.getString("symbol") + " | " + item.getString("description");
                                suggestions.add(suggestion);
                                Log.d("AutoComplete", "Suggestion: " + suggestion);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_dropdown_item_1line,
                                    suggestions);
                            inputbar.setAdapter(adapter);
                            inputbar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedsymbol = (String) parent.getItemAtPosition(position);
                                    String[] parts = selectedsymbol.split(" \\| ");
                                    if (parts.length > 0) {
                                        String symboltodisplay = parts[0].trim();
                                        inputbar.setText(symboltodisplay);
                                        detailscreen(symboltodisplay);
                                    }
                                }});
                            inputbar.showDropDown();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
        }
        );
        requestqueue.add(jsonObjectRequest);
    }

    private void detailscreen(String symbol){
        detailviewprogress.setVisibility(View.VISIBLE);
        detailprogressbar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, detailsactivity.class);
        intent.putExtra("symbol", symbol);
        startActivity(intent);
    }

    private void companysymboltwo(String symbol, companyquotedetails callback){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/companydetailstwo?q=" + uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("c") && response.has("d") && response.has("dp")&&response.has("h")&&response.has("l")&&response.has("o")&&response.has("pc")&&response.has("t")) {
                                cp = response.getDouble("c");
                                d = response.getDouble("d");
                                dpercent = response.getDouble("dp");
                                String dp = String.format("%.2f", response.getDouble("dp"));
                                Double o = response.getDouble("o");
                                Double close = response.getDouble("pc");
                                Double h = response.getDouble("h");
                                Double l = response.getDouble("l");
                                int t = response.getInt("t");
                                callback.companyquotedetails(cp, d, dpercent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing two JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error fetching comp2  data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }
    private void portfoliodata(){

        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/stocksforportfolio";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            networth = walletAmount;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                String ticker = item.getString("ticker");
                                if (item.has("quantity")) {
                                    int portquantity = item.getInt("quantity");
                                    Double total = item.getDouble("total");
                                    Double avgcost = total / portquantity;
                                 companysymboltwo(ticker, new companyquotedetails() {
                                     @Override
                                     public void companyquotedetails(double cp, double d, double dpercent) {
                                         Double marketval = cp*portquantity;
                                         networth = networth+marketval;
                                         Double totalchange = (cp - avgcost)*portquantity;
                                         Double changepercent = (totalchange/total)*100;
                                         String networthtwodec = String.format("%.2f", networth);
                                         networthvalue.setText("   $"+networthtwodec);
                                         buysellcols.add(new buysellcols(ticker, portquantity, total, marketval, totalchange,changepercent));
                                         portfoliosection.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                                         portfolioadapter = new portfoliodata(MainActivity.this, buysellcols, portfoliosection);
                                         portfoliosection.setAdapter(portfolioadapter);
                                         portfolioadapter.reorderitems();
                                     }
                                 });
                                 networthvalue.setText("   $"+networth);
                                }
                                else {
                                    Log.e("portfoliodata", "Missing 'quantity' key in JSON object");
                                }
                                }
                            favoritedata();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing port JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error fetching port data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonArrayRequest);
    }

    private void favoritedata(){

        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/watchlist";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                String ticker = item.getString("ticker");
                                String namecomp = item.getString("name");

                                companysymboltwo(ticker, new companyquotedetails() {
                                    @Override
                                    public void companyquotedetails(double cp, double d, double dpercent) {

                                        favoritecols.add(new favoritecols(ticker, namecomp, cp, d, dpercent));
                                        favoritesection.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                                        favoriteadapter = new favoritedata(MainActivity.this, favoritecols, favoritesection);
                                        favoritesection.setAdapter(favoriteadapter);
                                        favoriteadapter.swipetodelete();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing port JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error fetching port data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonArrayRequest);
    }

    public class buysellcols {
        private String ticker;
        private int quantity;
        private double total;
        private double marketval;
        private double totalchange;
        private double changepercent;
        public buysellcols(String ticker, int quantity, double total, double marketval, double totalchange, double changepercent ) {
            this.ticker = ticker;
            this.quantity = quantity;
            this.total = total;
            this.marketval = marketval;
            this.totalchange = totalchange;
            this.changepercent = changepercent;
        }

        public String tickercol() {
            return ticker;
        }

        public int quantcol() {
            return quantity;
        }
        public double totalcol() {
            return total;
        }
        public double marketvalcol() {
            return marketval;
        }
        public double totalchangecol() {
            return totalchange;
        }
        public double changepercentcol() {
            return changepercent;
        }
    }
    public interface companyquotedetails {
        void companyquotedetails(double cp, double d, double dpercent);
    }
    public class favoritecols {
        private String ticker;
        private String companyname;
        private double currprice;
        private double changeval;
        private double changepercent;
        private int position;
        private boolean deleteviewvisible;
        public favoritecols(String ticker, String companyname, double currprice, double changeval, double changepercent ) {
            this.ticker = ticker;
            this.companyname = companyname;
            this.currprice = currprice;
            this.changeval = changeval;
            this.changepercent = changepercent;
        }
        public void updateposition(int position) {
            this.position = position;
        }
        public boolean deleteviewvisible(){
            return deleteviewvisible;
        }
        public void deleteviewvisibility(boolean deleteviewv){
            deleteviewvisible = deleteviewv;
        }
        public String tickercol() {
            return ticker;
        }
        public String namecol() {
            return companyname;
        }
        public double currpricecol() {
            return currprice;
        }
        public double changevalcol() {
            return changeval;
        }
        public double changepercentcol() {
            return changepercent;
        }
    }
}




