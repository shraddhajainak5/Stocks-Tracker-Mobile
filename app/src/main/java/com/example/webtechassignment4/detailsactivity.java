package com.example.webtechassignment4;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.Spanned;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.text.style.ClickableSpan;
import android.text.method.LinkMovementMethod;
import android.text.SpannableStringBuilder;
import android.webkit.JavascriptInterface;
import android.webkit.WebViewClient;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.util.Log;
import org.json.JSONArray;
import com.android.volley.toolbox.JsonArrayRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import org.json.JSONObject;
import java.util.ArrayList;
import android.util.Pair;
import java.util.List;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.text.SpannableString;
import androidx.appcompat.app.AlertDialog;
import java.net.URLEncoder;
import android.os.Handler;
import com.squareup.picasso.Transformation;
import androidx.recyclerview.widget.RecyclerView;

public class detailsactivity extends AppCompatActivity {

    private TextView tickersymbol;
    private ImageButton detailsback;
    private TextView companysymbol;
    private TextView companyname;
    private TextView currentprice;
    private TextView changepercent;
    private ImageView trendingup;
    private ImageView trendingdown;
    private RequestQueue requestqueue;
    private ImageButton hourchartsymbol;
    private ImageButton historicalchartsymbol;
    private TextView portfoliosharesowned;
    private TextView portfolioavgcost;
    private TextView portfoliototalcost;
    private TextView portfoliochange;
    private TextView portfoliomarketval;
    private TextView statsop;
    private TextView statscp;
    private TextView statslp;
    private TextView statshp;
    private TextView aboutstartdate;
    private TextView aboutindustry;
    private TextView aboutwebpage;

    private TextView aboutpeers;
    private TextView tableticker;
    private TextView msprtotal;
    private TextView changetotal;
    private TextView msprpositive;
    private TextView changepositive;
    private TextView msprnegative;
    private TextView changenegative;
    private ImageButton watchlistempty;
    private ImageButton watchlistfull;
    private WebView hourchartdisplay;

    private WebView historicalchartdisplay;

    private WebView recommendationtrends;
    private WebView insighteps;
    private RecyclerView newsrecyclerview;
    private LinearLayout firstnewscard;
    private ImageView firstnewscardimage;
    private TextView firstnewscardsource;
    private TextView firstnewscadtitle;
    private TextView firstnewscardtime;
    private LinearLayout generalnewscard;
    private ImageView generalnewscardimage;
    private TextView generalnewscardsource;
    private TextView generalnewscadtitle;
    private TextView generalnewscardtime;
    private ImageButton tradebutton;
    private ImageView hourchartblue;
    private ImageView hourchartblueline;
    private ImageView historicalchartblue;
    private ImageView historicalchartblueline;
    String formattedWalletAmount;
    private Handler handler = new Handler();
    String namecompany;
    Double cp;
    Double d;
    Double dpercent;
    Double walletAmount;
    Integer sellquantity;
    boolean marketopen;
    private static final String TAG = "Log";


