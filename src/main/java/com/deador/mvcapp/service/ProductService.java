package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    boolean createProduct(ProductDTO productDTO, MultipartFile file, String imgName);

    boolean deleteProductById(Long id);

    boolean isProductExistsById(Long id);
}
