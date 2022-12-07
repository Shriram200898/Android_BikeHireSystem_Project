package com.example.bikehire.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterAdminViewCompany extends RecyclerView.Adapter<RecyclerViewAdapterAdminViewCompany.ViewMyHolder> implements Filterable {
    List<CompanyModel> companyList;
    Context context;
    List<CompanyModel> companyListFull;

    public RecyclerViewAdapterAdminViewCompany(List<CompanyModel> companyList, Context context) {
        this.companyList = companyList;
        this.context = context;
        companyListFull = new ArrayList<>(companyList);

    }

    @NonNull
    @Override
    public ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_view_company,parent,false);
        ViewMyHolder holder= new ViewMyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyHolder viewMyHolder, @SuppressLint("RecyclerView") int position) {
        viewMyHolder.compname.setText("Full Name : "+companyList.get(position).getCompname());
        viewMyHolder.compemail.setText("Email : "+companyList.get(position).getCompemail());
        viewMyHolder.compphoneno.setText("Phone No. : "+companyList.get(position).getCompphoneno());
        viewMyHolder.compaddress.setText("Address : "+companyList.get(position).getCompaddress());
        viewMyHolder.photo.setImageBitmap(companyList.get(position).getCompprofileimage());

        Bitmap str3 = companyList.get(position).getCompprofileimage();

        if (str3 == null) {
            viewMyHolder.photo.setImageResource(R.drawable.user1);
        } else {
        }


        int active = companyList.get(position).getIsactive();
        if(active==1){
            viewMyHolder.compstatus.setText("Status : Activated");
        }
        else{
            viewMyHolder.compstatus.setText("Status : Deactivated");
        }


        int id = companyList.get(position).getCompid();
        if(active==1){
            viewMyHolder.actdeact.setChecked(true);
        }
        else{
            viewMyHolder.actdeact.setChecked(false);
        }

        viewMyHolder.actdeact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DatabaseHelper dbhelper = new DatabaseHelper(context.getApplicationContext());
                    boolean success = dbhelper.isCompanyActive(id, true);
                    if (success == true) {
                        Toast.makeText(context.getApplicationContext(), "Company Activated...", Toast.LENGTH_SHORT).show();
                        Intent intent = ((Activity) context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    }

                } else {
                    DatabaseHelper dbhelper = new DatabaseHelper(context.getApplicationContext());
                    boolean success = dbhelper.isCompanyActive(id, false);
                    if (success == true) {
                        Toast.makeText(context.getApplicationContext(), "Company Activated...", Toast.LENGTH_SHORT).show();
                        Intent intent = ((Activity) context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    }

                }
            }
        });

        viewMyHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db=new DatabaseHelper(view.getContext());
                boolean success = db.deleteCompany(companyList.get(position).getCompid());
                if (success == true) {
                    Toast.makeText(view.getContext(), "Company Account Deleted....", Toast.LENGTH_SHORT).show();

                } else {
                }
                companyList.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return companyList.size();

    }

    @Override
    public Filter getFilter() {
        return companyFilter;
    }

    private Filter companyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CompanyModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(companyListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CompanyModel item : companyListFull) {
                    if (item.getCompname().toLowerCase().contains(filterPattern) || item.getCompaddress().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            companyList.clear();
            companyList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewMyHolder extends RecyclerView.ViewHolder {
        TextView compname,compemail,compphoneno,compaddress,compstatus;
        ToggleButton actdeact;
        ImageView photo;
        Button delete;

        public ViewMyHolder(@NonNull View itemView) {
            super(itemView);
            compname = itemView.findViewById(R.id.view_compname);
            compemail = itemView.findViewById(R.id.view_compemail);
            compphoneno = itemView.findViewById(R.id.view_compphoneno);
            compaddress = itemView.findViewById(R.id.view_compaddress);
            compstatus = itemView.findViewById(R.id.compstatus);
            actdeact = itemView.findViewById(R.id.toggleButton);
            photo = itemView.findViewById(R.id.compprofile_photo);
            delete = itemView.findViewById(R.id.comp_delete);
        }
    }
}
