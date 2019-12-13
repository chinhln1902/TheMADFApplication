package foodbank.themadf.themadf;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodWeightFragment extends Fragment  implements View.OnClickListener{


    public FoodWeightFragment() {
        // Required empty public constructor
    }
    ProgressDialog progressDialog;
    Button buttonFood;
    EditText edtHouseHold, edtCat, edtDog, edtWeight;
    RequestQueue queue;
    TextView tHouse, tWeight, wMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_food_weight, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        buttonFood = (Button) view.findViewById(R.id.buttonFood);
        edtHouseHold = (EditText) view.findViewById(R.id.edtHouseHold);
        edtCat = (EditText) view.findViewById(R.id.edtCat);
        edtDog = (EditText) view.findViewById(R.id.edtDog);
        edtWeight = (EditText) view.findViewById(R.id.edtWeight);


        tHouse=(TextView)view.findViewById(R.id.tHouse);
        tHouse.setText(Html.fromHtml("HouseHold" + " " + getString(R.string.star)));
        tWeight=(TextView)view.findViewById(R.id.tWeight);
        tWeight.setText(Html.fromHtml("Weight (lbs)" + " " + getString(R.string.star)));
        wMessage=(TextView)view.findViewById(R.id.wMessage);
        buttonFood.setOnClickListener(this);


        // Initializing Queue for Volley
        queue = Volley.newRequestQueue(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {
        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtHouseHold.getText().toString().trim().length() > 0 && edtWeight.getText().toString().trim().length() > 0) {
                    wMessage.setText("");
                    postData(edtHouseHold.getText().toString().trim(),
                            edtCat.getText().toString().trim(),
                            edtDog.getText().toString().trim(),
                            edtWeight.getText().toString().trim());
                } else {
                    wMessage.setText("Please Enter Required Fields!");
                }
            }
        });

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void postData(final String household, final String cat, final String dog, final String weight) {

        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.foodWeightUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            Snackbar.make(buttonFood, "Successfully Posted", Snackbar.LENGTH_LONG).show();
                            edtHouseHold.setText(null);
                            edtDog.setText(null);
                            edtWeight.setText(null);
                            edtCat.setText(null);
                            wMessage.setText(null);
                        } else {
                            Snackbar.make(buttonFood, "Try Again", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(buttonFood, "Error while Posting Data", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.HOUSEHOLD, household);
                params.put(Constants.CAT, cat);
                params.put(Constants.DOG, dog);
                params.put(Constants.WEIGHT, weight);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
