package    BookStore;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

imp   ort javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import  javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Add_GUI    extends JFrame implements ActionListener{
	/**       
	 * 
	 */
	priv   ate static final long serialVersi    onUID =      1L;
	private JTextField textF1_add;
	private J       TextField t extF2_add;
	private JTextField textF3_add;
	private JTextFie  ld textF4_add;
	private JTextField textF5_add;
	p      riv    ate JTextField textF6_add;
	private JTextFie    ld textF7_add;
	private     JTextField textF8_add;
	priv   ate JButton aButton_add;
	JTextFi     eld keyword_search ;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLa      bel label4;
	JLabel label5;
	JLabel label6;
	Administrator admin=new Administrat   or(Main.array);
  	
     	public Add_GUI()
	{
		 setTitle(  "Add a New Book");
		 setSize(400,270   );
		 setLayout(  nu  ll);
		 setLocation(300,200);
		 setR  esizable(false);
		 
		 JPane   l p1_add = new JPanel();
		 p1_add.setBounds(10,10,380,220);
		 p1_add.setLa yout(new FlowLayout(Flo  wLayout.LEFT));  
		 p1_add.setB   order(Bo   rderFacto ry.createTitledBo  rder("Add a new Book"));
		  label1 = new JLabel("Title:");
		 text      F 1_ad       d = new  JTextFiel   d        (2     5);
		 label2 = new JLabel("Autho  rNa  me:");
		 textF2_    add = new  JTextFi   eld(7);
		    textF3_add = new  JTextField(7);
		 textF4_add= new  JTex tF  ield(7);
		 label3 = new JLabel("   PublishedDate:");
		 textF5_add = new  JTextField(   24);
		 label4 = new JLa   b   el("Pric   e:");
		 textF6_add = new  JTextField(29);    
		         label5 =     new JLabel("ISBN:");
	  	 tex tF7_add = new  JTextField(28   );       
		 labe l6 = new JLabel("Stock:");
		 textF8_add = new  JTextField(25);
		 aButton_add = new JButton("Add");
		 
		 setEvent();
		 p1_add.add(label1);
		 p1_add.add(textF1_add);
		 p1_add.add(label2);
		 p1_add.add(textF2_add   );
		 p1_add.add(textF3_add);
		 p1_add.add(textF4_add);
		 p1_add.add(label3);
		 p1_add.add(textF5_add    );
		 p1   _add.add(label4) ;
		 p1_add.add(textF6_add);
		 p1_add.add(label5);
		 p1_add   .add(textF7_add);
		 p1_add.add(label6);
		 p1_add.add(textF8_add);
		 p1_add.ad     d(aButton_add);
		 add(p1_add);
		
	}
	public void setEvent()
	{
		aButton   _add.addActionL   istener(this);
		aButton_add.setActionComman  d("Add");
	}

	     @Override
	public void actionPerformed(A  ctionEvent e) {
		String name = e.getActionCommand();
		if(n  ame.equals("Add"))
		{
			Books aBook;
			String titl    e=textF1_add.getText();
			Name aName= new Name(textF2_add.getText(),textF3_add.getText(),textF4_add.getText());
			int publishedD       ate=Intege   r.parseInt(textF5_add.getText());
			double price=Double.parseDouble(textF6_add.getText());
			String ISBN=textF7_add.getText();
			int stock=Integer.parseInt(textF8_add.getText());
			aBook=new Books(title,aName,publishedDate,price,ISBN,stock);
			if(admin.addBook(aBook))
				JOptionPane.showMessageDialog(this,"Successful Added!!");
			else
				JOptionPane.showMessageDialog(this,"Failure Added!","ERROR!",JOptionPane.ERROR_MESSAGE);
			Main.display();

		}
		
	}
	
}
