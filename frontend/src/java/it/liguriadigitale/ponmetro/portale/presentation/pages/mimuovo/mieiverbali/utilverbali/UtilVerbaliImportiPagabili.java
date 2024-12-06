package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.FileAllegato;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportiPagabili;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class UtilVerbaliImportiPagabili {

  protected static Log log = LogFactory.getLog(UtilVerbaliImportiPagabili.class);

  private static final String ID_SERVIZIO = "10025WEBIUVES";

  static final String URL_PEOPLE =
      "https://vm-peopleprod.comune.genova.it/people/initProcess.do?processName=it.people.fsl.servizi.fiscali.pagamenti.pagcontravvenzione";

  public static boolean label5GGInBold(ImportiPagabili importiPagabili) {
    boolean isBold = false;

    if (UtilVerbali.checkIfNotNull(importiPagabili)
        && UtilVerbali.checkData5gg(importiPagabili.getDataEntro5GG())) {
      isBold = true;
    }

    return isBold;
  }

  public static boolean labelEntro60GGInBold(ImportiPagabili importiPagabili) {
    boolean isBold = false;

    if (UtilVerbali.checkIfNotNull(importiPagabili)
        && UtilVerbali.checkDataEntro60gg(
            importiPagabili.getDataEntro5GG(), importiPagabili.getDataEntro60GG())) {
      isBold = true;
    }

    return isBold;
  }

  public static boolean labelOltre60GGInBold(ImportiPagabili importiPagabili) {
    boolean isBold = false;

    if (UtilVerbali.checkIfNotNull(importiPagabili)
        && UtilVerbali.checkDataOltre60gg(importiPagabili.getDataEntro60GG())) {
      isBold = true;
    }

    return isBold;
  }

  public static boolean checkVisibilitaContainerMessaggiInfo(ImportiPagabili importiPagabili) {
    boolean visibile = false;

    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      visibile = true;
    }

    return visibile;
  }

  public static boolean checkVisibilitaTabellaImportiPagabili(DettaglioVerbale dettaglioVerbale) {
    return UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && !UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getIuv0())
        && !UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getCodiceAvviso0());
  }

  public static boolean checkIuvCodiceAvviso(String iuv, String codiceAvviso) {
    boolean presenti = false;

    if (UtilVerbali.checkIfNotNull(iuv) && UtilVerbali.checkIfNotNull(codiceAvviso)) {
      presenti = true;
    }

    return presenti;
  }

  private static boolean checkIuvCodAvv5gg(DettaglioVerbale dettaglioVerbale) {
    boolean presenti = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {
      if (checkIuvCodiceAvviso(
              dettaglioVerbale.getImportiPagabili().getIuvEntro5GG(),
              dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro5GG())
          || checkIuvCodiceAvviso(
              dettaglioVerbale.getImportiPagabili().getIuvIntegrazione(),
              dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())) {
        presenti = true;
      }
    }

    return presenti;
  }

  private static boolean checkIuvCodAvv60gg(DettaglioVerbale dettaglioVerbale) {
    boolean presenti = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {
      if (checkIuvCodiceAvviso(
              dettaglioVerbale.getImportiPagabili().getIuvEntro60GG(),
              dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG())
          || checkIuvCodiceAvviso(
              dettaglioVerbale.getImportiPagabili().getIuvIntegrazione(),
              dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())) {
        presenti = true;
      }
    }

    return presenti;
  }

  public static boolean checkVisibilitaPaga5gg(DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean paga5ggVisible = false;

    if (UtilVerbali.checkDataAccertamentoInizioPagoPa(dettaglioVerbale.getDataAccertamento())) {
      if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
          && UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkData5gg(dettaglioVerbale.getImportiPagabili().getDataEntro5GG())
            && (checkIuvCodAvv5gg(dettaglioVerbale))) {
          paga5ggVisible = true;
        }
      } else {
        if (checkIuvCodAvv5gg(dettaglioVerbale)) {
          paga5ggVisible = true;
        }
      }
    }

    return paga5ggVisible;
  }

  public static boolean checkVisibilitaPagaEntro60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean paga60ggVisible = false;

    if (UtilVerbali.checkDataAccertamentoInizioPagoPa(dettaglioVerbale.getDataAccertamento())) {
      if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
          && UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkDataEntro60gg(
                dettaglioVerbale.getImportiPagabili().getDataEntro5GG(),
                dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
            && (checkIuvCodAvv60gg(dettaglioVerbale))) {
          paga60ggVisible = true;
        }
      } else {
        if (checkIuvCodAvv60gg(dettaglioVerbale)) {
          paga60ggVisible = true;
        }
      }
    }

    return paga60ggVisible;
  }

  public static boolean checkVisibilitaPagaDopo60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean pagadopo60ggVisible = false;

    if (UtilVerbali.checkDataAccertamentoInizioPagoPa(dettaglioVerbale.getDataAccertamento())) {
      if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
          && UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkDataOltre60gg(dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
            && (checkIuvCodAvv60gg(dettaglioVerbale))) {
          pagadopo60ggVisible = true;
        }
      }
    }

    return pagadopo60ggVisible;
  }

  public static AbstractLink creaBtnPaga5gg(ImportiPagabili importiPagabili, Utente utente) {
    String wicketID = "btnPaga5gg";
    MipData data = new MipData();
    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      data = creaMipDataCinqueGG(importiPagabili, utente);
    }
    return creaBottonePeopleOrMipPagoPA(importiPagabili, wicketID, data);
  }

  public static AbstractLink creaBtnPagaDopo60gg(ImportiPagabili importiPagabili, Utente utente) {
    String wicketID = "btnPagaDopo60gg";
    MipData data = new MipData();
    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      data = creaMipDataOltre60GG(importiPagabili, utente);
    }
    return creaBottonePeopleOrMipPagoPA(importiPagabili, wicketID, data);
  }

  public static AbstractLink creaBtnPaga60gg(ImportiPagabili importiPagabili, Utente utente) {
    String wicketID = "btnPaga60gg";
    MipData data = new MipData();
    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      data = creaMipData60gg(importiPagabili, utente);
    }
    return creaBottonePeopleOrMipPagoPA(importiPagabili, wicketID, data);
  }

  private static MipData creaMipDataCinqueGG(ImportiPagabili importiPagabili, Utente utente) {
    MipData data = new MipData();
    data.setImportoAvviso(importiPagabili.getImportoEntro5GG());

    if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvEntro5GG())) {
      data.setNumeroDocumento(importiPagabili.getIuvEntro5GG());
    } else if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvIntegrazione())) {
      data.setNumeroDocumento(importiPagabili.getIuvIntegrazione());
    }

    data.setIdServizio(ID_SERVIZIO);
    data.setUtente(utente);
    return data;
  }

  private static MipData creaMipData60gg(ImportiPagabili importiPagabili, Utente utente) {
    MipData data = new MipData();

    if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvEntro60GG())) {
      data.setNumeroDocumento(importiPagabili.getIuvEntro60GG());
      data.setImportoAvviso(importiPagabili.getImportoEntro60GG());
    } else if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvIntegrazione())) {
      data.setNumeroDocumento(importiPagabili.getIuvIntegrazione());
      if (importiPagabili.getImportoIntegrazioneEntro60GG() != null) {
        data.setImportoAvviso(importiPagabili.getImportoIntegrazioneEntro60GG());
      } else if (importiPagabili.getImportoEntro60GG() != null) {
        data.setImportoAvviso(importiPagabili.getImportoEntro60GG());
      }
    }

    data.setIdServizio(ID_SERVIZIO);
    data.setUtente(utente);
    return data;
  }

  private static MipData creaMipDataOltre60GG(ImportiPagabili importiPagabili, Utente utente) {
    MipData data = new MipData();

    if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvEntro60GG())) {
      data.setNumeroDocumento(importiPagabili.getIuvEntro60GG());
      data.setImportoAvviso(importiPagabili.getImportoOltre60GG());
    } else if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvIntegrazione())) {
      data.setNumeroDocumento(importiPagabili.getIuvIntegrazione());
      if (importiPagabili.getImportoIntegrazioneOltre60GG() != null) {
        data.setImportoAvviso(importiPagabili.getImportoIntegrazioneOltre60GG());
      } else if (importiPagabili.getImportoOltre60GG() != null) {
        data.setImportoAvviso(importiPagabili.getImportoOltre60GG());
      }
    }

    data.setIdServizio(ID_SERVIZIO);
    data.setUtente(utente);
    return data;
  }

  public static AbstractLink creaBottonePeopleOrMipPagoPA(
      ImportiPagabili importiPagabili, String wicketID, MipData data) {
    String urlPagamento;
    if (UtilVerbali.checkIfNotNull(importiPagabili)) {
      if (UtilVerbali.checkIfNotNull(importiPagabili.getIuvEntro5GG())
          || UtilVerbali.checkIfNotNull(importiPagabili.getIuvEntro60GG())
          || UtilVerbali.checkIfNotNull(importiPagabili.getIuvIntegrazione())) {

        @SuppressWarnings("rawtypes")
        Link link =
            new Link(wicketID) {

              private static final long serialVersionUID = 7064812082735910085L;

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
    urlPagamento = URL_PEOPLE;
    ExternalLink linkVaiAlPagamento = new ExternalLink(wicketID, urlPagamento);
    return linkVaiAlPagamento;
  }

  public static boolean checkVisibilitaCodiceAvvisoEntro5gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean codiceAvviso5ggVisibile = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {

      ImportiPagabili importiPagabili = dettaglioVerbale.getImportiPagabili();

      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkIfNotNull(importiPagabili.getCodiceAvvisoEntro5GG())
            && UtilVerbali.checkData5gg(importiPagabili.getDataEntro5GG())) {
          codiceAvviso5ggVisibile = true;
        }
      } else {
        if (UtilVerbali.checkIfNotNull(importiPagabili.getCodiceAvvisoEntro5GG())) {
          codiceAvviso5ggVisibile = true;
        }
      }
    }

    return codiceAvviso5ggVisibile;
  }

  public static boolean checkVisibilitaCodiceAvvisoEntro60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean codiceAvvisoEntro60ggVisibile = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {

      ImportiPagabili importiPagabili = dettaglioVerbale.getImportiPagabili();

      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkIfNotNull(importiPagabili.getCodiceAvvisoEntro60GG())
            && UtilVerbali.checkDataEntro60gg(
                importiPagabili.getDataEntro5GG(), importiPagabili.getDataEntro60GG())) {
          codiceAvvisoEntro60ggVisibile = true;
        }
      } else {
        if (UtilVerbali.checkIfNotNull(importiPagabili.getCodiceAvvisoEntro60GG())) {
          codiceAvvisoEntro60ggVisibile = true;
        }
      }
    }

    return codiceAvvisoEntro60ggVisibile;
  }

  public static boolean checkVisibilitaCodiceAvvisoOltre60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean codiceAvvisoOltre60ggVisibile = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {

      ImportiPagabili importiPagabili = dettaglioVerbale.getImportiPagabili();

      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)
          && UtilVerbali.checkIfNotNull(importiPagabili.getCodiceAvvisoEntro60GG())
          && UtilVerbali.checkDataOltre60gg(importiPagabili.getDataEntro60GG())) {
        codiceAvvisoOltre60ggVisibile = true;
      }
    }

    return codiceAvvisoOltre60ggVisibile;
  }

  public static AvvisoPagamento getAvvisoPagamento(
      Utente utente, String codiceAvviso, boolean pdf, boolean attualizza) {
    AvvisoPagamento avvisoPagamento = null;
    try {
      avvisoPagamento =
          ServiceLocator.getInstance()
              .getServiziMipVerticali()
              .getAvvisoPagamentoDaCfECodiceAvviso(utente, codiceAvviso, pdf, attualizza);

    } catch (BusinessException | ApiException e) {
      log.error("Errore durante get avviso pagamento: " + e.getMessage());
    }
    return avvisoPagamento;
  }

  public static ResourceLink downloadPdfAvviso(
      AvvisoPagamento avvisoPagamento, String wicketIdPdfAvviso) {

    final AbstractResource fileResourceByte;

    FileAllegato pdfAvviso = avvisoPagamento.getPdfAvviso();

    String nomeFile = pdfAvviso.getNomeFile();

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = 5056598260387316773L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(pdfAvviso.getFile().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(pdfAvviso.getFile());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf avviso " + e.getMessage());
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink(wicketIdPdfAvviso, fileResourceByte) {

          private static final long serialVersionUID = -4905841306372495386L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };

    return linkFile;
  }

  public static AvvisoPagamento getAvvisoPagamentoAnonimo(
      String codiceAvviso0, boolean pdf, boolean attualizza) {
    AvvisoPagamento avvisoPagamento = null;
    try {
      avvisoPagamento =
          ServiceLocator.getInstance()
              .getServiziMipVerticali()
              .getAvvisoPagamentoDaCfAnonimoECodiceAvviso(codiceAvviso0, pdf, attualizza);

    } catch (BusinessException | ApiException e) {
      log.error("Errore durante get avviso pagamento: " + e.getMessage());
    }
    return avvisoPagamento;
  }
}
