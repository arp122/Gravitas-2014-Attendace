package com.example.organisationgravitas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapterStatus extends BaseAdapter 
{
    Context context;
    List<RowItemStatus> rowItems;
    DataBaseHandlerOdStatus db;
    int []status=new int[1000];
    public CustomBaseAdapterStatus(Context context, List<RowItemStatus> items) {
        this.context = context;
        this.rowItems = items;
        db=new DataBaseHandlerOdStatus(context);
        List<Integer> data= new ArrayList<Integer>();
        data=db.getAllStatus();
        int i =0;
        for(Integer cn:data){
        	status[i]=cn;
        	i++;
        }
    }

    

	/*private view holder class*/
    private class ViewHolder {

       TextView tvApply,tvSub;
      ImageView ivstatus;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItemStatus rowItem = (RowItemStatus) getItem(position);

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.status_details, null);
            holder = new ViewHolder();
            holder.tvSub=(TextView)convertView.findViewById(R.id.tvSub);
            holder.tvApply=(TextView)convertView.findViewById(R.id.tvApply);
            holder.ivstatus=(ImageView)convertView.findViewById(R.id.ivStatus);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        
        holder.tvSub.setText("Submission Date:"+rowItem.getSubDate());
        holder.tvApply.setText("Apply Date:"+rowItem.getApplyDate());
       if(status[position]==0){
    	   holder.ivstatus.setImageResource(R.drawable.status1);
       }
       else if(status[position]==1){
    	   holder.ivstatus.setImageResource(R.drawable.status2);
       }
       else if(status[position]==2){
    	   holder.ivstatus.setImageResource(R.drawable.status3);
       }
       else {
    	   holder.ivstatus.setImageResource(R.drawable.uncheck);
	}
        //holder.txtTitle.setText(rowItem.getTitle());
        
       // holder.txtCat.setText(rowItem.getCat());
       

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}