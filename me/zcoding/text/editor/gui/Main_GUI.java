package me.zcoding.text.editor.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import me.zcoding.text.editor.lang.LanguageManager;

public class Main_GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private Map<String, Color> words = new HashMap<String, Color>();

	private Style doc = new Style();

	public void init() {
		try {
			Main_GUI frame = new Main_GUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Main_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 557);

		words.put("hallo", Color.RED);

		Color comment = new Color(63, 197, 95);

		Color javadoc = new Color(63, 95, 191);

		Color annotation = new Color(100, 100, 100);

		doc.setCommentColor(comment);

		doc.setJavadocColor(javadoc);

		doc.setAnnotationColor(annotation);

		doc.setKeywords(words);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnEinstellungen = new JMenu(LanguageManager.getFromLanguage("menu_datei"));
		menuBar.add(mnEinstellungen);

		JMenuItem save_btn = new JMenuItem("Speichern");
		mnEinstellungen.add(save_btn);

		JMenuItem save_as_btn = new JMenuItem("Speichern als");
		mnEinstellungen.add(save_as_btn);

		JMenu mnEinstellungen_1 = new JMenu("Einstellungen");
		menuBar.add(mnEinstellungen_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		textPane.setDocument(doc);
		scrollPane.setViewportView(textPane);
	}
}
