package com.mml.pokemon.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mml.pokemon.model.Pokemon;
import com.mml.pokemon.model.PokemonResponse;
import com.mml.pokemon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PokemonViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favList;

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    @Inject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void getPokemons() {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(pokemonResponse -> {
                    ArrayList<Pokemon> list = pokemonResponse.getResults();
                    for (Pokemon pokemon : list) {
                        String url = pokemon.getUrl();
                        String[] pokemonIndex = url.split("/");
                        pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"
                                + pokemonIndex[pokemonIndex.length - 1] + ".png");
                    }
                    return list;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonList.setValue(result)
                        , error -> Log.e("ViewModel", error.getMessage())
                );

    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemons() {
        favList = repository.getFavPokemons();
    }
}
