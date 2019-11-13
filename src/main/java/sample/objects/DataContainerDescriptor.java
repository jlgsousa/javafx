package sample.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DATA_CONTAINER_DESCRIPTOR")
public class DataContainerDescriptor {
  @XmlAttribute(name = "NAME")
  private String name;
  @XmlAttribute(name = "VERSION")
  private String version;
  @XmlAttribute(name = "DESCRIPTION")
  private String description;
  @XmlElement(name="DATA_CONTAINER_DEFINITION_FILE")
  private List<DataContainerDefinitionFile> data_container_descriptor_file;

  public List<DataContainerDefinitionFile> getFiles() {return data_container_descriptor_file;}
   
  public void setFiles(List<DataContainerDefinitionFile> dcInstances) {this.data_container_descriptor_file = dcInstances;}

  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


}
