package com.mml.pokemon.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mml.pokemon.model.Pokemon;

import java.util.List;

@Dao
public interface PokemonDAO {

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Query("DELETE FROM FAV_TABLE WHERE NAME =:pokemonName")
    void delete(String pokemonName);

    @Query("SELECT * FROM FAV_TABLE")
    LiveData<List<Pokemon>> getPokemons();


}
