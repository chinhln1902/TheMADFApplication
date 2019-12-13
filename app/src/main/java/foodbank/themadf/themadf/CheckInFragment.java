package foodbank.themadf.themadf;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * For Clients Checking into the Food Bank
 */
public class CheckInFragment extends Fragment  implements View.OnClickListener {


    public CheckInFragment() {
        // Required empty public constructor
    }


    // BlueTooth Printer
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String value = "";
    int ticket = 0;
    ProgressDialog progressDialog;

    //Submit Form Button
    Button buttonClientCheckIn;

    //Client Input Fields
    EditText edtClientFirstName, edtClientLastName, edtDOB, edtClientDogSize,
            edtClientCatSize, edtClientBabiesSize, edtHomeless, edtGender, edtVetStat,
            edtClientFamilySize, edtClientStreetAddress, edtClientZipCode,
            edtPhone, edtEmail, edtCity, edtState;
    String gender;

    //For Privacy Policy & Terms Consent
    CheckBox edtCheckBox;

    //Handling data transport
    RequestQueue queue;

    //Text prompts
    TextView tFirstNN, tLastN, tDOB, tSig, tStreet, tZip, message, tNewClient,
            tHomeless, tGender, tVetStat, tEmail, tPhone, tCity;

    //Date Picker
    ImageButton imageButton;

    //Buttons
    RadioGroup rgGender, rgVetStat, rgHomeless;
    RadioButton yesButton, noButton, gother, gmale, gfemale, vother, vyes, vno, hother, hyes, hno;

    //For Date Handling
    private int mYear, mMonth, mDay;

    //Display UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_in, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        // Display require fields
        yesButton = (RadioButton) view.findViewById(R.id.yesButton);
        noButton = (RadioButton) view.findViewById(R.id.noButton);

        //Edit Text Fields for user input
        tFirstNN = (TextView) view.findViewById(R.id.tFirstN);
        tLastN = (TextView) view.findViewById(R.id.tLastN);
        tDOB = (TextView) view.findViewById(R.id.tDOB);
        tHomeless = (TextView) view.findViewById(R.id.tHomeless);
        tGender = (TextView) view.findViewById(R.id.tGender);
        tVetStat = (TextView) view.findViewById(R.id.tVetStat);
        tEmail = (TextView) view.findViewById(R.id.tEmail);
        tPhone = (TextView) view.findViewById(R.id.tPhone);
        tCity = (TextView) view.findViewById(R.id.tcity);
        tSig = (TextView) view.findViewById(R.id.tSig);
        tStreet = (TextView) view.findViewById(R.id.tStreet);
        tZip = (TextView) view.findViewById(R.id.tZip);
        tNewClient = (TextView) view.findViewById(R.id.tNewClient);

        //Radio Groups for Gender & Veteran Status
        rgGender = (RadioGroup) view.findViewById(R.id.rgGender);
        rgVetStat = (RadioGroup) view.findViewById(R.id.rgVetStat);
        rgHomeless = (RadioGroup) view.findViewById(R.id.rgHomeless);

        //Radio Group Buttons for Gender & Vet Status
        gother = (RadioButton) view.findViewById(R.id.gother);
        gmale = (RadioButton) view.findViewById(R.id.gmale);
        gfemale = (RadioButton) view.findViewById(R.id.gfemale);
        vother = (RadioButton) view.findViewById(R.id.vother);
        vyes = (RadioButton) view.findViewById(R.id.vyes);
        vno = (RadioButton) view.findViewById(R.id.vno);
        hother = (RadioButton) view.findViewById(R.id.hother);
        hyes = (RadioButton) view.findViewById(R.id.hyes);
        hno = (RadioButton) view.findViewById(R.id.hno);


