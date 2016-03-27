package com.lebron.carrot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lebron.carrot.R;

import java.util.List;

/**
 * Created by wuxiangkun on 2016/3/17.
 * Contacts by wuxiangkun@live.com
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<String> list;
    private OnChildClickListener listener;
    private RecyclerView recyclerView;
    public RecycleAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    /**
     * 创建ViewHOlder对象
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView when it starts observing this Adapter
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * 将数据放入到ViewHolder里面的控件中
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 实现了View的OnClickListener的方法
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (listener != null){
            int position = recyclerView.getChildAdapterPosition(view);
            listener.onChildClick(view, position, list.get(position));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_textView);
        }
    }

    public interface OnChildClickListener{
        void onChildClick(View view, int position, String data);
    }

    /**
     * 内部定义的接口的set方法，用于接口回调
     * @param listener
     */
    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    /**
     * 将list中position位置的数据移除后，更新Ui
     * @param position
     */
    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 在position位置插入数据data，然后更新UI
     * @param position
     * @param data
     */
    public void add(int position, String data){
        list.add(position, data);
        notifyItemInserted(position);
    }
}
