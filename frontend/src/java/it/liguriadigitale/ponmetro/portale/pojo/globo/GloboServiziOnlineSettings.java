package it.liguriadigitale.ponmetro.portale.pojo.globo;

import java.io.Serializable;
import java.util.List;

public class GloboServiziOnlineSettings implements Serializable {

  private static final long serialVersionUID = 3138905732991370108L;

  private String name;
  private Long timestamp;
  private List<Tag> tag_argomenti;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<Tag> getTag_argomenti() {
    return tag_argomenti;
  }

  public void setTag_argomenti(List<Tag> tag_argomenti) {
    this.tag_argomenti = tag_argomenti;
  }

  @Override
  public String toString() {
    return "GloboServiziOnlineSettings [name="
        + name
        + ", timestamp="
        + timestamp
        + ", tag_argomenti="
        + tag_argomenti
        + "]";
  }
}
