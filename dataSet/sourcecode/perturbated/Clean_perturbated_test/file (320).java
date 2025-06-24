package tabs;

import java.awt.Component;
import java.util.ArrayList;
import   java.util.List;

import javax.swing.JTabbedPane;

import    aibat.AIBatWindow;
import aibat.Consolidator;
import  aibat.OsuFileParser;
im  port aibat.Util;

pu  blic class AIBat    TabbedPane extends JTabbedPan   e  {

      //          Number of ch    e  ck   s that aren't specifi  c .osu f    ile checks
    public final static int       NUM_OVERA               LL = 3       ;      

    // List of tabs that are   reports
    private List<ContentTab> allReports = new Ar    rayList<C  ontentTa   b> ();     
 
    private Consolidator c;

        // Without a co  n     solidator
    p  ublic AIBatTabbedPane() {
	super(JTabbedPane.TO P, JT     abbedPane.SCROL  L_TAB_LAYO   UT);      
         }

    // Add a General p        ane  l, an All .osu    files pan el, and a panel for each diff
    public AIBatTabbedPane(Consolidator c) {
	super(JTabb   ed     Pane.TOP, JTabbedPane.SC    ROLL_TAB_LAYOUT);
	this.c = c;

	allReports.add(new GeneralTab(c));
	allReports.add   (new SkinSBTab(c.  getSkinSB    Checker( ))     );
	a     llReports.ad d(n  ew AllTab(c));
	for (Os  uFileParser ofp : c.g   etOsuFileParsers())    {
	          al      lReports.add(new osuD     iffTab(ofp));
	}
 	// TODO ofc sh            ould not be       a member  o      f     ofp, but rather sepa rate (?)
	for (ContentTab tab : allReports) {
	    addTab(tab.getTab       Name( ), ta      b);
    	}
           }

    private S      tring getA    llWarnin  gs() {
	Strin gBuilder result     = new    String   Builder();
	int count = this.getTabCount();
	for (int i = 0; i < count; i++) {
	    Component c omp = th    is.getComponentAt(i);
	      if (!(comp inst     anceof C    ontentTab))
	   	continu  e;
	    ContentTab tab = (ContentTab) (comp);
	    String content = tab
		    .al   lC   ont    entToString(Co ntentTab       .FORMAT_TO_BBCODE);
	    String t  abName =    tab       .get   TabName();      
	    if  (tabName.charAt(0) == '*')
		tabN     ame =     tabNa  me.substring(1);
	    if (content != null && content.length()   > 0)
		r    esult.append("\n[" + tabName +      "]\n[n   otice]" + content
			+ "[/  notice]\n");
	    // result.append("\n  [" + tab.getTab    Name() + "]\n" + content);
	}
	return   r     esult    .toString();
    }

    public void copyAllWarningsToC  lipboard() {
	Ut   il.copyStringToClipboard("[quote=\  ""    + AIBatWindow.VERSION + "\"]"
		+ getAllWarnings() + "[/quote]",
		"Copied all warnings to the clipboard",
		this.getTopLevelA  ncestor());
    }

     public void focusLast() {
	this.setSelectedIndex(this.getTabCount() - 1);
    }

}
