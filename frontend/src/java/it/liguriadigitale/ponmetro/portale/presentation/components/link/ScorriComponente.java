package it.liguriadigitale.ponmetro.portale.presentation.components.link;

import org.apache.wicket.markup.html.panel.Panel;

public class ScorriComponente extends Panel {

  private static final long serialVersionUID = -2011619740314513940L;

  public ScorriComponente(String id, ScorriAnnoLink link) {
    super(id);
    add(link);
  }
}
