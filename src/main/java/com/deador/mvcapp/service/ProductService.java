package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    boolean createProduct(ProductDTO productDTO, MultipartFile file, String imgName);

    boolean deleteProductById(Long id);

    boolean isProductExistsById(Long id);

    Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Product> findPaginatedInCategory(int pageNo, int pageSize, String sortField, String sortDirection, Long categoryId);

    Page<Product> findPaginatedInSearch(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);

    void incrementProductViewCountById(Long id);
}
