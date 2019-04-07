package com.apps.haitao.twatcher.twclient.activities.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentInfoViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView parentIcon;

    public TextView parentName;

    public TextView parentPhone;

    public ParentInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        parentIcon = (CircleImageView)itemView.findViewById(R.id.parent_icon);
        parentName = (TextView)itemView.findViewById(R.id.parent_name);
        parentPhone = (TextView)itemView.findViewById(R.id.parent_detail);
    }
}
