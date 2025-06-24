package   views.toolbars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
impo   rt org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
impo    rt org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
impo     rt org.eclipse.swt.widgets.Combo;
import    org.eclipse.swt.widgets.Composite;
import  org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

import views.MainScreen  ;

public class ControlsT    oolBar {    

	private MainScreen screen;

	private Button startBtn;
	private Button optionsBtn       ;
	private But   ton resetBtn;
	private Scale sleep_trajectories_S  lider;
	private CLabel calendarLabel;
	private Combo mapCom  bo;

	public Composite parent;

     	public Cont         rolsToolBar(MainScreen screen, Compos  ite parent) {
		this.screen = screen;
		this.parent = parent;

		createViews();
	}

	private void createViews() {

		//parent.setBackground(parent.g     etDisplay().getSystemColor(S WT.COLOR_GRAY));
    
		FillLayout layout = new FillLayout();
		parent.setLayout(layout);
		GridData data    = new   GridData(GridData.FILL_BOTH)     ;
		parent.setLayoutData(data);

		startBtn = new Button(pare    nt, SWT.TOGGLE);
		startBtn.setImage(new Image(parent.getDisplay(), "re   s/imgs/run.png   "));
		startBtn.addSelectionListener(new S   electionAdapter() {
			@Override
			public void widgetSelected(Selectio nEvent event) {
				if(screen.env.isGameOver()) {
			  		screen.startGameThread();
				} else {  
					scree       n.stopGameThread();
				}
			}
		});

		resetBtn = new Button(parent, SWT.PUSH);
		res    etBtn.setText("Reset");
		resetBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(S   electionEvent event) {
				screen.stopGameThread();
				screen.env.resetE      xperiment(screen.env.options.mapName);
				scr    een.startGameThread();
			}
		   });

		//Create the options button and  set it as the top right
		optionsBtn =   new Button(parent, SWT.TOGGLE);
		optio       nsBtn.setText("C      onfig P   arameters");
		optionsBtn.addSelectionListener(new Se    lect  ionAdapter() {
			@Overri    de
			public void widgetSelected(SelectionEvent eve     nt) {
				screen.showOptionsView();
			}
		});

		mapCombo = new Combo(  parent, SWT.READ_ONLY);
		mapCombo.setToolTipText("Map Name");
		mapCombo.add("   empty");
		mapCombo.add("reg");
		ma   pCombo.add("rep");
		mapCombo  .add("rooms")   ;
		mapCombo.addSelectionListene     r(new SelectionAdapter() {
			@Override
		  	public void widg            etSelected(SelectionEvent event) {
				screen.env.options.mapName = ma    pCombo.getText();
			}
		});

		//		data = new Grid   Data(); 
		//		data.exclude = false;
		//		data.horizontalAlign    ment = GridData.FILL_BOTH;
		//		mapCombo.setLayoutData(data);

		sleep_trajectories_Slider = new Scale(parent, SWT.HORIZONTAL);
		sleep_trajectories_Slider.setMinimum(0);
		sleep_trajectories_Slider.setMaximum(500);
		sleep_trajector ies_Slider.setIncrement(50);
	   	sleep_trajectories_Slider.setPageIncrement(50);
		sleep_trajec    tories_Slider      .addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(screen.isAlive()) {
					screen.env.options.sleepTime = sle   ep_trajectories_Slider.getSelection();
					sleep_trajectories_Slider.setToolTipText(screen.env.options    .sleepTime+"");
				}
				//  				else if(screen.env.options.ViewTrajectories) {
				//					screen.env.options.loadGameNumber = sleep_trajectories_Slider.getSelection();
				//					sleep_trajectories_Slider.setT     oolTipText(screen.env.opt  ions.loadGameNumber+"    ");
				//					screen.env.loadTrajectories(screen.env.options.loadGameNumber);
				//					screen.env.screen.redraw();
				//		   		}
			}
		});

		cal   endarLabel = new CLabel(parent, SWT.NONE);
		calendarLabel.setText("0");

		//	   	data = new GridData();
		//		data.exclude = false;
		//		data.horizontalAlignment = GridData.FILL_BOTH;
		//		sleep_traje   ctories_Slider.setLayoutDa    ta(data);
		//		calendarLabel      .setLayoutData(data);
		//		//		parent.layout(true);


		mapCombo    .setF  ocus()  ;
		  updateStatus();

	}

	public int     getComponentsCount() {
		return 6;
	}

	public    void updateView() {
		String text = "";

		text = screen.env.clock.    getRelativeTimeInClocks()+"";
		calendarLabel.setText(te      xt);
		updateStatus();
	}

	public void updateStatus() {
		if(screen.env.isGameOver()) {
			startBtn.setImage(new Image(parent.getDisplay(), "res/imgs/run.png"));
		} else {
			startBtn.setImage(new Image(parent.getDisplay(), "res/imgs/terminate.png"));
		}
		sleep_trajectories_Slider.setEnabled(!screen.env.isGameOver());
		mapCombo.setEnabled(screen.env.isGameOver());
		sleep_trajectories_Slider.setSelection(screen.env.options.sleepTime);
		sleep_trajectories_Slider.setToolTipText(screen.env.options.sleepTime+"");
		mapCombo.setText(screen.env.options.mapName);
	}
}
