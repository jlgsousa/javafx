package loader;

import com.gk_software.cst.development.tableau_editor.objects.DataContainerDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class DescriptorLoader {

  JAXBContext context;
  Unmarshaller um;
  DataContainerDescriptor descriptor = null;

  public void loadDescriptor(File descriptorFile) throws JAXBException {
    if(um == null) {
      context = JAXBContext.newInstance(DataContainerDescriptor.class);
      um = context.createUnmarshaller();
    }
    descriptor = (DataContainerDescriptor)um.unmarshal(descriptorFile);
  }


  public DataContainerDescriptor getDescriptor() {
    return descriptor;
  }

}
