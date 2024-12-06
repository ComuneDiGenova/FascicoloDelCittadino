package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.dichiarazioni;

import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazionePermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class DichiarazionePPPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CompoundPropertyModel<DichiarazionePermessiPersonalizzati>
      dichiarazionePermessiPersonalizzatiModel;

  WebMarkupContainer wmkChkDichiarazione;
  private CheckBox chkDichiarazione;
  private Label lblDichiarazione;

  WebMarkupContainer wmkVeicolo;
  private TextField<String> targaAutoVeicoloProprioODelFamiliare;
  private TextField<String> codiceFiscaleAutoVeicoloProprioODelFamiliare;
  private TextField<String> nomeProprietarioAutoVeicoloProprioODelFamiliare;
  private TextField<String> cognomeProprietarioAutoVeicoloProprioODelFamiliare;

  @SuppressWarnings("unused")
  private CompoundPropertyModel<RichiestaPermessoPersonalizzato>
      richiestaPermessoPersonalizzatoModel;

  @SuppressWarnings("unused")
  private DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel;

  public DichiarazionePPPanel(
      String id,
      CompoundPropertyModel<DichiarazionePermessiPersonalizzati>
          dichiarazionePermessiPersonalizzatiModel,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel) {
    super(id);
    this.dichiarazioniPermessiPersonalizzatiPanel = dichiarazioniPermessiPersonalizzatiPanel;
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    this.dichiarazionePermessiPersonalizzatiModel = dichiarazionePermessiPersonalizzatiModel;
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    wmkChkDichiarazione = new WebMarkupContainer("wmkChkDichiarazione");

    addOrReplace(wmkChkDichiarazione);
    chkDichiarazione =
        new CheckBox(
            "chkDichiarazione",
            dichiarazionePermessiPersonalizzatiModel.bind("valoreDichiarazione"));

    wmkChkDichiarazione.add(new AttributeModifier("for", chkDichiarazione.getMarkupId()));
    wmkChkDichiarazione.addOrReplace(chkDichiarazione);

    String asteriscoPerDichiarazioneAlternativa = "";
    if (dichiarazionePermessiPersonalizzatiModel.getObject().getDichiarazioneAlternativa() != null
        && dichiarazionePermessiPersonalizzatiModel
            .getObject()
            .getDichiarazioneAlternativa()
            .equalsIgnoreCase("1")) asteriscoPerDichiarazioneAlternativa = "(*)";

    lblDichiarazione =
        new Label(
            "lblDichiarazione",
            dichiarazionePermessiPersonalizzatiModel.getObject().getDescrizioneDichiarazione()
                + asteriscoPerDichiarazioneAlternativa);
    lblDichiarazione.setEscapeModelStrings(false);

    wmkChkDichiarazione.addOrReplace(lblDichiarazione);

    wmkVeicolo = new WebMarkupContainer("wmkVeicolo");

    targaAutoVeicoloProprioODelFamiliare =
        new TextField<>(
            "targaAutoVeicoloProprioODelFamiliare",
            dichiarazionePermessiPersonalizzatiModel.bind(
                "veicolo.targaAutoVeicoloProprioODelFamiliare"));

    targaAutoVeicoloProprioODelFamiliare.setOutputMarkupId(true);
    targaAutoVeicoloProprioODelFamiliare.setOutputMarkupPlaceholderTag(true);

    wmkVeicolo.addOrReplace(targaAutoVeicoloProprioODelFamiliare);

    codiceFiscaleAutoVeicoloProprioODelFamiliare =
        new TextField<>(
            "codiceFiscaleAutoVeicoloProprioODelFamiliare",
            dichiarazionePermessiPersonalizzatiModel.bind(
                "veicolo.codiceFiscaleAutoVeicoloProprioODelFamiliare"));

    wmkVeicolo.addOrReplace(codiceFiscaleAutoVeicoloProprioODelFamiliare);

    cognomeProprietarioAutoVeicoloProprioODelFamiliare =
        new TextField<>(
            "cognomeProprietarioAutoVeicoloProprioODelFamiliare",
            dichiarazionePermessiPersonalizzatiModel.bind(
                "veicolo.cognomeProprietarioAutoVeicoloProprioODelFamiliare"));

    wmkVeicolo.addOrReplace(cognomeProprietarioAutoVeicoloProprioODelFamiliare);

    nomeProprietarioAutoVeicoloProprioODelFamiliare =
        new TextField<>(
            "nomeProprietarioAutoVeicoloProprioODelFamiliare",
            dichiarazionePermessiPersonalizzatiModel.bind(
                "veicolo.nomeProprietarioAutoVeicoloProprioODelFamiliare"));

    wmkVeicolo.addOrReplace(nomeProprietarioAutoVeicoloProprioODelFamiliare);

    wmkVeicolo.setVisible(
        dichiarazionePermessiPersonalizzatiModel
            .getObject()
            .getCodiceDichiarazione()
            .equalsIgnoreCase("VCL"));

    add(wmkVeicolo);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    fillDati(dichiarazionePermessiPersonalizzatiModel);
  }
}
