package com.ivanov.MyShop.util;

import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.services.PeopleServiceForValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleServiceForValidation service;

    @Autowired
    public PersonValidator(PeopleServiceForValidation service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (service.isExist(person.getEmail())) errors.rejectValue("email", "", "Такой E-Mail уже зарегистрирован");


    }
}
