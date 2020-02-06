package com.mask.mywordbook.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mask.mywordbook.R;
import com.mask.mywordbook.app.StartPageActivity;

import static android.content.Context.MODE_PRIVATE;

public class TaskFragment extends Fragment {

    Button mBtnSet;


    public TaskFragment() {
        // Required empty public constructor
    }


    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mBtnSet = view.findViewById(R.id.task_btn_set_password);
        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Password",MODE_PRIVATE);
                SharedPreferences.Editor editable = sharedPreferences.edit();
                editable.putString(StartPageActivity.password,"");
                editable.apply();
                editable.commit();
                startActivity(new Intent(getContext(),StartPageActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
