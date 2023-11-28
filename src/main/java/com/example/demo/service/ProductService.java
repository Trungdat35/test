package com.example.demo.service;

import com.example.demo.dto.ProductViews;
import com.example.demo.model.ProductReview;
import com.example.demo.model.Products;

import java.util.List;

public interface ProductService {
     List<ProductViews> getListP();
     List<Products> getSanPhamLienQuan(int pdTID);
     List<Products> getSanPhamNoiBat();
     List<ProductReview> getBinhLuanTheoSP(int pID);
}
