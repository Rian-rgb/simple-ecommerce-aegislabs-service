package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;


@RestController
@RequestMapping("report")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/page")
    @Operation(
            summary = "Laporan Transaksi Berdasarkan Tanggal",
            description = "API ini digunakan untuk menghasilkan laporan transaksi yang dilakukan dalam rentang waktu " +
                    "tertentu. Pengguna atau admin dapat menentukan tanggal awal dan tanggal akhir untuk " +
                    "melihat daftar transaksi yang terjadi dalam periode tersebut."
    )
    public ResponseEntity<PageDataResponse> findReportByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Date dateStart,
            @RequestParam Date dateEnd
            ) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(reportService.findReportByPage(pageable, dateStart, dateEnd));
    }
}
