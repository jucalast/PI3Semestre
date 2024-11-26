package com.app.service;

import com.app.model.Banner;
import com.app.repository.BannerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    public List<Banner> createBanners(List<Banner> banners) {
        return bannerRepository.saveAll(banners);
    }

    public Banner updateBanner(Long id, Banner bannerDetails) {
        Banner banner = getBannerById(id);
        banner.setTitle(bannerDetails.getTitle());
        banner.setImageBase64(bannerDetails.getImageBase64());
        return bannerRepository.save(banner);
    }

    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}