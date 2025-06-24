package com.acharluk.luk;

import com.acharluk.luk.block.*;
import com.acharluk.luk.block.Class;
import com.acharluk.luk.parser.ClassParser;
import com.acharluk.luk.parser.MethodParser;
import com.acharluk.luk.parser.Parser;
import com.acharluk.luk.parser.VariableParser;
import com.acharluk.luk.tokenizer.Tokenizer;

import java.util.ArrayList;

/**
 * Created by ACharLuk on 14/02/2015.
 */
public class DebugTest {

    private ArrayList<Class> classes;

    public DebugTest() {
        this.classes = new ArrayList<Class>();

        String code = "class Variables" + "\n" +
                "func main : () -> void" + "\n" +
                "var int testNumber = 12" + "\n" +
                "func printString : (string str) -> void";

        Parser<?>[] parsers = new Parser<?>[] { new ClassParser(), new MethodParser(), new VariableParser() };

        Class main = null;

        Block block = null;

        boolean success = false;

        for (String line : code.split("\n")) {
            success = false;
            line = line.trim();
            Tokenizer tokenizer = new Tokenizer(line);

            for (Parser<?> parser : parsers) {
                if (parser.shouldParse(line)) {
                    Block newBlock = parser.parse(block, tokenizer);

                    if (newBlock instanceof Class) {
                        classes.add((Class) newBlock);
                    } else if (newBlock instanceof Method) {
                        block.getBlockTree().get(0).addBlock(newBlock);
                    } else {
                        block.addBlock(newBlock);
                    }

                    block = newBlock;
                    success = true;
                    break;
                }
            }

            if (!success) {
                throw new IllegalArgumentException("Invalid line " + line);
            }
        }

        for (Class c : classes) {
            for (Block b : c.getSubBlocks()) {
                if (b instanceof Method) {
                    Method method = (Method) b;
                    if (method.getName().equals("main") && method.getType().equals("void") && method.getParameters().length == 0) {
                        main = c;
                    }
                }
            }
        }

        if (main == null) {
            throw new IllegalStateException("No main method.");
        }

        main.run();
    }

    public static void main(String[] args) {
        new DebugTest();
    }

}
