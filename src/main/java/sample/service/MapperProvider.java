package sample.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MapperProvider {
  private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  private static ObjectMapper mapper;

  public static ObjectMapper getObjectMapper() {
    if(mapper == null) {
      mapper = new ObjectMapper();
      mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
      mapper.setDateFormat(DATE_FORMAT);
    }

    return mapper;
  }

}
