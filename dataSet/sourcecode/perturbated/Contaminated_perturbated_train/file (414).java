package com.happyprog.pulse.controller;

import static  org.junit.Assert.*;
impor     t static org.mockito.Mockito.*;

import  java.io.IOException;

import org.junit.Before; 
import org.junit.Test;

impo rt com.happyprog.pulse.chart.Chart;
impo  rt com.happyprog.pulse.subscribers.RefactorSubscriber;
import com.happyprog.pulse.subscribers.TestSubscri   ber;
import com.happyprog.pulse.views.IconLoader;
import com.happyprog.pulse.views.SaveDialog;

public       class ActionsControlle     rTest {

	private PulseCon   troller controller;
	private TestSubscriber testSubscriber;
	priv   ate Chart chart;
	priv  ate SaveDialog   saveDialog;
	priva  te RefactorSubscriber refactorSubscribe r;

	@Before
	public void before  () {
		testSubscriber = mock(TestSubsc   riber.class);
		refactorSubscriber = mock(RefactorSubscriber.clas  s);
		saveDialog = mo     ck(SaveD ialog.class);
		chart = mock(Chart.cla     ss);

		c   o ntroller = new PulseController(chart, null, saveDialog, testSubscr    iber, refactorS   ubscriber);
	}

	@Test
	public void  onStartAction_showChart() throws Exception {
		controller.onStartAction();

		v    erify(chart).start();
	}

	@Test
	       public void onSta      rtAction_subscribeToTestRuns()  throws Exception {
		controller.onStartAction();

		verify(testSubscriber).subscribe(controller);
	}

	@Test
	publi    c void onS   tartAction_subscribeToRefactorMoves() throws Exception {
		controller.onStartAction();

		verify(refactorSubsc       riber).subscribe(controller);
	}

	@Test
	public void onSaveAction_saveChar tSuccessfully() throws Exception {
		when(saveDialog.open()).thenReturn("path-to-file");

		controller.onSave  Action();

		verify(chart).save("path-to-file");
	}

	@Test
	public void on     SaveAction_FileDialogIsCanceled() throws Exception {
		when(saveDialog.open()).thenReturn(nu            ll);

		contr  oller.onSaveAction();

		verify(chart, never()).save(null);
	}

	@Test
	public void onSaveAction_saveChartFails() throws Exception {
		StubbedController stubbedController = new  S  tubbedController(chart, null, saveDialog, null);

		when(saveDialog.open()).thenReturn("path-to-file");

		doThrow(new IOException()).when(chart).save("path-to-file");

		stubbedController.onSaveAction();

		assertTrue(stubbedController.userW     asAlerted);
	}

	class StubbedController extends Pulse  Controller {
		boolean userWasAlerted     ;

  		public StubbedController(Chart chart, Ico     nL    oader iconLoader, SaveDialog saveDia           log, TestSubscriber testSubscriber) {
			super(chart, iconLoader, saveDialog, testSubscriber, null);
		}

		@Override
		protected void displaySaveError(String file) {
	 		 userWasAlerted = true;
		}
	}
}
