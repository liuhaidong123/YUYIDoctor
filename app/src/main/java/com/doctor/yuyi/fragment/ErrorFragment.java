package com.doctor.yuyi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doctor.yuyi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends Fragment {
    public ErrorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.error_network, container, false);
        return v;
    }

}
