package sample.ui;

import com.gk_software.core.common.core_util.UUIDUtils;
import com.gk_software.cst.development.tableau_editor.DescriptorLoader;
import com.gk_software.cst.development.tableau_editor.InstanceLoader;
import com.gk_software.cst.development.tableau_editor.objects.DataContainerDefinitionFile;
import com.gk_software.cst.development.tableau_editor.objects.TableauCacheEntry;
import com.gk_software.cst.development.tableau_editor.objects.TableauPositionWrapper;
import com.gk_software.cst.development.tableau_editor.service.MapperProvider;
import com.gk_software.cst.development.tableau_editor.service.TableauEditorService;
import com.gk_software.gkr.pos.tableau.api.model.TableauModelObjectsDefaultFactory;
import com.gk_software.gkr.pos.tableau.api.model.TableauPosition;
import com.gk_software.gkr.pos.tableau.api.model.TableauPositionAction;
import com.gk_software.pos.api.ui.tableau.service.TableauGeneratorService;
import com.gk_software.pos.locale.language.LanguageProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Properties;


public class MainWindow {

  private JFrame frame;
  private DefaultListModel<DataContainerDefinitionFile> descriptorModel = new DefaultListModel<>();
  private DefaultListModel<TableauCacheEntry> instanceModel = new DefaultListModel<>();
  private DefaultListModel<TableauPositionWrapper> buttonListModel = new DefaultListModel<>();
  private DefaultComboBoxModel<String> combolevelSelectionModel = new DefaultComboBoxModel<>();
  private DefaultComboBoxModel<Locale> comboLanguageSelectionModel = new DefaultComboBoxModel<>();

  private DescriptorLoader descriptorLoader = new DescriptorLoader();
  private InstanceLoader instanceLoader = new InstanceLoader();
  private LanguageProvider languageProvider;
  private JPanel descriptorPanel;
  private JList<DataContainerDefinitionFile> descriptorList;
  private JList<TableauCacheEntry> instanceList;
  private JComboBox<Locale> languageComboBox;
  private RSyntaxTextArea buttonTextArea;
  private JList<TableauPositionWrapper> buttonList;
  private JComboBox<String> comboLevelSelection;
  private LevelComboBoxListener levelComboBoxListener;
  private TableauEditorService tableauEditorService;
  private Properties settings;
  private File descriptorFile = null;
  private JButton addButton;
  private JButton removeButton;

  /**
   * Create the application.
   * 
   * @param tableauService
   * @param languageProvider
   * @param settings
   * @throws JAXBException
   */
  public MainWindow(TableauGeneratorService tableauService, LanguageProvider languageProvider, Properties settings) {
    this.languageProvider = languageProvider;
    this.tableauEditorService = new TableauEditorService(tableauService);
    this.settings = settings;
  }


  /**
   */
  public void loadData(File descriptor) throws JAXBException {
    descriptorFile = descriptor;
    descriptorModel.clear();
//    descriptorLoader.loadDescriptor(descriptor);
//    for(DataContainerDefinitionFile element : descriptorLoader.getDescriptor().getFiles()) {
//      descriptorModel.addElement(element);
//    }
    instanceLoader.loadInstances(descriptor.getParentFile());
  }


  /**
   * Initialize the contents of the frame.
   * @param settings
   * 
   * @throws JAXBException
   * @wbp.parser.entryPoint
   */
  public void initialize() {
    createBasicFrame();

    createDescriptorPanel();

    createDescriptorUserPanel();

    createInstancePanel();

    descriptorList.addListSelectionListener(new DescriptorListListener(instanceLoader, instanceList));

    JPanel rightPanel = createRightPanel();

    JPanel editorPanel = createEditorArea(rightPanel);



    languageComboBox = new JComboBox<>();
    GridBagConstraints gbc_languageComboBox = new GridBagConstraints();
    gbc_languageComboBox.fill = GridBagConstraints.HORIZONTAL;
    gbc_languageComboBox.insets = new Insets(0, 0, 5, 5);
    gbc_languageComboBox.gridx = 1;
    gbc_languageComboBox.gridy = 1;
    frame.getContentPane().add(languageComboBox, gbc_languageComboBox);
    languageComboBox.setModel(comboLanguageSelectionModel);

    createPreviewPanel(rightPanel);

    JButton saveButton = createSaveButton(editorPanel);
    saveButton.addActionListener(new SaveButtonActionListener());
    addButton.addActionListener(new AddButtonActionListener(levelComboBoxListener));
    removeButton.addActionListener(new RemoveButtonActionListener(levelComboBoxListener, buttonList));
    frame.setVisible(true);
  }


