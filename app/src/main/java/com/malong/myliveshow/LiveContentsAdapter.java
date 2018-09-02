package com.malong.myliveshow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Malong
 * on 18/7/25.
 */
public class LiveContentsAdapter extends RecyclerView.Adapter<LiveContentsAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private LinkedHashMap<String, String> mMap;
    private List<String> data;

    public LiveContentsAdapter(Context context, LinkedHashMap<String, String> map) {
        this.mContext = context;
        this.mMap = map;
        data = new ArrayList<>();
        for (Map.Entry<String, String> entries : map.entrySet()) {
            data.add(entries.getKey());
        }
    }

    @NonNull
    @Override
    public LiveContentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.tv_item = view.findViewById(R.id.tv_item);
        //3、在onCreateViewHolder()中为每个item添加点击事件
        view.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull LiveContentsAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tv_item.setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return mMap.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * RecyclerView为每个item添加点击事件
     */


    //2、声明这个接口变量
    private OnItemClickListener mItemClickListener;

    //1、在Adapter中定义接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //5、提供set方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
