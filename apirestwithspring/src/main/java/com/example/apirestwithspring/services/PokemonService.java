package com.example.apirestwithspring.services;

import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class PokemonService {

    private final RestClient client;
    private final PokemonRepository repository;

    public PokemonService(RestClient.Builder builder, PokemonRepository repository) {
        this.client = builder.baseUrl("https://pokeapi.co/api/v2").build();
        this.repository = repository;
    }

    // GET na PokeAPI
    public String getPokemonByName(String pokemon_name) {
        return Objects.requireNonNull(client.get()
                        .uri("/pokemon/{pokemon_name}", pokemon_name)
                        .retrieve()
                        .body(Pokemon.class))
                .toString();
    }


    public String listarTodos() {
        return client.get()
                .uri("/pokemon?limit=50")
                .retrieve()
                .body(String.class);
    }

    // Lista local
    public List<Pokemon> listarLocais() {
        return repository.listarLocais();
    }

    // POST
    public Pokemon criar(Pokemon pokemon) {
        return repository.salvar(pokemon);
    }

    // PUT
    public Pokemon atualizar(Integer id, Pokemon pokemon) {
        if (!repository.existe(id)) {
            return null;
        }
        return repository.salvar(new Pokemon(id, pokemon.name(), pokemon.height()));
    }

    // DELETE
    public boolean deletar(Integer id) {
        if (!repository.existe(id)) {
            return false;
        }
        repository.deletar(id);
        return true;
    }
}