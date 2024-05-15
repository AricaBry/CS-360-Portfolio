package com.example.inventoryapp_arica_bryant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class I_RecyclerViewAdapter extends RecyclerView.Adapter<I_RecyclerViewAdapter.MyViewHolder> {
    // Member variables
    private final Context context;
    private final ArrayList<ItemInfo> inventoryModels;

    // Constructor
    public I_RecyclerViewAdapter(Context context, ArrayList<ItemInfo> inventoryModels) {
        this.context = context;
        this.inventoryModels = inventoryModels;
    }

    // Adding a design to the rows
    @NonNull
    @Override
    public I_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_layout, parent, false);

        return new I_RecyclerViewAdapter.MyViewHolder(view);
    }

    // Assigns values to the rows based on the position of the recycler view
    @Override
    public void onBindViewHolder(@NonNull I_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.iName.setText(inventoryModels.get(position).getItemName());

        // Grabs integer value and converts into String for output in TextView
        int quantity = inventoryModels.get(position).getItemQuantity();
        holder.iQuantity.setText(String.valueOf(quantity));

//        // Handles the delete button in the recyclerView
        holder.delButton.setOnClickListener(v -> {
            try (InventoryAppDB inventoryAppDB = new InventoryAppDB(context)) {
                int clickedPos = holder.getBindingAdapterPosition();

                if (clickedPos != RecyclerView.NO_POSITION) {
                    ItemInfo clickedItem = inventoryModels.get(clickedPos);

                    // Removes the item from the database
                    inventoryAppDB.deleteOne(clickedItem);

                    // Removes the item from the RecyclerView
                    inventoryModels.remove(clickedItem);

                    // Notifies the adapter that the item has been removed
                    notifyItemRemoved(clickedPos);
                }
            }
        });

        // Handles the event that happens when you click an item in the recyclerView
        holder.itemView.setOnClickListener(v -> {
                int clickedPos = holder.getBindingAdapterPosition();

                if (clickedPos != RecyclerView.NO_POSITION) {
                    ItemInfo clickedItem = inventoryModels.get(clickedPos);

                    // Passing data in recyclerview row to the edit screen
                    Intent intent = new Intent(context, ScreenItem.class);
                    intent.putExtra("id", String.valueOf(clickedItem.getItemId()));
                    intent.putExtra("name", String.valueOf(clickedItem.getItemName()));
                    intent.putExtra("quantity", String.valueOf(clickedItem.getItemQuantity()));
                    context.startActivity(intent);

                    // Notifies the adapter that the item has been changed
                    notifyItemChanged(clickedPos);
                }
        });
    }

    // Gets total items on display
    @Override
    public int getItemCount() {
        return inventoryModels.size();
    }

    // Grabs the views from the recycler_view_layout file
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView iName, iQuantity;
        Button delButton;
        View rootView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView;
            iName = itemView.findViewById(R.id.iName);
            iQuantity = itemView.findViewById(R.id.iQuantity);
            delButton = itemView.findViewById(R.id.iDeleteButton);
        }
    }
}
