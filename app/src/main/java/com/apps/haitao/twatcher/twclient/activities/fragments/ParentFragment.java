package com.apps.haitao.twatcher.twclient.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.AppInfoAdapter;
import com.apps.haitao.twatcher.twclient.activities.adapters.AppTimeInfoAdapter;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;

import java.util.List;

public class ParentFragment extends Fragment{

    private List<App_Time_Infos> appTimeInfosList;

    private AppInfoAdapter appInfoAdapter;

    private AppTimeInfoAdapter appTimeInfoAdapter;

    private RecyclerView appTimeInfoView;

    //private ListView appTimeInfoListView;

    //private TextView swipeText;

    private SwipeRefreshLayout swipeRefreshLayout;


    public ParentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.activity_parent, container, false);
        return parentView;
    }
}
