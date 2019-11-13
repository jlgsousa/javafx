package sample.ui;

import com.gk_software.cst.development.tableau_editor.objects.TableauCacheEntry;
import com.gk_software.cst.development.tableau_editor.objects.TableauPositionWrapper;
import com.gk_software.cst.development.tableau_editor.service.TableauEditorService;
import com.gk_software.gkr.pos.tableau.api.model.Tableau;
import com.gk_software.gkr.pos.tableau.api.model.TableauLevel;
import com.gk_software.gkr.pos.tableau.api.model.TableauPosition;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class LevelComboBoxListener implements ItemListener {

  private JList<TableauPositionWrapper> buttonList;
  private JList<TableauCacheEntry> instanceList;
  private ImagePanel previewCanvas;
  private TableauEditorService tableauEditorService;
  private TableauLevel level;


  public TableauLevel getLevel() {
    return level;
  }


  public LevelComboBoxListener(
      JList<TableauCacheEntry> instanceList, JList<TableauPositionWrapper> buttonList, TableauEditorService tableauEditorService,
      ImagePanel previewPanel) {
    this.buttonList = buttonList;
    this.instanceList = instanceList;
    this.tableauEditorService = tableauEditorService;
    this.previewCanvas = previewPanel;
  }


  @Override
  public void itemStateChanged(ItemEvent e) {
    if(e.getStateChange() == ItemEvent.SELECTED) {
      String selectedItem = (String)e.getItem();
      updateView(selectedItem);
    }
  }


  public void updateView(String selectedTableau) {
    TableauCacheEntry cacheEntry = instanceList.getSelectedValue();
    Tableau tableau = null;
    tableau = cacheEntry.getTableau();
    tableauEditorService.setTableau(tableau);
    updateButtonList(cacheEntry, selectedTableau);
    generatePreview(selectedTableau);
  }


  public void generatePreview(String selectedTableau) {
    previewCanvas.setImage(tableauEditorService.generatePreview(selectedTableau));
    previewCanvas.repaint();
  }


  private void updateButtonList(TableauCacheEntry cacheEntry, String selectedTableau) {
    level = findSelectedLevel(cacheEntry.getTableau(), selectedTableau);
    if(level == null) {
      return;
    }

    DefaultListModel<TableauPositionWrapper> model = (DefaultListModel<TableauPositionWrapper>)buttonList.getModel();
    model.clear();
    if(level.getTableauPositionList() != null) {
      for(TableauPosition position : level.getTableauPositionList()) {
        model.addElement(
            new TableauPositionWrapper(
                position, cacheEntry, this.tableauEditorService
                .getTranslation(position.getTableauPositionTranslationKey(), position.getTableauPositionTranslationList())));
      }
    }

  }


  private TableauLevel findSelectedLevel(Tableau tableau, String selectedTableau) {
    if(tableau != null) {
      TableauLevel level = tableau.getTableauVersionList().get(0).getRootTableauLevel();
      if(level.getId().equals(selectedTableau)) {
        return level;
      }
      else if(level.getChildTableauLevelList() != null) {
        for(TableauLevel children : level.getChildTableauLevelList()) {
          if(children.getId().equals(selectedTableau)) {
            return children;
          }
        }
      }
    }
    return null;
  }

}
