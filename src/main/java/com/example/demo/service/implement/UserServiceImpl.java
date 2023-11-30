package com.example.demo.service.implement;

import com.example.demo.config.VnPayConfig;
import com.example.demo.dto.*;
import com.example.demo.exception.AllException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.example.demo.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductReviewRepo productReviewRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private OrderStatusRepo orderStatusRepo;
    @Autowired
    private CartsRepo cartsRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private EmailUtil emailUtil;

    public Users createUser(Users users) {
        return userRepo.save(users);
    }

    public List<Users> getListUser() {
        return userRepo.findAll();
    }

    public ProductReview updateReview(ProductReview productReview) {
        Optional<ProductReview> optionalProductReview = productReviewRepo.findById(productReview.getProductReviewID());
        if (optionalProductReview.isPresent()) {
            optionalProductReview.get().setUpdateAt(LocalDate.now());
            return productReviewRepo.save(optionalProductReview.get());
        }
        return null;
    }

    public List<ProductReview> getListPDR() {
        return productReviewRepo.findAll();
    }


    public Orders createOrder(Orders orders){
        return orderRepo.save(orders);
    }
    public void  createOrderDetail(OrderDetail orderDetail){
         orderDetailRepo.save(orderDetail);
    }
    public String getEmailByUserID(int userID){
        Optional<Users> users = userRepo.findById(userID);
        if(users.isPresent()){
            return users.get().getEmail();
        }
        return null;
    }

    public void datHang(OrderRequest orderRequest){
         Orders orders = new Orders();
         OrderDetail orderDetail = new OrderDetail();
         Optional<Users> users = userRepo.findById(orderRequest.getUserID());
         Optional<OrderStatus> orderStatus = orderStatusRepo.findById(1);
         Optional<Payment> payment = paymentRepo.findById(orderRequest.getPaymentID());
         Optional<Products> products = productRepo.findById(orderRequest.getProductID());
         if(users.isPresent()&&payment.isPresent()&&products.isPresent()&&orderStatus.isPresent()){
             orders.setUsers(users.get());
             if(orderRequest.getPaymentID()==2){

             }

             orders.setPayment(payment.get());
             orders.setOriginalPrice(products.get().getPrice());
             double giaKM = products.get().getPrice()*products.get().getDiscount()/100;
             orders.setActualPrice(products.get().getPrice()-giaKM);
             orders.setFullName(users.get().getUserName());
             orders.setEmail(users.get().getEmail());
             orders.setPhone(users.get().getPhone());
             orders.setAddress(users.get().getAddress());
             orders.setOrderStatus(orderStatus.get());
             orders.setCreateAt(LocalDate.now());
             Orders newOrder = createOrder(orders);
             orderDetail.setOrders(newOrder);
             orderDetail.setProducts(products.get());
             orderDetail.setQuantity(orderRequest.getQuantity());
             orderDetail.setPriceTotal(orderDetail.getQuantity()*orders.getActualPrice());
             orderDetail.setCreateAt(LocalDate.now());
             createOrderDetail(orderDetail);
             if(orderRequest.getPaymentID()==3){
//               try {
//                   getPay(orderDetail.getPriceTotal());
//               } catch (UnsupportedEncodingException e) {
//                   throw new RuntimeException(e);
//               }
             }
             try {
                    emailUtil.sendOrderEmail(newOrder.getOrderID(),
                         orderDetail.getProducts().getNameProduct(),
                         orderDetail.getQuantity(),
                         orderDetail.getCreateAt(),
                         orders.getPayment().getPaymentMethod(),
                         orders.getOrderStatus().getStatusName(),
                         orderDetail.getPriceTotal(),
                         orders.getUsers().getEmail());
             } catch (MessagingException e) {
                 throw new AllException("Unable to send order please try again");
             }

         }
    }
    public String huyDonHang(int orderID){
        Optional<Orders> orders = orderRepo.findById(orderID);
        if(orders.isPresent()){
            orderDetailRepo.huyChiTietOrder(orderID);
            orderRepo.delete(orders.get());
          return "Hủy đơn hàng " + orderID +" thành công";
        }
        return null;
    }
    public String getPay(double amount) throws UnsupportedEncodingException {

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
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
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
    public Carts createCart(Carts carts){
        return cartsRepo.save(carts);
    }
    public CartItem addIteminCart(CartItem cartItem){
        return cartItemRepo.save(cartItem);
    }
    public void orderByCart(OrderByCartRequest orderByCartRequest){

    }
}
