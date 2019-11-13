package sample.ui;

import com.gk_software.cst.development.tableau_editor.InstanceLoader;
import com.gk_software.cst.development.tableau_editor.objects.DataContainerDefinitionFile;
import com.gk_software.cst.development.tableau_editor.objects.TableauCacheEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;


public class DescriptorListListener implements ListSelectionListener {

  private InstanceLoader instanceLoader;
  private JList<TableauCacheEntry> instanceList;

  public DescriptorListListener(InstanceLoader instanceLoader, JList<TableauCacheEntry> instanceList) {
    this.instanceLoader = instanceLoader;
    this.instanceList = instanceList;
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {
    if(e.getSource() instanceof JList<?>) {
      JList<DataContainerDefinitionFile> source = (JList<DataContainerDefinitionFile>)e.getSource();
      DefaultListModel<TableauCacheEntry> instanceModel = (DefaultListModel<TableauCacheEntry>)instanceList.getModel();
      if(e.getValueIsAdjusting()) {
        instanceModel.clear();
        for(Object entry : source.getSelectedValuesList()) {
          DataContainerDefinitionFile dcFile = (DataContainerDefinitionFile)entry;
          List<TableauCacheEntry> list = instanceLoader.getEntries(dcFile.getFileKey());
          //TODO: HERE IS EASY TO GET THE BRAND FOR REPRESENTATION
          for(TableauCacheEntry cacheEntry : list) {
            instanceModel.addElement(cacheEntry);
          }
        }
        if(!instanceModel.isEmpty()) {
          instanceList.setSelectedIndex(0);
        }
      }
    }
  }
}
