package sample;

import com.gk_software.cst.development.tableau_editor.ui.MainWindow;
import com.gk_software.pos.api.model.types.LanguageScope;
import com.gk_software.pos.api.service.ServiceLocator;
import com.gk_software.pos.api.service.client.localization.LocalizationService;
import com.gk_software.pos.api.ui.tableau.service.TableauGeneratorService;
import com.gk_software.pos.locale.language.LanguageProvider;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;


public class TableauEditor {
  /**
   * Launch the application.
   * 
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Properties settings = new Properties();
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    final File settingsLocation = new File("./tableauEditor.properties");
    final InputStream propertiesInputStream = FileUtils.openInputStream(settingsLocation);
    settings.load(propertiesInputStream);
    
    ServiceLocator serviceLocator = (ServiceLocator)context.getBean("publicServiceLocator");
    LocalizationService localizationService = serviceLocator.getService(LocalizationService.class);
    LanguageProvider languageProvider = localizationService.getLanguageProvider();
    languageProvider.setLanguageForScope(LanguageScope.Operator, Locale.GERMANY);
    languageProvider.setActiveScope(LanguageScope.Operator);
    TableauGeneratorService tableauService = serviceLocator.getService(TableauGeneratorService.class);

    EventQueue.invokeLater(() -> {
      MainWindow window = new MainWindow(tableauService, languageProvider, settings);
      window.initialize();
      try {
        if(args != null && args.length > 0) {
          File descriptorFile = new File(args[0]);
          if(descriptorFile.exists())
            window.loadData(descriptorFile);
        }
      }
      catch(Exception e) {
        JOptionPane
            .showMessageDialog(window.getFrame(), "Descriptor file cannot be loaded", "Data loading error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

}
