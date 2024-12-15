package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.model.projection.SalesProjection;
import com.aegisultimateknologi.simple_ecommerce_service.repository.SalesRepository;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ReportService;
import com.aegisultimateknologi.simple_ecommerce_service.util.ConvertToPageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SalesRepository salesRepository;

    @Override
    public PageDataResponse findReportByPage(Pageable pageable, Date dateStart, Date dateEnd) {
        try {

            Page<SalesProjection> salesPage = salesRepository.findByCreatedAtBetween(dateStart, dateEnd, pageable);
            return ConvertToPageUtil.convertToPage(Collections.singletonList(salesPage.getContent()),
                    salesPage.getNumber(), salesPage.getSize(),
                    salesPage.getTotalElements(), salesPage.getTotalPages(),
                    salesPage.isLast());

        } catch (Exception e) {
            throw e;
        }
    }
}
