package com.apps.haitao.twatcher.twclient.activities.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppInfoViewHolder extends ViewHolder{

    public CircleImageView appIconView;

    public TextView appNameView;

    public TextView appUseInfoView;

    public TextView appDetails;

    public AppInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        appIconView = (CircleImageView)itemView.findViewById(R.id.app_icon);
        appNameView = (TextView)itemView.findViewById(R.id.app_name);
        appUseInfoView = (TextView)itemView.findViewById(R.id.app_use_info);
        appDetails = (TextView)itemView.findViewById(R.id.app_details);
    }

    public CircleImageView getAppIconView() {
        return appIconView;
    }

    public TextView getAppNameView() {
        return appNameView;
    }

    public TextView getAppUseInfoView() {
        return appUseInfoView;
    }

    public TextView getAppDetails() {
        return appDetails;
    }
}
