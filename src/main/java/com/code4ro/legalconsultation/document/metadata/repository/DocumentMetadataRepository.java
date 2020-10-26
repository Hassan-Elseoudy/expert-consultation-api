package com.code4ro.legalconsultation.document.metadata.repository;

import com.code4ro.legalconsultation.document.metadata.model.persistence.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, UUID> {
    Boolean existsByDocumentNumber(BigInteger documentNumber);
    Boolean existsByFilePath(String filePath);
}
