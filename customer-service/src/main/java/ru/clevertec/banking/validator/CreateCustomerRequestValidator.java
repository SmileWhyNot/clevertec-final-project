package ru.clevertec.banking.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.banking.dto.request.CreateCustomerRequest;
import ru.clevertec.banking.entity.CustomerType;
import ru.clevertec.banking.service.CustomerService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateCustomerRequestValidator implements ConstraintValidator<CreateCustomerProfileValidation, CreateCustomerRequest> {

    private final CustomerService customerService;

    @Override
    public void initialize(CreateCustomerProfileValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateCustomerRequest value, ConstraintValidatorContext context) {

        if ((value.getUnp() == null && customerService.isCustomerExist(value.getId(), value.getEmail()))
            || (value.getUnp() != null && customerService.isCustomerExist(value.getId(), value.getEmail(), value.getUnp()))) {
            addValidationMessage(context, "Customer with this email, UUID, or UNP already exists");
            return false;
        }

        if (Objects.equals(CustomerType.LEGAL.toString(), value.getCustomerType()) && (value.getUnp() == null || value.getUnp().isBlank())) {
            addValidationMessage(context, "Legal Customer must have UNP");
            return false;
        }

        return true;
    }

    private void addValidationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}
