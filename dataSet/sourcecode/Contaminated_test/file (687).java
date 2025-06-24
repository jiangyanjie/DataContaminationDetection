package com.agileapes.tools.jstrings.scan.impl;

import com.agileapes.tools.jstrings.reader.DelegatedReader;
import com.agileapes.tools.jstrings.token.impl.PatternTagDeterminer;
import com.agileapes.tools.jstrings.token.impl.TaggedToken;
import com.agileapes.tools.jstrings.token.impl.TaggedTokenFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.StringReader;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/5/5, 9:14)
 */
public class DefaultDocumentScannerTest {

    private static final int T_NUMBER = 1;
    private static final int T_ID = 2;
    private static final int T_BRACES = 3;
    private static final int T_PUNCTUATION = 4;
    private static final Pattern P_NUMBER = Pattern.compile("\\d+(\\.\\d*)?");
    private static final Pattern P_ID = Pattern.compile("[a-z_][\\da-z_]*", Pattern.CASE_INSENSITIVE);
    private DefaultDocumentScanner<TaggedToken> scanner;
    private DelegatedReader reader = new DelegatedReader();

    @BeforeMethod
    public void setUp() throws Exception {
        final PatternTagDeterminer number = new PatternTagDeterminer(T_NUMBER, P_NUMBER);
        final PatternTagDeterminer id = new PatternTagDeterminer(T_ID, P_ID);
        final PatternTagDeterminer braces = new PatternTagDeterminer(T_BRACES, Pattern.compile("(\\(|\\))"));
        final PatternTagDeterminer punctuation = new PatternTagDeterminer(T_PUNCTUATION, Pattern.compile(";"));
        scanner = new DefaultDocumentScanner<TaggedToken>(reader, new TaggedTokenFactory(number, id, braces, punctuation));
    }

    @Test
    public void testRead() throws Exception {
        reader.setReader(new StringReader("setLongitude(123.45);"));
        final TaggedToken id = ((TaggedToken) scanner.read(P_ID));
        Assert.assertNotNull(id);
        Assert.assertEquals(id.getValue(), "setLongitude");
        Assert.assertEquals(id.getTag(), (Integer) T_ID);
        final TaggedToken open = (TaggedToken) scanner.expect(Pattern.compile("\\("));
        Assert.assertNotNull(open);
        Assert.assertEquals(open.getValue(), "(");
        Assert.assertEquals(open.getTag(), (Integer) T_BRACES);
        final TaggedToken number = (TaggedToken) scanner.read(P_NUMBER);
        Assert.assertNotNull(number);
        Assert.assertEquals(number.getValue(), "123.45");
        Assert.assertEquals(number.getTag(), (Integer) T_NUMBER);
        final TaggedToken close = (TaggedToken) scanner.expect(Pattern.compile("\\)"));
        Assert.assertNotNull(close);
        Assert.assertEquals(close.getValue(), ")");
        Assert.assertEquals(close.getTag(), (Integer) T_BRACES);
        final TaggedToken ending = (TaggedToken) scanner.expect(Pattern.compile(";"));
        Assert.assertNotNull(ending);
        Assert.assertEquals(ending.getValue(), ";");
        Assert.assertEquals(ending.getTag(), (Integer) T_PUNCTUATION);
    }

}
