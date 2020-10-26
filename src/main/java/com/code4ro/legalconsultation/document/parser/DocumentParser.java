package com.code4ro.legalconsultation.document.parser;

import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNode;
import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNodeType;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DocumentParser extends DocumentNodeParser {
    private static final String TOKEN_WITHOUT_TITLE_REGEX = "(?!.*).";

    @Override
    protected String getTitle(final Matcher matcher) {
        return null;
    }

    @Override
    protected String getIdentifier(final Matcher matcher) {
        return null;
    }

    @Override
    protected StartToken getCurrentToken() {
        return StartToken.DOCUMENT;
    }

    @Override
    protected DocumentNodeType getNodeType() {
        return DocumentNodeType.DOCUMENT;
    }

    @Override
    protected Pattern getPatternWithoutTitle() {
        return Pattern.compile(TOKEN_WITHOUT_TITLE_REGEX);
    }

    @Override
    protected void setTitle(final DocumentNode documentNode, final String title, final Matcher titleMatcher) {
        documentNode.setTitle(title);
    }
}
