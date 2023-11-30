package com.example.demo.service.implement;

import com.example.demo.dto.BinhLuanTheoSP;
import com.example.demo.dto.ProductViews;
import com.example.demo.model.ProductReview;
import com.example.demo.model.Products;
import com.example.demo.repository.OrderDetailRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.ProductReviewRepo;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductReviewRepo productReviewRepo;
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    public List<ProductViews> getListP(){
        return productRepo.findAll().stream().map(products -> new ProductViews(
                products.getProductID(),
                products.getNameProduct(),
                products.getPrice(),
                products.getAvatarImageProduct(),
                products.getTitle(),
                products.getDiscount(),
                products.getNumberOfViews(),
                orderDetailRepo.luotMua(products.getProductID())
        )).collect(Collectors.toList());
    }
    public List<ProductViews> getSanPhamLienQuan(int pdTID){
        return productRepo.getSPLienQuan(pdTID).stream().map(products -> new ProductViews(
                products.getProductID(),
                products.getNameProduct(),
                products.getPrice(),
                products.getAvatarImageProduct(),
                products.getTitle(),
                products.getDiscount(),
                products.getNumberOfViews(),
                orderDetailRepo.luotMua(products.getProductID())
                )).collect(Collectors.toList());
    }
    public ProductViews getProductsByID(int pdID) {
        Products products = productRepo.findById(pdID).orElse(null);
        if(products!=null){
            products.setNumberOfViews(products.getNumberOfViews() + 1);
            productRepo.save(products);
            return   new ProductViews(
                    products.getProductID(),
                    products.getNameProduct(),
                    products.getPrice(),
                    products.getAvatarImageProduct(),
                    products.getTitle(),
                    products.getDiscount(),
                    products.getNumberOfViews(),
                    orderDetailRepo.luotMua(products.getProductID()));
        }else {
            return null;
        }
    }
    public ProductViews getSanPhamNoiBat(){
        int pdID = productRepo.getSPNoiBat();
        return getProductsByID(pdID);
    }
    public List<BinhLuanTheoSP> getBinhLuanTheoSP(int pID){
        return productReviewRepo.getBinhLuanBySP(pID).stream().map(bl-> new BinhLuanTheoSP(
                bl.getProducts().getNameProduct(),
                bl.getContentRated(),
                bl.getPoint_evaluation(),
                bl.getContentSeen(),
                bl.getCreateAt()
        )).collect(Collectors.toList());
    }
}
