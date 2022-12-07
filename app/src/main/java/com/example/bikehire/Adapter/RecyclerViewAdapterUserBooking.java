package com.example.bikehire.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikehire.UserBookingStatus;
import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;
import com.example.bikehire.R;
import com.example.bikehire.UserGiveFeedback;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterUserBooking extends RecyclerView.Adapter<RecyclerViewAdapterUserBooking.ViewHolder> implements Filterable {
    List<BookingModel> bookingList;
    List<BookingModel> bookingListFull;
    Context context;

    public RecyclerViewAdapterUserBooking(List<BookingModel> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
        bookingListFull = new ArrayList<>(bookingList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_view_booking,parent,false);
        ViewHolder holder= new ViewHolder(view);

        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DatabaseHelper db = new DatabaseHelper(context);
        List<BikeModel> bikeList =db.viewBike(bookingList.get(position).getBookingBikeId());
        String cname = db.getCompanyName(bookingList.get(position).getBookingCompanyId());
        holder.bookingdate.setText("Booked on  :  "+bookingList.get(position).getBookingDate());
        holder.bikename.setText("Bike Name  :  "+bikeList.get(0).getBikename());
        holder.bikeImg.setImageBitmap(bikeList.get(0).getBikeimage());
        holder.compname.setText("Company Name  :  "+cname);
        holder.date.setText("Journey Date : "+bookingList.get(position).getBookingJourneyDate()+"\nReturn Date : "+bookingList.get(position).getBookingReturnDate());
        holder.noofdays.setText("No. of Days : "+bookingList.get(position).getBookingNoOfHoursDaysWeek());
        holder.amount.setText("Booking Amount : \u20B9"+bookingList.get(position).getBookingAmount());
        int st=bookingList.get(position).getBookingStatus();

        holder.continuebooking.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                int status = bookingList.get(position).getBookingStatus();
                String bookingstatus=String.valueOf(status);
                int id=bookingList.get(position).getBookingId();
                String bookingid=String.valueOf(id);
                String bookingdate=bookingList.get(position).getBookingDate();
                String address=bookingList.get(position).getBookingDeliveryAddress();
                String jdate=bookingList.get(position).getBookingJourneyDate();
                String rdate=bookingList.get(position).getBookingReturnDate();
                String dtime=bookingList.get(position).getDeliverytime();
                String rtime=bookingList.get(position).getReturntime();
                Bitmap dlimg=bookingList.get(position).getBookingDrivingLicense();
                double d=bookingList.get(position).getBookingAmount();
                String amt=String.valueOf(d);
                Intent intent = new Intent(context.getApplicationContext(), UserBookingStatus.class);
                intent.putExtra("bookingid",bookingid);
                intent.putExtra("bookingdate",bookingdate);
                intent.putExtra("deliverydate",jdate);
                intent.putExtra("returndate",rdate);
                intent.putExtra("deliverytime",dtime);
                intent.putExtra("returntime",rtime);
                intent.putExtra("bookingStatus",bookingstatus);
                intent.putExtra("address",address);
                intent.putExtra("amount",amt);
                intent.putExtra("dlphoto",dlimg);
                context.startActivity(intent);
            }
        });


        if(st==1){
            holder.givefeedback.setVisibility(View.INVISIBLE);
        } else if(st==2){
            holder.givefeedback.setVisibility(View.INVISIBLE);
        } else if(st==3){
            holder.givefeedback.setVisibility(View.INVISIBLE);
        }else if(st==4){
            holder.givefeedback.setVisibility(View.VISIBLE);
        }

        holder.givefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int str = bookingList.get(position).getBookingId();
                int str1 = bookingList.get(position).getBookingCustomerId();
                int str2 = bookingList.get(position).getBookingCompanyId();
                String bookid = String.valueOf(str);
                String customerid = String.valueOf(str1);
                String companyid = String.valueOf(str2);

                Intent i = new Intent(view.getContext(), UserGiveFeedback.class);
                i.putExtra("bookId",bookid);
                i.putExtra("customerId",customerid);
                i.putExtra("companyId",companyid);

                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    @Override
    public Filter getFilter() {
        return bookingFilter;
    }


    private Filter bookingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BookingModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(bookingListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BookingModel item : bookingListFull) {
                    if (item.getBookingDate().toLowerCase().contains(filterPattern) || item.getBookingJourneyDate().toLowerCase().contains(filterPattern) ) {
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
            bookingList.clear();
            bookingList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingdate,bikename,compname,date,noofdays,amount;
        ImageView bikeImg;
        RelativeLayout continuebooking;
        Button givefeedback;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingdate = itemView.findViewById(R.id.viewbooking_date);
            bikename = itemView.findViewById(R.id.viewbike_name);
            bikeImg = itemView.findViewById(R.id.viewbike_img);
            compname = itemView.findViewById(R.id.viewbike_compname);
            date = itemView.findViewById(R.id.viewbike_date);
            continuebooking = itemView.findViewById(R.id.continuebooking);
            noofdays = itemView.findViewById(R.id.viewbike_noofdays);
            amount = itemView.findViewById(R.id.viewbike_amount);
            givefeedback = itemView.findViewById(R.id.give_feedbackbtn);

        }
    }

}

