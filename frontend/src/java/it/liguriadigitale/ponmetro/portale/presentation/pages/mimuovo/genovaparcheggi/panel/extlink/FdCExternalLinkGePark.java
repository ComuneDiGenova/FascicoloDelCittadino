package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.panel.extlink;

import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

public class FdCExternalLinkGePark extends Panel {

  private static final long serialVersionUID = -2635798970039917843L;

  public FdCExternalLinkGePark(String id, Permit permesso, PermitAllowedAction azione) {
    super(id);
    ExternalLink link = new ExternalLink("link", azione.getUrl(), azione.getOperationDescription());
    link.add(
        new AttributeAppender(
            "title", permesso.getCategoryDescription() + " " + permesso.getZoneDescription()));
    add(link);
  }
}
