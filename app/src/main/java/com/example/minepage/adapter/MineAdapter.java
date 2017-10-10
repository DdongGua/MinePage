package com.example.minepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minepage.R;

/**
 * Created by 亮亮 on 2017/10/10.
 */

public class MineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int HEAD = 101;
    public final int LIST = 102;
    public HeadViewHolder headViewHolder;

    public Context context;
    private final LayoutInflater inflater;

    public MineAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (HEAD == viewType) {
            View inflate = inflater.inflate(R.layout.item_mine_head, parent, false);
            headViewHolder=new HeadViewHolder(inflate);
            return headViewHolder;
        } else {
            View inflate = inflater.inflate(R.layout.item_mine_list, null, false);
            return new ListViewHolder(inflate);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //设置数据

    }
    @Override
    public int getItemCount() {
        return 20;
    }
    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return HEAD;
        } else {
            return LIST;
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;

        public HeadViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);

        }

    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ListViewHolder(View itemView) {
            super(itemView);
        }


    }
}
