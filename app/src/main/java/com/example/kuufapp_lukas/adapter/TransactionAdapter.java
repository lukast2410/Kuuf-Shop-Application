package com.example.kuufapp_lukas.adapter;

import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuufapp_lukas.R;
import com.example.kuufapp_lukas.model.Product;
import com.example.kuufapp_lukas.model.Transaction;
import com.example.kuufapp_lukas.util.HelperData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private ArrayList<Transaction> transactions;
    private TransactionClickListener listener;

    public TransactionAdapter(ArrayList<Transaction> transactions, TransactionClickListener listener){
        this.transactions = transactions;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TransactionClickListener listener;
        private TextView tvDate, tvProduct, tvPrice;
        private Button btnDelete;

        public ViewHolder(@NonNull View itemView, TransactionClickListener listener) {
            super(itemView);
            this.listener = listener;
            tvDate = (TextView) itemView.findViewById(R.id.tv_history_date);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_history_price);
            tvProduct = (TextView) itemView.findViewById(R.id.tv_history_product);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete_history);

            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onDeleteClicked(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history, parent, false);
        return new ViewHolder(view, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        holder.tvDate.setText(getTransactionDate(transactions.get(position).getTransactionDate()));
        Product product = getProduct(transactions.get(position).getProductId());
        holder.tvProduct.setText(product.getName());
        String price = "Rp. " + HelperData.getPriceString(product.getPrice());
        holder.tvPrice.setText(price);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTransactionDate(Date date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(formatter).toString();
    }

    private Product getProduct(int id){
        for (Product product: HelperData.getInstance().getProducts()) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public interface TransactionClickListener{
        void onDeleteClicked(int position);
    }
}
