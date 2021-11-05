package com.example.adminsdfd;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopularAdapter extends FirebaseRecyclerAdapter<PopularModel,PopularAdapter.myviewholder> {


    public PopularAdapter(@NonNull FirebaseRecyclerOptions<PopularModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PopularAdapter.myviewholder holder, int position, @NonNull PopularModel model) {
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.calo.setText(model.getCalo());
        Glide.with(holder.img.getContext()).load(model.getImg_url()).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText purl=myview.findViewById(R.id.uimgurl);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText description=myview.findViewById(R.id.updes);
                final EditText calo=myview.findViewById(R.id.upcalo);
                Button submit=myview.findViewById(R.id.usubmit);

                purl.setText(model.getImg_url());
                name.setText(model.getName());
                description.setText(model.getDescription());
                calo.setText(model.getCalo());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("purl",purl.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("calo",calo.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("PopularDish")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("PopularDish")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public PopularAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_dish,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView img;
        Button edit,delete;
        TextView name,description,calo;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.popular_img);
            name=itemView.findViewById(R.id.pop_name);
            description=itemView.findViewById(R.id.pop_des);
            calo=itemView.findViewById(R.id.pop_calo);

            edit=itemView.findViewById(R.id.edtEdit);
            delete=itemView.findViewById(R.id.edtDelete);
        }
    }
}


