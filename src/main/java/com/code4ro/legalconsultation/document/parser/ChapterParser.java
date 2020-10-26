package com.code4ro.legalconsultation.document.parser;

import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNodeType;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChapterParser extends DocumentNodeParser {
    private static final String TOKEN_WITHOUT_TITLE_REGEX = "(CAPITOLUL) ([^:]*?)$";

    @Override
    protected String getTitle(final Matcher matcher) {
        return matcher.group(3);
    }

    @Override
    protected String getIdentifier(final Matcher matcher) {
        return matcher.group(2);
    }

    @Override
    protected StartToken getCurrentToken() {
        return StartToken.CHAPTER;
    }

    @Override
    protected DocumentNodeType getNodeType() {
        return DocumentNodeType.CHAPTER;
    }

    @Override
    protected Pattern getPatternWithoutTitle() {
        return Pattern.compile(TOKEN_WITHOUT_TITLE_REGEX);
    }
}
