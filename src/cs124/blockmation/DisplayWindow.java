package cs124.blockmation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A Swing window for playing back each frame in a blockmation movie.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class DisplayWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4458887816968245518L;
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	//Menu bar
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem openMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenu speedMenu;
	private ButtonGroup speedOptions;
	private JRadioButtonMenuItem speedSlow;
	private JRadioButtonMenuItem speedMedium;
	private JRadioButtonMenuItem speedFast;
	
	// Toolbar
	private JToolBar toolBar;
	private JButton playButton;
	private JButton stopButton;
	
	// Content
	private FramesPlayerCanvas framesCanvas;
	private Thread animation;
	
	private JFileChooser fileChooser;
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/** Stores the localisation strings. */
	private ResourceBundle l10n;
	
	// TODO: Use Neil's LoadRecent library
	

	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	public DisplayWindow(ResourceBundle l10n) {
		this.l10n = l10n;
		
		// Frame
		setTitle(l10n.getString("DisplayWindowTitle"));
		
		// Menu bar
		menuBar = createMenuBar();
		
		// Toolbar
		toolBar = createToolBar();
		
		// Frame canvas
		framesCanvas = new FramesPlayerCanvas(new Model(0));
		animation = new Thread(framesCanvas);
		
		// Add the content to the frame
		setJMenuBar(menuBar);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(framesCanvas, BorderLayout.CENTER);
		
		// File chooser
		fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(
			// Only allow .txt files to be chosen
			new FileNameExtensionFilter("Blockmation file (*.txt)", "txt"));
		
		// Final frame settings
		setMinimumSize(new Dimension(500, 550));
		setLocationByPlatform(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Add the menus and menu items to the menu bar.
	 * @return a JMenuBar containing the menus
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// File menu
		fileMenu = new JMenu(l10n.getString("FileMenu"));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		openMenuItem = new JMenuItem(l10n.getString("OpenMenuItem"));
		openMenuItem.setToolTipText(l10n.getString("OpenMenuItemToolTip"));
		openMenuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_O, toolkit.getMenuShortcutKeyMask()));
		openMenuItem.setActionCommand("open");
		openMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		
		fileMenu.addSeparator();
		
		exitMenuItem = new JMenuItem(l10n.getString("ExitMenuItem"));
		exitMenuItem.setActionCommand("exit");
		exitMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
		
		// Speed menu
		speedMenu = new JMenu(l10n.getString("SpeedMenu"));
		menuBar.add(speedMenu);
		
		speedOptions = new ButtonGroup();
		
		speedSlow = new JRadioButtonMenuItem(l10n.getString("SlowMenuItem"));
		speedSlow.setActionCommand("speedSlow");
		speedSlow.addActionListener(this);
		speedOptions.add(speedSlow);
		speedMenu.add(speedSlow);
		
		speedMedium = new JRadioButtonMenuItem(l10n.getString("MediumMenuItem"));
		speedMedium.setActionCommand("speedMedium");
		speedMedium.addActionListener(this);
		speedMedium.setSelected(true);
		speedOptions.add(speedMedium);
		speedMenu.add(speedMedium);
		
		speedFast = new JRadioButtonMenuItem(l10n.getString("FastMenuItem"));
		speedFast.setActionCommand("speedFast");
		speedFast.addActionListener(this);
		speedOptions.add(speedFast);
		speedMenu.add(speedFast);
		
		return menuBar;
	}
	
	/**
	 * Add the buttons to the toolBar object.
	 * @return a JToolBar containing the buttons
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar(); 
		toolBar.setFloatable(false);
		
		playButton = new JButton(l10n.getString("PlayButton"));
		playButton.setToolTipText(l10n.getString("PlayButtonToolTip"));
		playButton.setEnabled(false);
		playButton.setActionCommand("play");
		playButton.addActionListener(this);
		toolBar.add(playButton);
		
		stopButton = new JButton(l10n.getString("StopButton"));
		stopButton.setToolTipText(l10n.getString("StopButtonToolTip"));
		stopButton.setEnabled(false);
		stopButton.setActionCommand("stop");
		stopButton.addActionListener(this);
		toolBar.add(stopButton);
		
		return toolBar;
	}
	
	/**
	 * Enable the play and stop buttons.
	 */
	private void enableControlButtons() {
		playButton.setEnabled(true);
		stopButton.setEnabled(true);
	}
	
	/**
	 * Let the user select a .blockmation file from their file system to create
	 * a new model.
	 */
	private void loadModel() {
		// Show the open dialog and load the file if the user selects a file
		if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			// Store the location of the file that the user selected
			File modelFile = fileChooser.getSelectedFile();
			
			try (ModelLoader modelLoader = new ModelLoader(modelFile)) {
				// Load in the model and pass it into the canvas
				framesCanvas.setModel(modelLoader.load());
				framesCanvas.setCurrentFrameId(0);
				
				framesCanvas.repaint();

				// Enable the editing buttons if they're currently disabled due to
				// there not being a model to edit, yet
				enableControlButtons();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
					this,
					l10n.getString("FileNotFoundDialog"),
					l10n.getString("ErrorDialogTitle"),
					JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidBlockmationFileException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
					this,
					l10n.getString("InvalidFileDialog"),
					l10n.getString("ErrorDialogTitle"),
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Close the window.
	 */
	private void exit() {
		dispose();
	}
	
	/**
	 * Set the delay between each frame in the movie being displayed.
	 * 
	 * The constants SLOW_PLAYBACK, MEDIUM_PLAYBACK and FAST_PLAYBACK contain
	 * the most commonly used values.
	 * @param playbackSpeed the speed of the playback in milliseconds
	 */
	private void setPlaybackSpeed(int playbackSpeed) {
		framesCanvas.setPlaybackSpeed(playbackSpeed);
	}
	
	/**
	 * Play the movie stored in the model.
	 */
	private void play() {
		animation = new Thread(framesCanvas);
		animation.start();
	}
	
	/**
	 * Stop the movie stored in the model.
	 */
	private void stop() {
		animation.interrupt();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Menu bar
		if(command.equals("open")) {
			loadModel();
		} else if(command.equals("exit")) {
			exit();
		} else if(command.equals("speedSlow")) {
			setPlaybackSpeed(FramesPlayerCanvas.SLOW_PLAYBACK);
		} else if(command.equals("speedMedium")) {
			setPlaybackSpeed(FramesPlayerCanvas.MEDIUM_PLAYBACK);
		} else if(command.equals("speedFast")) {
			setPlaybackSpeed(FramesPlayerCanvas.FAST_PLAYBACK);
		}
		
		// Tool bar
		else if(command.equals("play")) {
			// Don't play the animation again if it's already playing
			if(!animation.isAlive()) {
				play();
			}
		} else if(command.equals("stop")) {
			stop();
		}
	}
}
