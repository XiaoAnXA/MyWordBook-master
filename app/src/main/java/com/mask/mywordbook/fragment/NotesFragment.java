package com.mask.mywordbook.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mask.mywordbook.R;
import com.mask.mywordbook.adapter.NoteViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    public TabLayout mTabLayout;
    public ViewPager mViewPager;

    public NotesFragment(){

    }

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);

        mTabLayout = view.findViewById(R.id.tab);
        mViewPager = view.findViewById(R.id.notes_viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WordFragment());
        fragments.add(new MusicFragment());
        NoteViewAdapter adapter = new NoteViewAdapter(getFragmentManager(),fragments,new String[]{"单词本","听歌"});
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

}
