package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppTimeInfoAdapter0 extends ArrayAdapter<App_Time_Infos>{

    private int resourceId;

    public AppTimeInfoAdapter0(@NonNull Context context, int resource, @NonNull App_Time_Infos[] objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        App_Time_Infos appTimeInfos = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        CircleImageView appIconView;
        TextView appNameView;
        TextView appUseInfoView;
        TextView appDetails;
        appIconView = (CircleImageView)view.findViewById(R.id.app_icon);
        appNameView = (TextView)view.findViewById(R.id.app_name);
        appUseInfoView = (TextView)view.findViewById(R.id.app_use_info);
        appDetails = (TextView)view.findViewById(R.id.app_details);
        String userInfo = "最近开启时间: " + appTimeInfos.getLast_close_time() +
                " 使用总时长: " + appTimeInfos.getUsing_minutes() + "分钟";
        appIconView.setImageDrawable(appTimeInfos.getApp_icon_draw());
        appNameView.setText(appTimeInfos.getApp_name());
        appUseInfoView.setText(userInfo);
        appDetails.setText("点击查看详情");
        return view;
    }

    class AppTimeInfoViewHolder {

    }
}
