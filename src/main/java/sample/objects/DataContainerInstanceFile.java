package sample.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DATA_CONTAINER_INSTANCE_FILE")
public class DataContainerInstanceFile {

  @XmlAttribute(name = "FILE_KEY")
  private String file_key;
  
  @XmlAttribute(name = "FILE_NAME")
  private String file_name;


  public String getFileKey() {
    return file_key;
  }


  public void setFileKey(String file_key) {
    this.file_key = file_key;
  }


  public String getFileName() {
    return file_name;
  }


  public void setFileName(String file_name) {
    this.file_name = file_name;
  }

}
