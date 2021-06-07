package com.mml.pokemon.di;

import android.app.Application;

import androidx.room.Room;

import com.mml.pokemon.database.PokemonDAO;
import com.mml.pokemon.database.PokemonDB;
import com.mml.pokemon.model.Pokemon;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static PokemonDB provideDB(Application application){

        return Room.databaseBuilder(application,PokemonDB.class,"fav_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDAO provideDAO(PokemonDB pokemonDB){
        return pokemonDB.pokemonDAO();
    }
}
