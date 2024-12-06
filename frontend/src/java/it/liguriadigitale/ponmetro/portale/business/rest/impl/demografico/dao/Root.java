package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Root {
  public Data data;

  private ObjectMapper objectMapper = new ObjectMapper();

  public Root load(String json) throws IOException {
    return objectMapper.readValue(json, Root.class);
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }
}
