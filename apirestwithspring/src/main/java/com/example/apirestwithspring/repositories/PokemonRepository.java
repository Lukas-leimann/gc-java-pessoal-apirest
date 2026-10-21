package com.example.apirestwithspring.repositories;

import com.example.apirestwithspring.entities.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PokemonRepository {

    private final Map<Integer, Pokemon> pokemons = new LinkedHashMap<>();

    public List<Pokemon> listarLocais() {
        return pokemons.values().stream().limit(50).toList();
    }

    public Pokemon salvar(Pokemon pokemon) {
        pokemons.put(pokemon.id(), pokemon);
        return pokemon;
    }

    public boolean existe(Integer id) {
        return pokemons.containsKey(id);
    }

    public void deletar(Integer id) {
        pokemons.remove(id);
    }
}