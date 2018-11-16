package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.viewholders.AppTimeInfoViewHolder;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppTimeInfoAdapter extends RecyclerView.Adapter<AppTimeInfoViewHolder> {

    private List<App_Time_Infos> appTimeInfosList;

    public AppTimeInfoAdapter(List<App_Time_Infos> appTimeInfosList) {
        this.appTimeInfosList = appTimeInfosList;
    }

    @NonNull
    @Override
    public AppTimeInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.app_info_item, viewGroup, false);
        final AppTimeInfoViewHolder appTimeInfoViewHolder = new AppTimeInfoViewHolder(itemView);
        return appTimeInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppTimeInfoViewHolder appTimeInfoViewHolder, int i) {
        App_Time_Infos appTimeInfos = appTimeInfosList.get(i);
        String userInfo = "最近开启时间: " + appTimeInfos.getLast_close_time() +
                " 使用总时长: " + appTimeInfos.getUsing_minutes() + "分钟";
        appTimeInfoViewHolder.appIconView.setImageDrawable(appTimeInfos.getApp_icon_draw());
        appTimeInfoViewHolder.appNameView.setText(appTimeInfos.getApp_name());
        appTimeInfoViewHolder.appUseInfoView.setText(userInfo);
        appTimeInfoViewHolder.appDetails.setText("点击查看详情");
    }

    @Override
    public int getItemCount() {
        return appTimeInfosList == null ? 0 : appTimeInfosList.size();
    }

//    class AppTimeInfoViewHolder extends RecyclerView.ViewHolder {
//
//        CircleImageView appIconView;
//
//        TextView appNameView;
//
//        TextView appUseInfoView;
//
//        TextView appDetails;
//
//        public AppTimeInfoViewHolder(@NonNull View itemView) {
//            super(itemView);
//            appIconView = (CircleImageView)itemView.findViewById(R.id.app_icon);
//            appNameView = (TextView)itemView.findViewById(R.id.app_name);
//            appUseInfoView = (TextView)itemView.findViewById(R.id.app_use_info);
//            appDetails = (TextView)itemView.findViewById(R.id.app_details);
//        }
//    }

}
