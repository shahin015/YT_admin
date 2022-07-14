package com.shahindemunav.admin.ui.notifications;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahindemunav.admin.R;

import java.util.ArrayList;

public class AdepterForPaymane extends RecyclerView.Adapter<AdepterForPaymane.AdminVideHolder>  {
    Context context;
    ArrayList<PaymentData> list;

    public AdepterForPaymane(Context context, ArrayList<PaymentData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdepterForPaymane.AdminVideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.item_for_payment,parent,false);
        return new AdminVideHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdepterForPaymane.AdminVideHolder holder, int position) {
        PaymentData data=list.get(position);

        holder.transtionid.setText(data.getTrasngationid());
        holder.pending.setText(data.getStatus());
        holder.userkey.setText(data.getKey());
        holder.amontofpayment.setText(data.getAmount());
        holder.paymentmethord.setText(data.getBank());
        holder.refercode.setText(data.getRefcode());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class AdminVideHolder extends RecyclerView.ViewHolder {
        private TextView transtionid,userkey,amontofpayment,paymentNumber,refercode,paymentmethord,pending;
        private Button delete,validUse,hide;

        public AdminVideHolder(@NonNull View itemView) {
            super(itemView);

            transtionid=itemView.findViewById(R.id.transctionId);
            userkey=itemView.findViewById(R.id.transctionId);
            amontofpayment=itemView.findViewById(R.id.transctionId);
            paymentNumber=itemView.findViewById(R.id.transctionId);
            refercode=itemView.findViewById(R.id.transctionId);
            paymentmethord=itemView.findViewById(R.id.transctionId);
            delete=itemView.findViewById(R.id.delete);
            validUse=itemView.findViewById(R.id.valid);
            hide=itemView.findViewById(R.id.hide);
            pending=itemView.findViewById(R.id.stasus);


        }
    }
}
