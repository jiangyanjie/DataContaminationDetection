





// Generated from CSS.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;


import org.antlr.v4.runtime.TokenStream;



import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;






import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})



public class CSSLexer extends Lexer {







	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }







	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();



	public static final int
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 





		INT=10, STRING=11, WS=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};





	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'"




	};
	public static final String[] ruleNames = {













		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 

		"INT", "STRING", "WS"







	};






	public CSSLexer(CharStream input) {
		super(input);




		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}









	@Override
	public String getGrammarFileName() { return "CSS.g4"; }






	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }











	@Override





	public String getSerializedATN() { return _serializedATN; }









	@Override
	public String[] getModeNames() { return modeNames; }






	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16I\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\6\13:\n\13\r\13\16\13;\3\f\6\f?\n\f\r\f\16\f@\3\r\6\rD\n\r\r\r\16\rE"+


		"\3\r\3\r\2\2\16\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\3\2\5\3\2\62;\7\2\62;C\\^^aac|\5\2\13\f\17\17\"\"K\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33"+
		"\3\2\2\2\5\37\3\2\2\2\7$\3\2\2\2\t(\3\2\2\2\13.\3\2\2\2\r\60\3\2\2\2\17"+
		"\62\3\2\2\2\21\64\3\2\2\2\23\66\3\2\2\2\259\3\2\2\2\27>\3\2\2\2\31C\3"+




		"\2\2\2\33\34\7e\2\2\34\35\7u\2\2\35\36\7u\2\2\36\4\3\2\2\2\37 \7n\2\2"+
		" !\7c\2\2!\"\7p\2\2\"#\7i\2\2#\6\3\2\2\2$%\7e\2\2%&\7q\2\2&\'\7n\2\2\'"+

		"\b\3\2\2\2()\7v\2\2)*\7k\2\2*+\7v\2\2+,\7n\2\2,-\7g\2\2-\n\3\2\2\2./\7"+
		"]\2\2/\f\3\2\2\2\60\61\7}\2\2\61\16\3\2\2\2\62\63\7/\2\2\63\20\3\2\2\2"+
		"\64\65\7_\2\2\65\22\3\2\2\2\66\67\7\177\2\2\67\24\3\2\2\28:\t\2\2\298"+
		"\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\26\3\2\2\2=?\t\3\2\2>=\3\2\2\2"+
		"?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\30\3\2\2\2BD\t\4\2\2CB\3\2\2\2DE\3\2\2"+
		"\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\b\r\2\2H\32\3\2\2\2\6\2;@E\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());



	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}