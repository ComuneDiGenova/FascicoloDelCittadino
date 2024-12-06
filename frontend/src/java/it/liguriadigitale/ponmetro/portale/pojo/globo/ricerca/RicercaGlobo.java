package it.liguriadigitale.ponmetro.portale.pojo.globo.ricerca;

import it.liguriadigitale.ponmetro.portale.pojo.globo.Tag;
import java.io.Serializable;
import java.util.List;

public class RicercaGlobo implements Serializable {

  private static final long serialVersionUID = 279175520421908276L;

  private Tag combo;
  private Tag comboChild;
  private String testoLibero;
  private List<Tag> tags;

  public Tag getCombo() {
    return combo;
  }

  public void setCombo(Tag combo) {
    this.combo = combo;
  }

  public Tag getComboChild() {
    return comboChild;
  }

  public void setComboChild(Tag comboChild) {
    this.comboChild = comboChild;
  }

  public String getTestoLibero() {
    return testoLibero;
  }

  public void setTestoLibero(String testoLibero) {
    this.testoLibero = testoLibero;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "RicercaGlobo [combo="
        + combo
        + ", comboChild="
        + comboChild
        + ", testoLibero="
        + testoLibero
        + ", tags="
        + tags
        + "]";
  }
}
