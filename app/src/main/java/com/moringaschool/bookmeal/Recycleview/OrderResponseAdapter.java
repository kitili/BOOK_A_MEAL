package com.moringaschool.bookmeal.Recycleview;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.bookmeal.Model.Order;
import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.R;

import java.util.ArrayList;
import java.util.List;

public class OrderResponseAdapter extends RecyclerView.Adapter<OrderResponseAdapter.OrderViewHolder> {
    private ArrayList<Order> orderList;
    OrderResponseAdapter.RecyclerViewClickListener listener;
    public OrderResponseAdapter(ArrayList<Order> orderList, RecyclerViewClickListener listener, String user_id){
        this.orderList=orderList;
        this.listener=listener;
    }

    public void filterList(ArrayList<Order> fiteredList){
        orderList= fiteredList;
        notifyDataSetChanged();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView id,total_price,unit_price,menu_item,quantity;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            //change
            total_price = itemView.findViewById(R.id.total_price);
            unit_price = itemView.findViewById(R.id.unit_price);
            menu_item = itemView.findViewById(R.id.menu_item);
            quantity=itemView.findViewById(R.id.quantity);
            id=itemView.findViewById(R.id.id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
            if(view==itemView){
                List<Orders> new_order=new ArrayList<>();
            }


        }
    }
    @NonNull
    @Override
    public OrderResponseAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                //change
                .inflate(R.layout.r_order_history,parent,false);
        return new OrderResponseAdapter.OrderViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        String name= (String) orderList.get(position).getMenuItem();
        String quantity1= (String) orderList.get(position).getQuantity();
        String unit_cost=orderList.get(position).getPrice();
        String total_cost=orderList.get(position).getTotal();
        String ids=orderList.get(position).getId();
        holder.menu_item.setText(name);
        holder.quantity.setText(quantity1);
        holder.unit_price.setText(unit_cost);
        holder.total_price.setText(total_cost);
        holder.id.setText(ids);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
