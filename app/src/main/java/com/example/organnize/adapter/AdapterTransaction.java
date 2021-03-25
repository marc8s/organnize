package com.example.organnize.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.organnize.R;
import com.example.organnize.model.Transaction;

import java.util.List;



public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.MyViewHolder> {

    List<Transaction> mTransactions;
    Context mContext;

    public AdapterTransaction(List<Transaction> mTransactions, Context context) {
        this.mTransactions = mTransactions;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction, parent, false);
        return new MyViewHolder(itemList);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transaction transaction = mTransactions.get(position);

        holder.title.setText(transaction.getmDescription());
        holder.value.setText(String.valueOf(transaction.getmValue()));
        holder.category.setText(transaction.getmCategory());

        if (transaction.getmType().equals("e")) {
            holder.value.setTextColor(mContext.getResources().getColor(R.color.colorAccentDespesa));
            holder.value.setText("-" + transaction.getmValue());
        }
    }


    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, value, category;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textAdapterTitle);
            value = itemView.findViewById(R.id.textAdapterValue);
            category = itemView.findViewById(R.id.textAdapterCategory);
        }

    }

}
