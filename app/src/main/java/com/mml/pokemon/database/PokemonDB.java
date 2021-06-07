package com.mml.pokemon.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mml.pokemon.model.Pokemon;

@Database(entities = Pokemon.class,version = 1,exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {

    public abstract PokemonDAO pokemonDAO();

}
