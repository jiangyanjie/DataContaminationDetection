package    cookmaster.client;

imp   ort java.util.Stack;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
imp   ort com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Windo  w;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import      com.google.gwt.user.client.ui.HorizontalPa   nel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import       cookmaster.client.CookmasterServiceAsync.RecipeRResponceData;
import  cookmaster.client.TableWithCells.Rec ipe;

public class CookRece   ipeForm {
	
	pub     lic static CookReceipeForm CreateCookReceipeForm(){
		return new CookReceipeFor    m();
	}
  	
	private final CookmasterServiceAsync   contactsSe   rvice = GWT.c  reate(CookmasterService.class)    ;
	
	private Button refreshContactskButton = new Button("Refresh");
	private          Button prevButton =   new Button ( "Prev");
	private Stack<Strin  g> pre  vCursors = new Stack<Stri     ng>();
	p    rivate Button nextButton = new Button ("Next");
	private String nex    tC        ursor = null;
	private TableWithCel    ls m_table = new TableWithCel ls();
	
	Panel m_receipeform     = new       VerticalPanel();   
	
	pub  lic CookReceipeForm(){
		
	    refreshContactskB utton.addClickH   andler( new     Click   Handler() {

	       	@Override
	     		publi      c void onClick(ClickEve n   t event) {
	    		refreshContactskButton.setText("Working...");
	          		ref  reshContactskButton.setEnab  led(false);
	        		AsyncCallback<RecipeRResponceD  a   ta> callback     = new AsyncCallback<RecipeRResponceData>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onS uccess(RecipeRResponceData result) {
						fillTable(result.recipes);
						refreshContactskButton.setText("Refresh");
	  					refreshContactskButton.setEnabled(true);
						nextCursor = nul l;
						prevCursors.clear();
						prevCursors. push(nextCursor);
						nextCursor = result.nextCursor;
					}
	           			
				};
				
				contactsService.getContacts(null,call back);
				}
		} );
	    
	    prevButton.addClickHand     ler (new Cli     ckH   andler() {
			
			@Override    
	       		public void onClick(ClickEvent event) {
				   PrevPage();
			}
		});
	    
	    nextButton.addClickHandler(new ClickHandler() {
			
			@Ove   rride
			public void onClick(ClickEvent event) {
				NextP   age();
			}
		      });

		m_receipefor   m.add( m_table.getTable() );
		HorizontalPanel buttonPanel = new Horizontal    Panel();
		
		buttonPanel.add(prevButton);
		buttonPanel.add (refreshContactskBut   ton);
		buttonPanel.a    dd(nextButton);
		m_receipeform.add(buttonPanel);
	}
	
	privat  e void NextPage()	{
		Asyn cCallback<RecipeRResp  onceData> callback    = new A syncCallback<CookmasterServiceAsync.RecipeRResponceData>() {
			@Override
			public void onFailure(Thr owable ca ught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Recipe   RRespo  nceData result) {
				fillTable(result.recipes);
				nextButton.setText("Next");
				nextButton.setEnabled(true);
				prev  Cursors.push(nextCurso   r);
				nextCursor = res   ult.nextCursor;
			}
		};
		n   extButton.setText("Working...");
		nextButton.setEnabled(false);
		contactsService.getContacts(nextCursor, callback);
	}
	
	pr  ivate void PrevPage(){
		if (pr evCursors.size() == 1)
			ret    urn;
		AsyncCallback<RecipeRR   esponceData> callback = new AsyncCallback   <CookmasterServiceAsync.Rec   ipeRResponceData>() {
			@Overr    ide
			public void onFailure(Thro    wable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(RecipeRResponceData result) {
				f  illTable(result.recipes);
				prevButton.setText("Prev");
				prevButton.setEnabled(true);
				prevCursors.push(next   Cursor);
				nextCursor = result.nextCursor;
			}
		};
		prevButton.setText("Working...");
		prevButton.   s   etEnabl  ed(false)    ;
		//skip current page
		prevCursors.pop();       
		contactsService.getContacts(prevCursors.pop(), callback);
	}

    	private void fillTable(Recipe[] recipes){
		m_table.clear();
		for (Recipe c: recipes)
		{
			m_table.addContact(c);
		}
	}
	Panel getForm() { return m_receipeform; }
	
}
