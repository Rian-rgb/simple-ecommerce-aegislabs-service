package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import org.springframework.data.domain.Pageable;

import java.sql.Date;


public interface ReportService {

    PageDataResponse findReportByPage(Pageable pageable, Date dateStart, Date dateEnd);
}
