//package org.doogle.springbatch.service;
//
//import org.doogle.springbatch.entity.Hero;
//import org.doogle.springbatch.repository.HeroRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class HeroService {
//
//    private final HeroRepository heroRepository;
//
//    public HeroService(HeroRepository heroRepository) {
//        this.heroRepository = heroRepository;
//    }
//
//    public List<Hero> getAllHeroes() {
//        return heroRepository.findAll();
//    }
//
//    public Optional<Hero> getHeroById(Long id) {
//        return heroRepository.findById(id);
//    }
//
//    public Hero saveHero(Hero hero) {
//        return heroRepository.save(hero);
//    }
//
//    public void deleteHero(Long id) {
//        heroRepository.deleteById(id);
//    }
//}