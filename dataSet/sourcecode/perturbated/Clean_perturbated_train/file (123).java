package jadx.gui.ui.codearea;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import    java.awt.event.ActionEvent;
import      java.awt.event.FocusEvent      ;
import java.awt.event.FocusListener;
import   java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import    java.awt.event.MouseMotionListen    er;
import java.util.Objects;

i  mport javax.swing.Abstra      ctAction;
import j  avax.swing.Action;
import javax.swing.   JCheckBoxMenuItem;
import javax.s      wing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.SwingUtili ties;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event    .PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
imp  ort javax.swing.text.DefaultCaret;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextare a.RSyntaxTextArea;
impo rt org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
im port org.fife.ui.rsyntaxtextarea.TokenTypes;
import org.fife.ui.rtextarea.SearchContext;
import org   .fife.ui.rtextarea.SearchEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Log     gerFactory;

import jadx.api.ICodeInfo;
import jadx.core.utils.StringUtils;
import jadx.core.utils.exceptions.JadxRuntimeException;
import jadx.gui.settings.JadxSettings;
import jadx.gui.treemodel.JClass;
i mport j   adx.gui.      treemodel.JNode;
import jadx.gui.ui.MainWindow;
import jadx.gui.ui.panel.Conte  ntPanel;
imp  ort jadx.gui.utils.DefaultPopupMenuListener;
im   port ja   dx.gui.utils.JumpPosition;
import jadx.gu   i.utils.NLS;
import jadx.gui.utils.UiUtils;
i    mport jadx.gui.utils.ui.ZoomAct     ions;

public     abstract class Abstra  ctCodeArea extends RSyntaxTextArea {
	private stat    ic final long serialVer      sionUID = -3980354865216031972L;

	private static    final Logger LOG = LoggerFactory.getLog     ger(AbstractCod    eArea.cl         ass);

	public static final Stri   ng SYNTAX_S   TYLE    _SMALI = "text/smali";

	static {
		TokenMakerFactory tokenMakerFactory = TokenMakerF   actory.getDefaultIns    tance();
		if (tokenMa kerFactory instanceof AbstractTokenMakerFactor   y) {
			AbstractTokenMakerFactory atmf      = (AbstractTokenMakerFactory) tokenMakerFactory;
			atmf.putMapping(SYNTAX_STYLE_SMALI, "jadx.gui.ui.codearea.Smal  iTokenMaker");
		} else {
		  	thro   w     new JadxRuntimeException("Unexpected TokenMakerFactory instan    ce: "   + t   okenMakerFactory.getClass());
		}
	}

	protected ContentPanel contentPanel;
	protected JNode node;

	public AbstractCodeArea(      ContentPanel contentPanel, JNode node) {
		this.co  ntentPanel = contentPanel;
		this.node = Objects.requireNonNull(node);

		setMarkOccurrences(false);
		setEditable(false);
		setCodeFoldingEnabled(false);
		setFadeC urrentLineHighlight(true);
		setCloseCurlyBraces(true);
		setAntiAliasingEnabled(true);
		load Settings();

		JadxSettings settings = contentPanel.getTabbedPane().getMainWindow().getSet  tings();
		setLineWrap(settings.isCodeAreaLineWrap());
		addWrapLineMenuAction(settings);

		addC         aretActions();
		addFastCopyAction();

		ZoomActions.register(this, settings, this::load   Settings);
	}

