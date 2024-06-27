package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.entities.OrderLine;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.mappers.ProductsDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import com.acme.api.repositories.ProductRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.acme.api.entities.Product.generateReference;

@Service
public class ProductService implements ProductInterface{

    private final ProductRepository productRepository;
    private final ProductsDTOMapper productsDTOMapper;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinesDTOMapper orderLinesDTOMapper;

    public ProductService(ProductRepository productRepository, OrderLineRepository orderLineRepository, OrderLinesDTOMapper orderLinesDTOMapper) {
        this.productRepository = productRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderLinesDTOMapper = orderLinesDTOMapper;
        this.productsDTOMapper = new ProductsDTOMapper();
    }

    @Override
    public Stream<ProductDTO> getProducts() {
        return productRepository.findAll()
                .stream().map(productsDTOMapper);
    }

    @Override
    public Stream<ProductDTO> getProductsByName(String name) {
        Set<Product> productInDB = productRepository.findByNameContaining(name);
        if (productInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        }
         return productInDB.stream().map(productsDTOMapper);
    }

    @Override
    public Product getProductEntity(String reference) throws ResponseStatusException {
        Optional<Product> productInDB = productRepository.findByReference(reference);
        if (productInDB.isPresent()) {
            Product product = productInDB.get();
        return product;
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
    }

    @Override
    public ProductDTO getProductByReference(String reference) throws ResponseStatusException {
        Optional<Product> productInDB = productRepository.findByReference(reference);
        if (productInDB.isPresent()) {
            Product product = productInDB.get();
            return new ProductDTO(product.getReference(), product.getName(), product.getPrice());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
    }

    @Override
    public ProductDTO createProduct(ProductRequestBody productRequestBody) {
        Product product = new Product();
        product.setReference(generateReference());
        product.setName(productRequestBody.getName());
        product.setPrice(productRequestBody.getPrice());
        productRepository.save(product);
        return new ProductDTO(product.getReference(), product.getName(), product.getPrice());
    }

    @Override
    public ProductDTO updateProduct(String reference, ProductRequestBody productRequestBody) throws ResponseStatusException {
        Optional<Product> productToUpdate = productRepository.findByReference(reference);
        if (productToUpdate.isPresent()) {
            Product product = productToUpdate.get();
            if (productRequestBody.getName() != null) {
                product.setName(productRequestBody.getName());
            }
            if (productRequestBody.getPrice() != null) {
                product.setPrice(productRequestBody.getPrice());
            }
            productRepository.save(product);
            return new ProductDTO(reference, product.getName(), product.getPrice());
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
    }

    @Override
    public String deleteProduct(String reference) throws ResponseStatusException {
        Optional<Product> productToDelete = productRepository.findByReference(reference);
        if (productToDelete.isPresent()) {
            Product product = productToDelete.get();
            productRepository.delete(product);
            return product.getReference();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Produit non référencé.");
        }
    }

    @Override
    public Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference) {
        Set<OrderLine> orderLineInDB = orderLineRepository.findAllByIdProduct_Reference(productReference);
        if (orderLineInDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Aucune occurence.");
        }
        return orderLineInDB.stream().map(orderLinesDTOMapper);
    }

    // Tools

//    @Override
//    public Product getOrCreateProduct(Product product) {
//        Product productInDB = productRepository.findByReference(product.getReference());
//        if (productInDB == null) {
//            productInDB = productRepository.save(product);
//        }
//        return productInDB;
//    }
}
