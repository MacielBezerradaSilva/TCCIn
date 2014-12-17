/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.SessionController;
import factory.XMLFileManagerFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import management.ActionListenerGenerator;
import management.RequestManager;
import model.ExploratoryTestCase;
import model.Session;
import threads.DateThread;
import threads.LoadSheetThread;
import threads.ObjectCleanerThread;

/**
 *
 * @author rparaujo
 */
public class EntryPoint extends JFrame {

    public final int NEW_SESSION_PANEL = 1;
    private JPanel contentPanel;
    private JPanel panel;
    private RequestManager requestManager;
    private ActionListenerGenerator actionListenerGenerator;
    private static String language;
    private int languageSelection;
    private JLabel loadingLabel;
    private JLabel loadingIconLabel;
    private JPanel loadingPanel;
    private ObjectCleanerThread oct;
    private JMenuBar labelMenu;
    private boolean documentLoaded;
    private int listIndex;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JList tcList;
    private List<ExploratoryTestCase> loadedTestCases;
    private JPanel currentPanel;

    //Esse negoico nao devia ta aqui...
    //É uma feature. (:
    public static String getLanguage() {
        if (language == null) {
            language = "EN";
        }
        return language;
    }

    public EntryPoint() {

        oct = new ObjectCleanerThread();
        oct.start();

        documentLoaded = false;
        requestManager = new RequestManager(this);
        actionListenerGenerator = new ActionListenerGenerator(this);

        File languageFile = new File("language.xml");
        XMLFileManagerFactory xMLFileManagerFactory = XMLFileManagerFactory.getInstance();

        if (!languageFile.exists() || (languageFile.exists() && !xMLFileManagerFactory.isLanguageRedefinition())) {

            if (chooseLanguage().contains("PT")) {
                language = "BR";
                languageSelection = 0;
            } else {
                language = "EN";
                languageSelection = 1;
            }
            xMLFileManagerFactory.createLanguageFile(language, false);

        } else {
            language = xMLFileManagerFactory.readLanguage();
            languageSelection = (language.equalsIgnoreCase("BR") ? 0 : 1);
            xMLFileManagerFactory.createLanguageFile(language, false);
        }

        oct.addObject(this);
    }

    public void initialize() {
        createFrame();
    }

    public String chooseLanguage() {

        String[] options = {"Português (PT-BR)", "English (EN-US)"};
        String result = (String) JOptionPane.showInputDialog(this,
                "Escolha seu idioma:\n", "Gerenciador de Testes Exploratórios",
                JOptionPane.INFORMATION_MESSAGE, null, options,
                options[0]);
        String r = "";

        if (result != null) {

            if (result.isEmpty()) {
                exitApplication();
            } else {
                r = result;
            }
        } else {
            System.exit(0);
        }

        return r;
    }

