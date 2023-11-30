package com.example.demo.service;

import com.example.demo.dto.BinhLuanTheoSP;
import com.example.demo.dto.ProductViews;
import com.example.demo.model.ProductReview;
import com.example.demo.model.Products;

import java.util.List;

public interface ProductService {
     List<ProductViews> getListP();
     List<ProductViews> getSanPhamLienQuan(int pdTID);
     ProductViews getSanPhamNoiBat();
     List<BinhLuanTheoSP> getBinhLuanTheoSP(int pID);
     ProductViews getProductsByID(int pdID);
}
