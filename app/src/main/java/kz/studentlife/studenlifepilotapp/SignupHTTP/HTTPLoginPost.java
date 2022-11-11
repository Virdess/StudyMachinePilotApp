package kz.studentlife.studenlifepilotapp.SignupHTTP;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kz.studentlife.studenlifepilotapp.JWT.JWTDecode;
import kz.studentlife.studenlifepilotapp.JWT.TokenManager;
import kz.studentlife.studenlifepilotapp.RegisterPage3;

public class HTTPLoginPost {
    public String tokenToProvide;
    public void sendJsonPostRequest(Context MainActivity, String domain, String login, String password){

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity);
        JSONObject object = new JSONObject();
        try {

            object.put("username", login);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JWTDecode jwtDecode = new JWTDecode();

        // Enter the correct url for your api service site
        String url = "http://192.168.1.2:8081/api/v1" + domain;
        TokenManager tokenManager = new TokenManager(MainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                                try {
                                    JWTDecode.decodeJWT(response.getString("token"));
                                    tokenToProvide = response.getString("token");
                                    tokenManager.CreateLoginSession("username", login);
                                    Intent intent = new Intent("android.intent.action.MainPage");
                                    MainActivity.startActivity(intent);
                                    System.out.println(response);
                                } catch (UnsupportedEncodingException e) {
                                    System.out.println(e + "ENCODED OBJ ERR");
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    System.out.println(e + "ENCODED OBJ SEND ERR");
                                    e.printStackTrace();
                                }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity, "Попытка входа не удалась: что-то пошло не так..." + error, Toast.LENGTH_LONG).show();


            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    public void CreateUserHTTP(JSONObject regData, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);



        JWTDecode jwtDecode = new JWTDecode();

        // Enter the correct url for your api service site
        String url = "http://192.168.1.2:8081/api/v1/signup_prof";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, regData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Попытка входа не удалась: что-то пошло не так..." + error, Toast.LENGTH_LONG).show();


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}