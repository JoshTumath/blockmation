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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

// TODO: Prompt the user to save their changes when closing the window
// TODO: Add a delete frame function

/**
 * A Swing window for editing each frame in a blockmation movie.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class DirectorWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5072452232013878620L;
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	// Menu bar
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenu colorMenu;
	private ButtonGroup colorOptions;
	private JRadioButtonMenuItem colorLightGray;
	private JRadioButtonMenuItem colorDarkGray;
	private JRadioButtonMenuItem colorRed;
	private JRadioButtonMenuItem colorGreen;
	private JRadioButtonMenuItem colorBlue;
	
	// Toolbar
	private JToolBar toolBar;
	private JButton newFrameButton;
	private JButton previousFrameButton;
	private JButton nextFrameButton;
	private JButton clearFrameButton;
	
	// Status bar
	private JLabel statusBar;
	
	// Content
	private FramesEditorCanvas framesCanvas;
	
	private JFileChooser fileChooser;
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/** Stores the localisation strings. */
	private ResourceBundle l10n;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructs a new director window with a menu bar, tool bar, content area
	 * and status bar.
	 * @param l10n a <code>ResourceBundle</code> containing the window's
	 *             localisation strings.
	 */
	public DirectorWindow(ResourceBundle l10n) {
		this.l10n = l10n;
		
		// Frame
		setTitle(l10n.getString("DirectorWindowTitle"));
		
		// Menu bar
		menuBar = createMenuBar();
		
		// Tool bar
		toolBar = createToolBar();
		
		// Frame canvas
		framesCanvas = new FramesEditorCanvas(new Model(0)); // Create a blank model for now
		
		// Status bar
		statusBar = new JLabel(l10n.getString("CurrentFrameStatus"));
		
		// Add the content to the frame
		setJMenuBar(menuBar);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(framesCanvas, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
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
		
		newMenuItem = new JMenuItem(l10n.getString("NewMenuItem"));
		newMenuItem.setToolTipText(l10n.getString("NewMenuItemToolTip"));
		newMenuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_N, toolkit.getMenuShortcutKeyMask()));
		newMenuItem.setActionCommand("new");
		newMenuItem.addActionListener(this);
		fileMenu.add(newMenuItem);
		
		openMenuItem = new JMenuItem(l10n.getString("OpenMenuItem"));
		openMenuItem.setToolTipText(l10n.getString("OpenMenuItemToolTip"));
		openMenuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_O, toolkit.getMenuShortcutKeyMask()));
		openMenuItem.setActionCommand("open");
		openMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		
		saveAsMenuItem = new JMenuItem(l10n.getString("SaveAsMenuItem"));
		saveAsMenuItem.setToolTipText(l10n.getString("SaveAsMenuItemToolTip"));
		saveAsMenuItem.setActionCommand("saveAs");
		saveAsMenuItem.addActionListener(this);
		fileMenu.add(saveAsMenuItem);
		
		fileMenu.addSeparator();
		
		exitMenuItem = new JMenuItem(l10n.getString("ExitMenuItem"));
		exitMenuItem.setActionCommand("exit");
		exitMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
		
		// Colours menu
		colorMenu = new JMenu(l10n.getString("ColorMenu"));
		menuBar.add(colorMenu);
		
		colorOptions = new ButtonGroup();
		
		colorLightGray = new JRadioButtonMenuItem(l10n.getString("ColorLightGray"));
		colorLightGray.setActionCommand("colorLightGray");
		colorLightGray.addActionListener(this);
		colorOptions.add(colorLightGray);
		colorMenu.add(colorLightGray);
		
		colorDarkGray = new JRadioButtonMenuItem(l10n.getString("ColorDarkGray"));
		colorDarkGray.setActionCommand("colorDarkGray");
		colorDarkGray.addActionListener(this);
		colorDarkGray.setSelected(true);
		colorOptions.add(colorDarkGray);
		colorMenu.add(colorDarkGray);
		
		colorRed = new JRadioButtonMenuItem(l10n.getString("ColorRed"));
		colorRed.setActionCommand("colorRed");
		colorRed.addActionListener(this);
		colorOptions.add(colorRed);
		colorMenu.add(colorRed);
		
		colorGreen = new JRadioButtonMenuItem(l10n.getString("ColorGreen"));
		colorGreen.setActionCommand("colorGreen");
		colorGreen.addActionListener(this);
		colorOptions.add(colorGreen);
		colorMenu.add(colorGreen);
		
		colorBlue = new JRadioButtonMenuItem(l10n.getString("ColorBlue"));
		colorBlue.setActionCommand("colorBlue");
		colorBlue.addActionListener(this);
		colorOptions.add(colorBlue);
		colorMenu.add(colorBlue);
		
		return menuBar;
	}
	
	/**
	 * Add the buttons to the toolBar object.
	 * @return a JToolBar containing the buttons
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		newFrameButton = new JButton(l10n.getString("NewFrameButton"));
		newFrameButton.setToolTipText(l10n.getString("NewFrameButtonToolTip"));
		newFrameButton.setEnabled(false);
		newFrameButton.setActionCommand("newFrame");
		newFrameButton.addActionListener(this);
		toolBar.add(newFrameButton);
		
		previousFrameButton = new JButton(l10n.getString("PreviousFrameButton"));
		previousFrameButton.setToolTipText(l10n.getString("PreviousFrameButtonToolTip"));
		previousFrameButton.setEnabled(false);
		previousFrameButton.setActionCommand("previousFrame");
		previousFrameButton.addActionListener(this);
		toolBar.add(previousFrameButton);
		
		nextFrameButton = new JButton(l10n.getString("NextFrameButton"));
		nextFrameButton.setToolTipText(l10n.getString("NextFrameButtonToolTip"));
		nextFrameButton.setEnabled(false);
		nextFrameButton.setActionCommand("nextFrame");
		nextFrameButton.addActionListener(this);
		toolBar.add(nextFrameButton);
		
		toolBar.addSeparator();
		
		clearFrameButton = new JButton(l10n.getString("ClearFrameButton"));
		clearFrameButton.setToolTipText(l10n.getString("ClearFrameButtonToolTip"));
		clearFrameButton.setEnabled(false);
		clearFrameButton.setActionCommand("clearFrame");
		clearFrameButton.addActionListener(this);
		toolBar.add(clearFrameButton);
		
		return toolBar;
	}
	
	/**
	 * Enables all of the control buttons that are used to interact with the
	 * frames canvas. This method should only be used when a new frames canvas
	 * is created or loaded.
	 */
	private void enableEditingButtons() {
		newFrameButton.setEnabled(true);
		previousFrameButton.setEnabled(true);
		nextFrameButton.setEnabled(true);
		
		colorMenu.setEnabled(true);
		clearFrameButton.setEnabled(true);
	}
	
	/**
	 * Update the status bar with text showing the current frame being viewed
	 * and the total number of frames.
	 */
	private void updateCurrentFrameInStatusBar() {
		// Example output: "Frame: 2 of 10"
		statusBar.setText(l10n.getString("CurrentFrameStatus") + " "
			+ (framesCanvas.getCurrentFrameId() + 1) + " "
			+ l10n.getString("CurrentFrameStatusOf") + " "
			+ framesCanvas.getModel().getTotalFrames());
	}
	
	/**
	 * Creates a new empty blockmation movie model. The user is prompted to
	 * enter the dimensions of the new model.
	 */
	private void createNewModel() {
		String dimensionsInput = (String) JOptionPane.showInputDialog(
			this,
			l10n.getString("NewModelDialog"),
			l10n.getString("NewModelDialogTitle"),
			JOptionPane.PLAIN_MESSAGE,
			null,
			null,
			"20"
		);
		
		try {
			int input = Integer.parseInt(dimensionsInput);
			
			if(input > 0 && input <= 100) {
				// Create a new model with a size that the user entered and pass
				// it into the canvas
				framesCanvas.setModel(new Model(input));
				framesCanvas.getModel().addNewFrame();
				framesCanvas.setCurrentFrameId(0);
				
				framesCanvas.repaint();
				
				// Enable the editing buttons if they're currently disabled due
				// to there not being a model to edit, yet
				enableEditingButtons();
				updateCurrentFrameInStatusBar();
			} else {
				JOptionPane.showMessageDialog(
					this,
					l10n.getObject("InvalidDimensionDialog"),
					l10n.getString("ErrorDialogTitle"),
					JOptionPane.ERROR_MESSAGE);
			}
		} catch(NumberFormatException e) {
			// Throw if the user has not entered a number
			if(dimensionsInput != null) {
				JOptionPane.showMessageDialog(
					this,
					l10n.getObject("InvalidDimensionDialog"),
					l10n.getString("ErrorDialogTitle"),
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Load in a blockmation movie model from a file. The user is prompted to
	 * select where the blockmation file is.
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
				enableEditingButtons();
				updateCurrentFrameInStatusBar();
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
	 * Save blockmation movie model currently open into a file. The user is
	 * prompted to select where the blockmation file should be saved.
	 */
	private void saveModel() {
		// Show the save dialog and save the file if the user selects a location
		if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File modelFile = fileChooser.getSelectedFile();
			
			try(ModelSaver modelSaver = new ModelSaver(modelFile, framesCanvas.getModel())) {
				// Get the model from the canvas and put it in a file
				modelSaver.save();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
					this,
					l10n.getString("SavingErrorDialog"),
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
	 * Creates a new empty frame at the end of the model.
	 */
	private void createNewFrame() {
		framesCanvas.createNewFrame();
		updateCurrentFrameInStatusBar();
	}
	
	/**
	 * Go to the previous frame. If the user is already viewing the first frame,
	 * nothing happens.
	 */
	private void goToPreviousFrame() {
		if(framesCanvas.getCurrentFrameId() > 0) {
			framesCanvas.goToPreviousFrame();
			updateCurrentFrameInStatusBar();
		}
	}
	
	/**
	 * Go to the next frame. If the user is already viewing the last frame,
	 * nothing happens.
	 */
	private void goToNextFrame() {
		if(framesCanvas.getCurrentFrameId() < framesCanvas.getModel().getTotalFrames() - 1) {
			framesCanvas.goToNextFrame();
			updateCurrentFrameInStatusBar();
		}
	}
	
	/**
	 * Set the type (i.e. colour) of block that the user will paint when they
	 * click on the canvas.
	 * @param block the type of block to be painted
	 */
	private void setBrush(Block block) {
		framesCanvas.setBrush(block);
	}
	
	/**
	 * Resets every block in the current frame to light grey.
	 */
	private void clearFrame() {
		framesCanvas.getModel().getFrame(framesCanvas.getCurrentFrameId()).clear();
		
		framesCanvas.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Menu bar
		if(command.equals("new")) {
			createNewModel();
		} else if(command.equals("open")) {
			loadModel();
		} else if(command.equals("saveAs")) {
			saveModel();
		} else if(command.equals("exit")) {
			exit();
		}
		
		else if(command.equals("colorLightGray")) {
			setBrush(Block.LIGHT_GRAY);
		} else if(command.equals("colorDarkGray")) {
			setBrush(Block.DARK_GRAY);
		} else if(command.equals("colorRed")) {
			setBrush(Block.RED);
		} else if(command.equals("colorGreen")) {
			setBrush(Block.GREEN);
		} else if(command.equals("colorBlue")) {
			setBrush(Block.BLUE);
		}
		
		// Toolbar
		else if(command.equals("newFrame")) {
			createNewFrame();
		} else if(command.equals("previousFrame")) {
			goToPreviousFrame();
		} else if(command.equals("nextFrame")) {
			goToNextFrame();
		} else if(command.equals("clearFrame")) {
			clearFrame();
		}
	}
}
