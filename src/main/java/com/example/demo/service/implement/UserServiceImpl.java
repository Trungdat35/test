package com.example.demo.service.implement;

import com.example.demo.dto.OrderByCartRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.UpdateStatusOrder;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Products getProductsByID(int pdID) {
        Optional<Products> productsOptional = productRepo.findById(pdID);
        if (productsOptional.isPresent()) {
            Products p = productsOptional.get();
            p.setNumberOfViews(p.getNumberOfViews() + 1);
            productRepo.save(p);
            return p;
        }
        return null;
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
    public int updateStatusOrder(UpdateStatusOrder updateStatusOrder){
        Optional<Orders> orders = orderRepo.findById(updateStatusOrder.getOrderID());
        Optional<OrderStatus> orderStatus = orderStatusRepo.findById(updateStatusOrder.getOrderStatusID());
        if(orders.isPresent()&&orderStatus.isPresent()){
            orders.get().setOrderStatus(orderStatus.get());
            orderRepo.save(orders.get());
            return updateStatusOrder.getOrderStatusID();
        }
        return 0;
    }
    public void datHang(OrderRequest orderRequest){
         Orders orders = new Orders();
         OrderDetail orderDetail = new OrderDetail();
         Optional<Users> users = userRepo.findById(orderRequest.getUserID());
         Optional<Payment> payment = paymentRepo.findById(orderRequest.getPaymentID());
         Optional<Products> products = productRepo.findById(orderRequest.getProductID());
         if(users.isPresent()&&payment.isPresent()&&products.isPresent()){
             orders.setUsers(users.get());
             if(orderRequest.getPaymentID()==2){

             }
             if(orderRequest.getPaymentID()==3){

             }
             orders.setPayment(payment.get());
             orders.setOriginalPrice(products.get().getPrice());
             double giaKM = products.get().getPrice()*products.get().getDiscount()/100;
             orders.setActualPrice(products.get().getPrice()-giaKM);
             orders.setFullName(users.get().getUserName());
             orders.setEmail(users.get().getEmail());
             orders.setPhone(users.get().getPhone());
             orders.setAddress(users.get().getAddress());
             orders.setOrderID(orderRequest.getOrderStatus());
             orders.setCreateAt(LocalDate.now());
             Orders newOrder = createOrder(orders);
             orderDetail.setOrders(newOrder);
             orderDetail.setProducts(products.get());
             orderDetail.setQuantity(orderRequest.getQuantity());
             orderDetail.setPriceTotal(orderDetail.getPriceTotal()*orders.getActualPrice());
             orderDetail.setCreateAt(LocalDate.now());
             createOrderDetail(orderDetail);
         }
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
