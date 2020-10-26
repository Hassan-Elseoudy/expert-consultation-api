package com.code4ro.legalconsultation.document.consolidated.mapper;

import com.code4ro.legalconsultation.document.consolidated.model.dto.DocumentConsolidatedDto;
import com.code4ro.legalconsultation.document.consolidated.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.document.metadata.mapper.DocumentMetadataMapper;
import com.code4ro.legalconsultation.document.node.mapper.DocumentNodeMapper;
import com.code4ro.legalconsultation.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DocumentMetadataMapper.class, DocumentNodeMapper.class, UserMapper.class})
public interface DocumentConsolidatedMapper {

    DocumentConsolidatedDto map(DocumentConsolidated documentConsolidated);

}
