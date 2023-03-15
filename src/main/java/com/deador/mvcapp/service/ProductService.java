package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    boolean createProduct(ProductDTO productDTO, MultipartFile file);

    boolean deleteProduct(Product product);

    boolean productExists(Product product);
}
