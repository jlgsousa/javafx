package loader;

import com.gk_software.cst.development.tableau_editor.objects.DataContainerInstance;
import com.gk_software.cst.development.tableau_editor.objects.DataContainerInstanceFile;
import com.gk_software.cst.development.tableau_editor.objects.TableauCacheEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class InstanceLoader {

  Map<String, List<TableauCacheEntry>> tableauCache = new HashMap<>();
  Set<String> instanceFileCache = new HashSet<>();
  
  
  public void loadInstances(File instancePath) throws JAXBException {
    Collection<File> instanceFiles = FileUtils.listFiles(instancePath, new IOFileFilter() {

      public boolean accept(File dir, String name) {
        return name.endsWith("Instance.xml");
      }


      public boolean accept(File file) {
        return file.getName().matches(".*Instance.*xml$");
      }
    },  TrueFileFilter.TRUE);

    final JAXBContext context = JAXBContext.newInstance(DataContainerInstance.class);
    final Unmarshaller um = context.createUnmarshaller();

    for(File instances : instanceFiles) {

      
      DataContainerInstance instance = (DataContainerInstance)um.unmarshal(instances);

      for(DataContainerInstanceFile entry : instance.getItems()) {
        List<TableauCacheEntry> list = tableauCache.get(entry.getFileKey());
        if(list == null) {
          list = new ArrayList<>();
          tableauCache.put(entry.getFileKey(), list);
        }
        if(instanceFileCache.contains(instances.getParentFile().getPath()+"/"+entry.getFileName())) {
          continue;
        }
        instanceFileCache.add(instances.getParentFile().getPath()+"/"+entry.getFileName());
        list.add(new TableauCacheEntry(instances.getParentFile().getPath(), entry.getFileName()));
      }

    }
  }
  
  
  public List<TableauCacheEntry> getEntries(String key) {
    return tableauCache.get(key);
  }

}
