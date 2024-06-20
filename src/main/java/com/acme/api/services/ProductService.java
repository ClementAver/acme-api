package com.acme.api.services;

import com.acme.api.dto.GetProductDTO;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.mapper.GetAllProductsDTOMapper;
import com.acme.api.repositories.ProductRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
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
    public Stream<GetProductDTO> getProducts() {
        return productRepository.findAll()
                .stream().map(getAllProductsDTOMapper);
    }

    @Override
    public Stream<GetProductDTO> getProductsByName(String name) {
        Set<Product> productInDB = productRepository.findAllByName(name);
        if (productInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        }
         return productInDB.stream().map(getAllProductsDTOMapper);
    }

    @Override
    public Product getProductEntity(String reference) throws ResponseStatusException {
        Product productInDB = productRepository.findByReference(reference);
        if (productInDB == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
        return productInDB;
    }

    @Override
    public GetProductDTO getProductByReference(String reference) throws ResponseStatusException {
        Product productInDB = productRepository.findByReference(reference);
        if (productInDB == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
        return new GetProductDTO(productInDB.getReference(), productInDB.getName(), productInDB.getPrice());
    }

    @Override
    public void createProduct(ProductRequestBody productRequestBody) {
        Product product = new Product();
        product.setReference(generateReference());
        product.setName(productRequestBody.getName());
        product.setPrice(productRequestBody.getPrice());
        productRepository.save(product);
    }

    @Override
    public void updateProduct(String reference, ProductRequestBody productRequestBody) throws ResponseStatusException {
        Product productToUpdate = productRepository.findByReference(reference);
        if (productToUpdate == null) {

            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
        if (productRequestBody.getName() != null) {
            productToUpdate.setName(productRequestBody.getName());
        }
        if (productRequestBody.getPrice() != null) {
            productToUpdate.setPrice(productRequestBody.getPrice());
        }
        productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(String reference) throws ResponseStatusException {
        Product productToDelete = productRepository.findByReference(reference);
        if (productToDelete != null) {
            productRepository.delete(productToDelete);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
    }

    // Tools

    @Override
    public Product getOrCreateProduct(Product product) {
        Product productInDB = productRepository.findByReference(product.getReference());
        if (productInDB == null) {
            productInDB = productRepository.save(product);
        }
        return productInDB;
    }

}
