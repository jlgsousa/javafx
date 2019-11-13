package sample.ui;

import com.gk_software.cst.development.tableau_editor.service.TableauEditorService;
import com.gk_software.pos.locale.language.LanguageProvider;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;


public class LanguageComboBoxListener implements ItemListener {

  private TableauEditorService tableauEditorService;
  private LanguageProvider languageProvider;

  public LanguageComboBoxListener(LanguageProvider languageProvider, TableauEditorService tableauEditorService) {
    this.languageProvider = languageProvider;
    this.tableauEditorService = tableauEditorService;
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if(e.getStateChange() == ItemEvent.SELECTED) {
      languageProvider.setLanguageForCurrentScope((String)e.getItem().toString());
      tableauEditorService.setLocale(new Locale((String)e.getItem().toString()));
    }

  }

}
