import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class CSSFactory {

	public static int depth = 0;
	public static boolean startbody = true;
	public static String Title = "Default Title";
	public static String Lang = "en";

	public static void createCSS(InputStream inputStream) throws IOException {
		CSSLexer l = new CSSLexer(new ANTLRInputStream(inputStream));
		CSSParser p = new CSSParser(new CommonTokenStream(l));
		p.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer,
					Object offendingSymbol, int line, int charPositionInLine,
					String msg, RecognitionException e) {
				throw new IllegalStateException("failed to parse at line "
						+ line + " due to " + msg, e);
			}
		});

		File file = new File("genfile.html");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Unable to create file:\n" + e.getMessage());
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			System.err.println("Unable to load FileWriter:\n" + e.getMessage());
		}
		final BufferedWriter bw = new BufferedWriter(fw);
		
		p.addParseListener(new CSSBaseListener() {

			@Override
			public void exitTitle_string(CSSParser.Title_stringContext ctx) {
				Title = ctx.getText();
			}
			
			@Override
			public void exitLang_string(CSSParser.Lang_stringContext ctx) {
				Lang = ctx.getText();
			}

			@Override
			public void enterInteger(CSSParser.IntegerContext ctx) {
				depth++;
			}

			@Override
			public void exitInteger(CSSParser.IntegerContext ctx) {
				try {
					printTab(bw);
					bw.write("<div class='col-md-" + ctx.getText()
							+ "'></div>\n");
					depth--;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void exitSpecial(CSSParser.SpecialContext ctx) {
				try {
					printTab(bw);
					bw.write("<div class='col-md-" + ctx.getText() + "'>\n");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void enterSpecial(CSSParser.SpecialContext ctx) {
				depth++;
			}

			@Override
			public void exitRow(CSSParser.RowContext ctx) {
				try {					
					printTab(bw);
					bw.write("</div>\n");
					depth--;
					printTab(bw);
					bw.write("</div>\n");
					depth--;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void enterRow(CSSParser.RowContext ctx) {
				depth++;
				try {
					printTab(bw);
					bw.write("<div class='row'>\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void enterCol(CSSParser.ColContext ctx) {
				depth++;
				if(startbody) {
					try {
						bw.write("<!DOCTYPE html>\n");
						bw.write("<html lang=\"" + Lang + "\">\n");
						bw.write("<head>\n");
						bw.write("\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
						bw.write("\t<meta charset=\"utf-8\">\n");
						bw.write("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
						bw.write("\t<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
						bw.write("\t<link href=\"/favicon.ico\" rel=\"icon\">\n");
						bw.write("\t<title>" + Title + "</title>\n");
						bw.write("</head>\n");
						bw.write("<body>\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startbody = false;
				}
				try {
					printTab(bw);
					bw.write("<div>\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		p.prog();
		
		bw.write("\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>\n");
		bw.write("\t<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js\"></script>");
		bw.write("</body>\n");
		bw.write("</html>\n");
		bw.close();
		
		System.out.println("genfile.html generated");
	}

	public static void printTab(BufferedWriter bw) throws IOException {
		for (int i = 0; i < depth; i++) {
			bw.write("\t");
		}
	}
}
