package com.mml.pokemon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mml.pokemon.R;
import com.mml.pokemon.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewModel> {

    ArrayList<Pokemon> pokemonList = new ArrayList<>();
    private Context mContext;

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(ArrayList<Pokemon> PokemonArrayList) {
        this.pokemonList = PokemonArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewModel(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.pokemon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewModel holder, int position) {

        holder.pokemonName.setText(pokemonList.get(position).getName());
        Glide.with(mContext).load(pokemonList.get(position).getUrl()).into(holder.pokemonImage);
 
    }

    public Pokemon getPokemonAt(int position){
        return pokemonList.get(position);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewModel extends RecyclerView.ViewHolder {

        private ImageView pokemonImage;
        private TextView pokemonName;

        public PokemonViewModel(@NonNull View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.pokemonImageView);
            pokemonName = itemView.findViewById(R.id.pokemonNameTV);

        }

    }
}
