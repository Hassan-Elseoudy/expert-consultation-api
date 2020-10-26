package com.code4ro.legalconsultation.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
class StringComparatorTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @CsvSource({"' test test1 test2     ','   test      test '", "' test test1 test2     ','   test      1 '", "test123,test",
            "' test     ','   test '", "test,test"})
    void shouldCheckTrueComparisonsContainsIgnoreWhitespace(String str1, String str2) {
        assertThat(StringComparator.containsIgnoreWhitespace(str1, str2)).isTrue();
    }


    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @CsvSource({"test,test1"})
    void shouldCheckFalseComparisonsContainsIgnoreWhitespace(String str1, String str2) {
        assertThat(StringComparator.containsIgnoreWhitespace(str1, str2)).isFalse();
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @CsvSource({"test,test", "' test     ','   test "})
    void shouldCheckTrueComparisonsAreEqualIgnoreCaseAndWhitespace(String str1, String str2) {
        assertThat(StringComparator.areEqualIgnoreCaseAndWhitespace(str1, str2)).isTrue();
    }


    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @CsvSource({"test1,test", "' test1     ','   test "})
    void shouldCheckFalseComparisonsAreEqualIgnoreCaseAndWhitespace(String str1, String str2) {
        assertThat(StringComparator.areEqualIgnoreCaseAndWhitespace(str1, str2)).isFalse();
    }


}
