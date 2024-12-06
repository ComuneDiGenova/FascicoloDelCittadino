package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari;

import it.liguriadigitale.ponmetro.tarinetribe.apiclient.DocumentiPdfApi;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;

public class TariDocumentiPdfImpl implements DocumentiPdfApi {

  private DocumentiPdfApi instance;

  public TariDocumentiPdfImpl(DocumentiPdfApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public FileAllegato getAgevolazioneTariffariaTariPdf(String arg0) {
    return instance.getAgevolazioneTariffariaTariPdf(arg0);
  }
}
