package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.erede.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.service.ServiziTariEngService;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCodiceFiscaleTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.resource.FascicoloPdfResources;
import it.liguriadigitale.ponmetro.portale.presentation.components.tarieng.TipoEccedenzaRimborsoTariEngDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.tarieng.TipologiaRichiedenteRimborsoTariEngDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiRichiestaRimborsoTariEngEredePanel extends BasePanel {

  private static final long serialVersionUID = 647569895351597327L;

  private int index;

  public DatiRichiestaRimborsoTariEngEredePanel(String id) {
    super(id);
  }

  public DatiRichiestaRimborsoTariEngEredePanel(
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

    log.debug("CP dati rimborso " + datiRimborso);

    WebMarkupContainer containerEccedenze = new WebMarkupContainer("containerEccedenze");
    containerEccedenze.setOutputMarkupId(true);
    containerEccedenze.setOutputMarkupPlaceholderTag(true);
    containerEccedenze.setVisible(false);
    addOrReplace(containerEccedenze);

    String codiceHelpNetribe = BaseServiceImpl.COD_HELP_RIMBORSO_TARI;
    Long idHelpNetribe = -2L;
    addOrReplace(downloadPdfEsempio("btnDownloadEsempioNetribe", codiceHelpNetribe, idHelpNetribe));

    String codiceHelpEng = BaseServiceImpl.COD_HELP_RIMBORSO_TARI;
    Long idHelpEng = -4L;
    addOrReplace(downloadPdfEsempio("btnDownloadEsempioEng", codiceHelpEng, idHelpEng));

    add(new FdCTitoloPanel("titolo", getString("DatiRichiestaRimborsoTariEngEredePanel.titolo")));

    TipologiaRichiedenteRimborsoTariEngDropDownChoice tipologiaRichiedente =
        new TipologiaRichiedenteRimborsoTariEngDropDownChoice(
            "tipologiaRichiedente", new PropertyModel(datiRimborso, "tipologiaRichiedente"));
    tipologiaRichiedente.setRequired(true);
    tipologiaRichiedente.setLabel(Model.of("Tipologia richiedente"));
    addOrReplace(tipologiaRichiedente);

    FdCTextField nome =
        new FdCTextField(
            "nome",
            new PropertyModel(datiRimborso, "nomeRichiedente"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    nome.setRequired(true);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            new PropertyModel(datiRimborso, "cognomeRichiedente"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    cognome.setRequired(true);
    addOrReplace(cognome);

    FdCCodiceFiscaleTextField codiceFiscale =
        new FdCCodiceFiscaleTextField(
            "codiceFiscale",
            new PropertyModel(datiRimborso, "codiceFiscaleRichiedente"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    codiceFiscale.getTextField().setRequired(true);
    addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(datiRimborso, "emailRichiedente"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCTextField note =
        new FdCTextField(
            "note",
            new PropertyModel(datiRimborso, "note"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    note.setVisible(false);
    addOrReplace(note);

    FdCNumberField idDeb =
        new FdCNumberField(
            "idDeb",
            new PropertyModel(datiRimborso, "idDeb"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    idDeb.getNumberField().setMinimum(0L);
    addOrReplace(idDeb);

    FdCTextField numeroDocumento =
        new FdCTextField(
            "numeroDocumento",
            new PropertyModel(datiRimborso, "numeroDocumento"),
            DatiRichiestaRimborsoTariEngEredePanel.this);
    numeroDocumento.getTextField().add(StringValidator.minimumLength(0));
    numeroDocumento.getTextField().add(StringValidator.maximumLength(30));
    addOrReplace(numeroDocumento);

    NumberTextField<Double> eccTari =
        new NumberTextField<Double>("eccTari", new PropertyModel<Double>(datiRimborso, "eccTari"));
    eccTari.setMinimum(0.0);
    eccTari.add(new AttributeModifier("step", "any"));
    eccTari.setLabel(Model.of("Importo TARI"));
    eccTari.setVisible(false);
    eccTari.setOutputMarkupId(true);
    eccTari.setOutputMarkupPlaceholderTag(true);
    eccTari.setRequired(true);
    containerEccedenze.addOrReplace(eccTari);

    NumberTextField<Double> eccTefa =
        new NumberTextField<Double>("eccTefa", new PropertyModel<Double>(datiRimborso, "eccTefa"));
    eccTefa.setMinimum(0.0);
    eccTefa.add(new AttributeModifier("step", "any"));
    eccTefa.setLabel(Model.of("Importo TEFA"));
    eccTefa.setVisible(false);
    eccTefa.setOutputMarkupId(true);
    eccTefa.setOutputMarkupPlaceholderTag(true);
    eccTefa.setRequired(true);
    containerEccedenze.addOrReplace(eccTefa);

    NumberTextField<Double> eccTariR =
        new NumberTextField<Double>(
            "eccTariR", new PropertyModel<Double>(datiRimborso, "eccTariR"));
    eccTariR.setMinimum(0.0);
    eccTariR.add(new AttributeModifier("step", "any"));
    eccTariR.setLabel(Model.of("Importo Eccedenza da altri pagamenti"));
    eccTariR.setVisible(false);
    eccTariR.setOutputMarkupId(true);
    eccTariR.setOutputMarkupPlaceholderTag(true);
    eccTariR.setRequired(true);
    containerEccedenze.addOrReplace(eccTariR);

    TipoEccedenzaRimborsoTariEngDropDownChoice tipoEccedenzaRimborso =
        new TipoEccedenzaRimborsoTariEngDropDownChoice(
            "tipoEccedenzaRimborso", new PropertyModel(datiRimborso, "tipoEccedenzaRimborso"));

    tipoEccedenzaRimborso.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 6977208351439395179L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("CP tipoEccedenzaRimborso = " + tipoEccedenzaRimborso.getValue());

            if (LabelFdCUtil.checkIfNotNull(tipoEccedenzaRimborso)
                && LabelFdCUtil.checkIfNotNull(tipoEccedenzaRimborso.getValue())) {

              if (tipoEccedenzaRimborso.getValue().equalsIgnoreCase("1")) {

                eccTari.setVisible(true);
                eccTefa.setVisible(false);
                eccTariR.setVisible(false);

                containerEccedenze.setVisible(true);

                datiRimborso.setEccTefa(null);
                datiRimborso.setEccTariR(null);

              } else if (tipoEccedenzaRimborso.getValue().equalsIgnoreCase("2")) {

                eccTari.setVisible(false);
                eccTefa.setVisible(true);
                eccTariR.setVisible(false);

                containerEccedenze.setVisible(true);

                datiRimborso.setEccTari(null);
                datiRimborso.setEccTariR(null);

              } else if (tipoEccedenzaRimborso.getValue().equalsIgnoreCase("3")) {

                eccTari.setVisible(false);
                eccTefa.setVisible(false);
                eccTariR.setVisible(true);

                containerEccedenze.setVisible(true);

                datiRimborso.setEccTefa(null);
                datiRimborso.setEccTari(null);
              }
            } else {
              containerEccedenze.setVisible(false);
            }

            target.add(containerEccedenze, eccTari, eccTefa, eccTariR);
          }
        });

    addOrReplace(tipoEccedenzaRimborso);
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

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfEsempio(String idWicket, String codiceHelp, Long idHelp) {

    final FascicoloPdfResources pdfResourceByte;
    String nomePdf = "";

    ServiziTariEngService stampa = ServiceLocator.getInstance().getServiziTariEng();

    byte[] pdf = null;
    try {
      pdf = stampa.getHelpRimborsiPDF(getUtente(), codiceHelp, idHelp);
    } catch (BusinessException e) {
      log.error("Errore durante get pdf esempio rimborso tari" + e.getMessage(), e);
    }

    pdfResourceByte = new FascicoloPdfResources(nomePdf, pdf);
    ResourceLink resourceLink = pdfResourceByte.getResourceLink(idWicket);
    return resourceLink;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
