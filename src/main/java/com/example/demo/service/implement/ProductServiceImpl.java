package com.example.demo.service.implement;

import com.example.demo.dto.ProductViews;
import com.example.demo.model.ProductReview;
import com.example.demo.model.Products;
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

    public List<ProductViews> getListP(){
        return productRepo.findAll().stream().map(products -> new ProductViews(
                products.getProductID(),
                products.getNameProduct(),
                products.getPrice(),
                products.getAvatarImageProduct(),
                products.getTitle(),
                products.getDiscount(),
                products.getNumberOfViews())).collect(Collectors.toList());
    }
    public List<Products> getSanPhamLienQuan(int pdTID){
        return productRepo.getSPLienQuan(pdTID);
    }
    public List<Products> getSanPhamNoiBat(){
       List<Integer> listpID = productReviewRepo.getProductNoiBat();
       return productRepo.getSPNoiBat(listpID);
    }
    public List<ProductReview> getBinhLuanTheoSP(int pID){
        return productReviewRepo.getBinhLuanBySP(pID);
    }
}
