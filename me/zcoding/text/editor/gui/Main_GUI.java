package me.zcoding.text.editor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import me.zcoding.text.editor.editing.LoadSaveManager;
import me.zcoding.text.editor.gui.syntaxHighlighting.ColoredKeyWordList;
import me.zcoding.text.editor.gui.syntaxHighlighting.PaneFileCompound;
import me.zcoding.text.editor.lang.Language;
import me.zcoding.text.editor.settings.Settings;

public class Main_GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public final JMenuBar menuBar = new JMenuBar();
	public final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	private List<PaneFileCompound> textPanes;

	public final JMenu mnDatei = new JMenu("Datei");
	public final JMenuItem mntmNeu = new JMenuItem("Neu");
	public final JMenuItem mntmffnen = new JMenuItem("÷ffnen");
	public final JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	public final JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern Unter");
	public final JMenuItem mntmAllesSpeichern = new JMenuItem("Alles Speichern");
	public final JMenuItem mntmSchlieen = new JMenuItem("Schlieﬂen");
	public final JMenuItem mntmAllesSchlieen = new JMenuItem("Alles Schlieﬂen");
	public final JMenuItem mntmBeenden = new JMenuItem("Beenden");

	public List<PaneFileCompound> getTextPanes() {
		return textPanes;
	}

	public static Main_GUI INSTANCE;

	public void init() {
		try {
			Main_GUI frame = new Main_GUI();
			frame.setVisible(true);
			INSTANCE = frame;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Main_GUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main_GUI.class.getResource("/assets/icon/pencil (2).png")));
		setMinimumSize(new Dimension(300, 400));
		setBackground(Color.WHITE);
		setTitle("SupEdit");
		textPanes = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 557);
		{
			this.menuBar.setBackground(Color.WHITE);
			this.menuBar.setOpaque(false);
			setJMenuBar(this.menuBar);
		}
		{
			this.mnDatei.setBackground(Color.WHITE);
			this.menuBar.add(this.mnDatei);
		}
		{
			this.mntmNeu.setAction(new NewAction("Neu", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)));
			this.mnDatei.add(this.mntmNeu);
		}
		{
			this.mntmffnen
					.setAction(new OpenAction("÷ffnen", KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)));
			this.mnDatei.add(this.mntmffnen);
		}
		{
			this.mntmSpeichern.setAction(
					new SaveAction("Speichern", false, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)));
			this.mnDatei.add(this.mntmSpeichern);
		}
		{
			this.mntmSpeichernUnter.setAction(new SaveAction("Speichern Unter", true,
					KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)));
			this.mnDatei.add(this.mntmSpeichernUnter);
		}
		{
			this.mnDatei.add(this.mntmAllesSpeichern);
			this.mntmAllesSpeichern.setAction(new SaveAction("Alles Speichern", false, null));
		}
		{
			this.mntmSchlieen.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (tabbedPane.getTabCount() > 0) {
						save();
						closeTab(tabbedPane.getSelectedIndex());
					}
				}
			});
			this.mnDatei.add(this.mntmSchlieen);
		}
		{
			this.mntmAllesSchlieen.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (tabbedPane.getTabCount() > 0) {
						for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
							tabbedPane.setSelectedIndex(i);
							save();
							closeTab(tabbedPane.getSelectedIndex());
						}
					}
				}
			});
			this.mnDatei.add(this.mntmAllesSchlieen);
		}
		this.mnDatei.addSeparator();
		{
			this.mntmBeenden.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					int returnID = JOptionPane.showConfirmDialog(null, "Sicher", "Beenden", JOptionPane.YES_NO_OPTION);
					if (returnID == JOptionPane.OK_OPTION) {
						System.exit(0);
					}
				}
			});
			this.mnDatei.add(this.mntmBeenden);
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

	private void closeTab(int tab) {
		textPanes.get(tab).stopTimer();
		textPanes.remove(tab);
		tabbedPane.remove(tab);
	}

	public void openFile() {
		JFileChooser chooser = new JFileChooser();
		int jFileChooserReturn = chooser.showOpenDialog(null);
		if (jFileChooserReturn == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			addTabToPane(selectedFile.getName(), LoadSaveManager.loadFile(selectedFile), selectedFile);
			tabbedPane.setSelectedIndex(textPanes.size() - 1);
		}
	}

	/**
	 * Updates all names when Language changed
	 */
	public void updateNames() {
	}

	private int tabCount = -1;

	public void addTabToPane(String name, List<String> data, File from) {
		PaneFileCompound compound = new PaneFileCompound(from, Settings.allSyntaxes.get(0));
		compound.setContent(data);
		this.tabbedPane.addTab(name, compound.getPanel());
		this.textPanes.add(compound);
	}

	public void addEmptyFile() {
		tabCount++;
		addTabToPane("neu " + tabCount, new ArrayList<String>(), null);
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
		private boolean all = false;

		public SaveAction(String title, boolean as, KeyStroke acceleratorKey, boolean all) {
			super(title, null);
			putValue(ACCELERATOR_KEY, acceleratorKey);
			this.saveAs = as;
			this.all = all;
		}

		public SaveAction(String title, boolean as, KeyStroke acceleratorKey) {
			this(title, as, acceleratorKey, false);
		}

		public void actionPerformed(ActionEvent ae) {
			if (!saveAs)
				save();
			else
				saveAs();
			if (all) {
				saveAll();
			}
		}
	}

	class NewAction extends AbstractAction {

		public NewAction(String title, KeyStroke acceleratorKey) {
			super(title, null);
			putValue(ACCELERATOR_KEY, acceleratorKey);
		}

		public void actionPerformed(ActionEvent ae) {
			addEmptyFile();
		}
	}

	class OpenAction extends AbstractAction {

		public OpenAction(String title, KeyStroke acceleratorKey) {
			super(title, null);
			putValue(ACCELERATOR_KEY, acceleratorKey);
		}

		public void actionPerformed(ActionEvent ae) {
			openFile();
		}
	}

	public void saveAll() {
		if (tabbedPane.getTabCount() > 0) {
			for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
				tabbedPane.setSelectedIndex(i);
				save();
			}
		}
	}

	public void save(int selectedTab) {
		if (tabbedPane.getSelectedIndex() >= 0) {
			List<String> list = new ArrayList<>();
			PaneFileCompound compound = textPanes.get(selectedTab);
			for (String string : compound.getContent().split("\n")) {
				list.add(string);
			}
			try {
				if (compound.getFile() != null)
					LoadSaveManager.saveFile(list, compound.getFile());
				else
					saveAs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		save(tabbedPane.getSelectedIndex());
	}

	private void saveAs() {
		if (tabbedPane.getTabCount() > 0) {
			JFileChooser chooser = new JFileChooser();
			int returnType = chooser.showSaveDialog(null);
			if (returnType == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				if (file.exists()) {
					file.delete();
				}
				List<String> list = new ArrayList<>();
				PaneFileCompound compound = textPanes.get(tabbedPane.getSelectedIndex());
				for (String string : compound.getContent().split("\n")) {
					list.add(string);
				}
				try {
					LoadSaveManager.saveFile(list, file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
				compound.setFile(file);
			}
		}
	}
}
