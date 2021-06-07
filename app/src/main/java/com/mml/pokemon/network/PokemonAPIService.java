package com.mml.pokemon.network;

import com.mml.pokemon.model.Pokemon;
import com.mml.pokemon.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonAPIService {

    @GET("pokemon")
    Observable<PokemonResponse> getPokemon();
}
