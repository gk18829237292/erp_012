package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.SuperEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.CreateReportActivity;
import com.gk.erp012.ui.CreateSuperActivity;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperFragment extends BaseFragment {

    private ImageWatcher vImageWatcher;
    private GridView gridView;
    private PictureAdapter adapter;
    private TextView tv_star;
    private EditText tv_super;
    private List<String> pics = new ArrayList<>();
    private SuperEntry superEntry;
    private boolean updateFlag;
    private int index,taskId;

    public static final int CREATE_SUPER = 443;
    public SuperFragment() {
    }



    @Override
    public void update(TaskReportEntry taskReportEntry) {
        superEntry = taskReportEntry.getSuperEntry();
        updateFlag = true;
        try {
            pics.clear();

            if(superEntry!=null) {
                pics.addAll(superEntry.getPicture());
                tv_super.setText(superEntry.getComment());
                tv_star.setText(superEntry.getStar()+"");
            }else{
                tv_super.setText("");
                tv_star.setText("");
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }

    @Override
    public void update(TaskReportEntry taskReportEntry, int index,int taskId) {
        update(taskReportEntry);
        this.index =index;
        this.taskId = taskId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super, container, false);
        gridView = view.findViewById(R.id.grid_view_pics);
        tv_super = view.findViewById(R.id.tv_super);
        tv_star = view.findViewById(R.id.tv_star);
        adapter = new PictureAdapter(getContext(), pics);
        gridView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        //判断是否该显示
        if(!ErpApplication.getInstance().getUserEntry().isGuanlizhe()){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateSuperActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("taskId",taskId);
                getActivity().startActivityForResult(intent,CREATE_SUPER);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(updateFlag){
            pics.clear();

            if(superEntry!=null) {
                pics.addAll(superEntry.getPicture());
                tv_super.setText(superEntry.getComment());
                tv_star.setText(superEntry.getStar()+"");
            }else{
                tv_super.setText("");
                tv_star.setText("");
            }
            adapter.notifyDataSetChanged();
            updateFlag = false;
        }
    }


}
