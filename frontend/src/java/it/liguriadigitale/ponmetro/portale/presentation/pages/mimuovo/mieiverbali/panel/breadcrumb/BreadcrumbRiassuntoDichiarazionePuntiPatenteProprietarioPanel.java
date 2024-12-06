package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.breadcrumb;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BreadcrumbRiassuntoDichiarazionePuntiPatenteProprietarioPanel extends BasePanel {

  private static final long serialVersionUID = 6529718794833772460L;

  private Verbale verbale;

  public BreadcrumbRiassuntoDichiarazionePuntiPatenteProprietarioPanel(String id, Verbale verbale) {
    super(id);

    this.setVerbale(verbale);

    fillDati(verbale);

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    Verbale verbale = (Verbale) dati;

    add(creaLinkDettaglioVerbale(verbale));
  }

  private LaddaAjaxLink<Object> creaLinkDettaglioVerbale(Verbale verbale) {
    LaddaAjaxLink<Object> linkDettaglioVerbale =
        new LaddaAjaxLink<Object>("dettaglioVerbale", Type.Link) {

          private static final long serialVersionUID = -5532849150185785006L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new DettaglioVerbaliPage(verbale));
          }
        };

    String testoLink =
        getString("BreadcrumbRiassuntoDichiarazionePuntiPatenteProprietarioPanel.dettaglioVerbali");
    linkDettaglioVerbale.setLabel(Model.of(testoLink));

    AttributeModifier attributeModifier = new AttributeModifier("class", "btn-link ladda-button");
    linkDettaglioVerbale.add(attributeModifier);

    linkDettaglioVerbale.setSpinnerColor("#0066cb");

    return linkDettaglioVerbale;
  }

  public Verbale getVerbale() {
    return verbale;
  }

  public void setVerbale(Verbale verbale) {
    this.verbale = verbale;
  }
}