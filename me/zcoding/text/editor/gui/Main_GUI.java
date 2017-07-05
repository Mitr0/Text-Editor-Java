package me.zcoding.text.editor.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import me.zcoding.text.editor.editing.LoadSaveManager;
import me.zcoding.text.editor.filesystem.FileManager;
import me.zcoding.text.editor.gui.syntaxHighlighting.ColoredKeyWord;
import me.zcoding.text.editor.gui.syntaxHighlighting.ColoredKeyWordList;
import me.zcoding.text.editor.gui.syntaxHighlighting.Colored_Doc;
import me.zcoding.text.editor.gui.syntaxHighlighting.DefaultSyntax;
import me.zcoding.text.editor.gui.syntaxHighlighting.PaneFileCompound;
import me.zcoding.text.editor.lang.Language;
import me.zcoding.text.editor.lang.LanguageManager;
import me.zcoding.text.editor.settings.Settings;

public class Main_GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// public final JScrollPane scrollPane = new JScrollPane();
	// public final JTextPane WritingArea = new JTextPane();
	public final JMenuBar menuBar = new JMenuBar();
	public final JMenu mnFiles = new JMenu("Loading...");
	public final JMenu mnSettings = new JMenu("Settings");
	public final JMenu mnLanguages = new JMenu("Languages");
	public final JMenuItem mntmDefault = new JMenuItem("Default");
	public final JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	public final JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern Unter");
	public final JMenu mnSyntax = new JMenu("Syntax");
	public final JMenuItem mntmCurrent = new JMenuItem("Text");
	public final JMenuItem mntmText = new JMenuItem("Text");
	public final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	public final JMenuItem mntmLaden = new JMenuItem("Laden");

	private List<PaneFileCompound> textPanes = new ArrayList<>();

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
		{
			setJMenuBar(this.menuBar);
		}
		{
			this.menuBar.add(this.mnFiles);
		}
		{
			this.mntmLaden.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						addTabToPane(file.getName(), LoadSaveManager.loadFile(file), file);
					}
				}
			});
			this.mnFiles.add(this.mntmLaden);
		}
		{
			this.mntmSpeichern.setAction(new SaveAction("menu_files_save", false,
					KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)));
			this.mntmSpeichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
			this.mnFiles.add(this.mntmSpeichern);
			this.mnFiles.add(this.mntmSpeichernUnter);
		}
		{
			this.menuBar.add(this.mnSettings);
		}
		{
			this.mnSettings.add(this.mnLanguages);
		}
		{
			this.mntmDefault.setEnabled(false);
			this.mnLanguages.add(mntmDefault);
			this.mnLanguages.addSeparator();
			// TODO: Better Language Handeling
			initLanguages(mnLanguages);
		}
		{
			this.mnSettings.add(this.mnSyntax);
			{
				this.mntmCurrent.setEnabled(false);
				this.mnSyntax.add(this.mntmCurrent);
			}
			this.mnSyntax.addSeparator();
			initSyntaxes(mnSyntax);
		}
		{
			this.mntmText.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					Settings.curSyntax = new DefaultSyntax();
				}
			});
			this.mnSyntax.add(this.mntmText);
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			contentPane.add(this.tabbedPane, BorderLayout.CENTER);
		}
		updateNames();
	}

	public void updateNames() {
		this.mnFiles.setText(LanguageManager.getFromLanguage("menu_files"));
		this.mnSettings.setText(LanguageManager.getFromLanguage("menu_settings"));
		this.mnLanguages.setText(LanguageManager.getFromLanguage("menu_settings_language"));
		this.mntmSpeichern.setText(LanguageManager.getFromLanguage("menu_files_save"));
		this.mntmSpeichernUnter.setText(LanguageManager.getFromLanguage("menu_files_saveas"));
		this.mnSyntax.setText(LanguageManager.getFromLanguage("menu_settings_syntax"));
		this.mntmLaden.setText(LanguageManager.getFromLanguage("menu_files_load"));
		this.mntmDefault.setText(Settings.curLanguage == null ? "Default" : Settings.curLanguage.getName());
	}

	public void addTabToPane(String name, List<String> data, File from) {
		Colored_Doc doc = new Colored_Doc();
		List<ColoredKeyWord> words = new ArrayList<ColoredKeyWord>();
		words = Settings.curSyntax.getColoredKeyWords();
		doc.setColoredKeyWords(words);
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		JTextPane textPane = new JTextPane();
		panel.setLayout(new BorderLayout(0, 0));
		textPane.setDocument(doc);
		scrollPane.setViewportView(textPane);
		String toAdd = "";
		for (String string : data) {
			toAdd += string + "\n";
		}
		textPane.setText(toAdd);
		panel.add(scrollPane, BorderLayout.CENTER);
		this.tabbedPane.addTab(name, panel);
		this.textPanes.add(new PaneFileCompound(from, textPane));
	}

	private void initSyntaxes(JMenu menu) {
		for (ColoredKeyWordList coloredKeyWordList : Settings.allSyntaxes) {
			JMenuItem item = new JMenuItem(coloredKeyWordList.getName());
			item.addMouseListener(new MouseAdapter() {
				private ColoredKeyWordList coloredKeyWord = coloredKeyWordList;

				@Override
				public void mouseReleased(MouseEvent e) {
					Settings.curSyntax = coloredKeyWord;
				}
			});
			menu.add(item);
		}
	}

	private void initLanguages(JMenu menu) {
		for (Language language : Settings.avaiableLanguages) {
			JMenuItem item = new JMenuItem(language.getName());
			item.addMouseListener(new MouseAdapter() {
				private Language tLanguage = language;

				@Override
				public void mouseReleased(MouseEvent e) {
					Settings.curLanguage = tLanguage;
					updateNames();
				}
			});
			menu.add(item);
		}
	}

	class SaveAction extends AbstractAction {

		private boolean saveAs = false;

		public SaveAction(String title, boolean as, KeyStroke acceleratorKey) {
			super(LanguageManager.getFromLanguage(title), null);
			putValue(ACCELERATOR_KEY, acceleratorKey);
			this.saveAs = as;
		}

		public void actionPerformed(ActionEvent ae) {
			if (!saveAs)
				save();
			else
				saveAs();
		}

		public void saveAs() {

		}

		public void save() {
			if (tabbedPane.getSelectedIndex() >= 0) {
				List<String> list = new ArrayList<>();
				FileManager.INSTANCE.write();
				PaneFileCompound compound = textPanes.get(tabbedPane.getSelectedIndex());
				for (String string : compound.getjTextPane().getText().split("\n")) {
					list.add(string);
				}
				LoadSaveManager.saveFile(list, compound.getFile());
			}
		}
	}
}
