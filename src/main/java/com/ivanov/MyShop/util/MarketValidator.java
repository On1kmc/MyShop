package com.ivanov.MyShop.util;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.services.PeopleServiceForValidation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MarketValidator implements Validator {

    private final PeopleServiceForValidation service;

    public MarketValidator(PeopleServiceForValidation service) {
        this.service = service;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Market.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Market  market = (Market) target;

        if (service.isExist(market.getEmail())) errors.rejectValue("email", "", "Такой E-Mail уже зарегистрирован");
    }
}
