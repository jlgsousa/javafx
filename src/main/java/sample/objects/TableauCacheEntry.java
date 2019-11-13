package sample.objects;

import com.gk_software.cst.development.tableau_editor.service.MapperProvider;
import com.gk_software.gkr.pos.tableau.api.model.Tableau;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class TableauCacheEntry {


  public String getName() {
    return name;
  }




  public void setName(String name) {
    this.name = name;
  }




  public void setTableau(Tableau tableau) {
    this.tableau = tableau;
  }


  private String path;

  public String getPath() {
    return path;
  }



  public void setPath(String path) {
    this.path = path;
  }


  private Tableau tableau;
  private String name;

  public TableauCacheEntry(String path, String name) {
    this.path = path;
    this.name = name;
  }


  public Tableau getTableau()  {
    if(tableau == null) {
      loadTableau();
    }
    return tableau;
  }


  private Tableau loadTableau()  {
    InputStream stream = null;
    try {
      ObjectMapper objectMapper = MapperProvider.getObjectMapper();
      stream = FileUtils.openInputStream(new File(path + "/" + name));
      final String str = IOUtils.toString(stream, StandardCharsets.UTF_8);
      tableau = objectMapper.readValue(str, Tableau.class);
    }
    catch(IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      IOUtils.closeQuietly(stream);
    }
    return tableau;
  }


  @Override
  public String toString() {
    return name;
  }

}
