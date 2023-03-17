package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.converter.DTOConverter;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import com.deador.mvcapp.exception.DirectoryCreationException;
import com.deador.mvcapp.exception.FileTransferException;
import com.deador.mvcapp.exception.IncorrectInputException;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.repository.ProductRepository;
import com.deador.mvcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/productImages/";
    private static final String PRODUCT_ALREADY_EXIST = "Product already exist with name %s";
    private static final String PRODUCT_NOT_FOUND_BY_ID = "Product not found by id: %s";
    private static final String PRODUCT_DELETING_ERROR = "Product isn't deleted";
    private static final String PRODUCT_CREATING_ERROR = "Product isn't created";
    private static final String DIRECTORY_CREATION_ERROR = "Directory creation error with path: %s";
    private final ProductRepository productRepository;
    private final DTOConverter dtoConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              DTOConverter dtoConverter) {
        this.productRepository = productRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new NotExistException(String.format(PRODUCT_NOT_FOUND_BY_ID, id));
        }

        return optionalProduct.get();
    }

    // TODO: 15.03.2023 need to create ProductValidationException?
    @Override
    @Transactional
    public boolean createProduct(ProductDTO productDTO, MultipartFile file, String imgName) {
        if (productDTO == null) {
            throw new IncorrectInputException();
        }

        Product product = dtoConverter.convertToEntity(productDTO, Product.class);

        if (!file.isEmpty()) {
            Path uploadDir = Paths.get(uploadPath);

            if (!Files.exists(uploadDir)) {
                try {
                    Files.createDirectories(uploadDir);
                } catch (IOException e) {
                    throw new DirectoryCreationException(String.format(DIRECTORY_CREATION_ERROR, uploadPath));
                }
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                throw new FileTransferException();
            }

            product.setImageName(resultFilename);
        } else {
            product.setImageName(imgName);
        }

        productRepository.save(product);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteProductById(Long id) {
        if (isProductExistsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else if (!isProductExistsById(id)) {
            throw new NotExistException(String.format(PRODUCT_NOT_FOUND_BY_ID, id));
        } else {
            throw new NotExistException(String.format(PRODUCT_DELETING_ERROR, id));
        }
    }

    @Override
    public boolean isProductExistsById(Long id) {
        return productRepository.existsById(id);
    }
}
