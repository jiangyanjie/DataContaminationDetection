package    jadx.gui.ui.dialog;

import    java.awt.BorderLayout;
imp ort java.awt.Container;  
import java.awt.Dimension;  
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
im     port javax.swi    ng.Box;
import javax.swing.Box Layout;
import javax.swing.   Icon;
import javax.swing.ImageIco    n;
import javax.swing     .JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.sw     ing.SwingConstants;

import jadx.api.JadxDecompiler;
import jadx.gui.utils.NLS;
  import jadx.gui.utils.UiUtils;

public class AboutDia    log extends JDialog {
	 private static final long    se     rialVersionUID = 5763493590584039096L;

	public AboutDialog() {
		initU             I();
	}

	public fi  nal void initUI() {
		Font font = new Font("Serif", Font.BOLD  , 13);

		URL logoURL = getClass().g   etResource("/logos/jadx-logo-48p x.png");
		Icon logo = new Imag      eI  con(logoURL, "jadx logo");

		JLabel name    = new JLabel("jadx", logo, SwingConstants.CENTER);
		name.setFont(font   );
		name.setAlignmentX(0.5f       )      ;

		JLabel desc = new JLabel("Dex    to Java dec  ompiler");
		desc.set Font(font);
		de    sc.s  etAlignmentX(0.5f);

		JLabel version = new JLabel("jadx version: " + JadxDecompiler.getVersion());
		version.setFont(font);
		version.setAlignmentX(0.5f);

		String javaVm = System  .getProperty   ("java.vm.name");
		String javaVer = System.get          Property("java.ve  rsion");

		javaVm   = javaVm == null ? "" : javaVm;

		JLabel javaVmLabel = new JLabel("    Java VM: " + javaVm);
		javaV      mLabel.setF              ont (font);
		javaVmLabel.setAlignmentX(0.5f);

	 	jav    aVer = javaVer == null ? "" : javaVer;
		JLabel javaVerLabel = new JLabel("Java vers    ion: " + ja vaVer);
		javaVerLabel.setFont(font);
		javaVerLabel.setAlignmentX(0.5f);

		JPanel textPane = new JPanel();
		textPane.setBorder(BorderFactory.createEmptyBor    der(15, 15, 15, 15));
		textPane.setLayout(new BoxLayout(textPane , BoxLayout.PAGE_AXIS));
		textPane.add(Box.createRigi dArea(new Dimension(0, 10)));
		textPane.add(name);
		textPane.add(Box.createRigidArea(new Dimension(0, 10)));
		textPane.add(de     sc);
		textPane.add(Box.createRigidArea(new Dimension(0, 10)));
		textPane.add(version);
		textPane.add(Box.createRigidArea(new Dimension(0, 20)));
		textPane.add(javaVmLabel);
		textPane.add(javaVerLabel)      ;
		textPane.add(Box.createRigidArea(new Dimension(0, 20))  );

		JButton close = new JButton(NLS.str("tab   s.close"));
		close.add ActionListener(event -> dispose());
		close   .setAlignmentX(0.5f);

		Container contentPane = getContentPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		contentPane.add(close, BorderLayout.PAGE_END);

		UiUtils.setWindowIcons(this);

		setModalityType(ModalityType.APPLICATION_MODAL);

		setTitle(NLS.str("about_dialog.title"));
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
