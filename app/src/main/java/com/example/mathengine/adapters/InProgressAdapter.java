package com.example.mathengine.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mathengine.R;
import com.example.mathengine.pojo.EquationModel;

import java.util.ArrayList;


public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.InProgressHolder> {

    private ArrayList<EquationModel> list = new ArrayList<>();

    public InProgressAdapter(ArrayList<EquationModel> list) {
        this.list = list;
    }

    @Override
    public InProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.equation_item, parent, false);
        return new InProgressHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final InProgressHolder holder, final int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        EquationModel model = list.get(position);
        holder.equation.setTextSize(17);
        holder.equation.setText("Executing operation No. "+position+1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InProgressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView equation;
        ProgressBar progressBar;
        public InProgressHolder(View itemView) {
            super(itemView);
            equation= itemView.findViewById(R.id.equationText);
            progressBar = itemView.findViewById(R.id.progress_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}