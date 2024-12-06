package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class DatiRichiestaRimborsoTariEngPanel extends BasePanel {

  private static final long serialVersionUID = -8937295673972101266L;

  private int index;

  public DatiRichiestaRimborsoTariEngPanel(String id) {
    super(id);
  }

  public DatiRichiestaRimborsoTariEngPanel(
      String id, DatiRichiestaRimborsoTariEng datiRimborso, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiRimborso);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    DatiRichiestaRimborsoTariEng datiRimborso = (DatiRichiestaRimborsoTariEng) dati;

    FdCTitoloPanel titolo =
        new FdCTitoloPanel("titolo", getString("DatiRichiestaRimborsoTariEngPanel.titolo"));
    titolo.setVisible(isRimborsoRichiedibile(datiRimborso));
    addOrReplace(titolo);

    WebMarkupContainer containerRimborsoGiaChiesto =
        new WebMarkupContainer("containerRimborsoGiaChiesto");
    containerRimborsoGiaChiesto.setOutputMarkupId(true);
    containerRimborsoGiaChiesto.setOutputMarkupPlaceholderTag(true);
    containerRimborsoGiaChiesto.setVisible(!isRimborsoRichiedibile(datiRimborso));
    addOrReplace(containerRimborsoGiaChiesto);

    WebMarkupContainer containerRimborsoDaChiedere =
        new WebMarkupContainer("containerRimborsoDaChiedere");

    FdCTextField nome =
        new FdCTextField(
            "nome",
            new PropertyModel(datiRimborso, "nomeRichiedente"),
            DatiRichiestaRimborsoTariEngPanel.this);
    nome.setRequired(true);
    nome.setEnabled(false);
    containerRimborsoDaChiedere.addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            new PropertyModel(datiRimborso, "cognomeRichiedente"),
            DatiRichiestaRimborsoTariEngPanel.this);
    cognome.setRequired(true);
    cognome.setEnabled(false);
    containerRimborsoDaChiedere.addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale",
            new PropertyModel(datiRimborso, "codiceFiscaleRichiedente"),
            DatiRichiestaRimborsoTariEngPanel.this);
    codiceFiscale.setRequired(true);
    codiceFiscale.setEnabled(false);
    containerRimborsoDaChiedere.addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(datiRimborso, "emailRichiedente"),
            DatiRichiestaRimborsoTariEngPanel.this);
    email.getTextField().setRequired(true);
    containerRimborsoDaChiedere.addOrReplace(email);

    FdCTextField idDeb =
        new FdCTextField(
            "idDeb",
            new PropertyModel(datiRimborso, "idDeb"),
            DatiRichiestaRimborsoTariEngPanel.this);
    idDeb.setEnabled(false);
    containerRimborsoDaChiedere.addOrReplace(idDeb);

    FdCTextField note =
        new FdCTextField(
            "note",
            new PropertyModel(datiRimborso, "note"),
            DatiRichiestaRimborsoTariEngPanel.this);
    note.setVisible(false);
    containerRimborsoDaChiedere.addOrReplace(note);

    containerRimborsoDaChiedere.addOrReplace(
        createFdCTextFieldCurrency(
                "eccTari", new PropertyModel<Double>(datiRimborso, "eccTari"), false, true)
            .setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTari())));

    FdCTextField infoEccTari =
        new FdCTextField(
            "infoEccTari",
            new PropertyModel(datiRimborso, "infoEccTari"),
            DatiRichiestaRimborsoTariEngPanel.this);
    infoEccTari.setEnabled(false);
    infoEccTari.setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTari()));
    containerRimborsoDaChiedere.addOrReplace(infoEccTari);

    containerRimborsoDaChiedere.addOrReplace(
        createFdCTextFieldCurrency(
                "eccTefa", new PropertyModel<Double>(datiRimborso, "eccTefa"), false, true)
            .setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTefa())));

    FdCTextField infoEccTefa =
        new FdCTextField(
            "infoEccTefa",
            new PropertyModel(datiRimborso, "infoEccTefa"),
            DatiRichiestaRimborsoTariEngPanel.this);
    infoEccTefa.setEnabled(false);
    infoEccTefa.setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTefa()));
    containerRimborsoDaChiedere.addOrReplace(infoEccTefa);

    containerRimborsoDaChiedere.addOrReplace(
        createFdCTextFieldCurrency(
                "eccTariR", new PropertyModel<Double>(datiRimborso, "eccTariR"), false, true)
            .setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTariR())));

    FdCTextField infoEccTariR =
        new FdCTextField(
            "infoEccTariR",
            new PropertyModel(datiRimborso, "infoEccTariR"),
            DatiRichiestaRimborsoTariEngPanel.this);
    infoEccTariR.setEnabled(false);
    infoEccTariR.setVisible(isImportoEccedenzaPresente(datiRimborso.getEccTariR()));
    containerRimborsoDaChiedere.addOrReplace(infoEccTariR);

    containerRimborsoDaChiedere.addOrReplace(
        createFdCTextFieldCurrency(
                "sommaEccedenze",
                new PropertyModel<Double>(datiRimborso, "sommaEccedenze"),
                false,
                true)
            .setVisible(isImportoEccedenzaPresente(datiRimborso.getSommaEccedenze())));

    containerRimborsoDaChiedere.setOutputMarkupId(true);
    containerRimborsoDaChiedere.setOutputMarkupPlaceholderTag(true);
    containerRimborsoDaChiedere.setVisible(isRimborsoRichiedibile(datiRimborso));
    addOrReplace(containerRimborsoDaChiedere);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }

  private FdCTextFieldCurrency<Component> createFdCTextFieldCurrency(
      String id, IModel<Double> model, boolean enabled, boolean isRequired) {
    FdCTextFieldCurrency<Component> field =
        new FdCTextFieldCurrency<Component>(id, model, DatiRichiestaRimborsoTariEngPanel.this, "€");
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    return field;
  }

  private boolean isImportoEccedenzaPresente(Double importoEccedenza) {
    return LabelFdCUtil.checkIfNotNull(importoEccedenza)
        && Double.compare(importoEccedenza, 0.0) > 0;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  private boolean isRimborsoRichiedibile(DatiRichiestaRimborsoTariEng datiRichiesta) {
    return ServiceLocator.getInstance()
        .getServiziTariEng()
        .checkRimborsoIntestarioRichiedibile(datiRichiesta);
  }
}
