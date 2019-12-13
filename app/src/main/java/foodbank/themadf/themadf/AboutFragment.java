package foodbank.themadf.themadf;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Use PDF Viewer library
        // From this link https://github.com/barteksc/AndroidPdfViewer
        PDFView myPdfView =  view.findViewById(R.id.pdfView);
        myPdfView.fromAsset("UserManual.pdf").load();

        return view;
    }

}
