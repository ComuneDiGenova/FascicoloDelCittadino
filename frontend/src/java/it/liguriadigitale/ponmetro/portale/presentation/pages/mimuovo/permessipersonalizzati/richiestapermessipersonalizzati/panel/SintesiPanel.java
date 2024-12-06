package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel;

import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class SintesiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  TextField<String> idDomanda;
  TextField<String> descrizioneProcedimento;
  TextField<String> numeroProtocollo;
  TextField<String> annoProtocollo;

  public SintesiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      boolean attivo) {
    super(id);
    fillDati(richiestaPermessoPersonalizzatoModel);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setEnabled(false);
    setVisible(
        richiestaPermessoPersonalizzatoModel.getObject().getSintesiPermessiPersonalizzati()
            != null);
  }

  @Override
  public void fillDati(Object dati) {
    @SuppressWarnings("unchecked")
    CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel =
        (CompoundPropertyModel<RichiestaPermessoPersonalizzato>) dati;

    addOrReplace(
        idDomanda =
            new TextField<String>(
                "idDomanda",
                richiestaPermessoPersonalizzatoModel.bind(
                    "sintesiPermessiPersonalizzati.idDomanda")));
    idDomanda.setEnabled(false);

    addOrReplace(
        descrizioneProcedimento =
            new TextField<String>(
                "descrizioneProcedimento",
                richiestaPermessoPersonalizzatoModel.bind(
                    "sintesiPermessiPersonalizzati.descrizioneProcedimento")));
    descrizioneProcedimento.setEnabled(false);

    addOrReplace(
        numeroProtocollo =
            new TextField<String>(
                "numeroProtocollo",
                richiestaPermessoPersonalizzatoModel.bind(
                    "sintesiPermessiPersonalizzati.numeroProtocollo")));
    numeroProtocollo.setEnabled(false);

    addOrReplace(
        annoProtocollo =
            new TextField<String>(
                "annoProtocollo",
                richiestaPermessoPersonalizzatoModel.bind(
                    "sintesiPermessiPersonalizzati.annoProtocollo")));
    annoProtocollo.setEnabled(false);
  }
}
