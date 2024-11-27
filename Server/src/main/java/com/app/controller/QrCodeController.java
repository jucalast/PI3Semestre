package com.app.controller;

import com.app.model.QrCode;
import com.app.repository.QrCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/qrcode")
public class QrCodeController {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @PostMapping("/generate")
    public ResponseEntity<String> generateQrCode() {
        QrCode qrCode = new QrCode();
        qrCodeRepository.save(qrCode);
        return ResponseEntity.ok(qrCode.getCode());
    }

    @GetMapping("/check/{code}")
    public ResponseEntity<Boolean> checkQrCode(@PathVariable String code) {
        QrCode qrCode = qrCodeRepository.findByCode(code);
        if (qrCode != null && qrCode.getExpiryTime().isAfter(LocalDateTime.now())) {
            return ResponseEntity.ok(qrCode.isRead());
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/read/{code}")
    public ResponseEntity<Void> markAsRead(@PathVariable String code) {
        QrCode qrCode = qrCodeRepository.findByCode(code);
        if (qrCode != null && !qrCode.isRead() && qrCode.getExpiryTime().isAfter(LocalDateTime.now())) {
            qrCode.setRead(true);
            qrCodeRepository.save(qrCode);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