    public void createFrame() {

        this.setTitle(requestManager.loadProperty(language + "_Application_Title"));
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                EntryPoint.this.update(EntryPoint.this.getGraphics());
            }
        });

        this.setContentPane(createMainPanel());
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File("images/frame_icon.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setIconImage(image);
        this.setVisible(true);
        this.setEnabled(true);

    }

    public void restartApplication(int choice) {

        if (choice == languageSelection) {
            return;
        }

        int decision = JOptionPane.showConfirmDialog(this,
                requestManager.loadProperty(language + "_Restart_Application"),
                requestManager.loadProperty(language + "_Language_Change"),
                JOptionPane.YES_NO_OPTION);

        if (decision == 0) {

            if (choice == 0) {
                language = "BR";
                languageSelection = 0;
            } else {
                language = "EN";
                languageSelection = 1;
            }

            XMLFileManagerFactory xMLFileManagerFactory = XMLFileManagerFactory.getInstance();
            xMLFileManagerFactory.createLanguageFile(language, true);
            rebuildInterface();
        }

    }

    public void rebuildInterface() {
        this.setEnabled(false);
        this.setVisible(false);
        this.dispose();
        EntryPoint newInstance = new EntryPoint();
        newInstance.initialize();
    }

    public void exitApplication() {
        System.exit(0);
    }

    public JPanel createMainPanel() {
        panel = new JPanel(new BorderLayout());
        panel.add(createMenuBar(), BorderLayout.NORTH);
        panel.add(createContentPanel());
        panel.add(createLabelBar(), BorderLayout.SOUTH);
        oct.addObject(panel);
        return panel;
    }

    public JMenuBar createLabelBar() {
        labelMenu = new JMenuBar();
        labelMenu.setLayout(new BorderLayout());
        loadingIconLabel = new JLabel(requestManager.loadProperty(language + "_No_Activity"));
        labelMenu.add(loadingIconLabel, BorderLayout.WEST);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a  (EEEE, dd/MM/YY)");
        JLabel dateLabel = new JLabel(dateFormat.format(date));
        labelMenu.add(dateLabel, BorderLayout.EAST);
        DateThread dateThread = new DateThread(dateLabel);
        dateThread.start();
        return labelMenu;
    }

    public JPanel createContentPanel() {
//        contentPanel = new ImagePanel(new ImageIcon("et1.jpg").getImage());
//        contentPanel.setLayout(new FlowLayout());
        contentPanel = new JPanel();
        oct.addObject(contentPanel);
        return contentPanel;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createOptionsMenu());
        //menuBar.add(createReportsMenu());
        menuBar.add(createLanguageMenu());
        //menuBar.add(createHelpMenu());
        return menuBar;
    }

    public JMenu createOptionsMenu() {

        JMenu options = new JMenu(requestManager.loadProperty(language + "_ET_String"));

        JMenuItem newSession = new JMenuItem(requestManager.loadProperty(language + "_ET_Option_1"));
        JMenuItem openSession = new JMenuItem(requestManager.loadProperty(language + "_ET_Option_2"));
        JMenuItem closeSession = new JMenuItem(requestManager.loadProperty(language + "_ET_Option_3"));
        JMenuItem newCharter = new JMenuItem(requestManager.loadProperty(language + "_ET_Option_3.1"));
        JMenuItem exit = new JMenuItem(requestManager.loadProperty(language + "_ET_Option_4"));

        newSession.setIcon(new ImageIcon("images/new_session_icon.jpg"));
        openSession.setIcon(new ImageIcon("images/open_session_icon.jpg"));
        closeSession.setIcon(new ImageIcon("images/close_session_icon.jpg"));
        newCharter.setIcon(new ImageIcon("images/new_session_icon.jpg"));
        exit.setIcon(new ImageIcon("images/exit_icon.jpg"));

        openSession.addActionListener(actionListenerGenerator.generateNewSessionBySpreadSheetListener());
        exit.addActionListener(actionListenerGenerator.generateExitListener());
        newSession.addActionListener(actionListenerGenerator.generateNewSessionListener(this));
        newCharter.addActionListener(actionListenerGenerator.generateNewCharterListener(this));

        options.add(newSession);
        options.add(openSession);
        options.add(newCharter);
        //options.add(closeSession);
        options.add(exit);

        return options;
    }

    public synchronized void createLoadingIcon() {
        loadingIconLabel.setText("");

        ImageIcon icon = new ImageIcon("images/loading_icon.gif");
        loadingIconLabel = new JLabel();
        loadingIconLabel.setIcon(icon);
        icon.setImageObserver(loadingIconLabel);
        loadingIconLabel.setText(requestManager.loadProperty(language + "_Loading_Document"));

        labelMenu.add(loadingIconLabel);

        oct.addObject(loadingPanel);
        oct.addObject(loadingIconLabel);
        oct.addObject(loadingLabel);
    }

    public void removeLoadingIcon() {
        loadingIconLabel.setIcon(null);
        loadingIconLabel.setText(requestManager.loadProperty(language + "_No_Activity"));
        labelMenu.add(loadingIconLabel);
    }

    public void updateListInformation() {
        ExploratoryTestCase etc = loadedTestCases.get(listIndex);
        StringBuilder sb = new StringBuilder();
        sb.append("Abstract: ").append(etc.getAbstractString()).append("\n");
        sb.append("Objective: ").append(etc.getObjectiveString()).append("\n");
        sb.append("\nTime Constraints: \n\nMinimum(min): ").append(etc.getMinimum()).append("\n");
        sb.append("Maximum(max): ").append(etc.getMaximum()).append("\n\n");
        sb.append("Requirements: ").append(etc.getRequirements()).append("\n");
        sb.append("Setup: ").append(etc.getSetup()).append("\n");
        sb.append("Notes: ").append(etc.getNotes()).append("\n");
        sb.append("\nKey Areas Of Concentration: \n\n").append(etc.getConcentrationAreas()).append("\n");
        textArea1.setText(sb.toString());
        sb = new StringBuilder();
        sb.append("Issues to be aware of:\n").append(etc.getIssues()).append("\n");
        sb.append("\n\nImportant Execution Notes:\n").append(etc.getExecutionNotes()).append("\n");
        textArea2.setText(sb.toString());
    }

    public void alterContent(List<ExploratoryTestCase> testCases) {

        for (Component cp : contentPanel.getComponents()) {
            contentPanel.remove(cp);
        }

        String tcs[] = new String[testCases.size()];

        for (int i = 0; i < tcs.length; i++) {
            tcs[i] = testCases.get(i).getId() + " - " + testCases.get(i).getAbstractString();
        }

        JScrollPane scrollPane1 = new JScrollPane();
        tcList = new JList(tcs);

        tcList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listIndex = tcList.getSelectedIndex();
                updateListInformation();
            }
        });
        scrollPane1.setViewportView(tcList);
        scrollPane1.setVisible(true);
        scrollPane1.setPreferredSize(new Dimension(260, scrollPane1.getHeight()));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(requestManager.loadProperty(language + "_Test_Cases")));
        panel.add(scrollPane1, BorderLayout.WEST);

        textArea1 = new JTextArea();
