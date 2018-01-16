package com.chuck.statuslayoutcontroller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuck on 2018/1/15.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<String> dataList=new ArrayList<>();

    public Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void update(List<String> dataList) {
        if (this.dataList != null) {
            notifyItemRangeRemoved(0, dataList.size());
        }
        this.dataList = dataList;
        notifyItemRangeInserted(0,dataList.size());
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
