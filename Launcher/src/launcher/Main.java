package launcher;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.*;

class Config
{
	static int performancemode = 0;
	static int resolution_id = 0;
}

public class Main {

	static boolean saveConfig(int f)
	{
		try {
			Ini.setValue("performancemode",Integer.toString(Config.performancemode));
			Ini.setValue("windowresolution",Integer.toString(Config.resolution_id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		if (f == 1)
		{
			JOptionPane.showMessageDialog(
				    null, 
				    "Save successful!", 
				    "Info", 
				    JOptionPane.INFORMATION_MESSAGE
				);
		}
		return true;
	}
	
	static void loadDefault()
	{
		Config.performancemode = 0;
		Config.resolution_id = 0;
		if (saveConfig(0))
		{
			JOptionPane.showMessageDialog(
				    null, 
				    "Load successful!", 
				    "Info", 
				    JOptionPane.INFORMATION_MESSAGE
			);	
		}else {
			JOptionPane.showMessageDialog(
				    null, 
				    "UNKNOWN ERROR", 
				    "Error", 
				    JOptionPane.ERROR_MESSAGE
			);
		}
		return;
	}
	
	static void loadconfig()
	{
		File f = new File(Ini.filename);
		if(f.exists() && !f.isDirectory()) { 
			String tmpbuffer = "";
		    tmpbuffer = Ini.getValue("performancemode");
		    Config.performancemode = Integer.parseInt(tmpbuffer);
			tmpbuffer = "";
		    tmpbuffer = Ini.getValue("windowresolution");
		    Config.resolution_id = Integer.parseInt(tmpbuffer);
		}else {
			return;
		}
		return;
	}
	
    public static void main(String[] args) {
    	
    	loadconfig();

        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        float scaleFactor = dpi / 96f; // 96 DPI is standard
        int scaledButtonWidth = Math.round(180 * scaleFactor);
        int scaledButtonHeight = Math.round(30 * scaleFactor);
        int scaledFontSize = Math.round(14 * scaleFactor);
        
        JFrame frame = new JFrame("Launcher");
        JButton save = new JButton("Save Configuration");
        JButton defaultc = new JButton("Load Default Configuration");

        defaultc.addActionListener(e -> loadDefault());

        frame.setResizable(false);

        Font buttonFont = new Font("Arial", Font.PLAIN, scaledFontSize);
        save.setFont(buttonFont);
        defaultc.setFont(buttonFont);

        defaultc.setBounds(20, 200, scaledButtonWidth, scaledButtonHeight);
        save.setBounds(210, 200, scaledButtonWidth, scaledButtonHeight);

        frame.add(defaultc);

        String[] Quality_options = {"Quality", "Performance"};
        JComboBox<String> Quality_comboBox = new JComboBox<>(Quality_options);
        Quality_comboBox.setBounds(20, 50, Math.round(200 * scaleFactor), scaledButtonHeight);
        
        JLabel label2 = new JLabel("Video Modes:");
        label2.setBounds(20, 20, Math.round(200 * scaleFactor), scaledButtonHeight);
        label2.setFont(buttonFont);
        frame.add(label2);
        frame.add(Quality_comboBox);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = gd.getDisplayModes();
        Set<String> resolutionSet = new HashSet<>();

        for (DisplayMode mode : modes) {
            resolutionSet.add(mode.getWidth() + "x" + mode.getHeight());
        }

        List<String> resolutionList = new ArrayList<>(resolutionSet);

        resolutionList.sort((a, b) -> {
            String[] partsA = a.split("x");
            String[] partsB = b.split("x");
            int pixelsA = Integer.parseInt(partsA[0]) * Integer.parseInt(partsA[1]);
            int pixelsB = Integer.parseInt(partsB[0]) * Integer.parseInt(partsB[1]);
            return Integer.compare(pixelsB, pixelsA);
        });

        JLabel label1 = new JLabel("Resolutions:");
        label1.setBounds(20, 120, Math.round(200 * scaleFactor), scaledButtonHeight);
        label1.setFont(buttonFont);
        frame.add(label1);

        JComboBox<String> resolutionBox = new JComboBox<>(resolutionList.toArray(new String[0]));
        resolutionBox.setBounds(20, 160, Math.round(200 * scaleFactor), scaledButtonHeight);
        frame.add(resolutionBox);

        save.addActionListener(e -> {
            Config.performancemode = Quality_comboBox.getSelectedIndex();
            Config.resolution_id = resolutionBox.getSelectedIndex();
            saveConfig(1);
        });
        frame.add(save);

        if (Config.resolution_id >= 0 && Config.resolution_id < resolutionList.size()) {
            resolutionBox.setSelectedIndex(Config.resolution_id);
        }

        frame.setLayout(null);
        frame.setSize(Math.round(420 * scaleFactor), Math.round(300 * scaleFactor));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
