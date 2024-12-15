package com.aegisultimateknologi.simple_ecommerce_service.model.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SalesProjection {

    LocalDateTime getCreatedAt();

    boolean getSold();

    LocalDateTime getUpdatedAt();

    Integer getAmountProductBuy();

    BigDecimal getTotalPrice();

    String getNameProduct();

    BigDecimal getPriceProduct();

}
