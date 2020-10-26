package com.code4ro.legalconsultation.core.validators;

import com.code4ro.legalconsultation.core.model.dto.validator.UniqueUserEmailConstraint;
import com.code4ro.legalconsultation.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UserEmailValidator implements ConstraintValidator<UniqueUserEmailConstraint, String> {
    private final UserRepository userRepository;

    @Override
    public void initialize(final UniqueUserEmailConstraint constraintAnnotation) {
        throw new UnsupportedOperationException("Unsupported operation yet!");
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
