package com.app.services;

import com.app.entites.Coupon;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;
import com.app.repositories.CouponRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CouponDTO createCoupon(Coupon coupon) {
        Coupon savedCoupon = CouponRepo.findByCode(coupon.getCode());
        savedCoupon = couponRepo.save(coupon);
        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponResponse getAllCoupons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Coupon> pageCoupons = couponRepo.findAll(pageable);
        List<CouponDTO> couponDTOs = pageCoupons.getContent().stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class))
                .collect(Collectors.toList());
        CouponResponse response = new CouponResponse();
        response.setContent(couponDTOs);
        response.setPageNumber(pageCoupons.getNumber());
        response.setPageSize(pageCoupons.getSize());
        response.setTotalElements(pageCoupons.getTotalElements());
        response.setTotalPages(pageCoupons.getTotalPages());
        response.setLastPage(pageCoupons.isLast());
        return response;
    }

    @Override
    public CouponDTO getCouponByCode(String code) {
        Coupon coupon = couponRepo.findByCode(code);
        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon", "code", code);
        }
        return modelMapper.map(coupon, CouponDTO.class);
    }

    @Override
    public CouponDTO updateCoupon(Long couponId, CouponDTO couponDTO) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));
        coupon.setCode(couponDTO.getCode());
        coupon.setDiscountAmount(couponDTO.getDiscountAmount());
        coupon.setActive(couponDTO.getActive());
        Coupon updatedCoupon = couponRepo.save(coupon);
        return modelMapper.map(updatedCoupon, CouponDTO.class);
    }

    @Override
    public String deleteCoupon(Long couponId) {
        Coupon coupon = couponRepo.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));
        couponRepo.delete(coupon);
        return "Coupon with ID " + couponId + " deleted successfully!";
    }
}