package br.com.onboarding.anime.application.repository.jpa;

import br.com.onboarding.anime.application.repository.jpa.entity.AnimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeJpa extends JpaRepository<AnimeEntity, Long> {}
