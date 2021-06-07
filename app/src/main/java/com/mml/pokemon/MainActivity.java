package com.mml.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mml.pokemon.adapters.PokemonAdapter;
import com.mml.pokemon.model.Pokemon;
import com.mml.pokemon.viewmodel.PokemonViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        recyclerView = findViewById(R.id.pokemonRecyclerView);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        setupSwipe();

        pokemonViewModel.getPokemons();

        pokemonViewModel.getPokemonList().observe(this, pokemons -> {
            pokemonAdapter.setList(pokemons);
        });

        Button toFav = findViewById(R.id.to_fav_button);

        toFav.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,FavActivity.class));
        });

    }

    private void setupSwipe(){

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getBindingAdapterPosition();

                Pokemon swipedPokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition);

                pokemonViewModel.insertPokemon(swipedPokemon);
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Pokemon added to database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}