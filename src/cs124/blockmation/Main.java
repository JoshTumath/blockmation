package cs124.blockmation;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Launches the application.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class Main {
	public static void main(String[] args) {
		// Get the user's language for localisation.
		Locale currentLocale;
		
		if (args.length != 2) {
			currentLocale = new Locale("en", "US");
		} else {
			currentLocale = new Locale(args[0], args[1]);
		}
		
		final ResourceBundle l10n =
			ResourceBundle.getBundle("LocalisationBundle", currentLocale);
		
		// Set the look and feel to a native system UI.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException
				|ClassNotFoundException
				|InstantiationException
				|IllegalAccessException e) {
			System.err.println(e);
		}
		
		// Open the window that plays the movies.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DisplayWindow displayWindow = new DisplayWindow(l10n);
				displayWindow.setVisible(true);
			}
		});
		
		// Open the window that edits the movies.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DirectorWindow directorWindow = new DirectorWindow(l10n);
				directorWindow.setVisible(true);
			}
		});
	}
}
