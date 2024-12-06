package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.breadcrumb;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BreadcrumbCreaIstanzaPLPanel extends BasePanel {

  private static final long serialVersionUID = 7135447111155056800L;

  public BreadcrumbCreaIstanzaPLPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    add(creaLinkMieiVerbali());
  }

  private LaddaAjaxLink<Object> creaLinkMieiVerbali() {
    LaddaAjaxLink<Object> linkMieiVerbali =
        new LaddaAjaxLink<Object>("mieiVerbali", Type.Link) {

          private static final long serialVersionUID = 1155662943093879968L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new MieiVerbaliPage());
          }
        };

    String testoLink = getString("BreadcrumbCreaIstanzaPLPanel.mieiVerbali");
    linkMieiVerbali.setLabel(Model.of(testoLink));

    AttributeModifier attributeModifier = new AttributeModifier("class", "btn-link ladda-button");
    linkMieiVerbali.add(attributeModifier);

    linkMieiVerbali.setSpinnerColor("#0066cb");

    return linkMieiVerbali;
  }
}
