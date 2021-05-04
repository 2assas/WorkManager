package com.example.mathengine.adapters;

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


public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.CompletedHolder> {

    private ArrayList<EquationModel> list = new ArrayList<>();

    public CompletedAdapter(ArrayList<EquationModel> list) {
        this.list = list;
    }

    @Override
    public CompletedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.equation_item, parent, false);
        return new CompletedHolder(v);
    }

    @Override
    public void onBindViewHolder(final CompletedHolder holder, final int position) {
        holder.completedImg.setVisibility(View.VISIBLE);
        EquationModel model = list.get(position);
        holder.equation.setText(model.getNum1() + " "+model.getOperator() + " "+model.getNum2() +" = "+model.getResult());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CompletedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView equation;
        ImageView completedImg;
        public CompletedHolder(View itemView) {
            super(itemView);
            equation= itemView.findViewById(R.id.equationText);
            completedImg = itemView.findViewById(R.id.completed);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}