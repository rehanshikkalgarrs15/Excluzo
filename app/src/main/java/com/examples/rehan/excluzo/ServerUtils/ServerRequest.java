package com.examples.rehan.excluzo.ServerUtils;

import android.content.Context;
import android.location.LocationListener;
import android.util.Log;

import com.examples.rehan.excluzo.Models.Product;
import com.examples.rehan.excluzo.Models.Rating;
import com.examples.rehan.excluzo.Parsers.ProductParser;
import com.examples.rehan.excluzo.Parsers.RatingsParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by rehan r on 07-09-2016.
 */
public class ServerRequest {

    HttpClientWrapper clientWrapper;
    public ServerRequest(){
        clientWrapper = new HttpClientWrapper();
    }

    public JSONObject userLogin(Context context, String email, String password){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("password", password);
            Log.e("JSON-REQUEST",request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.USER_LOGIN,request.toString());
            jsonObject = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public List<Product> loadItem(Context context, String subId){
        List<Product> products = new ArrayList<>();
        JSONObject request = new JSONObject();
        JSONObject responseObject = null;
        try {
            request.put("subcategory_id",subId);
            Log.e("<----REQUEST---->",request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.ITEM,request.toString());
            responseObject = new JSONObject(response);
            if (responseObject.getString("status").equals("success")){
                products = new ProductParser().parse(responseObject.getJSONArray("product"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Rating> loadRatings(String productid) {
        List<Rating> ratings = new ArrayList<>();
        JSONObject requestObjet = new JSONObject();
        JSONObject responseObject = null;
        try{
            requestObjet.put("productid",productid);
            Log.e("<-----REQUEST----->",requestObjet.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.ITEM,requestObjet.toString());
            responseObject = new JSONObject(response);
            if (requestObjet.getString("status").equals("success")){
                ratings = new RatingsParser().parse(responseObject.getJSONArray("ratings"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ratings;
    }
}
