package com.mml.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.mml.pokemon.adapters.PokemonAdapter;
import com.mml.pokemon.model.Pokemon;
import com.mml.pokemon.viewmodel.PokemonViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        recyclerView = findViewById(R.id.favRecyclerView);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);

        setupSwipe();

        pokemonViewModel.getFavPokemons();

        pokemonViewModel.getFavList().observe(this, pokemons -> {
            pokemonAdapter.setList((ArrayList<Pokemon>) pokemons);
        });

        Button toHome = findViewById(R.id.to_home_button);

        toHome.setOnClickListener(view -> {
            startActivity(new Intent(FavActivity.this,MainActivity.class));
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

                pokemonViewModel.deletePokemon(swipedPokemon.getName());
                pokemonAdapter.notifyDataSetChanged();

                Toast.makeText(FavActivity.this,"Pokemon deleted from database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}