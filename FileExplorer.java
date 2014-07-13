
/*
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;

class newInteger {
	int s = 0;

	public newInteger(int i) {
		s = i;
	}

	public int getInteger() {
		return s;
	}

	public void setInteger(int e) {
		s = e;
	}
}

public class FileExplorer {
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */

	static private boolean inBounds(int mouseX, int mouseY, int x, int y,
			int width, int height) {
		if (mouseX > x && mouseX < x + width - 1 && mouseY > y
				&& mouseY < y + height - 1)
			return true;
		else
			return false;
	}

	public static int getFileSize(File folder) {
		System.out.println("Folder: " + folder.getName());
		int foldersize = 0;
		if (folder.isDirectory()) {
			File[] filelist = folder.listFiles();
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isDirectory()) {
					foldersize += getFileSize(filelist[i]);
				} else {
					foldersize += filelist[i].length();
				}
			}
		} else
			foldersize = 100;
		return foldersize;
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		final JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the ubiquitous "Hello World" label.
		JLabel label = new JLabel();
		frame.getContentPane().add(label);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		final ArrayList<File[]> directories = new ArrayList<>();
		File folder = new File("./");
		final File[] currentDirectory = folder.listFiles();
		directories.add(currentDirectory);
		for (int i = 0; i < currentDirectory.length; ++i) {
			if (currentDirectory[i].isFile()) {
				System.out.println(currentDirectory[i].getName());
			}
		}
		final newInteger s = new newInteger(0);
		final newInteger up = new newInteger(0);
		final JPanel pane = new JPanel() {
			public void paintComponent(Graphics g) {

				g.fillRect(0, 0, 1000, 1000);
				int x = s.getInteger();
				int y = up.getInteger();
				g.setColor(Color.white);
				for (int j = 0; j < directories.size(); ++j) {
					x = s.getInteger();
					int size = 0;
					for (int i = 0; i < directories.get(j).length; ++i) {
						size = (int) directories.get(j)[i].length() - 150;
						if (size > 200) {
							size = 200;
						}
						if (size < 100) {
							size = 100;
						}
						g.drawOval(x, y, size, size);
						g.setFont(new Font("SansSerif", Font.BOLD, 12));
						if (directories.get(j)[i].getName().length() > 12) {
							g.setFont(new Font("SansSerif", Font.BOLD, 10));
							char[] ope = directories.get(j)[i].getName()
									.toCharArray();
							String string = "";
							for (int re = 0; re < 12; ++re) {
								string += ope[re];
							}
							string += "...";
							g.drawString(string, x + size / 2 - 8, y + size / 2);
							g.setFont(new Font("SansSerif", Font.BOLD, 12));
							if (directories.get(j)[i].isDirectory()) {
								g.drawString("Is Folder", x + size / 2, y
										+ size / 2 + 10);
							} else {
								g.drawString("Is a file", x + size / 2, y
										+ size / 2 + 20);
							}
						} else {
							g.drawString(directories.get(j)[i].getName(), x
									+ size / 2 - 8, y + size / 2 + 5);

							g.setFont(new Font("SansSerif", Font.BOLD, 12));
							if (directories.get(j)[i].isDirectory()) {
								g.drawString("Is Folder", x + size / 2, y
										+ size / 2 + 20);
							} else {
								g.drawString("Is a file", x + size / 2, y
										+ size / 2 + 20);
							}
						}
						x += size;
					}

					y += 200;
				}
			}

		};
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				pane.grabFocus();
			}

		};
		Timer timer = new Timer(30, action);
		timer.start();
		final newInteger lastDirectoryNumber = new newInteger(0);
		pane.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					s.setInteger(s.getInteger() - 20);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					s.setInteger(s.getInteger() + 20);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					up.setInteger(up.getInteger() + 20);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					up.setInteger(up.getInteger() - 20);
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					s.setInteger(0);
				} else if (e.getKeyCode() == KeyEvent.VK_Z) {
					up.setInteger(0);
				} else if (e.getKeyCode() == KeyEvent.VK_X) {
					if (directories.size() > 1) {
						directories.remove(directories.size() - 1);
						pane.updateUI();
						pane.repaint();
						pane.resize(new Dimension(1000, 1000));
					}
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					String z = "";
					if (directories.size() - 1 != 0) {
						z = directories.get(directories.size() - 2)[lastDirectoryNumber
								.getInteger()].getName();
						z += "\\";
					} else {

					}
					System.out.println(z);
					String s = getFolderName(frame);
					File dir = new File("./" + z + s);
					dir.mkdir();

					File[] oldDirectory;
					System.out.println(directories.size() - 1);
					if (directories.size() - 1 != 0) {
						try {
							directories.remove(directories.size() - 1);
						} catch (Exception e1) {
							System.out.println("Could not delete File");
						}
						oldDirectory = new File(directories.get(directories
								.size() - 1)[lastDirectoryNumber.getInteger()]
								.getPath()).listFiles();
					} else {
						directories.remove(directories.size() - 1);
						File folder = new File("./");
						oldDirectory = folder.listFiles();
					}
					directories.add(oldDirectory);
				} else if (e.getKeyCode() == KeyEvent.VK_F) {
					String z = "";
					if (directories.size() - 1 != 0) {
						z = directories.get(directories.size() - 2)[lastDirectoryNumber
								.getInteger()].getName();
						z += "\\";
					} else {

					}

					String s = getFileName(frame);
					File dir = new File("./" + z + s);
					try {
						dir.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("File with that name already");
					}
					File[] oldDirectory;
					System.out.println(directories.size() - 1);
					if (directories.size() - 1 != 0) {
						try {
							directories.remove(directories.size() - 1);
						} catch (Exception e1) {
							System.out.println("Could not delete File");
						}
						oldDirectory = new File(directories.get(directories
								.size() - 1)[lastDirectoryNumber.getInteger()]
								.getPath()).listFiles();
					} else {
						directories.remove(directories.size() - 1);
						File folder = new File("./");
						oldDirectory = folder.listFiles();
					}
					directories.add(oldDirectory);
				}

				pane.repaint();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		pane.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

				int x = s.getInteger();
				int y = up.getInteger();
				int fileNumber = 0;
				for (int z = 0; z < directories.size(); ++z) {
					fileNumber = 0;
					x = s.getInteger();
					for (int i = 0; i < directories.get(z).length; ++i) {
						int size = (int) directories.get(z)[i].length() - 150;
						if (size > 200) {
							size = 200;
						}
						if (size < 100) {
							size = 100;
						}
						if (inBounds(e.getX(), e.getY(), x, y, size, size)) {
							if (e.isMetaDown()) {
								if (z == directories.size() - 1) {
									if (directories.get(z)[fileNumber].isFile() || true) {
										try {
											directories.get(z)[fileNumber]
													.delete();
											directories.remove(z);
										} catch (Exception e1) {
											System.out
													.println("Could not delete File");
										}
										File[] oldDirectory;
										if (z != 0) {
											oldDirectory = new File(
													directories.get(z - 1)[lastDirectoryNumber
															.getInteger()]
															.getPath())
													.listFiles();
										} else {
											File folder = new File("./");
											oldDirectory = folder.listFiles();
										}

										directories.add(oldDirectory);
									}
									pane.updateUI();
									pane.repaint();
									pane.resize(new Dimension(1000, 1000));
								}
							} else {
								System.out.println("In here" + x + " "
										+ fileNumber);
								System.out.println(directories.get(z)[fileNumber]
										.getName());
								if (z == directories.size() - 1)
									if (directories.get(z)[fileNumber]
											.isDirectory()) {
										File[] newDirectory = new File(
												directories.get(z)[fileNumber]
														.getPath()).listFiles();
										if (newDirectory.length == 0) {
											JOptionPane
													.showConfirmDialog(
															frame,
															"This Folder is Empty, you are now inside the empty folder",
															"Empty Folder",
															JOptionPane.PLAIN_MESSAGE);
										}
										directories.add(newDirectory);
										pane.repaint();
										lastDirectoryNumber
												.setInteger(fileNumber);

									} else if (directories.get(z)[fileNumber]
											.isFile()) {
										Desktop desktop = Desktop.getDesktop();
										try {
											desktop.open(directories.get(z)[fileNumber]);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
											System.out
													.println("Something went wrong");
										}
									}
							}
						}
						fileNumber += 1;
						x += size;
					}
					y += 200;
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		frame.getContentPane().add(pane);

		frame.pack();
		frame.resize(1000, 1000);

	}

	/**
	 * Prompt for and return the desired screen name.
	 */
	private static String getFolderName(Frame frame) {
		return JOptionPane.showInputDialog(frame, "Choose a Folder Name:",
				"Folder name selection", JOptionPane.PLAIN_MESSAGE);
	}

	private static String getFileName(Frame frame) {
		return JOptionPane.showInputDialog(frame, "Choose a File Name:",
				"Folder name selection", JOptionPane.PLAIN_MESSAGE);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