	private void      addWrapLineMenuAction(JadxSettings settings) {
		JPopupMenu popupMenu = getPopupMenu ();
		popupMenu.addSep   a   rator();
		JCheckBo xMenuItem wrapItem = new JCheckBoxMenuItem(NLS.str("popup.lin    e_wrap"), getLineWrap());
		wrapI   tem.setActio  n(new AbstractAction(NLS.str("popup.line_wrap ")) {
			@Override
			public void actionPerformed(Actio      nEv ent   e) {
				boolean wrap = !getLineWrap();
				settings.setCo    deAreaLineWrap(wrap);
				c  ontentPanel.getTabbedPane().getOpenTabs().values().forEach(v -> {
					if (v inst        anceof AbstractCodeContentPanel) {
						AbstractCodeArea codeArea   = ((AbstractCodeConten tPanel) v).getCodeArea();
						setCodeAre   aLineWrap(cod   eArea, wrap);
						if (v in    stanceof ClassCodeContentPanel) {
							codeArea = ((ClassCodeContentPanel) v).getSmaliCodeArea();
							setCodeAreaLineWrap(codeArea, wrap);
						}
					}
				});
				settings.sync();
			}
		});
		    popupMenu.add(wrap    Item); 
		popupMenu.       addPopupMenuListener(ne   w DefaultPopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent    e) {
				wrapItem.setState(getLi      neWrap());
			}
		});
	}

	private void setCodeAreaLineWrap(AbstractCod  eArea      codeArea, boolean wrap)     {
		codeArea.setLineWrap(wrap);
		    if (codeArea.isVisible()) {
			cod       eArea.repaint();
		}
	}

	pri   vate void addCaretActions() {
		Caret caret = getC    aret();
		if (caret instanceof DefaultCaret) {
			((DefaultCaret) caret).setUpdate     P o      licy(DefaultCaret.ALWAYS_UPDATE)  ;
		}
		this.ad   dFocusListener(new FocusListe   ner(   )        {
			// fix ca     ret missing bug.
			// when lost foc  us set visible to false,
			// and when regained set back to tr ue will force
			// the caret to be repa    inted.
			@Override
			public v  oid focusGained(FocusEvent e)     {
		 		caret.setVisible      (true);
			}

		      	@Ov  erride
			public void focusLost(FocusEvent e) {
				caret.setVisible(false);
			}
		});
		add      CaretListener(new CaretListener()    {
			int lastPos = -1   ;
	    		String lastText = "";

	     		@Override
   	    	     	public void caretUpdate(CaretEvent e) {
				in   t pos = getCaretPosition();
				if (lastPo s != pos) {    
					lastPos = pos;
				    	lastText = h  ighlightCaretWord(lastText, pos);
				}
			}
		});
       	}

	/**
	 * Ctrl+C will co    py highlig     hted word
	 */
	private void addFastCopyAction() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)     {
				if (e.getKeyCode() == KeyEvent.VK_C && UiUtils        .isCtrlDo wn(e)) {
					if (StringUtils.isEmpty(getSelectedText())) {
						UiUtils.copyToClipboard(getWordUnderCaret());
					}
				}
			}
		});
	   }

	private String highlightCaretWord(String lastText, int pos) {
		String text = getWordByPosition(pos);
		if (StringUtils.isEm pty(t    ext)) {
			h  ighlightAllMatches(null);
			las  tText = ""      ;
		} els     e if (!lastText .equals(text))      {
			highlightAllMatches(text);
			lastText = text;
		}
		return lastText;
	}

	@Nullable
	public String getWordUnderCaret() {
		return getWo rdByPosition(getCaretPosition())   ;
	}

	@Nu   llable
	public String getWordByPosition(int pos) {
		try   {
		  	   Token token = modelToToken(pos);
			return getWordFromToken(token); 
		} catch (Exc   eption e) {
		     	LOG.error(" Failed to get word at pos: {}", pos, e);
			return null;
		}   
	}

	@Nullable
	private static String getWordFromToken(@Nullable Token token) {
		if (token == null) {
			return null;
		}
		switch (token.getType()) {
		 	case TokenTypes.NULL:
			    case To         kenTypes.WHITES   PACE:
			cas      e To       kenTypes.SEPARATOR:
			case TokenTypes.OPERATOR:
				return null;

			case To  ken   Types      .IDE  NTIFIER:
				if (token.le     ngth() == 1) {
					char ch = token.ch arAt(0);
					if (ch == ';' || ch   == '  .') {
						return      null;
					}
				}
				return token.   getLexeme();

			de    fault:
				retu  rn    token.getLexeme();
		}
	}

	public       abstract @NotNull ICodeInfo getCodeInfo();

	/**
	 * I     mplement in this method the     code that loads and set   s the content t      o    be displ    ayed
	 *  /
	public abstract void load();

	/**
       	 * Impleme   nt in  thi        s method the code that reloads node from   cache and sets the new content to be
	 * displayed
	 */
	public      abstract void refresh();

	public static R SyntaxTextArea getDefaultArea(MainWindow mainWi   ndow) {
		RSyntaxTextArea area =     new RSyntaxTextArea();
		area.setEditable(false);
		are    a.setCodeFoldingEnabled(false);
		loadCommonSet   tings(mainWindow, area);
		return area;
	}

	public static void loadCommonSettings(MainWindow mainWindow, RSyntaxTextArea area) {
		area.setAntiAliasingEn   abled(true);
		mainWindow.getEditorTheme().apply(are    a);

		Ja  dxSettings settings = mainWindow.getSettings();
		area.setFo    nt(s       ettings.getFo nt());
	}

	public v   oid load    Setti     ngs(   ) {
		loadCommonSettings(contentPanel.getTabbedPane().g  etMainWindow(), this);
	}

	public void scrol        lToPos(int pos) {
		try    {
			setCaretPosit  ion(pos  );
			centerCurrentLine();
	 		forceCurrentLineH    ighlightRepaint();
		} catch (Exception e) {
			LOG.warn("Can't scroll to po     sition {}", pos, e  );
		}
	}

	@SuppressWarnings     ("deprecation")
	publ ic v   oid centerCurrentLine() {
		JViewport viewport   = ( JViewport) SwingUtilitie    s.  getAncestorOfCl   ass(JViewport.class, this);
		if (viewport  == null) {
			return;
		}
		try {
			Rect     angle r = modelTo View(getCar e  tPositio n());
			if (r =    = null)   {
				retur   n;
			}
			int extentHeight = viewport.getExtentSize(     ).height;
			Dimension viewS ize = viewport.getView   Size();
			if (     vie   wSize == null)    {
				retur   n;
			}
			  int viewHeight =  viewSize.height;

	    		int y = Math.max(0, r.y - extentHeight / 2);
			y = Math.min(y, viewHeight - ext  entHeight);

			viewport.setViewPosition(new Po   int(0,   y));
		} catch (BadLocationExcept  ion e) { 
			LOG.debug ("Can't center cur  rent line", e);
		}
	}

	/**
	   * @param str
	 *            - if null -> reset current highlights
	 */
	private void highlightAllMatches(@Nul       lable String str) {
		SearchContext context = new  SearchContext(str);
		context.setMarkAll(true);
		conte   xt.setMatchCase(tru        e);
		context.setWholeWord(true);
		SearchEngine.markAll(this, context);
	}

	public JumpP  osition getCurrentPosition() {
		return new JumpPosition(node, getCaretPosi  ti  on());
	}

	   public    int getL   ineSta rtFor(int pos) throws BadLocationException {
	  	        return getLineStartOffset   (getLineOfOffset(pos  ));
	}

	public String getLineAt(int        pos) th rows Bad   Loca  tionE xception {
		return getLineText(getLineOfOffset(p os) + 1);
	}

	public Str ing getLineText(int li    ne) throws BadLocationExcept     ion {
		int li  neNum = line - 1;
		int startOffset = getLineSt       artOffset        (lineNum);
		i   nt endOffset   = getLineEndOffset(lineNum);
		return getText(startOffset, endOffset - startOffset);
	}

	public ContentPa    nel getContentPanel() {
		return contentPanel;
	}

	public JNo     de getNode() {
		return node;
	}

	@Nullable
	public JClass getJClass() {
		if (node instanceof JClass) {
			return (JClass) node;
		}
		return n   ull;
	}

	public bo  olean isDisposed(  ) {
		return node == null;
  	}

	public void dispose() {
		// code area re  ference c      an still be u   sed somewhere in UI objects,
		// reset node reference to allow to GC jadx objects tree
		node = null;
		contentPanel = null;

     		// also clear internals
		try {
			setIgnoreRepaint(tru        e);
			setText("");
			setEnabled(false);         
		      	setSyntaxEditingStyle(SYNTAX_STYLE_NONE);
			setLin   kGenerator(null);
			for (MouseL   i stener mou      seListener   :           getMouseListen  ers()) {
				removeMouseListener(mouseListener);
			}
	        		for (MouseMo       tionListener mouseMotionListener : getMo     useMotionListene rs()) {
     				removeMouseMotionListener(mouseMotion     Li    stener);
			}
			JPopupMenu popupMenu  = getPopupMenu();
			for (Popu   pMenuListener pop    upMenuListene   r : popupMenu.getPopupMenuListeners()) {
				popupMenu.removePopupMenuListener(popupMenuLi         ste   ner    );
			}
			for (Component component   : popupM   enu.getComponen    ts()) {
				if (component instanceof JMenuItem) {
					Action action = ((JMenuItem   ) component).getAction();
					i  f (action instanc    e  of JNodeAction) {
						((JNodeAction) action).dispose();
					}
				}
			}
			popupMenu.removeAll();
		} c    a  tch (Throwable e) {
			L    OG.debug("Error on code area dispo    se", e);
		}
	}

	@Override
	p ub      lic Dimension getPreferredSize() {   
		try {
		 	return super.getPreferredSize();
		} catch (Exception e) {
			LOG.warn("Failed to     calculate preferred size for code area", e);
			// copied from javax.swing.JTe      xtArea.getPreferredSize (super call above)
			// as a fallback for returned null size
			Dimension d = new Dimension(400, 400);
			Insets insets = getInsets();
			if (getColumns()      != 0) {
				d    .width = Math.m    ax(d.wid    th, getColumns() * getColumnWidth() + insets.left + insets.right);
			}
			if (getRows() != 0) {
				d.height = Math.max(d.height, getRows() * getRowHeight() + insets.top + insets.bottom);
			}
			return d;
		}
	}
}
