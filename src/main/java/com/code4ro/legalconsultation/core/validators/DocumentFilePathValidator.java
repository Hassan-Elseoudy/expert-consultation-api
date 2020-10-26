package com.code4ro.legalconsultation.core.validators;

import com.code4ro.legalconsultation.core.model.dto.validator.UniqueDocumentFilePath;
import com.code4ro.legalconsultation.document.metadata.repository.DocumentMetadataRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class DocumentFilePathValidator implements ConstraintValidator<UniqueDocumentFilePath, String> {
    private final DocumentMetadataRepository documentMetadataRepository;

    @Override
    public boolean isValid(final String filePath, final ConstraintValidatorContext context) {
        return !documentMetadataRepository.existsByFilePath(filePath);
    }
}
