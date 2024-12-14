package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.user.UpdateActiveUserRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    @PutMapping("/active")
    public ResponseEntity<DataResponse> changeActiveUser(@RequestBody @Valid UpdateActiveUserRequest request) {
        return ResponseEntity.ok().body(userService.changeUserActive(request));
    }

    @GetMapping("/page")
    public ResponseEntity<PageDataResponse> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean enabled
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findByPage(pageable, enabled));
    }
}
