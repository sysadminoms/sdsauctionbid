package com.oms.sdsauctionbid.service;


import com.oms.sdsauctionbid.domain.FileUploadResponse;
import com.oms.sdsauctionbid.domain.Product;
import com.oms.sdsauctionbid.domain.ProductRequest;
import com.oms.sdsauctionbid.repository.ProductRepository;
import com.oms.sdsauctionbid.utility.FileOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ManageProductService {

    private static Timestamp timestamp;

    private static final Logger LOG = LoggerFactory.getLogger(Product.class);

    private ProductRepository productRepository;

    private FileOperation fileOperation;

    public ManageProductService(ProductRepository productRepository,
                                FileOperation fileOperation) {
        this.productRepository = productRepository;
        this.fileOperation = fileOperation;
    }

    public boolean addManageProduct(ProductRequest productRequest) {

        LOG.info("In ManageProductService class to save ManageProduct");
        timestamp = new Timestamp(System.currentTimeMillis());

        Product product = new Product();

        if (productRequest.getProductImageFile() == null) {
            return false;
        }
        FileUploadResponse fileUploadResponse = fileOperation.storeFile(productRequest.getProductImageFile());

        if (fileUploadResponse.getFileName() != null) {

            product.setProductName(productRequest.getProductName());
            product.setProductDescription(productRequest.getProductDescription());
            product.setImage(fileUploadResponse.getFileName());
            product.setImagePath(fileUploadResponse.getFilePath());
            product.setMinimumPrice(productRequest.getMinimumPrice());
            product.setOpenPrice(productRequest.getOpenPrice());
            product.setLastUpdatedBy(productRequest.getLastUpdatedBy());
            product.setUpdateDateTime(timestamp);
            product.setStatus(productRequest.isStatus());

            productRepository.save(product);
            return true;
        }
        return false;
    }

    public List<Product> getAllManageProduct() {

        LOG.info("In ManageProductService class Get All ManageProduct");
        return productRepository.findAll();
    }


    public boolean updateManageProduct(Long id, ProductRequest productRequest) {

        LOG.info("In ManageProductService class to update ManageProduct");
        timestamp = new Timestamp(System.currentTimeMillis());
        boolean exists = productRepository.existsById(id);
        if (exists) {
            if (productRequest.getProductImageFile() == null) {
                return false;
            }
            Product product = productRepository.getProductById(id);

            FileUploadResponse fileUploadResponse = fileOperation.storeFile(productRequest.getProductImageFile());

            product.setProductName(productRequest.getProductName());
            product.setProductDescription(productRequest.getProductDescription());
            product.setImage(fileUploadResponse.getFileName());
            product.setImagePath(fileUploadResponse.getFilePath());
            product.setMinimumPrice(productRequest.getMinimumPrice());
            product.setOpenPrice(productRequest.getOpenPrice());
            product.setLastUpdatedBy(productRequest.getLastUpdatedBy());
            product.setUpdateDateTime(timestamp);
            product.setStatus(productRequest.isStatus());

            productRepository.save(product);

            return true;
        }
        return false;
    }

    public boolean deleteManageProduct(long id) {

        LOG.info("In ManageProductService class to delete ManageProduct");

        boolean ifExists = productRepository.existsById(id);
        if (ifExists) {
            Product product = productRepository.getProductById(id);
            fileOperation.deleteFile(product.getImage());

            productRepository.deleteById(id);
            return true;
        } else
            return false;

    }
}

