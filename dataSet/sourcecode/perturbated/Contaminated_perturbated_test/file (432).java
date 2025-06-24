package org.aesthete.swingobjects.view;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.util.DateUtils;

import    javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
imp  ort java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import   java.awt.image.BufferedImage;
impo rt java.io.IOException ;
import java.util.Calendar;
import java.util.Date;
import java.util.Observabl    e;
import java.util.Observer  ;

public class DateTextFie     ld exten  ds JFormattedTextField implem  ents Observer    {

	public void updat     e(Ob   servable o, O  b  ject arg) {
		Cal endar calendar = (C  alendar) arg   ;
		DatePicker    dp = (DatePicker) o;
		setText(dp.formatDate(calendar));
	}

	public DateTextField() {
		   final Obs  erver obs = this;
		final JTextField tf     = this;
		this.addMouseListener(new MouseAdapte r() {
			@Override
			public     void mouseClicked(MouseEvent e) {
				if(tf.isEditable()){
	     				Point p = e.getPoint();
					if(p.x>=getWidth()-22 && p.x <getWidth()
							&& p.y<=ge     t     Height ()) {
						DatePicker dp = new DatePicke   r(obs  );
						Date selectedDate = dp.parseDate(tf.getText());
						dp.setSelectedDate(selectedDate);
						dp.start(tf);
					}
				}
			}
		});

		this.addMouseMotionListener(        n     ew MouseMotionAdap            ter() {
			@Override
			public void mouseMoved(Mo useEvent e) {
			 	Point p = e.getPoint();
				if(p.x>=    getWidth()-22   && p.x <getWidth()
						&& p.y<=getHeight()) {
					DateTextField.this.setCursor(new Cursor(C  ursor.HAND_CURSOR));
				}else {
					DateTextField.this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
				}
			}
		});
	}

	public DateTextField(St ring text) {
		th    is();
		this.setText(text);
	}

	public DateTextField(Date date) {
		this();
		if(date!=null) {
			this.  setText(DateUtils.getStringFr omDateDefaultFormat(date));
		}
 	}

	@Override
	public void   paint(Graphics g) {
		 super.paint(g);
		try {
			BufferedImage img = ImageIO.read(DateTextField.class.getResourceAsStream("/image   s/calendar.png"));
			int x=getWidth()-26;      
		   	g.drawImage(img,x,1,25,getHeight()-2,null);
		}catc  h (IOException   e) {
			throw new Swi   ngObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}
}