  private JPanel createEditorArea(JPanel rightPanel) {
    JPanel editorPanel = new JPanel();
    GridBagConstraints buttonPanelGbc = new GridBagConstraints();
    buttonPanelGbc.fill = GridBagConstraints.BOTH;
    buttonPanelGbc.gridx = 0;
    buttonPanelGbc.gridy = 0;
    rightPanel.add(editorPanel, buttonPanelGbc);
    GridBagLayout editorPanelGbc = new GridBagLayout();
    editorPanelGbc.columnWidths = new int[] { 120, 0, 0 };
    editorPanelGbc.rowHeights = new int[] { 0, 0, 0, 0, 0 };
    editorPanelGbc.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    editorPanelGbc.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
    editorPanel.setLayout(editorPanelGbc);

    buttonTextArea = new RSyntaxTextArea();
    buttonTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
    buttonTextArea.setCodeFoldingEnabled(true);
    RTextScrollPane sp = new RTextScrollPane(buttonTextArea);
    sp.setMinimumSize(new Dimension(200, 400));
    GridBagConstraints buttonTextAreaGbc = new GridBagConstraints();
    buttonTextAreaGbc.fill = GridBagConstraints.BOTH;
    buttonTextAreaGbc.gridheight = 5;
    buttonTextAreaGbc.gridwidth = 2;
    buttonTextAreaGbc.insets = new Insets(0, 0, 5, 0);
    buttonTextAreaGbc.gridx = 1;
    buttonTextAreaGbc.gridy = 0;
    editorPanel.add(sp, buttonTextAreaGbc);

    JLabel buttonLabel = new JLabel("Buttons");
    GridBagConstraints buttonLabelGbc = new GridBagConstraints();
    buttonLabelGbc.fill = GridBagConstraints.VERTICAL;
    buttonLabelGbc.insets = new Insets(0, 0, 5, 5);
    buttonLabelGbc.gridx = 0;
    buttonLabelGbc.gridy = 1;
    editorPanel.add(buttonLabel, buttonLabelGbc);

    comboLevelSelection = new JComboBox<>();
    GridBagConstraints comboLevelSelectionGbc = new GridBagConstraints();
    comboLevelSelectionGbc.insets = new Insets(0, 0, 5, 5);
    comboLevelSelectionGbc.fill = GridBagConstraints.HORIZONTAL;
    comboLevelSelectionGbc.gridx = 0;
    comboLevelSelectionGbc.gridy = 0;
    editorPanel.add(comboLevelSelection, comboLevelSelectionGbc);
    comboLevelSelection.setModel(combolevelSelectionModel);

    buttonList = new JList<>();
    GridBagConstraints buttonListGbc = new GridBagConstraints();
    buttonListGbc.insets = new Insets(0, 0, 0, 5);
    buttonListGbc.gridheight = 4;
    buttonListGbc.fill = GridBagConstraints.BOTH;
    buttonListGbc.gridx = 0;
    buttonListGbc.gridy = 2;
    editorPanel.add(buttonList, buttonListGbc);
    buttonList.setModel(buttonListModel);

    addButton = new JButton("Add Button");
    GridBagConstraints addButtonGbc = new GridBagConstraints();
    addButtonGbc.gridx = 0;
    addButtonGbc.gridy = 6;
    editorPanel.add(addButton, addButtonGbc);
    removeButton = new JButton("Remove Button");
    GridBagConstraints removeButtonGbc = new GridBagConstraints();
    removeButtonGbc.gridx = 1;
    removeButtonGbc.gridy = 6;
    editorPanel.add(removeButton, removeButtonGbc);
    return editorPanel;
  }


  private void createPreviewPanel(JPanel rightPanel) {
    ImagePanel previewPanel = new ImagePanel();
    GridBagConstraints previewPanelGbc = new GridBagConstraints();
    previewPanelGbc.gridwidth = 2;
    previewPanelGbc.gridheight = 3;
    previewPanelGbc.fill = GridBagConstraints.BOTH;
    previewPanelGbc.gridx = 0;
    previewPanelGbc.gridy = 1;
    rightPanel.add(previewPanel, previewPanelGbc);
    levelComboBoxListener = new LevelComboBoxListener(instanceList, buttonList, tableauEditorService, previewPanel);
    languageComboBox.addItemListener(new LanguageComboBoxListener(languageProvider, tableauEditorService));
    comboLevelSelection.addItemListener(levelComboBoxListener);
    instanceList.addListSelectionListener(new InstanceListListener(instanceList, combolevelSelectionModel));
    buttonList.addListSelectionListener(new ButtonListListener(buttonTextArea));
  }