    List<Pair<Long, Double>> xdata = new ArrayList<>();
    private MutableLiveData<List<Pair<Long, Double>>> xDataLiveData = new MutableLiveData<>();
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailsactivity);
        String symbol = getIntent().getStringExtra("symbol");


        requestqueue = Volley.newRequestQueue(this);
        tickersymbol = findViewById(R.id.tickersymbol);
        tickersymbol.setText(symbol);
        detailsback = findViewById(R.id.detailback);
        companysymbol = findViewById(R.id.companysymbol);
        companyname = findViewById(R.id.companyname);
        currentprice = findViewById(R.id.currentprice);
        changepercent = findViewById(R.id.changepercent);
        trendingup = findViewById(R.id.trendingabove);
        trendingdown = findViewById(R.id.trendingdown);
        hourchartsymbol = findViewById(R.id.hourchart);
        historicalchartsymbol = findViewById(R.id.historicalchart);
        portfoliosharesowned = findViewById(R.id.portfoliosharesowned);
        portfolioavgcost = findViewById(R.id.portfolioavgcost);
        portfoliototalcost = findViewById(R.id.portfoliototalcost);
        portfoliochange = findViewById(R.id.portfoliochange);
        portfoliomarketval = findViewById(R.id.portfoliomarketval);
        statsop = findViewById(R.id.statsop);
        statscp = findViewById(R.id.statscp);
        statshp = findViewById(R.id.statshp);
        statslp = findViewById(R.id.statslp);
        aboutstartdate = findViewById(R.id.aboutstartdate);
        aboutindustry = findViewById(R.id.aboutindustry);
        aboutwebpage = findViewById(R.id.aboutwebpage);
        aboutpeers = findViewById(R.id.aboutpeers);
        tableticker = findViewById(R.id.tableticker);
        msprtotal = findViewById(R.id.msprtotal);
        changetotal = findViewById(R.id.changetotal);
        msprpositive = findViewById(R.id.msprpositive);
        changepositive = findViewById(R.id.changepositive);
        msprnegative = findViewById(R.id.msprnegative);
        changenegative = findViewById(R.id.changenegative);
        watchlistempty = findViewById(R.id.watchlistempty);
        watchlistfull = findViewById(R.id.watchlistfull);
        hourchartdisplay = findViewById(R.id.hourchartdisplay);
        historicalchartdisplay = findViewById(R.id.historicalchartdisplay);
        recommendationtrends = findViewById(R.id.recommendationtrends);
        insighteps = findViewById(R.id.insighteps);
        newsrecyclerview = findViewById(R.id.newsrecyclerview);
        tradebutton = findViewById(R.id.tradebutton);
        hourchartblue = findViewById(R.id.hourchartblue);
        hourchartblueline = findViewById(R.id.hourchartblueline);
        historicalchartblue = findViewById(R.id.historicalchartblue);
        historicalchartblueline = findViewById(R.id.historicalchartblueline);

        detailsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openedfirst = false;
                Intent intent = new Intent(detailsactivity.this, MainActivity.class);
                intent.putExtra("selectedsymbol", symbol);
                startActivity(intent);
            }
    });
        companydetailsone(symbol);

        watchlistempty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtofavorites(symbol);
                watchlistempty.setVisibility(View.INVISIBLE);
                watchlistfull.setVisibility(View.VISIBLE);
            }
        });

        watchlistfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removefromfavorites(symbol);
                watchlistfull.setVisibility(View.INVISIBLE);
                watchlistempty.setVisibility(View.VISIBLE);
            }
        });
        portfoliodetails(symbol);

        WebSettings websettings = hourchartdisplay.getSettings();
        hourchartdisplay.setWebViewClient(new WebViewClient());
        websettings.setJavaScriptEnabled(true);
        WebSettings websetting2 = historicalchartdisplay.getSettings();
        historicalchartdisplay.setWebViewClient(new WebViewClient());
        websetting2.setJavaScriptEnabled(true);
        WebSettings websetting3 = recommendationtrends.getSettings();
        recommendationtrends.setWebViewClient(new WebViewClient());
        websetting3.setJavaScriptEnabled(true);
        WebSettings websetting4 = insighteps.getSettings();
        insighteps.setWebViewClient(new WebViewClient());
        websetting4.setJavaScriptEnabled(true);

        chartdisplay(symbol);

        hourchartsymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historicalchartdisplay.setVisibility(View.INVISIBLE);
                hourchartdisplay.setVisibility(View.VISIBLE);
                hourchartblue.setVisibility(View.VISIBLE);
                hourchartblueline.setVisibility(View.VISIBLE);
                historicalchartblue.setVisibility(View.INVISIBLE);
                historicalchartblueline.setVisibility(View.INVISIBLE);
            }
        });

        historicalchartsymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historicalchartdisplay.setVisibility(View.VISIBLE);
                hourchartdisplay.setVisibility(View.INVISIBLE);
                historicalchartblue.setVisibility(View.VISIBLE);
                historicalchartblueline.setVisibility(View.VISIBLE);
                hourchartblue.setVisibility(View.INVISIBLE);
                hourchartblueline.setVisibility(View.INVISIBLE);
            }
        });
        recommendationtrendschart(symbol);

}
private void chartdisplay(String symbol){
        companydetailstwo(symbol);
}
private void companydetailsone(String symbol) {
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/companydetailsone?q=" + uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("ticker") && response.has("name")&&response.has("ipo")&&response.has("finnhubIndustry")&&response.has("weburl")) {
                                String symbolcompany = response.getString("ticker");
                                namecompany = response.getString("name");
                                String ipodate = response.getString("ipo");
                                String industry = response.getString("finnhubIndustry");
                                String weburl = response.getString("weburl");
                                //aboutstartdate.setText(ipodate);
                                aboutindustry.setText(industry);
                                aboutwebpage.setText(weburl);
                                companysymbol.setText(symbolcompany);
                                companyname.setText(namecompany);
                                tableticker.setText(""+namecompany);
                                String ipoformat[] = ipodate.split("-");
                                String year = ipoformat[0];
                                String month = ipoformat[1];
                                String day = ipoformat[2];
                                aboutstartdate.setText(month+"-"+day+"-"+year);
                                aboutwebpage.setOnClickListener(v -> {
                                    String url = weburl;
                                    Uri webpage = Uri.parse(url);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                    //intent.setPackage("com.android.chrome");
                                    startActivity(intent);

                                });
                            }
                            companypeers(uppersymbol);
                            insightstable(uppersymbol);
                            historicalchartdata(uppersymbol);
                            insightsepschart(uppersymbol);
                            newssection(uppersymbol);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing one JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching comp1 data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

