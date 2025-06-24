package com.agileapes.tools.jstrings.scan.impl;

import com.agileapes.tools.jstrings.error.MissingExpectedTokenException;
import com.agileapes.tools.jstrings.error.ScannerException;
import com.agileapes.tools.jstrings.reader.impl.DelimitedTokenReader;
import com.agileapes.tools.jstrings.reader.impl.RegExpTokenReader;
import com.agileapes.tools.jstrings.scan.DocumentScanner;
import com.agileapes.tools.jstrings.scan.SnippetParser;
import com.agileapes.tools.jstrings.token.Token;
import com.agileapes.tools.jstrings.token.TokenFactory;

import java.io.Reader;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/5/5, 8:37)
 */
public class DefaultDocumentScanner<T extends Token> implements DocumentScanner<T> {

    private final DefaultDocumentReader reader;
    private final TokenFactory<T> tokenFactory;

    public DefaultDocumentScanner(Reader reader, TokenFactory<T> tokenFactory) {
        this.tokenFactory = tokenFactory;
        this.reader = new DefaultDocumentReader(reader, Integer.MAX_VALUE, 1024);
    }

    @Override
    public char peek() throws ScannerException {
        final char next = reader.next();
        reader.rewind(1);
        return next;
    }

    @Override
    public String peek(int length) throws ScannerException {
        String peek = read(length);
        reader.rewind(length);
        return peek;
    }

    @Override
    public char read() throws ScannerException {
        return reader.next();
    }

    @Override
    public String read(int length) throws ScannerException {
        String result;
        result = "";
        while (result.length() < length) {
            result += read();
        }
        return result;
    }

    @Override
    public Token read(String pattern) throws ScannerException {
        return reader.read(new RegExpTokenReader<T>(tokenFactory, pattern));
    }

    @Override
    public Token read(Pattern pattern) throws ScannerException {
        return reader.read(new RegExpTokenReader<T>(tokenFactory, pattern));
    }

    @Override
    public Token readUntil(String... delimiters) throws ScannerException {
        return reader.read(new DelimitedTokenReader<T>(tokenFactory, delimiters));
    }

    @Override
    public char expect(char... characters) throws ScannerException {
        final char next = read();
        for (char character : characters) {
            if (character == next) {
                return next;
            }
        }
        throw new MissingExpectedTokenException(Arrays.toString(characters));
    }

    @Override
    public String expect(String... tokens) throws ScannerException {
        final String token = has(tokens);
        if (token != null) {
            return token;
        }
        throw new MissingExpectedTokenException(Arrays.toString(tokens));
    }

    @Override
    public Token expect(Pattern... patterns) throws ScannerException {
        for (Pattern pattern : patterns) {
            final Token token = read(pattern);
            if (token != null) {
                return token;
            }
        }
        throw new MissingExpectedTokenException(Arrays.toString(patterns));
    }

    @Override
    public String has(String... tokens) throws ScannerException {
        int max = 0;
        for (String token : tokens) {
            if (max < token.length()) {
                max = token.length();
            }
        }
        final String next = read(max);
        for (String token : tokens) {
            if (next.startsWith(token)) {
                reader.rewind(next.length() - token.length());
                return token;
            }
        }
        return null;
    }

    @Override
    public <E> E parse(SnippetParser<E> parser) {
        reader.flush();
        final E result = parser.parse(this);
        reader.flush();
        return result;
    }

}
