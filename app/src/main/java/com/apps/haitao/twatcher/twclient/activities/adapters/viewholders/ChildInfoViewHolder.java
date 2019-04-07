package com.apps.haitao.twatcher.twclient.activities.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildInfoViewHolder extends RecyclerView.ViewHolder{

    public CircleImageView childIcon;

    public TextView childName;

    public TextView childPhone;

    public TextView selectCond;

    public ChildInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        childIcon = (CircleImageView)itemView.findViewById(R.id.child_icon);
        childName = (TextView)itemView.findViewById(R.id.child_name);
        childPhone = (TextView)itemView.findViewById(R.id.child_detail);
        selectCond = (TextView)itemView.findViewById(R.id.select_condition);
    }
}
