package com.code4ro.legalconsultation.core.validators;

import com.code4ro.legalconsultation.core.model.dto.validator.UniqueDocumentNumberConstraint;
import com.code4ro.legalconsultation.document.metadata.repository.DocumentMetadataRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

@AllArgsConstructor
public class DocumentNumberValidation implements ConstraintValidator<UniqueDocumentNumberConstraint, BigInteger> {
    private final DocumentMetadataRepository documentMetadataRepository;

    @Override
    public boolean isValid(final BigInteger documentNumber, final ConstraintValidatorContext context) {
        return !documentMetadataRepository.existsByDocumentNumber(documentNumber);
    }
}
