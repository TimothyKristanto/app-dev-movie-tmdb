package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;

import java.util.List;

public class ProductionCompaniesMovieDetailsFragmentRvAdapter extends RecyclerView.Adapter<ProductionCompaniesMovieDetailsFragmentRvAdapter.ProductionCompaniesViewholder> {
    private List<Movies.ProductionCompanies> listProductionCompanies;
    private Context context;

    public ProductionCompaniesMovieDetailsFragmentRvAdapter(List<Movies.ProductionCompanies> listProductionCompanies, Context context) {
        this.listProductionCompanies = listProductionCompanies;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductionCompaniesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.production_companies_viewholder, parent, false);
        return new ProductionCompaniesViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompaniesViewholder holder, int position) {
        Movies.ProductionCompanies productionCompanies = listProductionCompanies.get(position);

        if(productionCompanies.getLogo_path() != null){
            Glide.with(context).load(Const.IMG_URL + productionCompanies.getLogo_path()).into(holder.imgProductionCompany);
        }else{
            holder.imgProductionCompany.setImageResource(android.R.drawable.presence_busy);
        }
    }

    @Override
    public int getItemCount() {
        return listProductionCompanies.size();
    }

    public class ProductionCompaniesViewholder extends RecyclerView.ViewHolder {
        ImageView imgProductionCompany;

        public ProductionCompaniesViewholder(@NonNull View itemView) {
            super(itemView);

            imgProductionCompany = itemView.findViewById(R.id.imgProductionCompany);
        }
    }
}
