package com.code4ro.legalconsultation.document.node.model.dto;

import com.code4ro.legalconsultation.core.model.dto.BaseEntityDto;
import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNodeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentNodeSimpleDto extends BaseEntityDto {
    private UUID parentId;
    private DocumentNodeType documentNodeType;
    @Size(max = 255)
    private String title;
    private String content;
    @Size(max = 255)
    private String identifier;
}
