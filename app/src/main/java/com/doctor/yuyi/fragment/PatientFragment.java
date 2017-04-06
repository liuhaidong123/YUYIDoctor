package com.doctor.yuyi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.FragmentMyPatientListAdapter;
import com.doctor.yuyi.adapter.My_paintDataList_Adapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {
    private ListView my_patient_listview;
    private TextView titleText;
    private ImageView activity_include_imageReturn;
    private FragmentMyPatientListAdapter adapter;
    public PatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient, container, false);
        initView(view);
        return view;
    }
    public void initView(View view){
        my_patient_listview= (ListView) view.findViewById(R.id.my_patient_listview);
        titleText= (TextView) view.findViewById(R.id.titleinclude_textview);
        titleText.setText("我的患者");
        activity_include_imageReturn= (ImageView) view.findViewById(R.id.activity_include_imageReturn);
        activity_include_imageReturn.setVisibility(View.GONE);
        adapter=new FragmentMyPatientListAdapter(getContext(),null);
        my_patient_listview.setAdapter(adapter);
    }


}