private void companydetailstwo(String symbol){
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
                            String dtwodec = String.format("%.2f", response.getDouble("d"));
                            String cptwodec = String.format("%.2f", response.getDouble("c"));
                            Double o = response.getDouble("o");
                            Double close = response.getDouble("pc");
                            Double h = response.getDouble("h");
                            Double l = response.getDouble("l");
                            int t = response.getInt("t");
                            Long time = response.getLong("t");
                            String otwodec = String.format("%.2f", response.getDouble("o"));
                            String closetwodec = String.format("%.2f", response.getDouble("pc"));
                            String htwodec = String.format("%.2f", response.getDouble("h"));
                            String ltwodec = String.format("%.2f", response.getDouble("l"));

                            statsop.setText("$"+otwodec);
                            statscp.setText("$"+closetwodec);
                            statshp.setText("$"+htwodec);
                            statslp.setText("$"+ltwodec);
                            currentprice.setText("$"+cptwodec);
                            changepercent.setText("$"+dtwodec+" ("+dp+"%)");
                            if(d>=0){
                               changepercent.setTextColor(Color.GREEN);
                               trendingup.setVisibility(View.VISIBLE);
                            }
                            else{
                                changepercent.setTextColor(Color.RED);
                                trendingdown.setVisibility(View.VISIBLE);

                            }
                            marketopenclose(uppersymbol, time);
                            displayhourchart(uppersymbol,t, time);
                            tradebutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    opentradedialog(uppersymbol, cp);
                                    checksellquantity(uppersymbol);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error parsing two JSON", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(detailsactivity.this, "Error fetching comp2  data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    requestqueue.add(jsonObjectRequest);
    }

    private void marketopenclose(String symbol, long timestamp){
        Long differencetimestamp = (System.currentTimeMillis()) - (timestamp * 1000);
        if (differencetimestamp < 60 * 1000) {
         marketopen = true;
            Runnable fetchagain = new Runnable() {
                @Override
                public void run() {
                    updatedcompanydetailstwo(symbol);
                    handler.postDelayed(this, 15000);
                }
            };
            handler.postDelayed(fetchagain, 15000);
    }else {
            marketopen = false;
        }
    }

    private void updatedcompanydetailstwo(String symbol){
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
                                String dtwodec = String.format("%.2f", response.getDouble("d"));
                                String cptwodec = String.format("%.2f", response.getDouble("c"));
                                Double o = response.getDouble("o");
                                Double close = response.getDouble("pc");
                                Double h = response.getDouble("h");
                                Double l = response.getDouble("l");
                                int t = response.getInt("t");
                                Long time = response.getLong("t");
                                String otwodec = String.format("%.2f", response.getDouble("o"));
                                String closetwodec = String.format("%.2f", response.getDouble("pc"));
                                String htwodec = String.format("%.2f", response.getDouble("h"));
                                String ltwodec = String.format("%.2f", response.getDouble("l"));

                                statsop.setText("$"+otwodec);
                                statscp.setText("$"+closetwodec);
                                statshp.setText("$"+htwodec);
                                statslp.setText("$"+ltwodec);
                                currentprice.setText("$"+cptwodec);
                                changepercent.setText("$"+dtwodec+" ("+dp+"%)");
                                if(d>=0){
                                    changepercent.setTextColor(Color.GREEN);
                                    trendingup.setVisibility(View.VISIBLE);
                                }
                                else{
                                    changepercent.setTextColor(Color.RED);
                                    trendingdown.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing two JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching updatecomp2  data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void portfoliodetails(String symbol) {
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/checkinbuysell/" + uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean inbuysell = response.getBoolean("exists");
                            if (inbuysell) {

                                portfolioquant(uppersymbol);

                            }else if(!inbuysell){
                                portfoliosharesowned.setText("$0.00");
                                portfolioavgcost.setText("$0.00");
                                portfoliototalcost.setText("$0.00");
                                portfoliochange.setText("$0.00");
                                portfoliomarketval.setText(("$0.00"));
                                checkinfavorite(symbol);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing port JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching port data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void portfolioquant(String symbol){

        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/stocksforportfolio";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                String ticker = item.getString("ticker");

                                if (ticker.equals(symbol)) {
                                    Integer portquantity = item.getInt("quantity");
                                    Double total = item.getDouble("total");
                                    Double avgcost = total/portquantity;
                                    Double change = avgcost - cp;
                                    Double marketval = cp * portquantity;
                                    String totaltwodec = String.format("%.2f", total);
                                    String avgcosttwodec = String.format("%.2f", avgcost);
                                    String changetwodec = String.format("%.2f", change);
                                    String marketvaltwodec = String.format("%.2f", marketval);

                                    portfoliosharesowned.setText(""+portquantity);
                                    portfolioavgcost.setText("$"+avgcosttwodec);
                                    portfoliototalcost.setText("$"+totaltwodec);
                                    portfoliochange.setText("$"+changetwodec);
                                    portfoliomarketval.setText(("$"+marketvaltwodec));
                                    if(change>0){
                                        portfoliochange.setTextColor(Color.GREEN);
                                        portfoliomarketval.setTextColor(Color.GREEN);
                                    }else{
                                        portfoliochange.setTextColor(Color.RED);
                                        portfoliomarketval.setTextColor(Color.RED);
                                    }
                                }
                            }
                            checkinfavorite(symbol);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing port JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //Toast.makeText(detailsactivity.this, "Error fetching port data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonArrayRequest);

    }

    private void companypeers(String symbol) {
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/companypeers?q=" + uppersymbol;

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray peers = response;
                            SpannableStringBuilder peerssymbols = new SpannableStringBuilder();
                            for (int i = 0; i < peers.length(); i++) {
                                if (i > 0) {
                                    peerssymbols.append(", ");
                                }
                                String ticker = peers.getString(i);
                                int start = peerssymbols.length();
                                peerssymbols.append(ticker);

                                ClickableSpan clickableSpan = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        peerdetailscreen(ticker);
                                    }
                                };
                                peerssymbols.setSpan(clickableSpan, start, start + ticker.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }

                            if (aboutpeers != null) {
                                aboutpeers.setText(peerssymbols);
                                aboutpeers.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing peer JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //Toast.makeText(detailsactivity.this, "Error fetching peer data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(JsonArrayRequest);
    }

    private void peerdetailscreen(String symbol){
    Intent intent = new Intent(detailsactivity.this, detailsactivity.class);
    intent.putExtra("symbol", symbol);
    startActivity(intent);
    }

    private void insightstable(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/insighttab?q=" + uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            double tmspr = 0;
                            double tchange = 0;
                            double pmspr = 0;
                            double pchange = 0;
                            double nmspr = 0;
                            double nchange = 0;
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double msprvalue = dataobject.getDouble("mspr");
                                tmspr += msprvalue;
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double changevalue = dataobject.getDouble("change");
                                tchange += changevalue;
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double msprvalue = dataobject.getDouble("mspr");
                                if(msprvalue>0){
                                    pmspr += msprvalue;
                                }
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double msprvalue = dataobject.getDouble("mspr");
                                if(msprvalue<0){
                                    nmspr += msprvalue;
                                }
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double changevalue = dataobject.getDouble("change");
                                if(changevalue>0){
                                    pchange += changevalue;
                                }
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobject = data.getJSONObject(i);
                                double changevalue = dataobject.getDouble("change");
                                if(changevalue<0){
                                    nchange += changevalue;
                                }
                            }
                            msprtotal.setText(" "+String.format("%.2f", tmspr));
                            changetotal.setText(" "+String.format("%.2f", tchange));
                            msprpositive.setText(" "+String.format("%.2f", pmspr));
                            changepositive.setText(" "+String.format("%.2f", pchange));
                            msprnegative.setText(" "+String.format("%.2f", nmspr));
                            changenegative.setText(" "+String.format("%.2f", nchange));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing insight JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching insight data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void addtofavorites(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/addtowatchlist"+"?name="+namecompany+"&ticker="+uppersymbol+"&c="+cp+"&d="+d+"&dp="+dpercent;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            String toastmessage = uppersymbol + " added to favorites";
                            Toast.makeText(getApplicationContext(), toastmessage, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching watchlist data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);

    }

    private void removefromfavorites(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/deletewatchlist/"+uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String toastmessage = uppersymbol + " removed from favorites";
                        Toast.makeText(getApplicationContext(), toastmessage, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching removewatchlist data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);

    }

    private void checkinfavorite(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/checkwatchlist/" + uppersymbol;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean inwatchlist = response.getBoolean("exists");
                            if (inwatchlist) {
                                watchlistfull.setVisibility(View.VISIBLE);
                                watchlistempty.setVisibility(View.INVISIBLE);
                            }else{
                                watchlistfull.setVisibility(View.INVISIBLE);
                                watchlistempty.setVisibility(View.VISIBLE);
                            }
                            fetchwalletamount();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing fav JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                       // Toast.makeText(detailsactivity.this, "Error fetching fav data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);

    }

    private void displayhourchart(String symbol, int timestampc, long timestampl){
            String uppersymbol = symbol.toUpperCase();
            String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/hourchart"+"?q="+uppersymbol+"&timestamp="+ timestampc ;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray results = response.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject item = results.getJSONObject(i);
                                    long timestamp = item.getLong("t") * 1000;
                                    double value = item.getDouble("c");
                                    xdata.add(new Pair<>(timestamp, value));
                                }
                                WebAppInterface jsInterface = new WebAppInterface(xdata, symbol, timestampl);
                                Log.d(TAG,"timestamp"+ timestampl);
                                hourchartdisplay.addJavascriptInterface(jsInterface, "Android");
                                hourchartdisplay.loadUrl("file:///android_asset/hourchart.html");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(detailsactivity.this, "Error parsing hour JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error fetching hour chart data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            requestqueue.add(jsonObjectRequest);
        }

    public class WebAppInterface {
        private List<Pair<Long, Double>> xdata;
        private String symbol;
        private long timestampl;

        public WebAppInterface(List<Pair<Long, Double>> xdata, String symbol, long timestampl) {
            this.xdata = xdata;
            this.symbol = symbol;
            this.timestampl = timestampl;
        }

        @JavascriptInterface
        public String xdataforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < xdata.size(); i++) {
                Pair<Long, Double> pair = xdata.get(i);
                long timestamp = pair.first;
                double value = pair.second;
                //referred stackoverflow for line 833-841
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("[")
                        .append(timestamp)
                        .append(",")
                        .append(value)
                        .append("]");
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String tickerforhtml() {
            return symbol;
        }

        @JavascriptInterface
        public long timestampforhtml() {
            Log.d(TAG,"timestamp"+ timestampl);
            return timestampl;
        }

    }

    private void historicalchartdata(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/charttab?q="+ uppersymbol ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray results = response.getJSONArray("results");
                            List<stockdata> ohlc = new ArrayList<>();
                            List<Pair<Long, Long>> volume = new ArrayList<>();

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject item = results.getJSONObject(i);
                                long timestamp = item.getLong("t");
                                double o = item.getDouble("o");
                                double h = item.getDouble("h");
                                double l = item.getDouble("l");
                                double c = item.getDouble("c");
                                long v = item.getLong("v");

                                stockdata forohlc = new stockdata(timestamp, o, h, l, c);
                                ohlc.add(forohlc);
                                volume.add(new Pair<>(timestamp, v));
                            }
                            historicalchartdatainterface jsInterface = new historicalchartdatainterface(ohlc, volume, symbol);
                            historicalchartdisplay.addJavascriptInterface(jsInterface, "Android");
                            historicalchartdisplay.loadUrl("file:///android_asset/historicalchart.html");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing hist JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching hist chart data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void recommendationtrendschart(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/insighttrends?q="+ uppersymbol ;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {


                            ArrayList<Integer> buydata = new ArrayList<>();
                            ArrayList<Integer> holddata = new ArrayList<>();
                            ArrayList<Integer> selldata = new ArrayList<>();
                            ArrayList<Integer> strongbuydata = new ArrayList<>();
                            ArrayList<Integer> strongselldata = new ArrayList<>();
                            ArrayList<String> perioddata = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                int buy = item.getInt("buy");
                                int hold = item.getInt("hold");
                                String period = item.getString("period");
                                int sell = item.getInt("sell");
                                int strongBuy = item.getInt("strongBuy");
                                int strongSell = item.getInt("strongSell");

                                buydata.add(buy);
                                holddata.add(hold);
                                selldata.add(sell);
                                strongbuydata.add(strongBuy);
                                strongselldata.add(strongSell);
                                perioddata.add(period);
                            }
                            recommendationchartinterface trendsinterface = new recommendationchartinterface(buydata,holddata, selldata,strongbuydata,strongselldata,perioddata, symbol);
                            recommendationtrends.addJavascriptInterface(trendsinterface, "Android");
                            recommendationtrends.loadUrl("file:///android_asset/recommendationtrendschart.html");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing trend JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching trends chart data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void insightsepschart(String symbol){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/insighteps?q="+ uppersymbol ;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Double> actualdata = new ArrayList<>();
                            ArrayList<Double> estimateddata = new ArrayList<>();
                            ArrayList<Double> surprisedata = new ArrayList<>();
                            ArrayList<String> daydata = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                Double actual = item.getDouble("actual");
                                Double estimated = item.getDouble("estimate");
                                String day = item.getString("period");
                                Double surprise = item.getDouble("surprise");

                                actualdata.add(actual);
                                estimateddata.add(estimated);
                                surprisedata.add(surprise);
                                daydata.add(day);
                            }
                            insightepsinterface trendsinterface = new insightepsinterface(actualdata, estimateddata, surprisedata, daydata, symbol);
                            insighteps.addJavascriptInterface(trendsinterface, "Android");
                            insighteps.loadUrl("file:///android_asset/insightepschart.html");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing eps JSON ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching eps chart data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    public class historicalchartdatainterface {
        List<stockdata> ohlc;
        List<Pair<Long, Long>> volume;
        private String symbol;

        public historicalchartdatainterface(List<stockdata> ohlc,List<Pair<Long, Long>> volume, String symbol) {
            this.ohlc = ohlc;
            this.volume = volume;
            this.symbol = symbol;
        }

        @JavascriptInterface
        public String ohlcforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < ohlc.size(); i++) {
                stockdata data = ohlc.get(i);
                long timestamp = data.ohlctimestamp();
                double o = data.ohlco();
                double h = data.ohlch();
                double l = data.ohlcl();
                double c = data.ohlcc();
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("[")
                        .append(timestamp)
                        .append(",")
                        .append(o)
                        .append(",")
                        .append(h)
                        .append(",")
                        .append(l)
                        .append(",")
                        .append(c)
                        .append("]");
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String volumeforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < volume.size(); i++) {
                Pair<Long, Long> pair = volume.get(i);
                long timestamp = pair.first;
                long v = pair.second;
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("[")
                        .append(timestamp)
                        .append(",")
                        .append(v)
                        .append("]");
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String tickerforhtml() {

            return symbol;
        }

    }

    public class stockdata {
        private long timestamp;
        private double o;
        private double h;
        private double l;
        private double c;

        public stockdata(long timestamp, double o, double h, double l, double c) {
            this.timestamp = timestamp;
            this.o = o;
            this.h = h;
            this.l = l;
            this.c = c;
        }

        public long ohlctimestamp(){
            return timestamp;
        }

        public double ohlco(){
            return o;
        }

        public double ohlch(){
            return h;
        }

        public double ohlcl(){
            return l;
        }

        public double ohlcc(){
            return c;
        }

    }

    public class recommendationchartinterface {

        ArrayList<Integer> buydata;
        ArrayList<Integer> holddata;
        ArrayList<Integer> selldata;
        ArrayList<Integer> strongbuydata;
        ArrayList<Integer> strongselldata;
        ArrayList<String> perioddata;
        private String symbol;

        public recommendationchartinterface(ArrayList<Integer> buydata, ArrayList<Integer> holddata, ArrayList<Integer> selldata, ArrayList<Integer> strongbuydata, ArrayList<Integer> strongselldata, ArrayList<String> perioddata, String symbol) {
            this.buydata = buydata;
            this.holddata = holddata;
            this.selldata = selldata;
            this.strongbuydata = strongbuydata;
            this.strongselldata = strongselldata;
            this.perioddata = perioddata;
            this.symbol = symbol;
        }

        @JavascriptInterface
        public String buyforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < buydata.size(); i++) {
                int buy = buydata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(buy);
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String holdforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < holddata.size(); i++) {
                int hold = holddata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(hold);
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String strongbuyforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < strongbuydata.size(); i++) {
                int strongbuy = strongbuydata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(strongbuy);
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String strongsellforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < strongselldata.size(); i++) {
                int strongsell = strongselldata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(strongsell);
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String sellforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < selldata.size(); i++) {
                int sell = selldata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(sell);
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }

        @JavascriptInterface
        public String periodforhtml() {
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < perioddata.size(); i++) {
                String period = perioddata.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\"").append(period).append("\"");
            }
            jsonBuilder.append("]");
            return jsonBuilder.toString();
        }


        @JavascriptInterface
        public String tickerforhtml() {

            return symbol;
        }
    }
        public class insightepsinterface {

            ArrayList<Double> actualdata;
            ArrayList<Double> estimateddata;
            ArrayList<Double> surprisedata;
            ArrayList<String> daydata;
            private String symbol;

            public insightepsinterface(ArrayList<Double> actualdata, ArrayList<Double> estimateddata, ArrayList<Double> surprisedata, ArrayList<String> daydata,  String symbol) {
                this.actualdata = actualdata;
                this.estimateddata = estimateddata;
                this.surprisedata = surprisedata;
                this.daydata = daydata;
                this.symbol = symbol;
            }

            @JavascriptInterface
            public String actualforhtml() {
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < actualdata.size(); i++) {
                    double actual = actualdata.get(i);
                    if (i > 0) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append(actual);
                }
                jsonBuilder.append("]");
                return jsonBuilder.toString();
            }

            @JavascriptInterface
            public String estimatedforhtml() {
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < estimateddata.size(); i++) {
                    double estimated = estimateddata.get(i);
                    if (i > 0) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append(estimated);
                }
                jsonBuilder.append("]");
                return jsonBuilder.toString();
            }

            @JavascriptInterface
            public String surpriseforhtml() {
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < surprisedata.size(); i++) {
                    double surprise = surprisedata.get(i);
                    if (i > 0) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append(surprise);
                }
                jsonBuilder.append("]");
                return jsonBuilder.toString();
            }

            @JavascriptInterface
            public String dayforhtml() {
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < daydata.size(); i++) {
                    String day = daydata.get(i);
                    if (i > 0) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append("\"").append(day).append("\"");
                }
                jsonBuilder.append("]");
                return jsonBuilder.toString();
            }


            @JavascriptInterface
            public String tickerforhtml() {

                return symbol;
            }
        }

    private void newssection(String symbol) {
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/newstab?q=" + uppersymbol;

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            int totalitems = Math.min(response.length(), 20);

                            LinearLayout containerLayout = findViewById(R.id.allnewsdata);
                            containerLayout.removeAllViews();

                            for (int i = 0; i < totalitems; i++) {
                                JSONObject item = response.getJSONObject(i);
                                String headline = item.getString("headline");
                                String imageurl = item.getString("image");
                                String source = item.getString("source");
                                Long time = item.getLong("datetime");
                                String summary = item.getString("summary");
                                String url = item.getString("url");

                                Log.d(TAG, "news item " + i + " Image URL = " + imageurl);
                                String timepublished = timeofarticle(time);

                                if (i == 0) {
                                    View firstcarditem = LayoutInflater.from(detailsactivity.this)
                                            .inflate(R.layout.firstnewscard, containerLayout, false);

                                    TextView firstnewscadtitle = firstcarditem.findViewById(R.id.firstnewscardtitle);
                                    TextView firstnewscardtime = firstcarditem.findViewById(R.id.firstnewscardtime);
                                    ImageView firstnewscardimage = firstcarditem.findViewById(R.id.firstnewscardimage);
                                    TextView firstnewscardsource = firstcarditem.findViewById(R.id.firstnewscardsource);

                                    if (headline.length()>85){
                                        String trimmedheadline = headline.substring(0, 85)+"...";
                                        firstnewscadtitle.setText(trimmedheadline);
                                    } else if (headline.length()<=85) {
                                        firstnewscadtitle.setText(headline);
                                    }

                                   firstnewscardsource.setText(source + "   "+ timepublished );

                                    if (!TextUtils.isEmpty(imageurl)) {

                                       Picasso.get().load(imageurl).resize(300, 300).centerCrop().into(firstnewscardimage);
                                    }

                                    firstcarditem.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            opennewsdialog(source, time, headline, summary, url);
                                        }
                                    });

                                    containerLayout.addView(firstcarditem);
                                } else {

                                    View generalcarditem = LayoutInflater.from(detailsactivity.this)
                                            .inflate(R.layout.generalnewscard, containerLayout, false);

                                    TextView generalnewscadtitle = generalcarditem.findViewById(R.id.generalnewscardtitle);
                                    TextView generalnewscardsource = generalcarditem.findViewById(R.id.generalnewscardsource);
                                    ImageView generalnewscardimage = generalcarditem.findViewById(R.id.generalnewscardimage);
                                    TextView generalnewscardtime = generalcarditem.findViewById(R.id.generalnewscardtime);

                                    if (headline.length()>85){
                                        String trimmedheadline = headline.substring(0, 85)+"...";
                                        generalnewscadtitle.setText(trimmedheadline);
                                    } else if (headline.length()<=85) {
                                        generalnewscadtitle.setText(headline);
                                    }

                                    generalnewscardsource.setText(source + "   "+ timepublished );
                                    if (!TextUtils.isEmpty(imageurl)) {
                                       Picasso.get().load(imageurl).transform(new imagecornerspicasso(6)).resize(200,200).centerCrop().into(generalnewscardimage);
                                    }

                                    generalcarditem.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            opennewsdialog(source, time, headline, summary, url);
                                        }
                                    });
                                    containerLayout.addView(generalcarditem);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing news JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching news data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(JsonArrayRequest);
    }
    public class imagecornerspicasso  implements Transformation{
        private int radius;
        public imagecornerspicasso(int radius){
             this.radius = radius;

        }
        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap roundedBitmap = getRoundedCornerBitmap(source, radius);
            if (roundedBitmap != source) {
                source.recycle(); // Recycle the original bitmap if it's different from the rounded bitmap
            }
            return roundedBitmap;
        }
        @Override
        public String key() {
            return "rounded_corners";
        }
