package com.example.foode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foode.Models.Step;
import com.example.foode.R;

import java.util.List;

public class InstructionStepsAdapter extends RecyclerView.Adapter<instructionStepViewHolder>{
    Context context;
    List<Step> list;

    public InstructionStepsAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public instructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new instructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction_steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull instructionStepViewHolder holder, int position) {
        holder.textView_step_name.setText(list.get(position).step);
        holder.textView_instruction_number.setText(String.valueOf(list.get(position).number));

        holder.recycler_instruction_ingredients.setHasFixedSize(true);
        holder.recycler_instruction_ingredients.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionIngredientsAdapter instructionIngredientsAdapter = new InstructionIngredientsAdapter(context,list.get(position).ingredients);
        holder.recycler_instruction_ingredients.setAdapter(instructionIngredientsAdapter);

        holder.recycler_instruction_equipment.setHasFixedSize(true);
        holder.recycler_instruction_equipment.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionEquipmentAdapter instructionEquipmentAdapter = new InstructionEquipmentAdapter(context,list.get(position).equipment);
        holder.recycler_instruction_equipment.setAdapter(instructionEquipmentAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class instructionStepViewHolder extends RecyclerView.ViewHolder{
    TextView textView_instruction_number ,textView_step_name;
    RecyclerView recycler_instruction_ingredients,recycler_instruction_equipment;
    public instructionStepViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_instruction_number = itemView.findViewById(R.id.textView_instruction_number);
        textView_step_name = itemView.findViewById(R.id.textView_step_name);
        recycler_instruction_equipment = itemView.findViewById(R.id.recycler_instruction_equipment);
        recycler_instruction_ingredients = itemView.findViewById(R.id.recycler_instruction_ingredients);


    }
}
