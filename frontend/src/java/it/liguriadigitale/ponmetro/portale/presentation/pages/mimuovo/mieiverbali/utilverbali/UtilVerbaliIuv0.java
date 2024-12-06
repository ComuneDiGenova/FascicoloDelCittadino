package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportiPagabili;
import java.math.BigDecimal;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public class UtilVerbaliIuv0 {

  private static final String ID_SERVIZIO = "10025WEBIUVES";

  // static final String URL_PAGOPA =
  // "https://pagamenti.comune.genova.it/mip-portale/InitServizio.do?idServizio=PAIUV";

  static final String URL_PEOPLE =
      "https://vm-peopleprod.comune.genova.it/people/initProcess.do?processName=it.people.fsl.servizi.fiscali.pagamenti.pagcontravvenzione";

  public static boolean checkVisibilitaIuv0(DettaglioVerbale dettaglioVerbale) {
    return UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getIuv0())
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getCodiceAvviso0());
  }

  public static boolean checkVisibilitaCodiceAvviso0(DettaglioVerbale dettaglioVerbale) {
    return UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getCodiceAvviso0());
  }

  public static boolean checkVisibilitaImporto0(DettaglioVerbale dettaglioVerbale) {
    return UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getImporto0());
  }

  public static boolean checkVisibilitaBtnPagaIuv0(DettaglioVerbale dettaglioVerbale) {
    return UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getIuv0());
  }

  public static AbstractLink creaBtnPagaIuv0(ImportiPagabili importiPagabili, Utente utente) {
    String wicketID = "btnPagaIuv0";
    MipData data = new MipData();
    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      data = creaMipDataIuv0(importiPagabili, utente);
    }
    return creaBottonePeopleOrMipPagoPA(importiPagabili, wicketID, data);
  }

  private static MipData creaMipDataIuv0(ImportiPagabili importiPagabili, Utente utente) {
    MipData data = new MipData();
    data.setImportoAvviso(importiPagabili.getImporto0());
    data.setNumeroDocumento(importiPagabili.getIuv0());
    data.setIdServizio(ID_SERVIZIO);
    data.setUtente(utente);
    return data;
  }

  public static MipData creaMipDataRata(Utente utente, String numeroDocumento, BigDecimal importo) {
    MipData data = new MipData();
    data.setImportoAvviso(importo);
    data.setNumeroDocumento(numeroDocumento);
    data.setIdServizio(ID_SERVIZIO);
    data.setUtente(utente);
    return data;
  }

  // TODO ma si può pagare iuv 0 con people ???
  private static AbstractLink creaBottonePeopleOrMipPagoPA(
      ImportiPagabili importiPagabili, String wicketID, MipData data) {
    String urlPagamento;
    if (UtilVerbali.checkIfNotNull(importiPagabili)
        && UtilVerbali.checkIfNotNull(importiPagabili.getIuv0())) {

      @SuppressWarnings("rawtypes")
      Link link =
          new Link(wicketID) {

            private static final long serialVersionUID = -3502340521645296826L;

            @Override
            public void onClick() {
              PagamentoOnLinePage page = new PagamentoOnLinePage(data);
              setResponsePage(page);
            }

            @Override
            public MarkupContainer setDefaultModel(IModel model) {
              return setDefaultModel(model);
            }
          };
      return link;
    } else {
      urlPagamento = URL_PEOPLE;
      ExternalLink linkVaiAlPagamento = new ExternalLink(wicketID, urlPagamento);
      return linkVaiAlPagamento;
    }
  }
}
