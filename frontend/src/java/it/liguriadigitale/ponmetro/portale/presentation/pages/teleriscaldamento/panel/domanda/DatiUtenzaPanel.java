package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service.ServiziTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.resource.FascicoloPdfResources;
import it.liguriadigitale.ponmetro.portale.presentation.components.teleriscaldamento.TipoUtenzaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiUtenzaPanel extends BasePanel {

  private static final long serialVersionUID = 4425083482293450914L;

  private int index;

  public DatiUtenzaPanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
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

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiUtenzaPanel.titolo")));

    FdCTextField numeroCliente =
        new FdCTextField(
            "numeroCliente", new PropertyModel(datiDomanda, "numeroCliente"), DatiUtenzaPanel.this);
    numeroCliente.getTextField().setRequired(true);
    // TODO commentiamo per ora, non togliere
    // numeroCliente.getTextField().add(StringValidator.exactLength(8));
    numeroCliente.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(numeroCliente);

    FdCTextField numeroContratto =
        new FdCTextField(
            "numeroContratto",
            new PropertyModel(datiDomanda, "numeroContratto"),
            DatiUtenzaPanel.this);
    numeroContratto.getTextField().setRequired(true);
    numeroContratto.getTextField().add(StringValidator.exactLength(7));
    numeroContratto.getTextField().add(new PatternValidator("^[0-9]*$"));
    addOrReplace(numeroContratto);

    FdCTextField indirizzoCompleto =
        new FdCTextField(
            "indirizzoCompleto",
            new PropertyModel(datiDomanda, "indirizzoCompleto"),
            DatiUtenzaPanel.this);
    indirizzoCompleto.getTextField().setRequired(true);
    indirizzoCompleto.setEnabled(false);
    addOrReplace(indirizzoCompleto);

    FdCTextField comune =
        new FdCTextField("comune", new PropertyModel(datiDomanda, "comune"), DatiUtenzaPanel.this);
    comune.getTextField().setRequired(true);
    comune.setEnabled(false);
    addOrReplace(comune);

    FdCTextField provincia =
        new FdCTextField(
            "provincia", new PropertyModel(datiDomanda, "provincia"), DatiUtenzaPanel.this);
    provincia.getTextField().setRequired(true);
    provincia.setEnabled(false);
    addOrReplace(provincia);

    FdCTextField cap =
        new FdCTextField("cap", new PropertyModel(datiDomanda, "cap"), DatiUtenzaPanel.this);
    cap.getTextField().setRequired(true);
    cap.setEnabled(false);
    addOrReplace(cap);

    TipoUtenzaDropDownChoice tipoUtenza =
        new TipoUtenzaDropDownChoice("tipoUtenza", new PropertyModel(datiDomanda, "tipoUtenza"));
    tipoUtenza.setRequired(true);
    tipoUtenza.setLabel(Model.of("Tipo utenza"));
    addOrReplace(tipoUtenza);

    addOrReplace(downloadPdfEsempio("btnDownloadEsempio"));
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfEsempio(String idWicket) {

    final FascicoloPdfResources pdfResourceByte;
    String nomePdf = "";

    ServiziTeleriscaldamento stampa = ServiceLocator.getInstance().getServiziTeleriscaldamento();

    byte[] pdf = null;
    try {
      pdf = stampa.getHelpTeleriscaldamentoPDF(getUtente());
    } catch (BusinessException e) {
      log.error("Errore durante get pdf esempio" + e.getMessage(), e);
    }

    pdfResourceByte = new FascicoloPdfResources(nomePdf, pdf);
    ResourceLink resourceLink = pdfResourceByte.getResourceLink(idWicket);
    return resourceLink;
  }
}
