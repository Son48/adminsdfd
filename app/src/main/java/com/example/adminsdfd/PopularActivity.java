package com.example.adminsdfd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class PopularActivity extends AppCompatActivity {
    RecyclerView recview;
    PopularAdapter popularAdapter;
    FloatingActionButton fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        setTitle("Search here..");

        recview=findViewById(R.id.recviewPo);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<PopularModel> options =
                new FirebaseRecyclerOptions.Builder<PopularModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("PopularDish"), PopularModel.class)
                        .build();

        popularAdapter=new PopularAdapter(options);
        recview.setAdapter(popularAdapter);

        fb=(FloatingActionButton)findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),addDataPopular.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        popularAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        popularAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<PopularModel> options =
                new FirebaseRecyclerOptions.Builder<PopularModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("PopularDish").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), PopularModel.class)
                        .build();

        popularAdapter=new PopularAdapter(options);
        popularAdapter.startListening();
        recview.setAdapter(popularAdapter);

    }
}

