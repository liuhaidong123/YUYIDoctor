package com.doctor.yuyi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.CommentAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcademicFragment extends Fragment {
    private ListView mListview;
    private CommentAdapter mAdapter;

    public AcademicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view=   inflater.inflate(R.layout.fragment_academic, container, false);
        mListview = (ListView) view.findViewById(R.id.comment_listview);
        mAdapter = new CommentAdapter(getContext());
        mListview.setAdapter(mAdapter);
        return view;
    }

}
