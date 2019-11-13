package sample.service;

import com.gk_software.gkr.pos.tableau.api.model.ETableauStatusType;
import com.gk_software.gkr.pos.tableau.api.model.Tableau;
import com.gk_software.gkr.pos.tableau.api.model.TableauPositionTranslation;
import com.gk_software.gkr.pos.tableau.api.model.TableauVersion;
import com.gk_software.pos.api.model.config.component.client.ui.tableau.TableauConfig;
import com.gk_software.pos.api.ui.tableau.layout.TableauData;
import com.gk_software.pos.api.ui.tableau.layout.TableauImageRequest;
import com.gk_software.pos.api.ui.tableau.layout.TableauImageResult;
import com.gk_software.pos.api.ui.tableau.layout.TableauSelectionParameters;
import com.gk_software.pos.api.ui.tableau.service.TableauGeneratorService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;


public class TableauEditorService {

  private TableauConfig tableauConfig;
  private TableauGeneratorService tableauService;
  private Tableau tableau;
  private Locale locale;

  public Locale getLocale() {
    return locale;
  }


  public void setLocale(Locale locale) {
    this.locale = locale;
  }


  public TableauEditorService(TableauGeneratorService tableauService) {
    this.tableauService = tableauService;
  }


  private TableauConfig loadTableauConfig() throws IOException {
    InputStream stream = null;
    try {
      if(tableauConfig == null) {
        final ObjectMapper objectMapper = MapperProvider.getObjectMapper();
        final String str = FileUtils.readFileToString(
            new File(System.getProperty("pos.root.dir") + "/config/standard/parameter/client/tableau-data/TableauConfig.json"),
            StandardCharsets.UTF_8);
        tableauConfig = objectMapper.readValue(str, TableauConfig.class);
      }
      return tableauConfig;
    }
    finally {
      IOUtils.closeQuietly(stream);
    }
  }


  private Integer getActiveVersion() {
    for(TableauVersion version : tableau.getTableauVersionList()) {
      if(version.getStatusType().equals(ETableauStatusType.ACTIVE)) {
        return version.getVersion();
      }
    }
    return 0;
  }


  public Tableau getTableau() {
    return tableau;
  }


  public void setTableau(Tableau tableau) {
    this.tableau = tableau;
  }


  public BufferedImage generatePreview(String selectedTableau) {
    if(tableau != null) {
      try {
        final TableauSelectionParameters selection = new TableauSelectionParameters();
        selection.setTableauLevel2Id(selectedTableau);
        selection.setTableauLevel2Page(0);
        final TableauData tableauData = new TableauData(tableau, getActiveVersion());
        TableauImageRequest tableauImageRequest;
        tableauImageRequest = new TableauImageRequest(loadTableauConfig(), tableauData, selection, locale);
        final TableauImageResult result = this.tableauService.getTableauImage(tableauImageRequest);
        return result.getImage();
      }
      catch(IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    return null;
  }


  public String getTranslation(String tableauPositionTranslationKey, List<TableauPositionTranslation> tableauPositionTranslationList) {
    String translation = this.tableauService.getTranslation(null, tableauPositionTranslationKey, tableauPositionTranslationList);
    if(StringUtils.isEmpty(translation)) {
      if(StringUtils.isEmpty(tableauPositionTranslationKey)) {
        return "NO TRANSLATION<br>FOUND";
      }
      return tableauPositionTranslationKey;
    }
    return translation;
  }

}
