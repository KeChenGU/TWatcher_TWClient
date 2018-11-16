package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;

import com.apps.haitao.twatcher.twclient.activities.adapters.AppInfoAdapter;

public final class ViewUtil {

    //private View defaultView;

    //private Fragment defaultFragment;

    //private Activity defaultActivity;

    //private View[] viewControls;

    //public static void initViewControlsForWatchActivity(AppCompatActivity compatActivity)

    //}

    //public static void initViewControls(Activity activity){
    //   if (activity instanceof AppCompatActivity){

    //    }
    //}

    public static void replaceFragment(Fragment fragment,
                                       FragmentManager fragmentManager,
                                       @IdRes int resourceId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(resourceId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static View configAdapter(Activity activity, View view, Adapter adapter)
            throws PackageManager.NameNotFoundException{
        AppCompatActivity compatActivity = null;
        RecyclerView recyclerView = null;
        AppInfoAdapter appInfoAdapter = null;
        if (activity instanceof AppCompatActivity) {
            compatActivity = (AppCompatActivity)activity;
        }

        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView)view;
        }

        if (adapter instanceof AppInfoAdapter) {
            appInfoAdapter = (AppInfoAdapter)adapter;
        }

        if (compatActivity != null && appInfoAdapter != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(compatActivity);
            if (appInfoAdapter.getAppTimeInfosList().size() == 0) {
                appInfoAdapter.setAppTimeInfosList(Utility.getTimeInfoListAfterRequestSuccess(compatActivity));
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(appInfoAdapter);
            return recyclerView;
        }

        return null;
    }

    public static RecyclerView configRecyclerViewAdapter(AppCompatActivity appCompatActivity,
                                                 RecyclerView recyclerView,
                                                 RecyclerView.Adapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
//        if (adapter instanceof AppInfoAdapter) {
//            recyclerView.setAdapter((AppInfoAdapter)adapter);
//        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

}
