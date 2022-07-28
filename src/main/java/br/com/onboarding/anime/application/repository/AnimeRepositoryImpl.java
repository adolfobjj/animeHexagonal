package br.com.onboarding.anime.application.repository;

import br.com.onboarding.anime.application.mapper.AnimeMapper;
import br.com.onboarding.anime.application.repository.jpa.AnimeJpa;
import br.com.onboarding.anime.domain.domain.Anime;
import br.com.onboarding.anime.domain.port.AnimeRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AnimeRepositoryImpl implements AnimeRepositoryPort {

    private final AnimeJpa animeJpa;

    @Override
    public Anime save(Anime anime) {
        try {
            var animeEntity = animeJpa.save(AnimeMapper.toEntity(anime));
            return AnimeMapper.entityToDomain(animeEntity);
        } catch (Exception e) {
            log.error("Erro ao tentar salvar o anime. {}", anime, e);
            throw e;
        }
    }

    //convertendo do entity(jpa) para domain
    @Override
    public List<Anime> searchAnime() {
        var animeEntityList = animeJpa.findAll();
        return AnimeMapper.toDomainList(animeEntityList);
    }

    @Override
    public Anime searchAnimeById(Long id) {
        var animeById = animeJpa.findById(id);

        return animeById.map(AnimeMapper::entityToDomain).orElse(null);

//        if (animeById.isPresent()) {
//            return AnimeMapper.entityToDomain(animeById.get());
//        }
//
//        return null;

    }

    @Override
    public Anime updateAnime(Long id, Anime anime) throws HttpStatusCodeException {
        var animeEntityById= animeJpa.findById(id);

        if (animeEntityById.isPresent()) {
            anime.setId(animeEntityById.get().getId());
            var animeEntity = animeJpa.save(AnimeMapper.toEntity(anime));
            return AnimeMapper.entityToDomain(animeEntity);
        }

        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Anime doesn't exist");
    }

    @Override
    public void deleteAnime(Long id) throws HttpStatusCodeException {
        var animeToDelete = searchAnimeById(id);

        if (animeToDelete != null) {
            animeJpa.deleteById(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Anime doesn't exist");
        }
    }
}

