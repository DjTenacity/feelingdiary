package cn.dj.android.common.lib.ui.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView Holder
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2017-03-13
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, getAdapterPosition());
            }
        });
    }

    public abstract void onBindViewHolder(int position);
    public abstract void onItemClick(View view, int position);
}
