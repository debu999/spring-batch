//package org.doogle.springbatch.controller;
//
//import org.doogle.springbatch.entity.Hero;
//import org.doogle.springbatch.service.HeroService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/heroes")
//public class HeroController {
//
//    private final HeroService heroService;
//
//    public HeroController(HeroService heroService) {
//        this.heroService = heroService;
//    }
//
//    @GetMapping
//    public List<Hero> getAllHeroes() {
//        return heroService.getAllHeroes();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Hero> getHeroById(@PathVariable Long id) {
//        return heroService.getHeroById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public Hero createHero(@RequestBody Hero hero) {
//        return heroService.saveHero(hero);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Hero> updateHero(@PathVariable Long id, @RequestBody Hero hero) {
//        return heroService.getHeroById(id)
//                .map(existingHero -> {
//                    existingHero.setName(hero.getName());
//                    existingHero.setPowers(hero.getPowers());
//                    existingHero.setPicture(hero.getPicture());
//                    return ResponseEntity.ok(heroService.saveHero(existingHero));
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteHero(@PathVariable Long id) {
//        heroService.deleteHero(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}