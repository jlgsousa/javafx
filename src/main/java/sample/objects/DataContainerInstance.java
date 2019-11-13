package sample.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DATA_CONTAINER_INSTANCE")
public class DataContainerInstance {
  @XmlAttribute(name = "NAME")
  private String name;
  @XmlAttribute(name = "DESCRIPTION")
  private String description;
  @XmlAttribute(name = "DATA_CONTAINER_NAME")
  private String data_container_name;
  @XmlAttribute(name = "DATA_CONTAINER_VERSION")
  private String data_container_version;
  @XmlElement(name="DATA_CONTAINER_INSTANCE_FILE")
  private List<DataContainerInstanceFile> data_container_instance_file;

  
 
  public List<DataContainerInstanceFile> getItems() {return data_container_instance_file;}
   
  public void setItems(List<DataContainerInstanceFile> dcInstances) {this.data_container_instance_file = dcInstances;}

  
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

  public String getDataContainerName() {
    return data_container_name;
  }

  
  public void setDataContainerName(String data_container_name) {
    this.data_container_name = data_container_name;
  }

  public String getDataContainerVersion() {
    return data_container_version;
  }

  public void setDataContainerVersion(String data_container_version) {
    this.data_container_version = data_container_version;
  }


}
