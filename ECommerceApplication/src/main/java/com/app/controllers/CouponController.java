package com.app.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.config.AppConstants;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;
import com.app.services.CouponService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @PostMapping("/admin/coupon")
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody CouponDTO couponDTO) {
        CouponDTO createdCoupon = couponService.createCoupon(couponDTO);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }
    @GetMapping("/public/coupons")
    public ResponseEntity<CouponResponse> getAllCoupons(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "couponId", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
        CouponResponse response = couponService.getAllCoupons(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/public/coupon/{code}")
    public ResponseEntity<CouponDTO> getCouponByCode(@PathVariable String code) {
        CouponDTO couponDTO = couponService.getCouponByCode(code);
        return new ResponseEntity<>(couponDTO, HttpStatus.OK);
    }
    @PutMapping("/admin/coupon/{couponId}")
    public ResponseEntity<CouponDTO> updateCoupon(@PathVariable Long couponId, @RequestBody CouponDTO couponDTO) {
        CouponDTO updatedCoupon = couponService.updateCoupon(couponId, couponDTO);
        return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
    }
    @DeleteMapping("/admin/coupon/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long couponId) {
        String message = couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}