import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.*;

/**
 * A simple sound player. To start, create an instance of this class.
 * 
 * The sound player can play sound clips in WAV, AU and AIFF formats
 * with standard sample rates.
 * 
 * @author Michael Kolling and David J Barnes 
 * @version 1.0
 */
public class SoundPlayerGUI extends JFrame
    implements ListSelectionListener
{
    private static final String VERSION = "Version 1.0";
    
    private JList fileList;
    private JLabel infoLabel;
    private Player player;


    /**
     * Create a SoundPlayer and display its GUI on screen.
     */
    public SoundPlayerGUI()
    {
        super("kentSound");
        player = new Player();
        PlayList tracks = player.getPlayList();
        makeFrame(tracks);
    }

    /**
     * Play the sound file currently selected in the file list. If there is no
     * selection in the list, or if the selected file is not a sound file, 
     * do nothing.
     */
    private void play()
    {
        player.play();
    }

    /**
     * Display information about a selected sound file (name and clip length).
     * @param message The message to display.
     */
    private void showInfo(String message)
    {
        infoLabel.setText(message);
    }
    
    /**
     * Stop the currently playing sound file (if there is one playing).
     */
    private void stop()
    {
        player.stop();
    }

    /**
     * Quit function: quit the application.
     */
    private void quit()
    {
        System.exit(0);
    }
    
    
    /**
     * About function: show the 'about' box.
     */
    private void showAbout()
    {
        String text = "kentSound\n" + VERSION;

// CHALLENGE TASK: REMOVE THE LINE ABOVE, AND UNCOMMENT THE FOLLOWING 4 LINES:
//         String text = "kSound\n" + VERSION + "\n"
//                       + "Total tracks played: " + player.getNumberOfTracksPlayed() + "\n"
//                       + "Total track time: " + player.getTotalPlayedTrackLength() + "\n"
//                       + "Average track time: " + player.averageTrackLength();

        JOptionPane.showMessageDialog(this, 
                    text,
                    "About SoundPlayer", 
                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    // --- ListSelectionListener interface ---
    
    /**
     * List selection changed. Called when the user select an entry in the track list.
     */
    public void valueChanged(ListSelectionEvent evt)
    {
        int selected = fileList.getSelectedIndex();
        if(selected != -1) {
            player.setTrack(selected);
        }
        showInfo(player.getTrackInfo());
    }
    
    // ---- Swing stuff to build the frame and all its components and menus ----
    
    /**
     * Create the complete application GUI.
     * @param audioFiles The file names to display.
     */
    private void makeFrame(PlayList tracks)
    {
        // the following makes sure that our application exits when
        // the user closes its window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 10, 10, 10));

        makeMenuBar();
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(8, 8));

        // Create the left side with combobox and scroll list
        JPanel leftPane = new JPanel();
        {
            leftPane.setLayout(new BorderLayout(8, 8));

            // Create the scrolled list for file names
            fileList = new JList(tracks.asStrings());
            fileList.setForeground(new Color(212,212,255));
            fileList.setBackground(new Color(0,85,150));
            fileList.setSelectionBackground(new Color(212,212,255));
            fileList.setSelectionForeground(new Color(0,45,75));
            fileList.addListSelectionListener(this);
            JScrollPane scrollPane = new JScrollPane(fileList);
            scrollPane.setColumnHeaderView(new JLabel("Tracks"));
            leftPane.add(scrollPane, BorderLayout.CENTER);
        }
        contentPane.add(leftPane, BorderLayout.CENTER);

        // Create the center with image, text label, and slider
       JPanel centerPane = new JPanel();
        {
            centerPane.setLayout(new BorderLayout(8, 8));
    
            JLabel image = new JLabel(new ImageIcon("title.jpg"));
            centerPane.add(image, BorderLayout.NORTH);
            centerPane.setBackground(Color.WHITE);

            infoLabel = new JLabel("  ");
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setForeground(new Color(0,85,150));
            centerPane.add(infoLabel, BorderLayout.CENTER);

            centerPane.add(new JLabel("  "), BorderLayout.SOUTH);
        }
        contentPane.add(centerPane, BorderLayout.EAST);

        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        {
            toolbar.setLayout(new GridLayout(1, 0));
  
            JButton button = new JButton("Play");
            button.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) { play(); }
                               });
            toolbar.add(button);
            
            button = new JButton("Stop");
            button.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) { stop(); }
                               });
            toolbar.add(button);
        }
        
        contentPane.add(toolbar, BorderLayout.NORTH);

        // building is done - arrange the components      
        pack();
        
        // place this frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2 - getWidth()/2, d.height/2 - getHeight()/2);
        setVisible(true);
    }
    
    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar()
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        
        item = new JMenuItem("About SoundPlayer...");
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { showAbout(); }
                           });
        menu.add(item);
    }
}