//        textArea1.setBorder(BorderFactory.createTitledBorder("Test Specification: "));
        textArea1.setEditable(false);
        textArea1.setVisible(true);
        JScrollPane scrollPaneText1 = new JScrollPane();
        scrollPaneText1.setViewportView(textArea1);
        scrollPaneText1.setBorder(BorderFactory.createTitledBorder(requestManager.loadProperty(language + "_Test_Specification")));

        textArea2 = new JTextArea();
//        textArea2.setBorder(BorderFactory.createTitledBorder("Additional Information: "));
        textArea2.setEditable(false);
        textArea2.setVisible(true);
        JScrollPane scrollPaneText2 = new JScrollPane();
        scrollPaneText2.setViewportView(textArea2);
        scrollPaneText2.setBorder(BorderFactory.createTitledBorder(requestManager.loadProperty(language + "_Additional_Information")));

        textArea3 = new JTextArea();
//        textArea3.setBorder(BorderFactory.createTitledBorder("User notes: "));
        textArea3.setEditable(true);
        textArea3.setVisible(true);
        JScrollPane scrollPaneText3 = new JScrollPane();
        scrollPaneText3.setViewportView(textArea3);
        scrollPaneText3.setBorder(BorderFactory.createTitledBorder(requestManager.loadProperty(language + "_User_Notes")));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
//        leftPanel.add(textArea1);
//        leftPanel.add(textArea2);
        leftPanel.add(scrollPaneText1);
        leftPanel.add(scrollPaneText2);

        JPanel rightPanel = new JPanel(new GridLayout(1, 1));
//        rightPanel.add(textArea3);
        rightPanel.add(scrollPaneText3);

        contentPanel.setLayout(new GridLayout(1, 1));
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        //Button section
        JLabel saveChanges = new JLabel();
        URL url;
        ImageIcon iconSave;

        try {
            BufferedImage images = ImageIO.read(new File("images/save_changes.png"));
            iconSave = new ImageIcon(images);
            saveChanges = new JLabel(iconSave);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedImage images1 = null;
        try {
            images1 = ImageIO.read(new File("images/background1.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        JPanel panel3 = new ImagePanel(images1);
        panel3.add(saveChanges);
        panel.add(panel3, BorderLayout.EAST);

    }

    public ImageIcon getResizedImage(BufferedImage image, int w, int h) {
        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, w, h, null);
        graphics2D.dispose();
        ImageIcon imageIcon = new ImageIcon(image);
        return imageIcon;
    }

    public JMenu createReportsMenu() {
        JMenu reports = new JMenu(requestManager.loadProperty(language + "_Reports_String"));
        return reports;
    }

    public JMenu createHelpMenu() {
        JMenu help = new JMenu(requestManager.loadProperty(language + "_Help_String"));
        return help;
    }

    public JMenu createLanguageMenu() {
        JMenu languageMenu = new JMenu(requestManager.loadProperty(language + "_Language_String"));


        JMenuItem portuguese = new JMenuItem(requestManager.loadProperty(language + "_Language_1"));
        JMenuItem english = new JMenuItem(requestManager.loadProperty(language + "_Language_2"));
        portuguese.setIcon(new ImageIcon("images/language_1_icon.jpg"));
        english.setIcon(new ImageIcon("images/language_2_icon.jpg"));

        if (languageSelection == 0) {
            portuguese.setSelected(true);
            english.setSelected(false);
        } else {
            portuguese.setSelected(false);
            english.setSelected(true);
        }

        portuguese.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartApplication(0);
            }
        });

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartApplication(1);
            }
        });

        languageMenu.add(portuguese);
        languageMenu.add(english);

        return languageMenu;
    }

    public void restoreSession() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    requestManager.loadProperty(language + "_New_Session_Files"),
                    "xml");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {

                String filename = fileChooser.getSelectedFile().getName();
                Session loadSession = SessionController.getInstance().loadSession(filename);
//                System.out.println(loadSession.toString());
                switchToPanel(new SessionPanelForm(loadSession, true));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao restaurar a sessão.");
        }
    }

    public void newSessionPopup() {

        if (!documentLoaded) {

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    requestManager.loadProperty(language + "_New_Session_Files"),
                    "xls", "xlsx");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getName();
                loadDocument(filename);
                documentLoaded = true;
            }

        } else {
            JOptionPane.showMessageDialog(this, requestManager.loadProperty(language + "_File_Already_Loaded"));
        }

    }

    public void loadDocument(String filename) {
        createLoadingIcon();
        LoadSheetThread loadSheetThread = new LoadSheetThread(this, filename, "Charters", 5, 11);
        loadSheetThread.start();
    }

    public List<ExploratoryTestCase> getLoadedTestCases() {
        return loadedTestCases;
    }

    public void setLoadedTestCases(List<ExploratoryTestCase> loadedTestCases) {
        this.loadedTestCases = loadedTestCases;
    }

    public void switchToPanel(JPanel nextPanel) {
        if (currentPanel != null) {
            contentPanel.remove(currentPanel);
        }
        currentPanel = nextPanel;
        contentPanel.add(currentPanel);
        contentPanel.validate();
        pack();
    }
}