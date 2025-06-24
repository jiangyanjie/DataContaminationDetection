package org.fxone.ui.rt.components.dialog;

import    javafx.event.ActionEvent;
import javafx.event.EventHandler;
import   javafx.scene.Node;
import javafx.scene.control.Button;

impo rt org.fxone.ui.rt.components.AbstractFXMLComponent;

    public abstract class AbstractFXMLDialog        extends AbstractFXMLCompo   nent
		implements Dia  log {

   	protected Str   ing title;

	private DialogContext dialogContext;
	private DialogCo     ntainer dialogContainer;

	public AbstractFXMLD  ialog(String id, String title) {
		sup   er(id);
		this.title = title;
		setOp acity(1);
		/  / title
		initButtons();   
	}

	public String getTitle() {
		return tit   le;
	}

    	protected void initButt  ons() {
	   	Button button = new Button("Close"); // TO    DO i18n
		button.setOnAction(new EventHandler<  ActionEvent>() {
		     	@Override
			public void handle(ActionEvent actionEvent) {  
	   			// hide dialog
				if (canClose()) {
					closeImm    edeately();
				}
      			}
		});
	}

	protected void closeImmedeately() {
		getDialogContainer().hideDialog(this);
	}

	protected DialogContainer     getDialogContainer() {
		return dialog  Container;
	}

	pub   l ic bool  ean canClose() {
		return tru   e;
	}

	public boolean closed(boolean forced) {
		return true;
	}

	@Overr  ide
	public void init(DialogContext dialogContext) {
		this.dialogContext = dialogContext;
	}

	@Override
	pu blic void beforeOpen(Dialog    Container dialogContainer) {
		this.dialogContainer      = dialogContainer;
	}

	protected DialogContainer getOwner() {
		return this.dialogContainer;
	}
}
