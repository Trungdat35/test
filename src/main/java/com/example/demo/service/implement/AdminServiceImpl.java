package com.example.demo.service.implement;

import com.example.demo.dto.UpdateStatusOrder;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Orders;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.OrderStatusRepo;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderStatusRepo orderStatusRepo;
    public String updateStatusOrder(UpdateStatusOrder updateStatusOrder){
        Optional<Orders> orders = orderRepo.findById(updateStatusOrder.getOrderID());
        Optional<OrderStatus> orderStatus = orderStatusRepo.findById(updateStatusOrder.getOrderStatusID());
        if(orders.isPresent()&&orderStatus.isPresent()){
            orders.get().setOrderStatus(orderStatus.get());
            orderRepo.save(orders.get());
            return "Đơn hàng: " + orders.get().getOrderID() + " " + orders.get().getOrderStatus().getStatusName();
        }
        return null;
    }
}
