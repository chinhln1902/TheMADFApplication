package foodbank.themadf.themadf;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerFragment extends Fragment implements View.OnClickListener{


    public VolunteerFragment() {
        // Required empty public constructor
    }
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/1GU3ueDQhuzX1Z_SaP7UYtbB_tQiuGJ1OiWoIhSmqIjk/formResponse";
    //input element ids found from the live form page
    private static final String PREF_KEY = "NAMEKEY";
    private SharedPreferences mPrefs;
    DatePickerDialog picker;
    public static final String FIRSTNAME = "entry_1441903659=";
    public static final String LASTNAME = "entry_1063919093=";
    public static final String DOB = "entry_1011739144=";
    public static final String CLOCK_IN_HOUR="entry_2140498817=";
    public static final String CLOCK_OUT_HOUR="entry_108229625=";
    private EditText fNameIn, lNameIn,edtDOB;
    private TextView display, displayInfo, message;
    private Button clockInButton;
    private Button clockOutButton;
    private ImageButton imageButton;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        mPrefs = this.getActivity().getSharedPreferences(PREF_KEY
                , Context.MODE_PRIVATE);

        // User input
        fNameIn=(EditText)view.findViewById(R.id.fNameInput);
        fNameIn.setCursorVisible(false);
        lNameIn=(EditText)view.findViewById(R.id.lNameInput);
        lNameIn.setCursorVisible(false);
        edtDOB=(EditText)view.findViewById(R.id.edtDOB);

        display = (TextView) view.findViewById(R.id.display);
        displayInfo = (TextView) view.findViewById(R.id.displayInfo);
        displayInfo.setText(MainActivity.arrList.toString());



        message = (TextView) view.findViewById(R.id.message);
        clockInButton=(Button)view.findViewById(R.id.ClockInButton);
        clockOutButton=(Button)view.findViewById(R.id.ClockOutButton);
        imageButton=(ImageButton)view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtDOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        clockOutButton.setOnClickListener(this);
        clockInButton.setOnClickListener( new View.OnClickListener(){
                                              @Override
                                              public void onClick(View v) {
                                                  mPrefs = getActivity().getApplicationContext().getSharedPreferences(PREF_KEY, 0);

                                                  final String fName = fNameIn.getText().toString();
                                                  final String lName = lNameIn.getText().toString();
                                                  final String dob = edtDOB.getText().toString();




                                                  if (fNameIn.getText().length() == 0 || lNameIn.getText().length() == 0 ||
                                                          edtDOB.getText().length() == 0)  {
                                                      Toast.makeText(getActivity().getApplicationContext(), "Missing Fields ", Toast.LENGTH_LONG).show();
                                                      message.setText("Please Enter Your Information!");
                                                  }

                                                  else  {

                                                      message.setText("Hello " + fName.toUpperCase() + "!");

                                                      if (MainActivity.arrList.contains(fName) && MainActivity.arrList2.contains(lName) && MainActivity.arrList3.contains(dob)
                                                              ) {

                                                          message.setText(fName.toUpperCase() + " is already clocked in!");
                                                          display.setText("");
                                                          fNameIn.setText("");
                                                          lNameIn.setText("");
                                                          edtDOB.setText("");
                                                      }
                                                      else {

                                                          String clockInHour;
                                                          Calendar rightNow = Calendar.getInstance();
                                                          if (rightNow.get(Calendar.MINUTE) < 10){
                                                              clockInHour = rightNow.get(Calendar.HOUR_OF_DAY) + ":0" + rightNow.get(Calendar.MINUTE);
                                                          } else{
                                                              clockInHour = rightNow.get(Calendar.HOUR_OF_DAY) + ":" + rightNow.get(Calendar.MINUTE);
                                                          }

                                                          mPrefs.edit().putString(fName, clockInHour).apply();

                                                          MainActivity.arrList.add(fName);
                                                          MainActivity.arrList2.add(lName);
                                                          MainActivity.arrList3.add(dob);

                                                          displayInfo.setText(MainActivity.arrList.toString());

                                                          // Reset input values
                                                          fNameIn.setText("");
                                                          lNameIn.setText("");
                                                          edtDOB.setText("");
                                                      }

                                                  }

                                              }
                                          }
        );
        return view;
    }


    @Override
    public void onClick(View v) {

        final String fName = fNameIn.getText().toString();
        final String lName = lNameIn.getText().toString();
        final String dob = edtDOB.getText().toString();

        if (fName.length() !=0 && lName.length() !=0 && edtDOB.length() !=0) {
            message.setText("");
            mPrefs = getActivity().getApplicationContext().getSharedPreferences(PREF_KEY, 0);
            if (mPrefs.getString(fName, "").equals("")){
                message.setText("Please clock in!");
            }

            else{
                String clockInHour = mPrefs.getString(fName,  "");
                String clockOutHour;
                Calendar rightNow = Calendar.getInstance();
                if (rightNow.get(Calendar.MINUTE) < 10){
                    clockOutHour = rightNow.get(Calendar.HOUR_OF_DAY) + ":0" + rightNow.get(Calendar.MINUTE);
                } else{
                    clockOutHour = rightNow.get(Calendar.HOUR_OF_DAY) + ":" + rightNow.get(Calendar.MINUTE);
                }
                PostDataTask postDataTask = new PostDataTask();
                postDataTask.execute(URL,fName, lName, dob, clockInHour,clockOutHour);

                /////////////////
                // REMOVE HERE///
                ////////////////
                displayInfo.setText(MainActivity.arrList.toString());
                MainActivity.arrList.remove(fName);
                MainActivity.arrList2.remove(lName);
                MainActivity.arrList3.remove(dob);

                displayInfo.setText(MainActivity.arrList.toString());
                fNameIn.setText("");
                lNameIn.setText("");
                edtDOB.setText("");
                message.setText("Thanks for the valuable help " + fName.toUpperCase() + "!");
            }

        } else {
            message.setText("Please Enter Your Information");
        }
    }



    private class PostDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... data) {

            String url = data[0];
            String fNameData = data[1];
            String lNameData = data[2];
            String dobData = data[3];
            String clockIn = data[4];
            String clockOut = data[5];
            String postBody = "";

            try {
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = FIRSTNAME + URLEncoder.encode(fNameData, "UTF-8") + "&" +
                        LASTNAME + URLEncoder.encode(lNameData, "UTF-8") + "&" +
                        DOB + URLEncoder.encode(dobData, "UTF-8") + "&" +
                        CLOCK_IN_HOUR + URLEncoder.encode(clockIn, "UTF-8") + "&" +
                        CLOCK_OUT_HOUR + URLEncoder.encode(clockOut, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
                Log.d("Error:  ", "Exception");
            }

            try {
                HttpRequest httpRequest = new HttpRequest();
                httpRequest.sendPost(url, postBody);
            } catch (Exception exception) {
                exception.getStackTrace();
            }
            return null;
        }
    }
}