        //"Submission", Submit form button, Date Picker button
        buttonClientCheckIn = (Button) view.findViewById(R.id.buttonClientCheckIn);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);


        //Edit Text Fields for User Input
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        edtCity = (EditText) view.findViewById(R.id.edtCity);
        edtState = (EditText) view.findViewById(R.id.edtState);
        edtClientFirstName = (EditText) view.findViewById(R.id.edtClientFirstName);
        edtClientLastName = (EditText) view.findViewById(R.id.edtClientLastName);
        edtDOB = (EditText) view.findViewById(R.id.edtDOB);
        edtClientFamilySize = (EditText) view.findViewById(R.id.edtClientFamilySize);
        edtClientStreetAddress = (EditText) view.findViewById(R.id.edtClientStreetAddress);
        edtClientZipCode = (EditText) view.findViewById(R.id.edtClientZipCode);
        edtCheckBox = (CheckBox) view.findViewById(R.id.edtCheckBox);
        edtClientDogSize = (EditText) view.findViewById(R.id.edtClientDogSize);
        edtClientCatSize = (EditText) view.findViewById(R.id.edtClientCatSize);
        edtClientBabiesSize = (EditText) view.findViewById(R.id.edtClientBabiesSize);

        //For Toast - "Thank You For Your Service!"
        vyes.setOnClickListener(this);
        hyes.setOnClickListener(this);

        //For Form Submission
        buttonClientCheckIn.setOnClickListener(this);

        //For Date Picker
        imageButton.setOnClickListener(this);

        // Initializing Queue for Volley
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Disables "Submit" button until all required fields are supplemented
        //checkFieldsForEmptyValues();

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            //GetDate from Calendar Icon
            case R.id.imageButton:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtDOB.setText(" " + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

                //Veteran Status = Undisclosed; Provide Client Feedback
            case R.id.vother:

                Toast.makeText(getContext(), "'Undisclosed' selected for being a Veteran", Toast.LENGTH_SHORT).show();

                break;

                //Veteran Status = Yes; toast "TYFYS'
            case R.id.vyes:

                Toast.makeText(getContext(), "Thank you For Your Service!", Toast.LENGTH_SHORT).show();

                break;

                //Veteran Status= No; Provide Feedback
            case R.id.vno:

                Toast.makeText(getContext(), "You Selected 'No' to being a Veteran", Toast.LENGTH_SHORT).show();

                break;

                //Homeless = Undisclosed; Provide Client Feedback
            case R.id.hother:

                Toast.makeText(getContext(), "'Undisclosed' selected for Homeless Status", Toast.LENGTH_SHORT).show();

                break;

                //Homeless = No; Provide Client Feedback
            case R.id.hno:

                Toast.makeText(getContext(), "You Selected 'No' to being homeless", Toast.LENGTH_SHORT).show();

                break;

                // Homeless = True; fill in address field (req'd) with ECP Address
            case R.id.hyes:

                Toast.makeText(getContext(), "Not a Problem, we'll use our Address!", Toast.LENGTH_SHORT).show();
                edtClientStreetAddress.setText("3543 E Mckinley Avenue");
                edtCity.setText("Tacoma");
                edtState.setText("WA");
                edtClientZipCode.setText("98404");

                break;

                //Gender = Female; make toast for client feedback
            case R.id.gfemale:

                Toast.makeText(getContext(), "You Selected 'Female' as your Gender", Toast.LENGTH_SHORT).show();

                break;

                //Gender = male; make toast for client feedback
            case R.id.gmale:

                Toast.makeText(getContext(), "You Selected 'Male' as your Gender", Toast.LENGTH_SHORT).show();

                break;

                //Gender = Undisclosed; make toast for client feedback
            case R.id.gother:

                Toast.makeText(getContext(), "'Undisclosed' selected for Gender", Toast.LENGTH_SHORT).show();

                break;

            case R.id.edtCheckBox:

                //Display Privacy Policy

                break;


                //Check for empty fields, then submit form
            case R.id.buttonClientCheckIn:
                //checkFieldsForEmptyValues();

                buttonClientCheckIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int zipCodeValidate = edtClientZipCode.getText().toString().trim().length();

                        Date currentTime = Calendar.getInstance().getTime();


                        //internal field check
                        if (edtClientFirstName.getText().toString().trim().length() > 0 && edtClientLastName.getText().toString().trim().length() > 0
                                && edtDOB.getText().toString().trim().length() > 0
                                && edtCheckBox.getText().toString().trim().length() > 0
                                && edtClientStreetAddress.getText().toString().trim().length() > 0 && zipCodeValidate > 0
                                && edtCheckBox.isChecked() && (yesButton.isChecked() || noButton.isChecked())) {
                            if (zipCodeValidate != 5) {
                                message.setText(edtClientZipCode.getText().toString() + " is an invalid ZipCode!");
                            } else if (zipCodeValidate == 5) {
                                ticket += 1;
                                final String printValue = "TICKET NUMBER: " + ticket + "\n" + currentTime + "\nName: [" +
                                        edtClientLastName.getText().toString() + "," + edtClientFirstName.getText().toString() +
                                        "]\nFamily Size: " + edtClientFamilySize.getText().toString() +
                                        "\nDogs: " + edtClientDogSize.getText().toString() + "\nCats: " + edtClientCatSize.getText().toString() +
                                        "\nBabies: " + edtClientBabiesSize.getText().toString() +
                                        "\n----------------------------\n\n\n" +
                                        "\n----------------------------\n\n\n" +
                                        "\n----------------------------\n\n\n";

                                //Indicates a New Client - Post's Client Check In Data to Database, Calls New Client Fragment
                                if (yesButton.isChecked()) {


                                    //create temp Bundle Object for storing Strings
                                    final Bundle extras = new Bundle();

                                    //Homeless Check
                                    int id3 = rgHomeless.getCheckedRadioButtonId();
                                    View radioButton3 = rgHomeless.findViewById(id3);
                                    int radioId3 = rgHomeless.indexOfChild(radioButton3);
                                    RadioButton btn3 = (RadioButton) rgHomeless.getChildAt(radioId3);
                                    final String homelessSelection = (String) btn3.getText();

                                    //Gender Check
                                    int id = rgGender.getCheckedRadioButtonId();
                                    View radioButton = rgGender.findViewById(id);
                                    int radioId = rgGender.indexOfChild(radioButton);
                                    RadioButton btn = (RadioButton) rgGender.getChildAt(radioId);
                                    final String genderSelection = (String) btn.getText();

                                    //Veteran Check
                                    int id2 = rgVetStat.getCheckedRadioButtonId();
                                    View radioButton2 = rgVetStat.findViewById(id2);
                                    int radioId2 = rgVetStat.indexOfChild(radioButton2);
                                    RadioButton btn2 = (RadioButton) rgVetStat.getChildAt(radioId2);
                                    final String vetSelection = (String) btn2.getText();

                                    //Populate Bundle with Clients Information
                                    extras.putString("FirstName", (edtClientFirstName.getText().toString()));
                                    extras.putString("LastName", (edtClientLastName.getText().toString()));
                                    extras.putString("DOB", (edtDOB.getText().toString()));
                                    extras.putString("Gender", genderSelection);
                                    extras.putString("Veteran", vetSelection);
                                    extras.putString("Street", (edtClientStreetAddress.getText().toString()));
                                    extras.putString("Zip", edtClientZipCode.getText().toString());
                                    extras.putString("City", edtCity.getText().toString());
                                    extras.putString("Email", edtEmail.getText().toString());
                                    extras.putString("Phone", edtPhone.getText().toString());
                                    extras.putString("Consent", "I Agree");

                                    // Alert - makes and displays an alert message
                                    AlertDialog.Builder alert = new AlertDialog.Builder(
                                            getActivity());
                                    alert.setTitle(Html.fromHtml(getString(R.string.warning)));
                                    alert
                                            .setIcon(R.drawable.cookingpot)
                                            .setMessage(Html.fromHtml(getString(R.string.warning1)))
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {


                                                    //Validates User Input & Calls initPrint() to print client ticket
                                                    IntentPrint(printValue);

                                                    //Populate New Client Check In for 1st time clients
                                                    NewClientFragment frag = new NewClientFragment();
                                                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.container, frag);
                                                    frag.setArguments(extras);
                                                    fragmentTransaction.addToBackStack(null);
                                                    fragmentTransaction.commit();

                                                    //Post Client Data
                                                    postData(edtClientFirstName.getText().toString(),
                                                            edtClientLastName.getText().toString(),
                                                            edtDOB.getText().toString(),
                                                            homelessSelection,
                                                            genderSelection,
                                                            vetSelection,
                                                            edtClientFamilySize.getText().toString(),
                                                            edtClientStreetAddress.getText().toString(),
                                                            edtState.getText().toString(),
                                                            edtClientZipCode.getText().toString(),
                                                            "I Agree",
                                                            edtCity.getText().toString(),
                                                            edtEmail.getText().toString(),
                                                            edtPhone.getText().toString()
                                                    );
                                                }
                                            });


                                    // create alert dialog
                                    AlertDialog alertDialog = alert.create();
                                    // show it
                                    alertDialog.show();
                                }

                                //Repeat Customer - No Need to submit New Client Data
                                if (noButton.isChecked()) {

                                    //Gender Selection String Extraction
                                    int id = rgGender.getCheckedRadioButtonId();
                                    View radioButton = rgGender.findViewById(id);
                                    int radioId = rgGender.indexOfChild(radioButton);
                                    RadioButton btn = (RadioButton) rgGender.getChildAt(radioId);
                                    final String genderSelection = (String) btn.getText();

                                    //Veteran Selection String Extraction
                                    int id2 = rgVetStat.getCheckedRadioButtonId();
                                    View radioButton2 = rgVetStat.findViewById(id2);
                                    int radioId2 = rgVetStat.indexOfChild(radioButton2);
                                    RadioButton btn2 = (RadioButton) rgVetStat.getChildAt(radioId2);
                                    final String vetSelection = (String) btn2.getText();

                                    //Homeless Check
                                    int id3 = rgHomeless.getCheckedRadioButtonId();
                                    View radioButton3 = rgHomeless.findViewById(id3);
                                    int radioId3 = rgHomeless.indexOfChild(radioButton3);
                                    RadioButton btn3 = (RadioButton) rgHomeless.getChildAt(radioId3);
                                    final String homelessSelection = (String) btn3.getText();

                                    //Alert - Informs Client their ticket is printing & use instructions
                                    AlertDialog.Builder alert = new AlertDialog.Builder(
                                            getActivity());
                                    alert.setTitle(Html.fromHtml(getString(R.string.warning)));
                                    alert.setIcon(R.drawable.warning);
                                    alert.setMessage(Html.fromHtml(getString(R.string.warning2)));
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {
                                            IntentPrint(printValue);
                                            postData(edtClientFirstName.getText().toString(),
                                                    edtClientLastName.getText().toString(),
                                                    edtDOB.getText().toString(),
                                                    homelessSelection,
                                                    genderSelection,
                                                    vetSelection,
                                                    edtClientFamilySize.getText().toString(),
                                                    edtClientStreetAddress.getText().toString(),
                                                    edtState.getText().toString(),
                                                    edtClientZipCode.getText().toString(),
                                                    "I Agree",
                                                    edtCity.getText().toString(),
                                                    edtEmail.getText().toString(),
                                                    edtPhone.getText().toString()

                                            );
                                        }
                                    });

                                    AlertDialog alertDialog = alert.create();
                                    alertDialog.show();
                                }
                            }
                        } else {
                            message.setText("Missing Information!");
                        }

                    }
                });
                break;

        }


        buttonClientCheckIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int zipCodeValidate = edtClientZipCode.getText().toString().trim().length();

                Date currentTime = Calendar.getInstance().getTime();


                if (edtClientFirstName.getText().toString().trim().length() > 0 && edtClientLastName.getText().toString().trim().length() > 0
                        && edtDOB.getText().toString().trim().length() > 0
                        && edtCheckBox.getText().toString().trim().length() > 0
                        && edtClientStreetAddress.getText().toString().trim().length() > 0 && zipCodeValidate > 0
                        && edtCheckBox.isChecked() && (yesButton.isChecked() || noButton.isChecked())) {
                    if (zipCodeValidate != 5) {
                        message.setText(edtClientZipCode.getText().toString() + " is  an invalid ZipCode!");
                    } else if (zipCodeValidate == 5) {
                        ticket += 1;
                        final String printValue = "TICKET NUMBER: " + ticket + "\n" + currentTime + "\nName: [" +
                                edtClientLastName.getText().toString() + "," + edtClientFirstName.getText().toString() +
                                "]\nFamily Size: " + edtClientFamilySize.getText().toString() +
                                "\nDogs: " + edtClientDogSize.getText().toString() + "\nCats: " + edtClientCatSize.getText().toString() +
                                "\nBabies: " + edtClientBabiesSize.getText().toString() +
                                "\n----------------------------\n\n\n" +
                                "\n----------------------------\n\n\n" +
                                "\n----------------------------\n\n\n";

                        //do stuff
                        if (yesButton.isChecked()) {


                            final Bundle extras = new Bundle();

                            //Gender
                            int id = rgGender.getCheckedRadioButtonId();
                            View radioButton = rgGender.findViewById(id);
                            int radioId = rgGender.indexOfChild(radioButton);
                            RadioButton btn = (RadioButton) rgGender.getChildAt(radioId);
                            final String genderSelection = (String) btn.getText();

                            //Veteran
                            int id2 = rgVetStat.getCheckedRadioButtonId();
                            View radioButton2 = rgVetStat.findViewById(id2);
                            int radioId2 = rgVetStat.indexOfChild(radioButton2);
                            RadioButton btn2 = (RadioButton) rgVetStat.getChildAt(radioId2);
                            final String vetSelection = (String) btn2.getText();

                            //Homeless Check
                            int id3 = rgHomeless.getCheckedRadioButtonId();
                            View radioButton3 = rgHomeless.findViewById(id3);
                            int radioId3 = rgHomeless.indexOfChild(radioButton3);
                            RadioButton btn3 = (RadioButton) rgHomeless.getChildAt(radioId3);
                            final String homelessSelection = (String) btn3.getText();

                            extras.putString("FirstName", (edtClientFirstName.getText().toString()));
                            extras.putString("LastName", (edtClientLastName.getText().toString()));
                            extras.putString("DOB", (edtDOB.getText().toString()));
                            extras.putString("Homeless", homelessSelection);
                            extras.putString("Gender", genderSelection);
                            extras.putString("Veteran", vetSelection);
                            extras.putString("LastName", (edtClientLastName.getText().toString()));
                            extras.putString("Street", (edtClientStreetAddress.getText().toString()));
                            extras.putString("State", edtState.getText().toString());
                            extras.putString("Zip", edtClientZipCode.getText().toString());
                            extras.putString("City", edtCity.getText().toString());
                            extras.putString("Email", edtEmail.getText().toString());
                            extras.putString("Phone", edtPhone.getText().toString());
                            extras.putString("Consent", "I Agree");


                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    getActivity());
                            // set title
                            alert.setTitle(Html.fromHtml(getString(R.string.warning)));
                            // set dialog message
                            alert
                                    .setIcon(R.drawable.cookingpot)
                                    .setMessage(Html.fromHtml(getString(R.string.warning1)))
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            IntentPrint(printValue);
                                            //Inflate the fragment
                                            NewClientFragment frag = new NewClientFragment();
                                            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.container, frag);
                                            frag.setArguments(extras);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();


                                            postData(edtClientFirstName.getText().toString(),
                                                    edtClientLastName.getText().toString(),
                                                    edtDOB.getText().toString(),
                                                    homelessSelection,
                                                    genderSelection,
                                                    vetSelection,
                                                    edtClientFamilySize.getText().toString(),
                                                    edtClientStreetAddress.getText().toString(),
                                                    edtState.getText().toString(),
                                                    edtClientZipCode.getText().toString(),
                                                    "I Agree",
                                                    edtCity.getText().toString(),
                                                    edtEmail.getText().toString(),
                                                    edtPhone.getText().toString()
                                            );
                                        }
                                    });
                            // create alert dialog
                            AlertDialog alertDialog = alert.create();
                            // show it
                            alertDialog.show();
                        }
                        if (noButton.isChecked()) {

                            //Gender
                            int id = rgGender.getCheckedRadioButtonId();
                            View radioButton = rgGender.findViewById(id);
                            int radioId = rgGender.indexOfChild(radioButton);
                            RadioButton btn = (RadioButton) rgGender.getChildAt(radioId);
                            final String genderSelection = (String) btn.getText();

                            //Veteran
                            int id2 = rgVetStat.getCheckedRadioButtonId();
                            View radioButton2 = rgVetStat.findViewById(id2);
                            int radioId2 = rgVetStat.indexOfChild(radioButton2);
                            RadioButton btn2 = (RadioButton) rgVetStat.getChildAt(radioId2);
                            final String vetSelection = (String) btn2.getText();

                            //Homeless Check
                            int id3 = rgHomeless.getCheckedRadioButtonId();
                            View radioButton3 = rgHomeless.findViewById(id3);
                            int radioId3 = rgHomeless.indexOfChild(radioButton3);
                            RadioButton btn3 = (RadioButton) rgHomeless.getChildAt(radioId3);
                            final String homelessSelection = (String) btn3.getText();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    getActivity());
                            // set title
                            alert.setTitle(Html.fromHtml(getString(R.string.warning)));
                            // set dialog message
                            alert.setIcon(R.drawable.warning);
                            alert.setMessage(Html.fromHtml(getString(R.string.warning2)));
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    IntentPrint(printValue);
                                    postData(edtClientFirstName.getText().toString(),
                                            edtClientLastName.getText().toString(),
                                            edtDOB.getText().toString(),
                                            homelessSelection.substring(0, homelessSelection.indexOf("/")),
                                            genderSelection.substring(0, genderSelection.indexOf("/")),
                                            vetSelection.substring(0, vetSelection.indexOf("/")),
                                            edtClientFamilySize.getText().toString(),
                                            edtClientStreetAddress.getText().toString(),
                                            edtState.getText().toString(),
                                            edtClientZipCode.getText().toString(),
                                            "I Agree",
                                            edtCity.getText().toString(),
                                            edtEmail.getText().toString(),
                                            edtPhone.getText().toString()

                                    );
                                }
                            });
                            // create alert dialog
                            AlertDialog alertDialog = alert.create();
                            // show it
                            alertDialog.show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Please Provide Missing Information", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void postData(final String fName, final String lName,
                         final String dob, final String gend, final String vet, final String home,
                         final String fSize, final String street, final String state, final String zip,
                         final String checkBox, final String city, final String email, final String phone) {

        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.clientCheckInUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            Snackbar.make(buttonClientCheckIn, "Successfully Posted", Snackbar.LENGTH_LONG).show();
                            edtClientFirstName.setText(null);
                            edtClientLastName.setText(null);
                            edtDOB.setText(null);
                            gother.setChecked(true);
                            vother.setChecked(true);
                            hother.setChecked(true);
                            gmale.setChecked(false);
                            gfemale.setChecked(false);
                            vyes.setChecked(false);
                            vno.setChecked(false);
                            hyes.setChecked(false);
                            hno.setChecked(false);
                            edtEmail.setText(null);
                            edtPhone.setText(null);
                            edtCity.setText(null);
                            edtState.setText(null);
                            edtClientStreetAddress.setText(null);
                            edtClientFamilySize.setText(null);
                            edtClientZipCode.setText(null);
                            edtClientDogSize.setText(null);
                            edtClientCatSize.setText(null);
                            edtClientBabiesSize.setText(null);
                            edtCheckBox.setChecked(false);

                        } else {
                            Snackbar.make(buttonClientCheckIn, "Try Again", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(buttonClientCheckIn, "Error while Posting Data", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.FIRSTNAME_CHECKIN, fName);
                params.put(Constants.LASTNAME_CHECKIN, lName);
                params.put(Constants.DOB, dob);
                params.put(Constants.GENDER, gend);
                params.put(Constants.VETSTATUS, vet);
                params.put(Constants.FAMILYSIZE_CHECKIN, fSize);
                params.put(Constants.STREETADDRESS_CHECKIN, street);
                params.put(Constants.STATE, state);
                params.put(Constants.ZIPCODE, zip);
                params.put(Constants.SIGNATURE_CHECKIN, "I Agree");
                params.put(Constants.CITY, city);
                params.put(Constants.EMAIL, email);
                params.put(Constants.PHONE, phone);
                params.put(Constants.HOMELESS, home);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void IntentPrint(String txtvalue) {
        byte[] buffer = txtvalue.getBytes();
        byte[] PrintHeader = {(byte) 0xAA, 0x55, 2, 0};
        PrintHeader[3] = (byte) buffer.length;
        InitPrinter();
        if (PrintHeader.length > 256) {
            value += "\nValue is more than 256 size\n";

        } else {
            try {
                outputStream.write(txtvalue.getBytes());
                outputStream.close();
                socket.close();
            } catch (Exception ex) {
                value += ex.toString() + "\n" + "Except IntentPrint \n";

            }
        }
    }

    public void InitPrinter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("BlueTooth Printer")) //Note, you will need to change this to match the name of your device
                    {
                        bluetoothDevice = device;
                        break;
                    }
                }

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
                bluetoothAdapter.cancelDiscovery();
                socket.connect();
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                beginListenForData();
            } else {
                value += "No Devices found";

                return;
            }
        } catch (Exception ex) {
            value += ex.toString() + "\n" + " InitPrinter \n";

        }
    }

    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = inputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                inputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Log.d("e", data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkFieldsForEmptyValues() {

        String s1 = edtClientFirstName.getText().toString();
        String s2 = edtClientLastName.getText().toString();
        String s3 = edtDOB.getText().toString();
        String s4 = edtClientDogSize.getText().toString();
        String s5 = edtClientCatSize.getText().toString();
        String s6 = edtClientBabiesSize.getText().toString();
        String s7 = edtClientFamilySize.getText().toString();
        String s8 = edtClientStreetAddress.getText().toString();
        String s9 = edtClientZipCode.getText().toString();
        String s10 = edtPhone.getText().toString();
        String s11 = edtEmail.getText().toString();
        String s12 = edtCity.getText().toString();
        String s13 = edtState.getText().toString();


        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") ||
                s5.equals("") || s6.equals("") || s7.equals("") || s8.equals("") ||
                s9.equals("") || s10.equals("") || s11.equals("") || s12.equals("") ||
                s13.equals("")) {
            buttonClientCheckIn.setEnabled(false);
        } else {
            buttonClientCheckIn.setEnabled(true);
        }
    }
}






