package    rsync.ui;

import   org.eclipse.swt.SWT;
import   org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
impo    rt org.eclipse.ui.console.ICons  ole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
/*
impo  rt org.eclipse.ui.console.IConsoleConstants;
im   port org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;

impo  rt org.eclipse.swt.widgets.Shel       l;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
im         port org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
*/


public class ConsoleDisplayMgr
{
	private static ConsoleDisplayMgr Console = null;
	private Me  ssageConsole m_   MessageCon   so   le = null;   
	private String m_Title = null;	
	
		
	protected ConsoleDisplayMgr(String messa     geTitle){	   	
		m_   Title = messageTitle;
		
		m_MessageConsole = new MessageConsole(m_Tit l            e, null); 
		m_MessageConsole.setTabWidth(4 );
		IConsole[] c = new IConsole[]{ m_MessageConsole };
		ConsolePlugin.getDefault().getConsoleManager().addCons   oles(c);		
	}

	public static ConsoleDisplay      Mgr getDefault(String consoleTitle){
		if(Console == null)      Console = new ConsoleDisplayMgr(consoleTitle);
	 	
		return   Console;
	}	
  		
	public void   println(String msg){		
		if(      msg  == null ) return;

		i   nt swtColorId = SWT.COLOR_DARK_GREEN;
		
		MessageConsoleStream msgConsoleStream = m_MessageConsole.newMessageStream();	
		
		if   (msgConsoleStream ==    null) {
			System.out.print(Messages.MESG_ERROR_GETTING_CONSOLE_STREAM);
		}else{
			msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId  ));
		}
	 	
		/* display message on console */	
		msgConsoleStream.println(msg);
	}

}