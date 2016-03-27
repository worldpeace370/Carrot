package com.lebron.carrot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lebron.carrot.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wuxiangkun on 2016/3/15.
 * Contacts by wuxiangkun@live.com
 */
public class ListViewAdapter extends BaseAdapterHelper<String>{

    public ListViewAdapter(List<String> list, Context context) {
        super(list, context);//调用父类构造方法传参
    }
    //父类getView（）方法调用getItemView（）方法，将list,inflater传进来
    //此处的getItemView()相当于父类的getView（），list,inflater可以直接使用
    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<String> list, LayoutInflater inflater) {
        ViewHolder viewHolder;
        if (null == convertView){
            convertView = inflater.inflate(R.layout.item_recyclerview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.getTextView().setText(list.get(position));
        return convertView;
    }

    class ViewHolder{
        private View view;
        private TextView textView;

        public ViewHolder(View view) {
            this.view = view;
        }

        public TextView getTextView(){
            if (textView ==null){
                textView = (TextView) view.findViewById(R.id.item_textView);
            }
            return textView;
        }
    }

}
