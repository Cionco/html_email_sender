package frame;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import io.Mailer;
import lib.CenterlessBorderLayout;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1579810921087937046L;
	JPanel contentPane;
	
	String templateFile;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainFrame jf = new MainFrame();
				jf.setVisible(true);
			}
		});
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new CenterlessBorderLayout());
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setBounds(100, 100, 450, 650);
		
		JTextArea txa_source = new JTextArea();
		contentPane.add(txa_source, CenterlessBorderLayout.WEST);
		
		JPanel southPane = new JPanel();
		southPane.setLayout(new GridLayout(1, 2));
		
		JButton btn_select_template = new JButton("Select Template Email");
		btn_select_template.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"HTML & Text Files", "html", "txt");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(MainFrame.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				templateFile = chooser.getSelectedFile().getName();
			}
		});
		southPane.add(btn_select_template);
		
		JButton btn_send = new JButton("Send");
		btn_send.addActionListener(e -> Mailer.send(new io.Converter(txa_source.getText()).convertTextToHTML()));
		southPane.add(btn_send);
		
		contentPane.add(southPane, CenterlessBorderLayout.SOUTH);
		
		setContentPane(contentPane);
	}
}
