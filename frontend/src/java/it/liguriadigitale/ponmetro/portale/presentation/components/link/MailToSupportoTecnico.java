package it.liguriadigitale.ponmetro.portale.presentation.components.link;

import org.apache.wicket.markup.html.link.ExternalLink;

public class MailToSupportoTecnico extends ExternalLink {

  private static final long serialVersionUID = -75408928587597858L;

  private static String EMAIL_SUPPORTO = "mailto:fascicolodigitale@comune.genova.it";
  private static String TESTO_SUPPORTO = "Contatta il supporto tecnico";

  public MailToSupportoTecnico(String id) {
    super(id, EMAIL_SUPPORTO, TESTO_SUPPORTO);
  }
}
