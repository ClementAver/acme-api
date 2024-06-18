package com.acme.api.services;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.mapper.GetAllProductsDTOMapper;
import com.acme.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

import static com.acme.api.entities.Product.generateReference;

@Service
public class ProductService implements ProductInterface{

    private final ProductRepository productRepository;
    private final GetAllProductsDTOMapper getAllProductsDTOMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.getAllProductsDTOMapper = new GetAllProductsDTOMapper();
    }

    @Override
    public Product createProduct(ProductRequestBody productRequestBody) {
        Product product = new Product();
        product.setReference(generateReference());
        product.setName(productRequestBody.getName());
        product.setPrice(productRequestBody.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Stream<GetProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream().map(getAllProductsDTOMapper);
    }

    @Override
    public Product getProductEntity(String reference) {
        return productRepository.findByReference(reference);
    }

    @Override
    public GetProductDTO getProduct(String reference) {
        Product product = productRepository.findByReference(reference);
        return new GetProductDTO(product.getReference(), product.getName(), product.getPrice());
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, ProductRequestBody productRequestBody) {
        Product productToUpdate = productRepository.getReferenceById(id);
        if (productRequestBody.getName() != null) {
            productToUpdate.setName(productRequestBody.getName());
        }
        if (productRequestBody.getPrice() != null) {
            productToUpdate.setPrice(productRequestBody.getPrice());
        }
        return productRepository.save(productToUpdate);
    }
    
    @Override
    public Product getOrCreateProduct(Product product) {
        Product productInDB = productRepository.findByReference(product.getReference());
        if (productInDB == null) {
            productInDB = productRepository.save(product);
        }
        return productInDB;
    }
}
