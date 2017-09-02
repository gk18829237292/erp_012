package com.gk.erp012.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gk.erp012.R;
import com.gk.erp012.ui.CreateDepartActivity;
import com.gk.erp012.ui.StuffListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StuffFragment extends Fragment implements View.OnClickListener {


    public StuffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stuff, container, false);
        view.findViewById(R.id.stuff_0).setOnClickListener(this);
        view.findViewById(R.id.stuff_1).setOnClickListener(this);
        view.findViewById(R.id.stuff_2).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(getActivity(), StuffListActivity.class);
        switch (id){
            case R.id.stuff_0:
                intent.putExtra("type","0");
                break;
            case R.id.stuff_1:
                intent.putExtra("type","1");
                break;
            case R.id.stuff_2:
                intent.putExtra("type","2");
                break;
        }
        startActivity(intent);
    }
}
