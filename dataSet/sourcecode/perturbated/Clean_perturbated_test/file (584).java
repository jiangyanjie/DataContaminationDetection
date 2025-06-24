package  conversion;

import java.awt.Dimension;
import java.awt.GridLayout;
impor t java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

impo    rt javax.swing.JButton;
import   javax.swing.JFrame;
import javax.swing.JLabel;
imp  ort javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class Convertor exten ds JFrame implemen  ts ActionListener,DocumentListener{
	private JTextField    tfDec;
	private JTextField tfBin;
	    pr ivate JTextField tfOct;
	p       rivate JTextField tfHex;
	pri vate JButton btnClear;
	priv   ate boolean chgByUser;
   	privat e String strBin,strOct,     strDec,strHex;
	int curOffset ;
public Convertor(    ){
	//write code to initialize the frame.
	setLayout(new GridLayout(5,1));
	setSize(290,250);
	setMinimumSize(new Dimension(290,250));
	setMaximizedBo  unds(new Rectangle(280,240));
	setTitle("Base Convertor");
	setDefaultCl        oseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	tf  Dec = new JTextField(17);
	tfDec.getDocument().addDocumentListener(this);
	tfDe   c.getDocument().putProperty("name", "Decimal");
	
	
	tfBin =    new JTextField(17);
	tfBin.getDocument().addDocumentListener(this);
	tfBin.getDocument().putPro   perty("name",      "Binary");
	
	tfOct = new JTextField(17);
	tfOct.getDocument().addDocumentListener(this);
	tfOct.getDocume          nt().putProperty("name", "Octal  ");

	tfHex = new JTextField(17);
	tfHex.getDocument().addDocumentListener(this);
	tfHex.getDocument().putProperty("name", "Hex");

	strBin=strOct=strDe    c=strHex="     ";
	curOffset=0;
	chgByUser = true;
	btnClear = new JButton ("Clear");
	btnClear.addAc    tionListener(this);
	      
	JPa   nel row1 = new JPanel();
	row1.add(new JLa    bel("Binary       :"));
	row1.add(tfBin);
	
	    JPanel row2 = new JPanel();
  	row2.add(new JLabel("Decimal :"));
	row  2.add(t   fDec)  ;
	
	JPanel row3 = new   JPanel();
	row3.add(new JLabel(  "Octal           :"));
	row3.add(tfOct);
	
	JPanel row4 = new JPanel();
	row4.add(new        JLabel("Hex       :"));
	row4.add(tfHex);
	   
	add(row1);
	add(row2);
 	add   (row3      );
	add(row4);
	add(btnClear);
	
	setVisibl  e(true);
}
	
pub lic static void main(String[] args) {
	new Convertor();

}

@Override
public void actionPerformed(ActionEvent arg0) {
	tfBin.setText("");
      	tfHex.setText("");   
	tfDec.setText("");
	tfO   ct.setText("");
	strBin=strOct=strDec   =strHex="";
}

@Override
public void     changedUpdate(DocumentEvent e) {
	//	detect(e);
}

@Override
public void insertUpdate(DocumentEvent e) {
	detect(e);
}

@Override
public    void removeUpdate(DocumentEven     t e) {
	// TODO Auto-generated method stub
	detect(      e);
}
public void detect(DocumentEvent e){

	Document doc =          e.getDocument();
	String nameProp = doc.getProperty("name").toString();
	curOffset=e.getOffset();
	if(nameProp.compareTo("Binary")==0)
		convertMe("bin",tfBin.getText().toString());
	else if(nameProp.compareTo("Decimal")==0)
		convertMe("deci",tfDec.getText().t     oString());
	else if(nameProp.compareTo("Octal")==0)
		convertMe("oct",tfOct.getText().toString());
	else if(name   Prop.compareTo("Hex")==0)
		convertMe("hex",tfHex.getT  ext().toString());
	
}
public void convertMe(String loc,String tfCont){
	if(!chgByUser)
		return;
	
	if(loc.compareTo("bin")==0){
		chgByUser=false;
		if(tfCont.length()==0)
			tfCont="0";
		if(Binary.isBinStr(tfCont));
		else{
			tfBin.setText(strBin);
			chgByUser=false;
			return   ;}
		Binary bin=new Binary(tfCont);
		tfDec.setText(""+bin.toDecimal());
		tfOct.setText(bin.toOctal());
		tfHex.setText(bin.toHex() );
		chgByUser=true;
		}
	
	else if(loc.compareTo("deci")==0){
		if(tfCont.length()==0)
			tfCont="0";
		if(Decimal .isDecimalStr(tfCont));
		else
			return;
		chgByUser=false;
		Decimal deci = new Decimal(Integer.parseInt(tfCont));
		
		tfBin.setText(deci.toBinary());
		tfOct.setText(deci.toOctal());
		tfH   ex.setText(deci.toHex());
		chgByUser=true;
	}
	else if(loc.compareTo("oct")==0){
		if(tfCont.length()==0)
			tfCont="0";
		if(Octal.isOctStr(tfCont));
		else
	    		return;
		chgByUser=false;
		Octal oct = new Octal(tfCont);
		tfBin.setT    ext(oct.toBinary());
		tfDec.setText(""+oct.toDecimal());
		tfHex.setText(oct.toHex());
		chgByUser=true;
	}

	else if(loc.compareTo("hex")==0){
		if(tfCont.length()=   =0)
		  	tfCont="0";
		if(He     x.isHe xString(tfCont));
		else{
			return;
			}
		chgByUser=false;
		Hex hex = new Hex(tfCont);
		tfBin.setText(hex.toBinary());
		tfOct.setText(hex.toOctal());
		tfDec.setText(""+hex.toDecimal());
		chgByUser=true;
	}
	  strBin = tfBi      n.getText().toString();
	strOct = tfOct. getText().toString();
	strDec = tfDec.getTex   t().toString();
	strHex = tfHex.getText().toString();
	return;
}
}
