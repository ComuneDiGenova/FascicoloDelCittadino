package it.liguriadigitale.ponmetro.portale.pojo.globo;

import java.io.Serializable;
import java.util.List;

public class GloboServiziOnline implements Serializable {

  private static final long serialVersionUID = 5386666981602455539L;

  private String name;
  private Long totalResults;
  private Long timestamp;
  private List<Node> nodes;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Long totalResults) {
    this.totalResults = totalResults;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  @Override
  public String toString() {
    return "GloboServiziOnline [name="
        + name
        + ", totalResults="
        + totalResults
        + ", timestamp="
        + timestamp
        + ", nodes="
        + nodes
        + "]";
  }
}
