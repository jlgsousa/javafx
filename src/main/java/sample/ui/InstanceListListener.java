package sample.ui;

import com.gk_software.cst.development.tableau_editor.objects.TableauCacheEntry;
import com.gk_software.gkr.pos.tableau.api.model.Tableau;
import com.gk_software.gkr.pos.tableau.api.model.TableauLevel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InstanceListListener implements ListSelectionListener {

  private JList<TableauCacheEntry> instanceList;
  private DefaultComboBoxModel<String> levelComboBoxModel;

  public InstanceListListener(JList<TableauCacheEntry> instanceList, DefaultComboBoxModel<String> levelComboBoxModel) {
    this.levelComboBoxModel = levelComboBoxModel;
    this.instanceList = instanceList;
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {
    if(!e.getValueIsAdjusting()) {
      for(Object entry : instanceList.getSelectedValuesList()) {
        TableauCacheEntry cacheEntry = (TableauCacheEntry)entry;
        Tableau tableau = null;
          tableau = cacheEntry.getTableau();

        if(tableau != null) {
          levelComboBoxModel.removeAllElements();
          TableauLevel level = tableau.getTableauVersionList().get(0).getRootTableauLevel();
          levelComboBoxModel.addElement(level.getId());
          if(level.getChildTableauLevelList() != null) {

            for(TableauLevel children : level.getChildTableauLevelList()) {
              levelComboBoxModel.addElement(children.getId());
            }
          }
        }
      }

    }

  }
}
