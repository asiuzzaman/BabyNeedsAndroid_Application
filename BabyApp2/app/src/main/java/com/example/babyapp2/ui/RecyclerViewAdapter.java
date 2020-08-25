package com.example.babyapp2.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyapp2.R;
import com.example.babyapp2.data.DatabaseHandler;
import com.example.babyapp2.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Item>itemList;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private AlertDialog alertDialog;
    public RecyclerViewAdapter(Context context, List<Item>itemList) {
            this.context=context;
            this.itemList=itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.itemName.setText(MessageFormat.format("Item Name: {0}", item.getItemName()));
        holder.itemColor.setText(MessageFormat.format("Color: {0}", item.getItemColor()));
        holder.quantity.setText(MessageFormat.format("Qty: {0}", item.getItemQuentity()));
        holder.size.setText(MessageFormat.format("Size: {0}", item.getItemSize()));
        holder.dateAdded.setText(MessageFormat.format("Added time: {0}", item.getDateItemAdded()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName;
        public TextView itemColor;
        public TextView quantity;
        public TextView size;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        int id;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;

            itemName = itemView.findViewById(R.id.itemName);
            itemColor = itemView.findViewById(R.id.itemColor);
            quantity = itemView.findViewById(R.id.itemQuantity);
            size = itemView.findViewById(R.id.itemSize);
            dateAdded = itemView.findViewById(R.id.itemDate);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position;
            position=getAdapterPosition();
            Item item=itemList.get(position);
            switch (v.getId()) {
                case R.id.editButton:
                    //edit item
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }
        }

        private void deleteItem(final int id) {
            builder=new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_popup,null);

            Button yes_button = view.findViewById(R.id.conf_yes_button);
            Button no_button = view.findViewById(R.id.conf_no_button);

            builder.setView(view);
            alertDialog= builder.create();
            alertDialog.show();
            yes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.delete(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });
            no_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        alertDialog.dismiss();
                }
            });

        }

        private void editItem(final Item newItem) {

          //  Item item = itemList.get(getAdapterPosition());

            builder=new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup,null);

            Button saveButton;
            final EditText babyItem,itemQuentity,itemColor,itemSize;
            TextView title;

            // Connect the entity
            babyItem=view.findViewById(R.id.baby_item);
            itemQuentity=view.findViewById(R.id.item_quentity);
            itemColor=view.findViewById(R.id.item_color);
            itemSize=view.findViewById(R.id.item_size);
            title = view.findViewById(R.id.title);
            saveButton =view.findViewById(R.id.save_button);
            saveButton.setText(R.string.update_text);

            // set up items
            title.setText(R.string.edit_item);
            babyItem.setText(newItem.getItemName());
            itemQuentity.setText(String.valueOf(newItem.getItemQuentity()));
            itemColor.setText(newItem.getItemColor());
            itemSize.setText(String.valueOf(newItem.getItemSize()));

            builder.setView(view);
            alertDialog= builder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);

                    //Todo: Update Items
                    newItem.setItemName(babyItem.getText().toString());
                    newItem.setItemColor(itemColor.getText().toString());
                    newItem.setItemQuentity(Integer.parseInt(itemQuentity.getText().toString()));
                    newItem.setItemSize(Integer.parseInt(itemSize.getText().toString()));

                    if(!babyItem.getText().toString().isEmpty()
                            && !itemColor.getText().toString().isEmpty()
                            && ! itemQuentity.getText().toString().isEmpty()
                            && ! itemSize.getText().toString().isEmpty()
                    ){
                        databaseHandler.updateItem(newItem);
                        Snackbar.make(view,"Data updated successfully",Snackbar.LENGTH_SHORT).show();
                        notifyItemChanged(getAdapterPosition(),newItem);
                    }else{
                        Snackbar.make(view,"Fields are Empty",Snackbar.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();

                }
            });


        }
    }
}
