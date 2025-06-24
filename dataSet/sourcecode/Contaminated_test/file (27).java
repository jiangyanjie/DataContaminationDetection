package cookmaster.client;

import java.util.Stack;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import cookmaster.client.CookmasterServiceAsync.RecipeRResponceData;
import cookmaster.client.TableWithCells.Recipe;

public class CookReceipeForm {
	
	public static CookReceipeForm CreateCookReceipeForm(){
		return new CookReceipeForm();
	}
	
	private final CookmasterServiceAsync contactsService = GWT.create(CookmasterService.class);
	
	private Button refreshContactskButton = new Button("Refresh");
	private Button prevButton = new Button ("Prev");
	private Stack<String> prevCursors = new Stack<String>();
	private Button nextButton = new Button ("Next");
	private String nextCursor = null;
	private TableWithCells m_table = new TableWithCells();
	
	Panel m_receipeform = new VerticalPanel();
	
	public CookReceipeForm(){
		
	    refreshContactskButton.addClickHandler( new ClickHandler() {

	    	@Override
			public void onClick(ClickEvent event) {
	    		refreshContactskButton.setText("Working...");
	    		refreshContactskButton.setEnabled(false);
	    		AsyncCallback<RecipeRResponceData> callback = new AsyncCallback<RecipeRResponceData>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(RecipeRResponceData result) {
						fillTable(result.recipes);
						refreshContactskButton.setText("Refresh");
						refreshContactskButton.setEnabled(true);
						nextCursor = null;
						prevCursors.clear();
						prevCursors.push(nextCursor);
						nextCursor = result.nextCursor;
					}
	    			
				};
				
				contactsService.getContacts(null,callback);
				}
		} );
	    
	    prevButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PrevPage();
			}
		});
	    
	    nextButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				NextPage();
			}
		});

		m_receipeform.add( m_table.getTable() );
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		buttonPanel.add(prevButton);
		buttonPanel.add(refreshContactskButton);
		buttonPanel.add(nextButton);
		m_receipeform.add(buttonPanel);
	}
	
	private void NextPage()	{
		AsyncCallback<RecipeRResponceData> callback = new AsyncCallback<CookmasterServiceAsync.RecipeRResponceData>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(RecipeRResponceData result) {
				fillTable(result.recipes);
				nextButton.setText("Next");
				nextButton.setEnabled(true);
				prevCursors.push(nextCursor);
				nextCursor = result.nextCursor;
			}
		};
		nextButton.setText("Working...");
		nextButton.setEnabled(false);
		contactsService.getContacts(nextCursor, callback);
	}
	
	private void PrevPage(){
		if (prevCursors.size() == 1)
			return;
		AsyncCallback<RecipeRResponceData> callback = new AsyncCallback<CookmasterServiceAsync.RecipeRResponceData>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(RecipeRResponceData result) {
				fillTable(result.recipes);
				prevButton.setText("Prev");
				prevButton.setEnabled(true);
				prevCursors.push(nextCursor);
				nextCursor = result.nextCursor;
			}
		};
		prevButton.setText("Working...");
		prevButton.setEnabled(false);
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
