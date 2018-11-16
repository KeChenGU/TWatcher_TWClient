package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.viewholders.AppInfoViewHolder;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoViewHolder>{

    private List<App_Time_Infos> appTimeInfosList;

    public AppInfoAdapter(List<App_Time_Infos> appTimeInfosList) {
        this.appTimeInfosList = appTimeInfosList;
    }

    @NonNull
    @Override
    public AppInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.app_info_item, viewGroup, false);
        final AppInfoViewHolder appInfoViewHolder = new AppInfoViewHolder(itemView);
        return appInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoViewHolder appInfoViewHolder, int i) {
        App_Time_Infos appTimeInfos = appTimeInfosList.get(i);
        //appInfoViewHolder.getAppNameView().setText(appTimeInfos.getApp_name());
        //appInfoViewHolder.getAppIconView().setImageBitmap(appTimeInfos.getApp_icon());
        String userInfo = "最近开启时间: " + appTimeInfos.getLast_close_time() +
                " 使用总时长: " + appTimeInfos.getUsing_minutes() + "分钟";
        //appInfoViewHolder.getAppUseInfoView().setText(userInfo);
        //appInfoViewHolder.getAppDetails().setText("点击查看详情");
        appInfoViewHolder.appNameView.setText(appTimeInfos.getApp_name());
        //appInfoViewHolder.appIconView.setImageBitmap(appTimeInfos.getApp_icon());
        appInfoViewHolder.appIconView.setImageDrawable(appTimeInfos.getApp_icon_draw());
        appInfoViewHolder.appUseInfoView.setText(userInfo);
        appInfoViewHolder.appDetails.setText("点击查看详情");
    }

    @Override
    public int getItemCount() {
        return appTimeInfosList == null ? 0 : appTimeInfosList.size();
    }

    public List<App_Time_Infos> getAppTimeInfosList() {
        return appTimeInfosList;
    }

    public void setAppTimeInfosList(List<App_Time_Infos> appTimeInfosList) {
        this.appTimeInfosList = appTimeInfosList;
    }
}
