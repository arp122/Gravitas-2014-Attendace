package com.example.coveneorgravitas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomBaseAdapterDetails extends BaseAdapter 
{
    Context context;
    List<RowItemDetails> rowItems;
    DatabaseHandlerOd db;
    int []AllStatus=new int[1000];
    int i=0;
    public CustomBaseAdapterDetails(Context context, List<RowItemDetails> items) {
        this.context = context;
        this.rowItems = items;
       db=new DatabaseHandlerOd(context);
       List<Integer> data= new ArrayList<Integer>();
       data=db.getStatusEvents();
       for(Integer cn:data){
       	AllStatus[i]=cn;
       	i++;
       }
      
    }

    

	/*private view holder class*/
    private class ViewHolder {

       TextView tvName,tvReg;
       ImageButton btCheck;

    }
   
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final RowItemDetails rowItem = (RowItemDetails) getItem(position);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_details, null);
            holder = new ViewHolder();
            holder.tvName=(TextView)convertView.findViewById(R.id.tvName);
            holder.tvReg=(TextView)convertView.findViewById(R.id.tvReg);
            holder.btCheck=(ImageButton)convertView.findViewById(R.id.btCheck);
            
            if(AllStatus[position]==1){
            	holder.btCheck.setBackgroundResource(R.drawable.selector_check);
				
            }
            else if(AllStatus[position]==0){
            	holder.btCheck.setBackgroundResource(R.drawable.selector_uncheck);
				
            }
            holder.tvName.setText(""+rowItem.getName());
            holder.tvReg.setText(""+rowItem.getReg());
            holder.btCheck.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(AllStatus[position]==0){
						holder.btCheck.setBackgroundResource(R.drawable.selector_check);
						db.updateFavImp(rowItem.getReg());
						AllStatus[position]=1;
					}
					else {
						holder.btCheck.setBackgroundResource(R.drawable.selector_uncheck);
						db.updateFavNotImp(rowItem.getReg());
						AllStatus[position]=0;
					}
					}
			});
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
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