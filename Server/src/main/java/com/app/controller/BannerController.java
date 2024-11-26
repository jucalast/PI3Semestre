package com.app.controller;

import com.app.model.Banner;
import com.app.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public List<Banner> getAllBanners() {
        return bannerService.getAllBanners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable Long id) {
        Banner banner = bannerService.getBannerById(id);
        return ResponseEntity.ok(banner);
    }

    @PostMapping
    public Banner createBanner(@RequestBody Banner banner) {
        return bannerService.createBanner(banner);
    }

    @PostMapping("/bulk")
    public List<Banner> createBanners(@RequestBody List<Banner> banners) {
        return bannerService.createBanners(banners);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner bannerDetails) {
        Banner updatedBanner = bannerService.updateBanner(id, bannerDetails);
        return ResponseEntity.ok(updatedBanner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}