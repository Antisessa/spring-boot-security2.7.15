package ru.antisessa.SecurityAppSpring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.antisessa.SecurityAppSpring.models.Person;
import ru.antisessa.SecurityAppSpring.services.PersonDetailsService;
import ru.antisessa.SecurityAppSpring.services.PersonService;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PersonService personService) {
        this.personDetailsService = personDetailsService;
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if(personService.findByUsername(person.getUsername()).isPresent())
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");

//        try {
//            personDetailsService.loadUserByUsername(person.getUsername());
//        } catch (UsernameNotFoundException ignored) {
//            return; // все ок, пользователь не найден
//        }
//
//        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
