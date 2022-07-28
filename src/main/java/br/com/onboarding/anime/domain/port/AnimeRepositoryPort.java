package br.com.onboarding.anime.domain.port;

import br.com.onboarding.anime.domain.domain.Anime;

import java.util.List;

public interface AnimeRepositoryPort {

    Anime save(Anime anime);

    List<Anime> searchAnime();

    Anime searchAnimeById(Long id);

    Anime updateAnime(Long id, Anime anime);

    void deleteAnime(Long id);

}