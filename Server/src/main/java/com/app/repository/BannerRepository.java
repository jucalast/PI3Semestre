package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}