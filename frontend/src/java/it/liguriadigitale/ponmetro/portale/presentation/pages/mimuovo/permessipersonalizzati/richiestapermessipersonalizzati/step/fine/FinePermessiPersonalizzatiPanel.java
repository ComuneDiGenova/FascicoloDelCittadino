package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.fine;

import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;

public class FinePermessiPersonalizzatiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  private Label annoProtocollo;
  private Label numeroProtocollo;
  private Label tipoProtocollo;

  public FinePermessiPersonalizzatiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel) {
    super(id);
    fillDati(richiestaPermessoPersonalizzatoModel);
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel =
        (CompoundPropertyModel<RichiestaPermessoPersonalizzato>) dati;

    addOrReplace(
        annoProtocollo =
            new Label(
                "annoProtocollo",
                richiestaPermessoPersonalizzatoModel.bind("protocollazione.anno")));

    addOrReplace(
        numeroProtocollo =
            new Label(
                "numeroProtocollo",
                richiestaPermessoPersonalizzatoModel.bind("protocollazione.numero")));

    addOrReplace(
        tipoProtocollo =
            new Label(
                "tipoProtocollo",
                richiestaPermessoPersonalizzatoModel.bind("protocollazione.tipoProtocollo")));
  }
}
