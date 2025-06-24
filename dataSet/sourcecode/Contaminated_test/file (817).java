package com.agileapes.tools.jstrings.reader.impl;

import com.agileapes.tools.jstrings.scan.impl.DefaultDocumentReader;
import com.agileapes.tools.jstrings.token.impl.ValueToken;
import com.agileapes.tools.jstrings.token.impl.ValueTokenFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.StringReader;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/5/5, 3:10)
 */
public class DelimitedTokenReaderTest {

    @Test
    public void testReadingWithDelimiter() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader("hello[<world>]"));
        final DelimitedTokenReader<ValueToken> reader = new DelimitedTokenReader<ValueToken>(new ValueTokenFactory(), "[<", ">]");
        Assert.assertEquals(scanner.read(reader).getValue(), "hello");
        Assert.assertEquals(scanner.next(), '[');
        Assert.assertEquals(scanner.next(), '<');
        Assert.assertEquals(scanner.read(reader).getValue(), "world");
        Assert.assertEquals(scanner.next(), '>');
        Assert.assertEquals(scanner.next(), ']');
        Assert.assertFalse(scanner.hasMore());
    }

    @Test
    public void testReadingWithLessTextThanDelimiterLength() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader("hell"));
        final DelimitedTokenReader<ValueToken> reader = new DelimitedTokenReader<ValueToken>(new ValueTokenFactory(), "<!---");
        Assert.assertEquals(scanner.read(reader).getValue(), "hell");
        Assert.assertFalse(scanner.hasMore());
    }

    @Test
    public void testReadingWithoutDelimiters() throws Exception {
        final String expected = "hello world";
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(expected));
        final DelimitedTokenReader<ValueToken> reader = new DelimitedTokenReader<ValueToken>(new ValueTokenFactory(), "[", "]");
        Assert.assertEquals(scanner.read(reader).getValue(), expected);
        Assert.assertFalse(scanner.hasMore());
    }

}
