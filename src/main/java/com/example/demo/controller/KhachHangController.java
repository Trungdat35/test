package com.example.demo.controller;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.ProductViews;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UpdateStatusOrder;
import com.example.demo.exception.Response;
import com.example.demo.model.ProductReview;
import com.example.demo.model.Products;
import com.example.demo.model.Users;
import com.example.demo.service.MailService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(
        name = "bearerAuth")
@RequestMapping("api/v1/khachhang")
@RequiredArgsConstructor
public class KhachHangController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi khach hang");
    }

    @GetMapping(value = "/getlistuser")
    public List<Users> getlistUser() {
        return userService.getListUser();
    }

    @GetMapping(value = "/getlistproduct")
    public List<ProductViews> getlistproduct() {
        return productService.getListP();
    }

    @PutMapping(value = "updatereview", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductReview updateProductReview(@RequestBody ProductReview productReview) {
        return userService.updateReview(productReview);
    }

    @GetMapping(value = "/getlistreview")
    public List<ProductReview> getListPDR() {
        return userService.getListPDR();
    }

    @GetMapping(value = "/getproductbyid/{id}")
    public Products getProductByID(@PathVariable(name = "id") int pdID) {
        return userService.getProductsByID(pdID);
    }

    @GetMapping(value = "/getsanphannoibat")
    public List<Products> getsanphannoibat() {
        return productService.getSanPhamNoiBat();
    }

    @GetMapping(value = "/getsanphanlienquan")
    public List<Products> getsanphanlienquan(@RequestParam int pdTID) {
        return productService.getSanPhamLienQuan(pdTID);
    }

    @GetMapping(value = "/getbinhluansp/{id}")
    public List<ProductReview> getBinhLuanSP(@PathVariable(name = "id") int pdID) {
        return productService.getBinhLuanTheoSP(pdID);
    }

    @PostMapping(value = "/dathang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> datHang(@RequestBody OrderRequest orderRequest) {
        userService.datHang(orderRequest);
        mailService.sendMail(userService.getEmailByUserID(orderRequest.getUserID()), "Trạng thái đơn hàng", "Đặt hàng thành công");
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Thông báo đã được gửi đến mail của bạn !"));
    }

    @PutMapping(value = "/capnhattrangthaidonhang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateStatusOrder(@RequestBody UpdateStatusOrder updateStatusOrder) {
        int status = userService.updateStatusOrder(updateStatusOrder);
        if (status == 2) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", "Đơn hàng đã được xác nhận");
        }
        if (status == 3) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", "Đơn hàng đang trên đường giao đến bạn");
        }
        if (status == 4) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", "Đơn hàng đã được giao thành công");
        }
        if (status == 5) {
            mailService.sendMail(updateStatusOrder.getEmail(), "Trạng thái đơn hàng", "Đơn hàng đã bị hủy");
        }
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Thông báo đã được gửi đến mail của bạn !"));
    }
}
