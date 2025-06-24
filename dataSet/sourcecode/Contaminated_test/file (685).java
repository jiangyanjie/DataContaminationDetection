package com.agileapes.tools.jstrings.scan.impl;

import com.agileapes.tools.jstrings.error.ScannerException;
import com.agileapes.tools.jstrings.error.ScannerReadException;
import com.agileapes.tools.jstrings.reader.TokenReader;
import com.agileapes.tools.jstrings.scan.DocumentReader;
import com.agileapes.tools.jstrings.token.Token;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.StringReader;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/5/5, 2:24)
 */
public class DefaultDocumentReaderTest {

    /**
     * We establish a string and read it. The read value must match that of the initial string.
     * @throws Exception
     */
    @Test
    public void testReadingContinuously() throws Exception {
        final String string = "this is a test";
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(string));
        String result = "";
        while (scanner.hasMore()) {
            result += scanner.next();
        }
        Assert.assertEquals(result, string);
    }

    /**
     * We will read a string through, and then rewind and read it backwards.
     * It must match without a problem, both ways.
     * @throws Exception
     */
    @Test
    public void testRewinding() throws Exception {
        @SuppressWarnings("SpellCheckingInspection")
        final String string = "abcdefghijklmnopqrstuvwxyz";
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(string));
        int position = 0;
        for (; position < string.length(); position ++) {
            Assert.assertEquals(scanner.next(), string.charAt(position));
        }
        Assert.assertFalse(scanner.hasMore());
        position --;
        for (; position >= 0; position --) {
            scanner.rewind(1);
            Assert.assertEquals(scanner.next(), string.charAt(position));
            scanner.rewind(1);
        }
    }

    /**
     * We will first read a string, rewind back to the beginning and then attempt to rewind one
     * more character, which is sure to result in an overflow exception.
     * @throws Exception
     */
    @Test(expectedExceptions = ScannerException.class, expectedExceptionsMessageRegExp = ".*overflow.*")
    public void testRewindOverflow() throws Exception {
        final String string = "hello";
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(string));
        for (int i = 0; i < string.length(); i ++) {
            scanner.next();
        }
        Assert.assertFalse(scanner.hasMore());
        scanner.rewind(string.length());
        Assert.assertTrue(scanner.hasMore());
        scanner.rewind(1);
    }

    /**
     * We will read and rewind once. Everything should be peachy. Then we will repeat the process;
     * however, this time we will first flush the buffer and then rewind. Surely an error must be raised.
     * @throws Exception
     */
    @Test(expectedExceptions = ScannerException.class, expectedExceptionsMessageRegExp = ".*overflow.*")
    public void testRewindAfterFlush() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader("hello"));
        final char first = scanner.next();
        scanner.rewind(1);
        Assert.assertEquals(scanner.next(), first);
        scanner.flush();
        scanner.rewind(1);
    }

    /**
     * This test shows that while a rewind after a single call to next is perfectly
     * valid in the same context, if taken into another context
     * will raise an exception.
     * @throws Exception
     */
    @Test(expectedExceptions = ScannerReadException.class)
    public void testBadlyBehavedReader() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader("hello world"));
        scanner.next();
        scanner.read(new TokenReader() {
            @Override
            public Token read(DocumentReader scanner) throws ScannerReadException {
                try {
                    scanner.rewind(1);
                } catch (ScannerException e) {
                    throw new ScannerReadException(e);
                }
                return null;
            }
        });
    }

    @Test(expectedExceptions = ScannerReadException.class, expectedExceptionsMessageRegExp = "No more .*")
    public void testWithNoMoreToGo() throws Exception {
        final DefaultDocumentReader scanner = new DefaultDocumentReader(new StringReader(""));
        scanner.next();
    }
}
