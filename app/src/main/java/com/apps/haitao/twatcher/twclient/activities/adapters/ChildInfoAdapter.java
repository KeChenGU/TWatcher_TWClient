package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.viewholders.ChildInfoViewHolder;
import com.apps.haitao.twatcher.twclient.activities.gsons.MyChildren;

import java.util.List;

public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoViewHolder> {

    private List<MyChildren> myChildren;

    public ChildInfoAdapter(List<MyChildren> myChildren) {
        this.myChildren = myChildren;
    }

    @NonNull
    @Override
    public ChildInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.child_dialog_item, viewGroup, false);
        return new ChildInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildInfoViewHolder childInfoViewHolder, int i) {
        MyChildren children = myChildren.get(i);
        childInfoViewHolder.childIcon.setImageResource(children.getSex().equals("male")
                ? R.drawable.child_boy : R.drawable.child_girl);
        childInfoViewHolder.childName.setText(children.getChildName());
        childInfoViewHolder.childPhone.setText(children.getChildPhone());
        childInfoViewHolder.selectCond.setText(children.getSelectCond());
    }

    @Override
    public int getItemCount() {
        return myChildren == null ? 0 : myChildren.size();
    }
}
