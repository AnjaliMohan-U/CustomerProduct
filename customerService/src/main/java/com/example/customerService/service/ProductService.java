package com.example.customerService.service;

import com.example.customerService.entity.PriceEntity;
import com.example.customerService.entity.ProductsEntity;
import com.example.customerService.entity.SkuEntity;
import com.example.customerService.model.PriceModel;
import com.example.customerService.model.ProductModel;
import com.example.customerService.model.SkuModel;
import com.example.customerService.repository.ProductRepository;
import com.example.customerService.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SkuRepository skuRepository;

    public ProductsEntity addProducts(ProductModel productsModel){
        ProductsEntity productsEntity = new ProductsEntity();

        productsEntity.setProductCode(productsModel.getProductCode());
        productsEntity.setProductName(productsModel.getProductName());
        productsEntity.setDescription(productsModel.getDescription());

        return productRepository.save(productsEntity);
    }

    public ProductsEntity addSkus(String productCode, SkuModel skuModel){
        Optional<ProductsEntity> product = productRepository.findById(productCode);
        if(product.isPresent()){
            System.out.println("found the product:"+ skuModel.getSkuCode());
//            List<SkuEntity> sk = product.get().getSkuEntityList();
            List<SkuEntity> skuEntityList = new ArrayList<>();

            SkuEntity sku = new SkuEntity();
            sku.setSkuCode(skuModel.getSkuCode());
            sku.setSize(skuModel.getSize());
            sku.setProducts(product.get());

            skuEntityList.add(sku);
            product.get().setSkuEntityList(skuEntityList);
            return productRepository.save(product.get());
        }
        return null;
    }

    public SkuEntity addPrice(String skuCode, PriceModel priceModel){
        Optional<SkuEntity> sku = skuRepository.findById(skuCode);
        if(sku.isPresent()){
            System.out.println("found sku :"+ priceModel.getPrice());
            PriceEntity price = new PriceEntity();
            price.setPrice(priceModel.getPrice());
            price.setCurrency(priceModel.getCurrency());
            price.setSkuEntity(sku.get());
            sku.get().setPriceEntity(price);
            return skuRepository.save(sku.get());
        }
        return null;
    }

    private ProductModel getProductModel(ProductsEntity productsEntity){
        ProductModel productsModel = new ProductModel();
        List<SkuModel> skuModelList = new ArrayList<>();
        productsEntity.getSkuEntityList().forEach(x->{
            SkuModel skuModel = new SkuModel();
            skuModel.setSkuCode(x.getSkuCode());
            skuModel.setSize(x.getSize());
            PriceModel priceModel = new PriceModel();
            priceModel.setPrice(x.getPriceEntity().getPrice());
            priceModel.setCurrency(x.getPriceEntity().getCurrency());
            skuModel.setPriceModel(priceModel);
        });
        productsModel.setProductCode(productsEntity.getProductCode());
        productsModel.setProductName(productsEntity.getProductName());
        productsModel.setDescription(productsEntity.getDescription());
        productsModel.setSkuModelList(skuModelList);
        return productsModel;
    }
    public List<ProductsEntity> getAllProducts(){
        return productRepository.findAll();
    }
}
