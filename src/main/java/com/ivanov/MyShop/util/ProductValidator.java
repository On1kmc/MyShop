package com.ivanov.MyShop.util;

import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.services.ProductServiceForValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {

    private final ProductServiceForValidation serviceForValidation;

    @Autowired
    public ProductValidator(ProductServiceForValidation serviceForValidation) {
        this.serviceForValidation = serviceForValidation;
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (serviceForValidation.isExist(product.getArticle()))
            errors.rejectValue("article", "", "Товар с таким артикулом уже существует");
    }
}
