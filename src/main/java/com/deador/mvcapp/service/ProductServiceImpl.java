package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import com.deador.mvcapp.repository.ProductRepository;
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

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean createProduct(ProductDTO productDTO, MultipartFile file) {
        if (productDTO == null) {
            return false;
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Product product = modelMapper
                .map(productDTO, Product.class);

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
            product.setImageName("unknown");
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
