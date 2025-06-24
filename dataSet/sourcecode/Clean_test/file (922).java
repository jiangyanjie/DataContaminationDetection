// Generated from CSS.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSSParser}.
 */
public interface CSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSSParser#row_items}.
	 * @param ctx the parse tree
	 */
	void enterRow_items(@NotNull CSSParser.Row_itemsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#row_items}.
	 * @param ctx the parse tree
	 */
	void exitRow_items(@NotNull CSSParser.Row_itemsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#special}.
	 * @param ctx the parse tree
	 */
	void enterSpecial(@NotNull CSSParser.SpecialContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#special}.
	 * @param ctx the parse tree
	 */
	void exitSpecial(@NotNull CSSParser.SpecialContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#col}.
	 * @param ctx the parse tree
	 */
	void enterCol(@NotNull CSSParser.ColContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#col}.
	 * @param ctx the parse tree
	 */
	void exitCol(@NotNull CSSParser.ColContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#title_string}.
	 * @param ctx the parse tree
	 */
	void enterTitle_string(@NotNull CSSParser.Title_stringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#title_string}.
	 * @param ctx the parse tree
	 */
	void exitTitle_string(@NotNull CSSParser.Title_stringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(@NotNull CSSParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(@NotNull CSSParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#lang_string}.
	 * @param ctx the parse tree
	 */
	void enterLang_string(@NotNull CSSParser.Lang_stringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#lang_string}.
	 * @param ctx the parse tree
	 */
	void exitLang_string(@NotNull CSSParser.Lang_stringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(@NotNull CSSParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(@NotNull CSSParser.RowContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(@NotNull CSSParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(@NotNull CSSParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(@NotNull CSSParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(@NotNull CSSParser.TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#lang}.
	 * @param ctx the parse tree
	 */
	void enterLang(@NotNull CSSParser.LangContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#lang}.
	 * @param ctx the parse tree
	 */
	void exitLang(@NotNull CSSParser.LangContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(@NotNull CSSParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(@NotNull CSSParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#row_item}.
	 * @param ctx the parse tree
	 */
	void enterRow_item(@NotNull CSSParser.Row_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#row_item}.
	 * @param ctx the parse tree
	 */
	void exitRow_item(@NotNull CSSParser.Row_itemContext ctx);
}