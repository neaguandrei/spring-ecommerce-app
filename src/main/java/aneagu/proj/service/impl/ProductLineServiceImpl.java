package aneagu.proj.service.impl;

import aneagu.proj.models.domain.ProductLine;
import aneagu.proj.models.dto.ProductLineDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.ProductLineRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.ProductLineService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductLineRepository productLineRepository;
    private final MapperService mapperService;

    public ProductLineServiceImpl(ProductLineRepository productLineRepository, MapperService mapperService) {
        this.productLineRepository = productLineRepository;
        this.mapperService = mapperService;
    }

    @Override
    public void delete(ProductLineDto object) throws NotFoundException {
        if (productLineRepository.findById(object.getId()).isPresent()) {
            throw new NotFoundException("Not found");
        }
        
        productLineRepository.delete(mapperService.convertProductLineDtoToProductLine(object));
    }

    @Override
    public void save(ProductLineDto object) {
        productLineRepository.save(mapperService.convertProductLineDtoToProductLine(object));
    }

    @Override
    public void update(Long id, ProductLineDto object) {
        if (object == null) {
            throw new IllegalArgumentException("ProductLineDto can't be null");
        }

        Optional<ProductLine> optionalProductLine = productLineRepository.findById(id);
        if (optionalProductLine.isPresent()) {
            ProductLine productLine = mapperService.convertProductLineDtoToProductLine(object);
            productLine.setId(id);
            productLineRepository.save(productLine);
        }
    }

    @Override
    public ProductLineDto get(Long id) throws NotFoundException {
        Optional<ProductLine> optionalProductLine = productLineRepository.findById(id);
        if (!optionalProductLine.isPresent()) {
            throw new NotFoundException("Product Line not found");
        }

        return mapperService.convertProductLineToProductLineDto(optionalProductLine.get());
    }

}
