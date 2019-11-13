package sample.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DATA_CONTAINER_DEFINITION_FILE")
public class DataContainerDefinitionFile {
  @XmlAttribute(name = "FILE_KEY")
  private String fileKey;
  @XmlAttribute(name = "FILE_NAME")
  private String fileName;
  @XmlAttribute(name = "INSTANCE_FILE_NAME")
  private String instanceFileName;
  @XmlAttribute(name = "VALIDATION_CLASS")
  private String validationClass;
  
  
  
  public String getFileKey() {
    return fileKey;
  }
  
  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }
  
  public String getFileName() {
    return fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getInstanceFileName() {
    return instanceFileName;
  }
  
  public void setInstanceFileName(String instanceFileName) {
    this.instanceFileName = instanceFileName;
  }
  
  public String getValidationClass() {
    return validationClass;
  }
  
  public void setValidationClass(String validationClass) {
    this.validationClass = validationClass;
  }

  
  @Override
  public String toString() {
   return fileKey;
  }
}
