package com.apps.haitao.twatcher.twclient.activities.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppTimeInfoViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView appIconView;

    public TextView appNameView;

    public TextView appUseInfoView;

    public TextView appDetails;

    public AppTimeInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        appIconView = (CircleImageView)itemView.findViewById(R.id.app_icon);
        appNameView = (TextView)itemView.findViewById(R.id.app_name);
        appUseInfoView = (TextView)itemView.findViewById(R.id.app_use_info);
        appDetails = (TextView)itemView.findViewById(R.id.app_details);
    }
}
