package com.rueloparente.menu_service.domain;

import com.rueloparente.menu_service.ApplicationProperties;
import com.rueloparente.menu_service.dto.PagedResult;
import com.rueloparente.menu_service.dto.ProductWeb;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private final ApplicationProperties properties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.properties = applicationProperties;
    }

    public PagedResult<ProductWeb> findAll(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pagSize(), sort);

        Page<ProductWeb> productsPage = productRepository.findAll(pageable).map(ProductMapper::toProductWeb);

        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }

    public Optional<ProductWeb> getProductByBode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProductWeb);
    }
}
