package com.projects.arch_ref.interfaces.http.inbound.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<ValidEnum, Enum> {
    private static final String MESSAGE_TEMPLATE = "%s: received invalid value. accepted values: %s";

    private final List<String> validValues = new ArrayList<>();
    private String enumName;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumName = annotation.enumType().getSimpleName();
        Enum<?>[] enumConstants = annotation.enumType().getEnumConstants();
        for (Enum<?> enumVal : enumConstants) {
            validValues.add(enumVal.name());
        }
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        this.createConstraintViolation(context);
        return value != null && this.validValues.contains(value.name());
    }

    private void createConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format(MESSAGE_TEMPLATE, this.enumName, this.validValues))
                .addConstraintViolation();
    }
}