  private JButton createSaveButton(JPanel editorPanel) {
    JButton saveButton = new JButton("Save");
    GridBagConstraints saveButtonGbc = new GridBagConstraints();
    saveButtonGbc.gridx = 1;
    saveButtonGbc.gridy = 5;
    editorPanel.add(saveButton, saveButtonGbc);
    return saveButton;
  }


  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel();
    GridBagConstraints rightPanelGbc = new GridBagConstraints();
    rightPanelGbc.insets = new Insets(0, 0, 5, 0);
    rightPanelGbc.fill = GridBagConstraints.BOTH;
    rightPanelGbc.gridx = 2;
    rightPanelGbc.gridy = 0;
    rightPanelGbc.gridheight = 3;
    rightPanelGbc.fill = GridBagConstraints.BOTH;
    frame.getContentPane().add(rightPanel, rightPanelGbc);
    GridBagLayout gbl_panel_2 = new GridBagLayout();
    gbl_panel_2.columnWidths = new int[] { 0, 0 };
    gbl_panel_2.rowHeights = new int[] { 372, 0, 0, 0, 0, 0 };
    gbl_panel_2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gbl_panel_2.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
    rightPanel.setLayout(gbl_panel_2);
    return rightPanel;
  }


  private void createInstancePanel() {
    JPanel instancePanel = new JPanel();
    GridBagConstraints instancePanelGbc = new GridBagConstraints();
    instancePanelGbc.fill = GridBagConstraints.BOTH;
    instancePanelGbc.insets = new Insets(0, 0, 5, 5);
    instancePanelGbc.gridx = 1;
    instancePanelGbc.gridy = 0;
    frame.getContentPane().add(instancePanel, instancePanelGbc);
    instancePanel.setLayout(new BorderLayout(0, 0));

    instanceList = new JList<>();
    instanceList.setModel(instanceModel);
    instancePanel.add(instanceList, BorderLayout.CENTER);
    JLabel instanceLabel = new JLabel("Instances");
    instancePanel.add(instanceLabel, BorderLayout.NORTH);


    loadLanguagesFromSettings(comboLanguageSelectionModel);
  }


  private void loadLanguagesFromSettings(DefaultComboBoxModel<Locale> comboLanguageSelectionModel2) {
    String[] languages = settings.getProperty("languages").split(",");
    for(String language : languages) {
      String[] languageSplitUp = language.split("_");
      if(languageSplitUp.length == 2) {
        comboLanguageSelectionModel.addElement(new Locale(languageSplitUp[0], languageSplitUp[1]));
      }
    }
  }


  private void createDescriptorUserPanel() {
    JPanel descriptorUserPanel = new JPanel();
    descriptorPanel.add(descriptorUserPanel, BorderLayout.NORTH);
    descriptorUserPanel.setLayout(new BorderLayout(0, 0));

    JButton descriptorSelector = new JButton("Load Descriptor");
    descriptorSelector.addActionListener(new DescriptorActionListener(descriptorUserPanel));
    descriptorUserPanel.add(descriptorSelector, BorderLayout.NORTH);

    JLabel descriptorLabel = new JLabel("Descriptor");
    descriptorUserPanel.add(descriptorLabel, BorderLayout.SOUTH);
    descriptorLabel.setMinimumSize(new Dimension(100, 16));
    descriptorLabel.setMaximumSize(new Dimension(0, 0));
    descriptorLabel.setHorizontalAlignment(SwingConstants.CENTER);
    descriptorLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

    descriptorList = new JList<>();
    descriptorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    descriptorList.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    descriptorList.setModel(descriptorModel);
    descriptorPanel.add(descriptorList, BorderLayout.CENTER);
  }


  private void createDescriptorPanel() {
    descriptorPanel = new JPanel();
    GridBagConstraints descriptorPanelGbc = new GridBagConstraints();
    descriptorPanelGbc.gridheight = 4;
    descriptorPanelGbc.anchor = GridBagConstraints.WEST;
    descriptorPanelGbc.fill = GridBagConstraints.VERTICAL;
    descriptorPanelGbc.insets = new Insets(0, 0, 0, 5);
    descriptorPanelGbc.gridx = 0;
    descriptorPanelGbc.gridy = 0;
    descriptorPanelGbc.fill = GridBagConstraints.BOTH;
    frame.getContentPane().add(descriptorPanel, descriptorPanelGbc);
    descriptorPanel.setLayout(new BorderLayout(0, 0));
  }


  private void createBasicFrame() {
    frame = new JFrame();
    frame.setMinimumSize(new Dimension(1024, 768));
    frame.getContentPane().setMinimumSize(new Dimension(1024, 768));
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 200, 200, 590 };
    gridBagLayout.rowHeights = new int[] { 721, 0, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 2.0 };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE, 1.0 };
    frame.getContentPane().setLayout(gridBagLayout);
  }


  public JFrame getFrame() {
    return frame;
  }

  class SaveButtonActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      if(!buttonTextArea.getText().isEmpty()) {
        try {
          TableauPosition position = MapperProvider.getObjectMapper().readValue(buttonTextArea.getText(), TableauPosition.class);
          BeanUtils.copyProperties(buttonList.getSelectedValue().getPosition(), position);
          TableauCacheEntry cacheEntry = buttonList.getSelectedValue().getTableau();
          MapperProvider.getObjectMapper().writeValue(new File(cacheEntry.getPath() + "/" + cacheEntry.getName()), cacheEntry.getTableau());
          levelComboBoxListener.generatePreview(comboLevelSelection.getSelectedItem().toString());
        }
        catch(IllegalAccessException | InvocationTargetException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        catch(JsonParseException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        catch(JsonMappingException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        catch(IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }

  }

  class AddButtonActionListener implements ActionListener {

    private LevelComboBoxListener levelComboBoxListener;
    private TableauModelObjectsDefaultFactory  factory = new TableauModelObjectsDefaultFactory();
    public AddButtonActionListener(LevelComboBoxListener levelComboBoxListener) {
      this.levelComboBoxListener = levelComboBoxListener;
    }

    public void actionPerformed(ActionEvent e) {
      TableauPosition position = factory.createTableauPosition();
      position.setId(UUIDUtils.fromString32(UUIDUtils.generate32()).toString());
      position.setPage(0);
      position.setCol(0);
      position.setRow(0);
      position.setColSpan(1);
      position.setRowSpan(1);
      position.setTableauPositionTranslationKey("TOBEFILLED");
      TableauPositionAction action = factory.createTableauPositionAction();
      action.setProcessConfigId("TOBEFILLED");
      action.setProcessReactionName("TOBEFILLED");
      position.setTableauPositionAction(action);

      levelComboBoxListener.getLevel().getTableauPositionList().add(position);
      levelComboBoxListener. updateView(levelComboBoxListener.getLevel().getId());
    }

  }

  class RemoveButtonActionListener implements ActionListener {

    private LevelComboBoxListener levelComboBoxListener;
    private JList<TableauPositionWrapper> buttonList;
    public RemoveButtonActionListener(LevelComboBoxListener levelComboBoxListener, JList<TableauPositionWrapper> buttonList) {
      this.levelComboBoxListener = levelComboBoxListener;
      this.buttonList = buttonList;
    }

    public void actionPerformed(ActionEvent e) {
      if(buttonList.getSelectedValue() != null) {
        TableauPosition position = buttonList.getSelectedValue().getPosition();
        int index = levelComboBoxListener.getLevel().getTableauPositionList().indexOf( position);
        if(index > -1 ) {
          levelComboBoxListener.getLevel().getTableauPositionList().remove(index);
          levelComboBoxListener. updateView(levelComboBoxListener.getLevel().getId());
        }
      }
    }

  }

  class DescriptorActionListener implements ActionListener {

    private Component parentComponent;
    public DescriptorActionListener(Component parentComponent) {
      this.parentComponent = parentComponent;
    }


    public void actionPerformed(ActionEvent e) {
      final JFileChooser fc = new JFileChooser(descriptorFile);
      int returnVal = fc.showOpenDialog(parentComponent);

      if(returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          descriptorFile = fc.getSelectedFile();
          loadData(descriptorFile);
        }
        catch(JAXBException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else {}
    }
  }

}
