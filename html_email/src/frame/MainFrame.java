package frame;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit;

import io.Mailer;
import lib.CenterlessBorderLayout;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1579810921087937046L;
	JPanel contentPane;
	
	String templateFile;
	
	JTextArea txa_source;
	JEditorPane htmlPane;
	
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
		
		ChangeListener updater = new ChangeListener();
		
		txa_source = new JTextArea();
		txa_source.getDocument().addDocumentListener(updater);
		contentPane.add(txa_source, CenterlessBorderLayout.WEST);
		
		JPanel southPane = new JPanel();
		southPane.setLayout(new GridLayout(1, 2));

		JButton btn_send = new JButton("Send");
		btn_send.setEnabled(false);
		btn_send.addActionListener(e -> Mailer.send(new io.Converter(txa_source.getText()).convertTextToHTML(), templateFile));
		
		JButton btn_select_template = new JButton("Select Template Email");
		btn_select_template.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.addActionListener(updater);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"HTML & Text Files", "html", "txt");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(MainFrame.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				templateFile = chooser.getSelectedFile().getAbsolutePath();
				btn_send.setEnabled(true);
			}
		});
		southPane.add(btn_select_template);
		southPane.add(btn_send);
		
		contentPane.add(southPane, CenterlessBorderLayout.SOUTH);
		
		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		JScrollPane htmlScroller = new JScrollPane(htmlPane);
		HTMLEditorKit kit = new HTMLEditorKit();
		htmlPane.setEditorKit(kit);
		htmlPane.setDocument(kit.createDefaultDocument());
		
		
		contentPane.add(htmlScroller, CenterlessBorderLayout.EAST);
		
		setContentPane(contentPane);
	}
	
	public class ChangeListener implements DocumentListener, ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {update();}
		@Override
		public void insertUpdate(DocumentEvent e) {update();}
		@Override
		public void removeUpdate(DocumentEvent e) {update();}
		@Override
		public void changedUpdate(DocumentEvent e) {update();}

		public void update() {
			byte[] encoded;
			try {
				String html;
				if(templateFile != null) {
					encoded = Files.readAllBytes(Paths.get(templateFile));
					html = new String(encoded, Charset.forName("UTF-8"));
					html = html.replace("<div class=\"emailcontent\"></div>", "<div class=\"emailcontent\">" + new io.Converter(txa_source.getText()).convertTextToHTML() +"</div>");
				} else
					html = new io.Converter(txa_source.getText()).convertTextToHTML();
				htmlPane.setText(html);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
