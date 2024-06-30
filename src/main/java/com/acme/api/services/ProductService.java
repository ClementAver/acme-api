package com.acme.api.services;

import com.acme.api.dto.OrderLineDTO;
import com.acme.api.dto.ProductDTO;
import com.acme.api.entities.OrderLine;
import com.acme.api.entities.Product;
import com.acme.api.dto.ProductRequestBody;
import com.acme.api.exceptions.NoMatchException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.mappers.OrderLinesDTOMapper;
import com.acme.api.mappers.ProductsDTOMapper;
import com.acme.api.repositories.OrderLineRepository;
import com.acme.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;
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
    public Stream<ProductDTO> getProductsByName(String name) throws NoMatchException {
        Optional<Set<Product>> productsInDB = productRepository.findProductsByNameContainingIgnoreCase(name);
        if (productsInDB.isPresent() && !productsInDB.get().isEmpty()) {
            Set<Product> products = productsInDB.get();
         return products.stream().map(productsDTOMapper);
        }
            throw new NoMatchException("Aucune occurence.");
    }

    @Override
    public Product getProductEntity(String reference) throws NotFoundException {
        Optional<Product> productInDB = productRepository.findByReference(reference);
        if (productInDB.isPresent()) {
            return productInDB.get();
        } else {
            throw new NotFoundException("Produit non référencé.");
        }
    }

    @Override
    public ProductDTO getProductByReference(String reference) throws NotFoundException {
        Optional<Product> productInDB = productRepository.findByReference(reference);
        if (productInDB.isPresent()) {
            Product product = productInDB.get();
            return new ProductDTO(product.getReference(), product.getName(), product.getPrice());
        } else {
            throw new NotFoundException("Produit non référencé.");
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
    public ProductDTO updateProduct(String reference, ProductRequestBody productRequestBody) throws NotFoundException {
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
            throw new NotFoundException("Produit non référencé.");
        }
    }

    @Override
    public String deleteProduct(String reference) throws NotFoundException {
        Optional<Product> productToDelete = productRepository.findByReference(reference);
        if (productToDelete.isPresent()) {
            Product product = productToDelete.get();
            productRepository.delete(product);
            return product.getReference();
        } else {
            throw new NotFoundException("Produit non référencé.");
        }
    }

    @Override
    public Stream<OrderLineDTO> getOrderLinesFromProduct(String productReference) throws NoMatchException, NotFoundException {
       Optional<Product> productInDB = productRepository.findByReference(productReference);
       if (productInDB.isPresent()) {
           Product product = productInDB.get();
           Set<OrderLine> orderLineInDB = orderLineRepository.findAllByIdProduct_Reference(productReference);
           if (orderLineInDB.isEmpty()) {
               throw new NoMatchException("Aucune occurence.");
           }
           return orderLineInDB.stream().map(orderLinesDTOMapper);
       } else {
           throw new NotFoundException("Produit non référencé.");
       }
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
