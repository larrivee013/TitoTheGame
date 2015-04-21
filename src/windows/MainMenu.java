package windows;

import RunningClasses.RunGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * This class creates a new main menu so the game can be started.
 * 
 * @author Keven-Matthew
 */
public class MainMenu extends JPanel implements ActionListener{

	/**
	 * This holds the image for the specific buttons.
	 */
	private BufferedImage playButtonIcon, playButtonHighlightIcon, newGameButtonIcon, newGameHighlightButtonIcon, titleImage;

	/**
	 * Holds the background image for the main menu.
	 */
	private Image backgroundImage = null;

	/**
	 * The song used in the main menu.
	 */
	private Clip menuSong;

	/**
	 * These are the variables for the "play" and "new game" buttons.
	 */
	private static JButton playButton, newGameButton, exitButton, fullScreenButton, musicButton;

	private Timer t = new Timer(100, this);
	private int yTitle = 20;
	boolean direction = true;
	boolean isFullScreen = false;
	
	/**
	 * Creates a new main menu.
	 */
	public MainMenu() {
		
		setLayout(null);

		/**
		 * This is the mouse listener to make the buttons change when the mouse hovers over them.
		 * @author Keven-Matthew
		 *
		 */
		class ButtonListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				setCursor(cursor);
				if ((JButton) e.getSource() == playButton) {
					MainFrame.getTl().changeButtonImage(playButton, MainFrame.getTl().mainMenuPlayButtonHighlightTexture);
				} else if ((JButton) e.getSource() == newGameButton) {
					MainFrame.getTl().changeButtonImage(newGameButton, MainFrame.getTl().mainMenuNewGameButtonHighlightTexture);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
				setCursor(cursor);
				if ((JButton) e.getSource() == playButton) {
					MainFrame.getTl().changeButtonImage(playButton, MainFrame.getTl().mainMenuPlayButtonTexture);
				} else if ((JButton) e.getSource() == newGameButton) {
					MainFrame.getTl().changeButtonImage(newGameButton, MainFrame.getTl().mainMenuNewGameButtonTexture);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		}
		
		playButton = new JButton(new ImageIcon(MainFrame.getTl().mainMenuPlayButtonTexture));
		playButton.setBorder(BorderFactory.createEmptyBorder());
		playButton.setContentAreaFilled(false);
		playButton.addMouseListener(new ButtonListener());
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) MainFrame.getMenus().getLayout();
				cardLayout.show(MainFrame.getMenus(), MainFrame.getLevelselectpanel());
			}
		});
		playButton.addComponentListener(new ButtonResizeListener());

		newGameButton = new JButton(new ImageIcon(MainFrame.getTl().mainMenuNewGameButtonTexture));
		newGameButton.setBorder(BorderFactory.createEmptyBorder());
		newGameButton.setContentAreaFilled(false);
		newGameButton.addMouseListener(new ButtonListener());
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO actually delete the old save file and create a new one
				//JOptionPane.showConfirmDialog(null,"Creating a new game will create a new save file. Previous progress will be lost. Do you want to continue?", "Warning!", JOptionPane.YES_NO_OPTION);
				JOptionPane.showConfirmDialog(null, getPanel(), "WARNING!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				CardLayout cardLayout = (CardLayout) MainFrame.getMenus().getLayout();
				cardLayout.show(MainFrame.getMenus(), MainFrame.getLevelselectpanel());
			}

			private JPanel getPanel() {
		        JPanel panel = new JPanel();
		        JLabel label = new JLabel("Starting a new game will erase any previous save file. Are you sure you want to continue?");
		        ImageIcon image = null;
		        try {
		            image = new ImageIcon(ImageIO.read(new File("Resources/titoStepOne.png")));
		        } catch(IOException ioe) {
		            ioe.printStackTrace();
		        } 

		        label.setIcon(image);
		        panel.add(label);
		        
		        return panel;
		    }


		});
		newGameButton.addComponentListener(new ButtonResizeListener());

		exitButton = new JButton();
		exitButton.setBorder(BorderFactory.createEmptyBorder());
		exitButton.setContentAreaFilled(false);
		exitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = MainFrame.getLevels().length-1; i>=0; i--){
					if (MainFrame.getLevels()[i].hasBeenCompleted()){
						try {
							PrintWriter writer = new PrintWriter(new File("Resources/gameSave.sav"));
							writer.print(i+1);
							writer.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				System.exit(0);		
			}
		});
		
		
		
		fullScreenButton = new JButton();
		fullScreenButton.setBorder(BorderFactory.createEmptyBorder());
		fullScreenButton.setContentAreaFilled(false);
		fullScreenButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!isFullScreen){
					RunGame.getFrame().dispose();
					RunGame.getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
					RunGame.getFrame().setVisible(true);
					isFullScreen = true;
					RunGame.setgUnit(RunGame.getFrame().getWidth()/5);
				}
					
				else{
					RunGame.getFrame().dispose();
					RunGame.getFrame().setVisible(true);
					RunGame.getFrame().setSize(new Dimension(1280,720));
					RunGame.getFrame().setLocationRelativeTo(null);
					isFullScreen = false;
					RunGame.setgUnit(RunGame.getFrame().getWidth()/5);
				}
			}
		});
		
		musicButton = new JButton(new ImageIcon(MainFrame.getTl().mainMenuMusicButtonTexture));
		musicButton.setBorder(BorderFactory.createEmptyBorder());
		musicButton.setContentAreaFilled(false);
		musicButton.addMouseListener(new ButtonListener());
		musicButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuSong.isActive()){
					MainFrame.getTl().changeButtonImage(musicButton, MainFrame.getTl().mainMenuMusicButtonTexture2);
					menuSong.close();
				}
				else{
					MainFrame.getTl().changeButtonImage(musicButton, MainFrame.getTl().mainMenuMusicButtonTexture);
					startMenuMusic();
				}
			}
		});
		musicButton.addComponentListener(new ButtonResizeListener());
		
		add(playButton);
		add(newGameButton);
		add(exitButton);
		add(fullScreenButton);
		add(musicButton);
		startMenuMusic();
		t.start();
	}

	/**
	 * Starts the music in the main menu.
	 */
	public void startMenuMusic() {
		try {
			menuSong = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("Resources/Music/callToAdventure.wav").getAbsoluteFile());
			menuSong.open(ais);
			menuSong.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method changes the size of the image for a specific button. 
	 * @param jbutton
	 * @param img
	 */
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(MainFrame.getTl().mainMenuBackgroundTexture, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(MainFrame.getTl().mainMenuTitleTexture, 81*getWidth()/1280, yTitle*getHeight()/720, MainFrame.getTl().mainMenuTitleTexture.getWidth()*getWidth()/1280, MainFrame.getTl().mainMenuTitleTexture.getHeight()*getHeight()/720, null);
		playButton.setBounds(541*getWidth()/1280, 398*getHeight()/720, 232*getWidth()/1280, 69*getHeight()/720);
		newGameButton.setBounds(541*getWidth()/1280, 498*getHeight()/720, 232*getWidth()/1280, 69*getHeight()/720);
		exitButton.setBounds(1256*getWidth()/1280, 10*getHeight()/720, 14*getWidth()/1280, 14*getHeight()/720);
		fullScreenButton.setBounds(1235*getWidth()/1280, 10*getHeight()/720, 14*getWidth()/1280, 14*getHeight()/720);
		musicButton.setBounds(10*getWidth()/1280, 10*getHeight()/720, 25*getWidth()/1280, 25*getHeight()/720);
		
		g.setColor(new Color(204, 213, 187));
		// draws the exit button
		g.drawLine(1270*getWidth()/1280, 10*getHeight()/720, 1256*getWidth()/1280, 24*getHeight()/720);
		g.drawLine(1256*getWidth()/1280, 10*getHeight()/720, 1270*getWidth()/1280, 24*getHeight()/720);
		
		//draws the fullscreen button
		g.fillRect(1235*getWidth()/1280, 10*getHeight()/720, 14*getWidth()/1280, 14*getHeight()/720);
		
		g.setColor(new Color(255, 0, 0, 128));
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		
		if(direction){
			yTitle++;
		}
		else {
			yTitle--;
		}
		if(yTitle == 20 || yTitle == 30){
			direction =!direction;
		}
		
		repaint();
	}
	
	public static JButton getPlayButton() {
		return playButton;
	}

	public static JButton getNewGameButton() {
		return newGameButton;
	}
	
	public static JButton getMusicButton(){
		return musicButton;
	}
}
