package foodbank.themadf.themadf;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 */
public class NewClientFragment extends Fragment  {


    public NewClientFragment() {
        // Required empty public constructor
    }


    //Widget - GUI
    Spinner spRace1, spRace2, spRace3, spRace4, spRace5, spRace6, spRace7, spRace8, spRace9,
            spRace10;

    ProgressDialog progressDialog;
    Button buttonClientNew;
    CheckBox edtNewCheckBox;

    EditText edtMember2DOB, edtMember3DOB, edtMember4DOB, edtMember5DOB, edtMember6DOB, edtMember7DOB,
            edtMember8DOB,edtMember9DOB,edtMember10DOB;


    CheckBox edtCheckBox;
    RequestQueue queue;
    TextView edtNewClientFirstName,edtNewClientLastName, edtNewDOB,
            edtNewClientStreetAddress, edtNewClientZipCode, edtMember1DOB, nFirstN, nLastN, nDOB, nStreet, nZip, nSig,nMessage;
    ImageButton imageMem2Button,imageMem3Button, imageMem4Button, imageMem5Button,
            imageMem6Button, imageMem7Button, imageMem8Button, imageMem9Button, imageMem10Button;

    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_client, container, false);
        //getActivity().setTitle("New Client Form");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        nMessage = (TextView) view.findViewById(R.id.nMessage);
        // Display require fields
        nFirstN=(TextView)view.findViewById(R.id.nFirstN);
        nFirstN.setText(Html.fromHtml("First Name" + " " + getString(R.string.star)));
        nLastN=(TextView)view.findViewById(R.id.nLastN);
        nLastN.setText(Html.fromHtml("Last Name" + " " + getString(R.string.star)));

        nDOB= (TextView)view.findViewById(R.id.nDOB);
        nDOB.setText(Html.fromHtml("DOB" + " " + getString(R.string.star)));
        nStreet=(TextView)view.findViewById(R.id.nStreet);
        nStreet.setText(Html.fromHtml("Street" + " " + getString(R.string.star)));

        nZip=(TextView)view.findViewById(R.id.nZip);
        nZip.setText(Html.fromHtml("Zip Code" + " " + getString(R.string.star)));

        nSig=(TextView)view.findViewById(R.id.nSig);
        nSig.setText(Html.fromHtml("Signature" + " " + getString(R.string.star)));

        edtNewClientFirstName=(TextView)view.findViewById(R.id.edtNewClientFirstName);
        edtNewClientLastName=(TextView)view.findViewById(R.id.edtNewClientLastName);
        edtNewDOB=(TextView)view.findViewById(R.id.edtNewDOB);
        edtNewClientStreetAddress=(TextView)view.findViewById(R.id.edtNewClientStreetAddress);
        edtNewClientZipCode=(TextView)view.findViewById(R.id.edtNewClientZipCode);
        edtMember1DOB=(TextView)view.findViewById(R.id.edtMember1DOB);
        //call race spinner
        spRace1 = (Spinner) view.findViewById(R.id.spRace1);
        spRace2 = (Spinner) view.findViewById(R.id.spRace2);
        spRace3 = (Spinner) view.findViewById(R.id.spRace3);
        spRace4 = (Spinner) view.findViewById(R.id.spRace4);
        spRace5 = (Spinner) view.findViewById(R.id.spRace5);
        spRace6 = (Spinner) view.findViewById(R.id.spRace6);
        spRace7 = (Spinner) view.findViewById(R.id.spRace7);
        spRace8 = (Spinner) view.findViewById(R.id.spRace8);
        spRace9 = (Spinner) view.findViewById(R.id.spRace9);
        spRace10 = (Spinner) view.findViewById(R.id.spRace10);

        Bundle bundle = getArguments();
      //  edtNewClientFirstName = (EditText) view.findViewById(R.id.edtNewClientFirstName);

        edtNewClientFirstName.setText(String.valueOf(bundle.getString("FirstName")));
        edtNewClientLastName.setText(String.valueOf(bundle.getString("LastName")));
        edtNewDOB.setText(String.valueOf(bundle.getString("DOB")));

        spRace1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // Create list of Spinners
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                //do nothing
            }
        });

        edtMember1DOB.setText(String.valueOf(bundle.getString("DOB")));
        edtMember2DOB = (EditText) view.findViewById(R.id.edtMember2DOB);
        edtMember3DOB = (EditText) view.findViewById(R.id.edtMember3DOB);
        edtMember4DOB = (EditText) view.findViewById(R.id.edtMember4DOB);
        edtMember5DOB = (EditText) view.findViewById(R.id.edtMember5DOB);
        edtMember6DOB = (EditText) view.findViewById(R.id.edtMember6DOB);
        edtMember7DOB = (EditText) view.findViewById(R.id.edtMember7DOB);
        edtMember8DOB = (EditText) view.findViewById(R.id.edtMember8DOB);
        edtMember9DOB = (EditText) view.findViewById(R.id.edtMember9DOB);
        edtMember10DOB = (EditText)view. findViewById(R.id.edtMember10DOB);

        edtNewCheckBox = (CheckBox) view.findViewById(R.id.edtNewCheckBox);
        edtNewClientStreetAddress.setText(String.valueOf(bundle.getString("Street")));
        edtNewClientZipCode.setText(String.valueOf(bundle.getString("Zip")));

        imageMem2Button = (ImageButton) view.findViewById(R.id.imageMem2Button);
        imageMem2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember2DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace2.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        imageMem3Button = (ImageButton) view.findViewById(R.id.imageMem3Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember3DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace3.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        imageMem4Button = (ImageButton) view.findViewById(R.id.imageMem4Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember4DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace4.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        imageMem5Button = (ImageButton) view.findViewById(R.id.imageMem5Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember5DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace5.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        imageMem6Button = (ImageButton) view.findViewById(R.id.imageMem6Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember6DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace6.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        imageMem7Button = (ImageButton) view.findViewById(R.id.imageMem7Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember7DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace7.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        imageMem8Button = (ImageButton) view.findViewById(R.id.imageMem8Button);
        imageMem8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember8DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace8.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        imageMem9Button = (ImageButton) view.findViewById(R.id.imageMem9Button);
        //imageMem2Button.setOnClickListener(this);
        imageMem9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember9DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace9.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        imageMem10Button = (ImageButton)view. findViewById(R.id.imageMem10Button);
        imageMem10Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edtMember10DOB.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);
                                spRace10.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        buttonClientNew = (Button) view.findViewById(R.id.buttonClientNew);
        buttonClientNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spRace1.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Race");
                }
                else if (edtMember2DOB.getText().toString().length() > 0 && spRace2.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Second Member Race");
                } else if (edtMember3DOB.getText().toString().length() > 0 && spRace3.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Third Member Race");
                } else if (edtMember4DOB.getText().toString().length() > 0 && spRace4.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Fourth Member Race");
                } else if (edtMember5DOB.getText().toString().length() > 0 && spRace5.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Fifth Member Race");
                } else if (edtMember6DOB.getText().toString().length() > 0 && spRace6.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Sixth Member Race");
                } else if (edtMember7DOB.getText().toString().length() > 0 && spRace7.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Seventh Member Race");
                } else if (edtMember8DOB.getText().toString().length() > 0 && spRace8.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Eighth Member Race");
                } else if (edtMember9DOB.getText().toString().length() > 0 && spRace9.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Ninth Member Race");
                } else if (edtMember10DOB.getText().toString().length() > 0 && spRace10.getSelectedItemPosition() == 0) {
                    nMessage.setText("Please Choose Your Tenth Member Race");
                } else {

                    if (edtNewCheckBox.isChecked()) {
                        if (edtMember2DOB.getText().toString().length() == 0 && spRace2.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Second Member DOB");
                        } else if (edtMember3DOB.getText().toString().length() == 0 && spRace3.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Third Member DOB");
                        }
                        else if (edtMember4DOB.getText().toString().length() == 0 && spRace4.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Fourth Member DOB");
                        } else if (edtMember9DOB.getText().toString().length() == 0 && spRace9.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Ninth Member DOB");
                        }
                        else if (edtMember5DOB.getText().toString().length() == 0 && spRace5.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Fifth Member DOB");
                        }
                        else if (edtMember10DOB.getText().toString().length() == 0 && spRace10.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Tenth Member DOB");
                        }
                        else if (edtMember6DOB.getText().toString().length() == 0 && spRace6.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Sixth Member DOB");
                        }
                        else if (edtMember7DOB.getText().toString().length() == 0 && spRace7.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Seventh Member DOB");
                        }
                        else if (edtMember8DOB.getText().toString().length() == 0 && spRace8.getSelectedItemPosition() != 0) {
                            nMessage.setText("Please Choose Your Eighth Member DOB");
                        } else {
                            postData(edtNewClientFirstName.getText().toString(),
                                    edtNewClientLastName.getText().toString(),
                                    edtNewDOB.getText().toString(),
                                    edtNewClientStreetAddress.getText().toString(),
                                    edtNewClientZipCode.getText().toString(),
                                    edtMember1DOB.getText().toString(),
                                    spRace1.getSelectedItem().toString(),
                                    edtMember2DOB.getText().toString(),
                                    spRace2.getSelectedItem().toString(),
                                    edtMember3DOB.getText().toString(),
                                    spRace3.getSelectedItem().toString(),
                                    edtMember4DOB.getText().toString(),
                                    spRace4.getSelectedItem().toString(),
                                    edtMember5DOB.getText().toString(),
                                    spRace5.getSelectedItem().toString(),
                                    edtMember6DOB.getText().toString(),
                                    spRace6.getSelectedItem().toString(),
                                    edtMember7DOB.getText().toString(),
                                    spRace7.getSelectedItem().toString(),
                                    edtMember8DOB.getText().toString(),
                                    spRace8.getSelectedItem().toString(),
                                    edtMember9DOB.getText().toString(),
                                    spRace9.getSelectedItem().toString(),
                                    edtMember10DOB.getText().toString(),
                                    spRace10.getSelectedItem().toString(),
                                    edtNewCheckBox.getText().toString());

                            CheckInFragment checkin = new CheckInFragment();

                            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, checkin);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                    } else {
                        nMessage.setText("Missing Signature");
                    }

                }
            }
        });

       queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        return view;
    }
    private void getCalendar() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }


    public void postData(final String fName, final String lName,
                         final String dob, final String street, final String zip,
                         final String mem1, final String race1,  final String mem2, final String race2,
                         final String mem3, final String race3,  final String mem4, final String race4,
                         final String mem5, final String race5,  final String mem6, final String race6,
                         final String mem7, final String race7,  final String mem8, final String race8,
                         final String mem9, final String race9, final String mem10, final String race10,
                         final String checkBox) {

        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.newClientInUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            Snackbar.make(buttonClientNew, "Successfully Posted", Snackbar.LENGTH_LONG).show();
                            setNull ();

                        } else {
                            Snackbar.make(buttonClientNew, "Try Again", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(buttonClientNew, "Error while Posting Data", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.FIRSTNAME_NEW, fName);
                params.put(Constants.LASTNAME_NEW, lName);
                params.put(Constants.DOB_NEW, dob);
                params.put(Constants.STREETADDRESS_NEW, street);
                params.put(Constants.ZIPCODE_NEW, zip);
                params.put(Constants.MEM1, mem1);
                params.put(Constants.MEM2, mem2);
                params.put(Constants.MEM3, mem3);
                params.put(Constants.MEM4, mem4);
                params.put(Constants.MEM5, mem5);
                params.put(Constants.MEM6, mem6);
                params.put(Constants.MEM7, mem7);
                params.put(Constants.MEM8, mem8);
                params.put(Constants.MEM9, mem9);
                params.put(Constants.MEM10, mem10);
                params.put(Constants.RACE1, race1);
                if (edtMember2DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE2, race2);
                }
                if (edtMember3DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE3, race3);
                }
                if (edtMember4DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE4, race4);
                }
                if (edtMember5DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE5, race5);
                }
                if (edtMember6DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE6, race6);
                }
                if (edtMember7DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE7, race7);
                }
                if (edtMember8DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE8, race8);
                }
                if (edtMember9DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE9, race9);
                }
                if (edtMember10DOB.getText().toString().trim().length() > 0) {
                    params.put(Constants.RACE10, race10);
                }
                params.put(Constants.SIGNATURE_NEW, checkBox);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);



    }

    private void setNull () {
        edtNewClientFirstName.setText(null);
        edtNewClientLastName.setText(null);
        edtNewDOB.setText(null);
        edtNewClientStreetAddress.setText(null);
        edtNewClientZipCode.setText(null);
        edtMember2DOB.setText(null);
        edtMember3DOB.setText(null);
        edtMember4DOB.setText(null);
        edtMember5DOB.setText(null);
        edtMember6DOB.setText(null);
        edtMember7DOB.setText(null);
        edtMember8DOB.setText(null);
        edtMember9DOB.setText(null);
        edtMember10DOB.setText(null);
        edtNewCheckBox.setChecked(false);
    }



}
