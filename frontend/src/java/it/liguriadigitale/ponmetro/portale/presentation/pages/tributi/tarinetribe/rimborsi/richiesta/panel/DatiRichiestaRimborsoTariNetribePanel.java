package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariRimborsiService;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberDoubleField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.components.resource.FascicoloPdfResources;
import it.liguriadigitale.ponmetro.portale.presentation.components.tarinetribe.TipologiaRichiedenteRimborsoTariNetribeDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.Saldi;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiRichiestaRimborsoTariNetribePanel extends BasePanel {

  private static final long serialVersionUID = 3454331971046452875L;

  private int index;

  public DatiRichiestaRimborsoTariNetribePanel(
      String id, DatiRichiestaRimborsoTariNetribe datiRimborso, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiRimborso);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    DatiRichiestaRimborsoTariNetribe datiRimborso = (DatiRichiestaRimborsoTariNetribe) dati;

    // FdCTitoloPanel titolo = new FdCTitoloPanel("titolo",
    // getString("DatiRichiestaRimborsoTariNetribePanel.titolo"));
    // addOrReplace(titolo);

    FdCTextField nomeIntestatario =
        new FdCTextField(
            "nomeIntestatario",
            new PropertyModel(datiRimborso, "nomeIntestatario"),
            DatiRichiestaRimborsoTariNetribePanel.this);
    nomeIntestatario.setRequired(true);
    nomeIntestatario.setEnabled(!datiRimborso.isIntestatario());
    addOrReplace(nomeIntestatario);

    FdCTextField cognomeIntestatario =
        new FdCTextField(
            "cognomeIntestatario",
            new PropertyModel(datiRimborso, "cognomeIntestatario"),
            DatiRichiestaRimborsoTariNetribePanel.this);
    cognomeIntestatario.setRequired(true);
    cognomeIntestatario.setEnabled(!datiRimborso.isIntestatario());
    addOrReplace(cognomeIntestatario);

    FdCTextField codiceFiscaleIntestatario =
        new FdCTextField(
            "codiceFiscaleIntestatario",
            new PropertyModel(datiRimborso, "codiceFiscaleIntestatario"),
            DatiRichiestaRimborsoTariNetribePanel.this);
    codiceFiscaleIntestatario.setRequired(true);
    codiceFiscaleIntestatario.setEnabled(!datiRimborso.isIntestatario());
    addOrReplace(codiceFiscaleIntestatario);

    TipologiaRichiedenteRimborsoTariNetribeDropDownChoice tipologiaRichiedenteRimborso =
        new TipologiaRichiedenteRimborsoTariNetribeDropDownChoice(
            "tipologiaRichiedenteRimborso",
            new PropertyModel(datiRimborso, "tipologiaRichiedenteRimborso"),
            datiRimborso.isIntestatario());
    tipologiaRichiedenteRimborso.setRequired(true);
    tipologiaRichiedenteRimborso.setLabel(Model.of("Tipologia richiedente"));
    tipologiaRichiedenteRimborso.setEnabled(!datiRimborso.isIntestatario());
    addOrReplace(tipologiaRichiedenteRimborso);

    WebMarkupContainer containerInfoIdAvviso = new WebMarkupContainer("containerInfoIdAvviso");
    containerInfoIdAvviso.setOutputMarkupId(true);
    containerInfoIdAvviso.setOutputMarkupPlaceholderTag(true);
    containerInfoIdAvviso.setVisible(!datiRimborso.isIntestatario());
    String codiceHelpNetribe = BaseServiceImpl.COD_HELP_RIMBORSO_TARI;
    Long idHelpNetribe = -2L;
    containerInfoIdAvviso.addOrReplace(
        downloadPdfEsempio("btnDownloadEsempioNetribe", codiceHelpNetribe, idHelpNetribe));

    String codiceHelpEng = BaseServiceImpl.COD_HELP_RIMBORSO_TARI;
    Long idHelpEng = -4L;
    containerInfoIdAvviso.addOrReplace(
        downloadPdfEsempio("btnDownloadEsempioEng", codiceHelpEng, idHelpEng));

    addOrReplace(containerInfoIdAvviso);

    FdCTextField idAvvisoBolletta =
        new FdCTextField(
            "idAvvisoBolletta",
            new PropertyModel(datiRimborso, "idAvvisoBolletta"),
            DatiRichiestaRimborsoTariNetribePanel.this);
    idAvvisoBolletta.setRequired(true);
    idAvvisoBolletta.setEnabled(!datiRimborso.isIntestatario());
    idAvvisoBolletta.getTextField().add(StringValidator.maximumLength(23));
    idAvvisoBolletta.getTextField().add(StringValidator.minimumLength(14));
    // idAvvisoBolletta.getTextField().add(new TariIdAvvisoValidator());
    idAvvisoBolletta.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(idAvvisoBolletta);

    FdCTextFieldCurrency importoRimborso =
        new FdCTextFieldCurrency(
            "importoRimborso",
            new PropertyModel(datiRimborso, "importoRimborso"),
            DatiRichiestaRimborsoTariNetribePanel.this,
            " €");
    importoRimborso.setEnabled(false);
    importoRimborso.setVisible(
        datiRimborso.isIntestatario()
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getImportoRimborso()));
    addOrReplace(importoRimborso);

    FdCTextField note =
        new FdCTextField(
            "note",
            new PropertyModel(datiRimborso, "note"),
            DatiRichiestaRimborsoTariNetribePanel.this);
    note.setRequired(false);
    note.setVisible(false);
    addOrReplace(note);

    ListView<Saldi> listViewSaldi =
        new ListView<Saldi>("listaSaldi", datiRimborso.getListaSaldi()) {

          @Override
          protected void populateItem(ListItem<Saldi> itemSaldi) {
            int indice = itemSaldi.getIndex();
            final Saldi saldo = itemSaldi.getModelObject();
            itemSaldi.setOutputMarkupId(true);
            itemSaldi.setMarkupId("importo" + indice);

            FdCNumberDoubleField<Component> tributoSaldo =
                new FdCNumberDoubleField(
                    "importo",
                    new PropertyModel(saldo, "importo"),
                    DatiRichiestaRimborsoTariNetribePanel.this,
                    indice) {

                  private static final long serialVersionUID = -2925284217053642379L;

                  @Override
                  protected void creaLabelEtichettaGenerandoResourceId(
                      Component pannello, String wicketId) {
                    String nomePannello = pannello.getClass().getSimpleName();
                    String resourceId = "";

                    if (saldo.getTributo().equalsIgnoreCase("TEFA")) {
                      resourceId = nomePannello + "." + "tefa";
                    } else {
                      resourceId = nomePannello + "." + "tari";
                    }

                    creaLabelEtichetta(pannello, resourceId, wicketId);
                  }
                };

            tributoSaldo.getDoubleField().setMinimum(0.0);
            tributoSaldo.add(new AttributeModifier("step", "any"));

            itemSaldi.addOrReplace(tributoSaldo);
          }
        };
    listViewSaldi.setEnabled(!datiRimborso.isIntestatario());
    addOrReplace(listViewSaldi);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfEsempio(String idWicket, String codiceHelp, Long idHelp) {

    final FascicoloPdfResources pdfResourceByte;
    String nomePdf = "";

    ServiziTariRimborsiService stampa =
        ServiceLocator.getInstance().getServiziTariRimborsiNetribe();

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
}
