package com.app.repository;

import com.app.model.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    QrCode findByCode(String code);
}