//referred geeks for geeks for line 1420 to 1430
        private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radius) {
            Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(roundedBitmap);
            final Paint paint = new Paint();
            final RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final float roundPx = radius * 2;
            paint.setAntiAlias(true);
            canvas.drawRoundRect(rect, roundPx, roundPx, paint);
            paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return roundedBitmap;
        }
    }

    private String timeofarticle(Long time){
        Long currenttime = System.currentTimeMillis()/1000;
        long seconds = currenttime - time ;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        Log.d(TAG, ""+hours);
        if (hours < 24) {
            return hours + " hours ago";
        } else if (minutes < 60) {
            return minutes + " minutes ago";
        } else if(seconds<60){
            return seconds + " seconds ago";
        }
        return hours + " hours ago";
    }

    private void opennewsdialog(String source, Long time, String headline, String summary, String url){
        View newsdialogview = LayoutInflater.from(detailsactivity.this)
                .inflate(R.layout.newsdialog, null);
        TextView newsdialogsource = newsdialogview.findViewById(R.id.newsdialogsource);
        TextView newsdialogdate = newsdialogview.findViewById(R.id.newsdialogdate);
        TextView newsdialogheadline = newsdialogview.findViewById(R.id.newsdialogheadline);
        TextView newsdialogsummary = newsdialogview.findViewById(R.id.newsdialogsummary);
        ImageButton newsdialogchrome = newsdialogview.findViewById(R.id.newsdialogchrome);
        ImageButton newsdialogtwitter = newsdialogview.findViewById(R.id.newsdialogtwitter);
        ImageButton newsdialogfacebook = newsdialogview.findViewById(R.id.newsdialogfacebook);

        newsdialogsource.setText(source);
        Long timemilliseconds = time*1000L;
        Date date = new Date(timemilliseconds);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        String datepublished = dateformat.format(date);
        newsdialogdate.setText(datepublished);
        newsdialogheadline.setText(headline);

        if (summary.length()>150){
            String trimmedsummary = summary.substring(0, 150)+"...";
            newsdialogsummary.setText(trimmedsummary);
        } else if (summary.length()<=150) {
            newsdialogsummary.setText(summary);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(detailsactivity.this);
        builder.setView(newsdialogview);
        final AlertDialog dialog = builder.create();
        dialog.show();
        newsdialogchrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);

            }
        });
        newsdialogtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                String twitterurl = "https://twitter.com/intent/tweet?text=" + URLEncoder.encode(url, "UTF-8");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl));
                //intent.setPackage("com.twitter.android");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl)));
            }catch (Exception e) {
                    e.printStackTrace();}
            }
        });
        newsdialogfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String facebookurl = "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(url, "UTF-8");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl));
                    //intent.setPackage("com.twitter.android");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl)));
                }catch (Exception e) {
                    e.printStackTrace();}
            }
        });
    }

    private void checksellquantity(String symbol){
        String sellurl ="https://webassignment3-service-idpstnzwpq-wl.a.run.app/checkstockquantity?ticker="+symbol;
        JsonObjectRequest selljsonObjectRequest = new JsonObjectRequest(Request.Method.GET, sellurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("quantity")) {
                                sellquantity = response.getInt("quantity");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(detailsactivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //Toast.makeText(detailsactivity.this, "Error fetching sell quantity data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(selljsonObjectRequest);
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
                                formattedWalletAmount = String.format("%.2f", walletAmount);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(detailsactivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching wallet data: " + error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void opentradedialog(String symbol, Double currentprice) {

        View tradedialogview = LayoutInflater.from(detailsactivity.this)
                .inflate(R.layout.tradedialog, null);
        TextView tradesharetext = tradedialogview.findViewById(R.id.tradesharetext);
        EditText sharequantity = tradedialogview.findViewById(R.id.sharesquantity);
        TextView tradedialogtotal = tradedialogview.findViewById(R.id.tradedialogtotal);
        ImageButton buybutton = tradedialogview.findViewById(R.id.buybutton);
        ImageButton sellbutton = tradedialogview.findViewById(R.id.sellbutton);
        TextView tradedialogwalletamount = tradedialogview.findViewById(R.id.tradedialogwalletamount);
        AlertDialog.Builder builder = new AlertDialog.Builder(detailsactivity.this);
        builder.setView(tradedialogview);
        final AlertDialog dialog = builder.create();
        dialog.show();
        tradesharetext.setText("Trade "+namecompany+" shares");
        sharequantity.setText("0");
        double initialTotal = 0.00;
        String formattotal = String.format("%.2f", initialTotal);
        String formatcp =String.format("%.2f", currentprice);
        tradedialogtotal.setText("0*"+formatcp+"="+formattotal);

        sharequantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String quantityStr = s.toString();
                int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);
                double total = quantity * currentprice;
                String formattotalchange = String.format("%.2f", total);
                tradedialogtotal.setText(quantity+"*"+formatcp+"="+formattotalchange);
            }
        });
        tradedialogwalletamount.setText("$ " + formattedWalletAmount + " to buy "+symbol);
        buybutton.setOnClickListener(view -> {
            String quantityentered = sharequantity.getText().toString().trim();
            int quantity = Integer.parseInt(quantityentered);
            double totalamount = quantity * currentprice;
            if (quantityentered.isEmpty() || !TextUtils.isDigitsOnly(quantityentered)) {
                Toast.makeText(detailsactivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if (quantity <= 0) {
                Toast.makeText(detailsactivity.this, "Cannot buy non-positive shares", Toast.LENGTH_SHORT).show();
                return;
            }
            if (totalamount > walletAmount) {
                Toast.makeText(detailsactivity.this, "Not enough money to buy", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                addtobuystocks(symbol, quantity, totalamount);
                dialog.dismiss();
                opencongratsbuydialog(symbol, quantity);
            }
        });

        sellbutton.setOnClickListener(view -> {
            String quantityentered = sharequantity.getText().toString().trim();
            int quantity = Integer.parseInt(quantityentered);
            double totalamount = quantity * currentprice;
            if (quantityentered.isEmpty() || !TextUtils.isDigitsOnly(quantityentered)) {
                Toast.makeText(detailsactivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if (quantity <= 0) {
                Toast.makeText(detailsactivity.this, "Cannot sell non-positive shares", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sellquantity < quantity) {
                Toast.makeText(detailsactivity.this, "Not enough shares to sell", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                removefrombuystocks(symbol, quantity, totalamount);
                dialog.dismiss();
                opencongratsselldialog(symbol, quantity);
            }
        });
    }

    private void addtobuystocks(String symbol, Integer quantity, Double totalamount){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/buysellstocks"+"?ticker="+uppersymbol+"&newquantity="+quantity+"&newtotal="+totalamount;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updatebuywallet(totalamount);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching buystocks data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);

    }

    private void updatebuywallet(Double total){
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/buywalletupdate?total="+total;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "updated buy wallet");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching buywallet data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void opencongratsbuydialog(String symbol, Integer quantity){
        View congratsdialogview = LayoutInflater.from(detailsactivity.this)
                .inflate(R.layout.congrats, null);
        TextView sharesquantity = congratsdialogview.findViewById(R.id.sharesquantity);
        TextView sharesticker = congratsdialogview.findViewById(R.id.sharesticker);
        ImageButton donebutton = congratsdialogview.findViewById(R.id.donebutton);

        sharesquantity.setText("You have successfully bought "+quantity);
        sharesticker.setText("shares of "+symbol);
        AlertDialog.Builder builder = new AlertDialog.Builder(detailsactivity.this);
        builder.setView(congratsdialogview);
        final AlertDialog dialog = builder.create();
        dialog.show();
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                portfolioquant(symbol);

            }
        });
    }

    private void opencongratsselldialog(String symbol, Integer quantity){
        View congratsdialogview = LayoutInflater.from(detailsactivity.this)
                .inflate(R.layout.congrats, null);
        TextView sharesquantity = congratsdialogview.findViewById(R.id.sharesquantity);
        TextView sharesticker = congratsdialogview.findViewById(R.id.sharesticker);
        ImageButton donebutton = congratsdialogview.findViewById(R.id.donebutton);

        sharesquantity.setText("You have successfully sold "+quantity);
        sharesticker.setText("shares of "+symbol);
        AlertDialog.Builder builder = new AlertDialog.Builder(detailsactivity.this);
        builder.setView(congratsdialogview);
        final AlertDialog dialog = builder.create();
        dialog.show();

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                portfolioquant(symbol);
            }
        });
    }

    private void removefrombuystocks(String symbol, Integer quantity, Double totalamount){
        String uppersymbol = symbol.toUpperCase();
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/sellstocks"+"?ticker="+symbol+"&newquantity="+quantity+"&newtotal="+totalamount;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updatesellwallet(totalamount);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching sellstocks data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

    private void updatesellwallet(Double total){
        String url = "https://webassignment3-service-idpstnzwpq-wl.a.run.app/sellwalletupdate?total="+total;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "updated sell wallet");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(detailsactivity.this, "Error fetching sell wallet data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);
    }

}
