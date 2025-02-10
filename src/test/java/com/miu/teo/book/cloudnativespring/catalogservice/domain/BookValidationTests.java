package com.miu.teo.book.cloudnativespring.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenAllFieldsAreValid() {
        var book = new Book("1234567890","title","author",1.0);
        Set<ConstraintViolation<Book>> violationSet = validator.validate(book);
        assertThat(violationSet).isEmpty();
    }

    @Test
    void whenISBNisInvalid() {
        var book = new Book("123456789a","title","author",1.0);
        Set<ConstraintViolation<Book>> violationSet = validator.validate(book);
        assertThat(violationSet).hasSize(1);
        assertThat(violationSet.iterator().next().getMessage()).contains("The ISBN format must be valid.");
    }

}