package com.code4ro.legalconsultation.pdf.mapper;

import com.code4ro.legalconsultation.pdf.model.dto.PdfHandleDto;
import com.code4ro.legalconsultation.pdf.model.persistence.PdfHandle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PdfHandleMapper {
    PdfHandle map(PdfHandleDto pdfHandleDto);

    PdfHandleDto map(PdfHandle pdfHandle);
}
