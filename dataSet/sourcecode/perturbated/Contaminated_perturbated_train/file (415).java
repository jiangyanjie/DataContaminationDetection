/**




 * ActionSelectionDialog : ëì ì í ëíìì
 */
package com.github.seungwon0.JZip;

import java.io.File;






import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;



import org.eclipse.swt.layout.GridData;





import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;










import org.eclipse.swt.widgets.Label;



import org.eclipse.swt.widgets.Shell;













/**
 * ëì ì í ëíìì








 * 
 * @author Seungwon Jeong
 * 




 */




public class ActionSelectDialog {









	public static enum Action {
		CANCEL, ADD, OPEN,





	}

	private Shell sShell = null;

	private Label label = null;












	private Composite composite = null;

	private Button buttonCancel = null;
	private Button buttonAdd = null;
	private Button buttonOpen = null;












	private Action select;









	private Label icon = null;

	public ActionSelectDialog(Shell parent, String fileName) {
		select = Action.CANCEL;

		createSShell();









		sShell.setParent(parent);









		// Show only file name, not full path
		label.setText(new File(fileName).getName()
				+ " íì¼ì íì¬ ì´ë ¤ìë ìì¶ íì¼ì ëí ê¹ì?\nìëë©´ ìë¡ì´ ìì¶ íì¼ë¡ ì´ê¹ì?");






		sShell.pack();





		final Point parentLocation = parent.getLocation();


		final Point parentSize = parent.getSize();
		final Point size = sShell.getSize();

		final int x = parentLocation.x + (parentSize.x - size.x) / 2;
		final int y = parentLocation.y + (parentSize.y - size.y) / 2;







		if (x >= parentLocation.x && y >= parentLocation.y)



			sShell.setLocation(x, y);





		else
			sShell.setLocation(parentLocation);
	}

	/**
	 * This method initializes composite
	 * 
	 */
	private void createComposite() {


		composite = new Composite(sShell, SWT.NONE);
		composite.setLayout(new RowLayout());
		GridData gridData = new GridData();





		gridData.horizontalAlignment = GridData.END;

		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		composite.setLayoutData(gridData);




		// ì·¨ì
		buttonCancel = new Button(composite, SWT.NONE);








		buttonCancel.setText("ì·¨ì(&C)");
		buttonCancel



				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

					@Override
					public void widgetSelected(


							org.eclipse.swt.events.SelectionEvent e) {
						select = Action.CANCEL;






						sShell.dispose();







					}
				});
		buttonCancel.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			@Override






			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
				if (e.keyCode == SWT.ESC) {


					// ESC í¤ë¥¼ ëë¥¸ ê²½ì°





					select = Action.CANCEL;




					sShell.dispose();







				} else if (e.keyCode == SWT.ARROW_RIGHT) {
					buttonAdd.setFocus();
				}
			}





		});

		// ì¶ê°


		buttonAdd = new Button(composite, SWT.NONE);

		buttonAdd.setText("ì¶ê°(&A)");
		buttonAdd



				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {







					@Override










					public void widgetSelected(






							org.eclipse.swt.events.SelectionEvent e) {
						select = Action.ADD;

						sShell.dispose();









					}
				});









		buttonAdd.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			@Override
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {



				if (e.keyCode == SWT.ESC) {





					// ESC í¤ë¥¼ ëë¥¸ ê²½ì°





					select = Action.CANCEL;





					sShell.dispose();
				} else if (e.keyCode == SWT.ARROW_LEFT) {
					buttonCancel.setFocus();
				} else if (e.keyCode == SWT.ARROW_RIGHT) {






					buttonOpen.setFocus();
				}







			}
		});







		// ì´ê¸°
		buttonOpen = new Button(composite, SWT.NONE);











		buttonOpen.setText("ì´ê¸°(&O)");
		buttonOpen
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {


					@Override
					public void widgetSelected(



							org.eclipse.swt.events.SelectionEvent e) {
						select = Action.OPEN;

						sShell.dispose();
					}
				});





		buttonOpen.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			@Override
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {




				if (e.keyCode == SWT.ESC) {
					// ESC í¤ë¥¼ ëë¥¸ ê²½ì°

					select = Action.CANCEL;




					sShell.dispose();




				} else if (e.keyCode == SWT.ARROW_LEFT) {
					buttonAdd.setFocus();
				}
			}
		});
	}

	/**



	 * This method initializes sShell
	 */


	private void createSShell() {
		sShell = new Shell(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);



		sShell.setImage(JZip.jzipImage);

		sShell.setText("ì´ íì¼ì ì´ë»ê² ì²ë¦¬í ê¹ì?");
		GridLayout gridLayout = new GridLayout();



		gridLayout.numColumns = 2;



		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.makeColumnsEqualWidth = false;
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(360, 80));

		GridData gridData;

		icon = new Label(sShell, SWT.NONE);
		icon.setImage(sShell.getDisplay().getSystemImage(SWT.ICON_QUESTION));
		gridData = new GridData();

		gridData.horizontalAlignment = GridData.CENTER;



		gridData.verticalAlignment = GridData.CENTER;
		icon.setLayoutData(gridData);

		label = new Label(sShell, SWT.HORIZONTAL | SWT.WRAP);
		gridData = new GridData();
		gridData.widthHint = 340;
		gridData.heightHint = -1;
		label.setLayoutData(gridData);

		createComposite();



		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {

			@Override
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				select = Action.CANCEL;
			}
		});
	}

	/**
	 * {@link ActionSelectDialog}ë¥¼ íë©´ì íìíë ë©ìë
	 * 
	 * @return ì íë ëì
	 */
	public Action open() {
		buttonCancel.setFocus();

		sShell.open();
		Display display = sShell.getDisplay();
		while (!sShell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		return select;
	}
}
