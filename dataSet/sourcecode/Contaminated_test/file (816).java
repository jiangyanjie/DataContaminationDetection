package com.agileapes.tools.jstrings.reader.impl;

import com.agileapes.tools.jstrings.error.ScannerException;
import com.agileapes.tools.jstrings.error.ScannerReadException;
import com.agileapes.tools.jstrings.scan.DocumentReader;
import com.agileapes.tools.jstrings.token.Token;
import com.agileapes.tools.jstrings.token.TokenFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/5/5, 2:57)
 */
public class DelimitedTokenReader<T extends Token> extends AbstractFactoryTokenReader<T> {

    private final List<String> delimiters;

    public DelimitedTokenReader(TokenFactory<T> tokenFactory, List<String> delimiters) {
        super(tokenFactory);
        this.delimiters = delimiters;
        Collections.sort(delimiters, new Comparator<String>() {
            @Override
            public int compare(String first, String second) {
                return ((Integer) first.length()).compareTo(second.length());
            }
        });
    }

    public DelimitedTokenReader(TokenFactory<T> tokenFactory, String ... delimiters) {
        this(tokenFactory, Arrays.asList(delimiters));
    }

    @Override
    public Token read(DocumentReader scanner) throws ScannerReadException {
        String buffer = "";
        while (scanner.hasMore()) {
            if (scanner.hasMore()) {
                buffer += scanner.next();
            }
            for (String delimiter : delimiters) {
                while (buffer.length() < delimiter.length()) {
                    if (!scanner.hasMore()) {
                        return getTokenFactory().getToken(buffer, scanner.getCursor() - buffer.length());
                    }
                    buffer += scanner.next();
                }
                if (buffer.endsWith(delimiter)) {
                    buffer = buffer.substring(0, buffer.length() - delimiter.length());
                    try {
                        scanner.rewind(delimiter.length());
                    } catch (ScannerException e) {
                        throw new ScannerReadException("Could not reset cursor for delimiter " + delimiter);
                    }
                    return getTokenFactory().getToken(buffer, scanner.getCursor() - buffer.length());
                }
            }
        }
        return getTokenFactory().getToken(buffer, scanner.getCursor() - buffer.length());
    }

}
