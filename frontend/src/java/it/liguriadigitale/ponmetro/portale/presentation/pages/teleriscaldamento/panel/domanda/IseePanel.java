package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;

public class IseePanel extends BasePanel {

  private static final long serialVersionUID = 6279213229824492493L;

  private int index;

  public IseePanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaTeleriscaldamento datiDomanda = (DatiDomandaTeleriscaldamento) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("IseePanel.titolo")));

    WebMarkupContainer containerIseeNonPresente =
        new WebMarkupContainer("containerIseeNonPresente");
    containerIseeNonPresente.setVisible(!checkIseePresentato(datiDomanda));
    addOrReplace(containerIseeNonPresente);

    WebMarkupContainer containerIseePresente = new WebMarkupContainer("containerIseePresente");
    containerIseePresente.setVisible(
        checkIseePresentato(datiDomanda) && checkDomandaPresentabile(datiDomanda));
    addOrReplace(containerIseePresente);

    WebMarkupContainer issePresenteMaFuoriRange =
        new WebMarkupContainer("issePresenteMaFuoriRange");
    issePresenteMaFuoriRange.setVisible(
        checkIseePresentato(datiDomanda) && !checkDomandaPresentabile(datiDomanda));
    addOrReplace(issePresenteMaFuoriRange);

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");

    FdCTextField importoIsee =
        new FdCTextField(
            "importoIsee", new PropertyModel(datiDomanda, "importoIsee"), IseePanel.this);
    importoIsee.setEnabled(false);
    containerDati.addOrReplace(importoIsee);

    FdCTextField numeroComponenti =
        new FdCTextField(
            "numeroComponenti", new PropertyModel(datiDomanda, "numeroComponenti"), IseePanel.this);
    numeroComponenti.setEnabled(false);
    containerDati.addOrReplace(numeroComponenti);

    containerDati.setVisible(checkIseePresentato(datiDomanda));
    addOrReplace(containerDati);
  }

  private boolean checkDomandaPresentabile(DatiDomandaTeleriscaldamento datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isDomandaPresentabile();
  }

  private boolean checkIseePresentato(DatiDomandaTeleriscaldamento datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isIseePresentato();
  }
}
