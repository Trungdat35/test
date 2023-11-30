package com.example.demo.controller;

import com.example.demo.config.VnPayConfig;
import com.example.demo.dto.*;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ProductViews getProductByID(@PathVariable(name = "id") int pdID) {
        return productService.getProductsByID(pdID);
    }

    @GetMapping(value = "/getsanphannoibat")
    public ProductViews getsanphannoibat() {
        return productService.getSanPhamNoiBat();
    }

    @GetMapping(value = "/getsanphanlienquan")
    public List<ProductViews> getsanphanlienquan(@RequestParam int pdTID) {
        return productService.getSanPhamLienQuan(pdTID);
    }

    @GetMapping(value = "/getbinhluansp/{id}")
    public List<BinhLuanTheoSP> getBinhLuanSP(@PathVariable(name = "id") int pdID) {
        return productService.getBinhLuanTheoSP(pdID);
    }

    @PostMapping(value = "/dathang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> datHang(@RequestBody OrderRequest orderRequest) {
        userService.datHang(orderRequest);
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Đặt hàng thàng công, thông tin đơn hàng đã được gửi đến mail của bạn !"));
    }

    @PutMapping(value = "/huydathang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> datHang(@RequestBody CancelOrderRequest orderRequest) {
        String mess = userService.huyDonHang(orderRequest.getOrderID());
        mailService.sendMail(userService.getEmailByUserID(orderRequest.getUserID()), "Thông tin đơn đặt hàng", mess);
        return ResponseEntity.ok(new Response(HttpStatus.ACCEPTED, "Đặt hàng thàng công, thông tin đơn hàng đã được gửi đến mail của bạn !"));
    }
    @GetMapping("/pay/{amount}")
    public String getPay(@PathVariable(name = "amount") int amount) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String bankCode = "NCB";

        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }
}
