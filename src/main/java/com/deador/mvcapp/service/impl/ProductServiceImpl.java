package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.converter.DTOConverter;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import com.deador.mvcapp.repository.ProductRepository;
import com.deador.mvcapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/productImages/";
    private final ProductRepository productRepository;
    private final DTOConverter dtoConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, DTOConverter dtoConverter) {
        this.productRepository = productRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // TODO: 15.03.2023 need to create ProductValidationException
    @Override
    public boolean createProduct(ProductDTO productDTO, MultipartFile file, String imgName) {
        if (productDTO == null) {
            return false;
        }

        Product product = dtoConverter.convertToEntity(productDTO, new Product());

        if (!file.isEmpty()) {
            Path uploadDir = Paths.get(uploadPath);

            if (!Files.exists(uploadDir)) {
                try {
                    Files.createDirectories(uploadDir);
                } catch (IOException e) {
                    // FIXME: 14.03.2023 need to use custom exception
                    throw new RuntimeException(e);
                }
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                // FIXME: 14.03.2023 need to use custom exception
                throw new RuntimeException(e);
            }

            product.setImageName(resultFilename);
        } else {
            product.setImageName(imgName);
        }

        productRepository.save(product);

        return true;
    }

    @Override
    public boolean deleteProduct(Product product) {
        if (product != null && productRepository.findById(product.getId()).isPresent()) {
            productRepository.delete(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean productExists(Product product) {
        return product != null && productRepository.findById(product.getId()).isPresent();
    }
}
