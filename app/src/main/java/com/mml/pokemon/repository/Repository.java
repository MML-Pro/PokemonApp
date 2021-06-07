package com.mml.pokemon.repository;

import androidx.lifecycle.LiveData;

import com.mml.pokemon.database.PokemonDAO;
import com.mml.pokemon.model.Pokemon;
import com.mml.pokemon.model.PokemonResponse;
import com.mml.pokemon.network.PokemonAPIService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {

    private final PokemonAPIService pokemonAPIService;
    private PokemonDAO pokemonDAO;

    @Inject
    public Repository(PokemonAPIService pokemonAPIService, PokemonDAO pokemonDAO) {
        this.pokemonAPIService = pokemonAPIService;
        this.pokemonDAO = pokemonDAO;
    }

    public Observable<PokemonResponse> getPokemons(){

        return pokemonAPIService.getPokemon();
    }

    public void insertPokemon(Pokemon pokemon){
        pokemonDAO.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){
        pokemonDAO.delete(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavPokemons(){
        return pokemonDAO.getPokemons();
    }
}
