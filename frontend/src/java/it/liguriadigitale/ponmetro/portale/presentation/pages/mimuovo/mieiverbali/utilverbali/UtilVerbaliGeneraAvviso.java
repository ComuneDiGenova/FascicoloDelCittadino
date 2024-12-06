package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraAvviso;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoPagamentoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportiPagabili;
import java.math.BigInteger;
import java.time.LocalDate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class UtilVerbaliGeneraAvviso {

  private static Log log = LogFactory.getLog(UtilVerbaliGeneraAvviso.class);

  public static boolean checkInizioGeneraAvviso(DettaglioVerbale dettaglioVerbale) {
    LocalDate dateInizio = LocalDate.of(2019, 01, 01);
    boolean checkData = false;
    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getDataAccertamento())) {
      checkData =
          dettaglioVerbale.getDataAccertamento().isAfter(dateInizio)
              || dettaglioVerbale.getDataAccertamento().isEqual(dateInizio);
    }
    return checkData;
  }

  public static boolean checkVisibilitaBtnGeneraAvviso5gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean btnGeneraAvviso5ggVisibile = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)) {
      if (!dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())
          && UtilVerbali.checkIfNotNull(dettaglioVerbale.getStatoPagamento())) {
        if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.INSUFFICIENTE.toString())
              && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagati())
              && !dettaglioVerbale.getImportiPagati().isEmpty()
              && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
              && UtilVerbali.checkData5gg(dettaglioVerbale.getImportiPagabili().getDataEntro5GG())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getPdfAvvisoIntegrazione())) {
            btnGeneraAvviso5ggVisibile = true;
          }
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.toString())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getIuvIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())
              && UtilVerbali.checkData5gg(dettaglioVerbale.getImportiPagabili().getDataEntro5GG())
              && (!UtilVerbali.checkIfNotNull(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro5GG())
                  || UtilVerbali.checkNotEmpty(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro5GG()))) {
            btnGeneraAvviso5ggVisibile = true;
          }
        } else {
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.toString())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getIuvIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getDataEntro5GG())
              && (!UtilVerbali.checkIfNotNull(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro5GG())
                  || UtilVerbali.checkNotEmpty(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro5GG()))) {
            btnGeneraAvviso5ggVisibile = true;
          }
        }
      }
    }

    log.debug("CP btnGeneraAvviso5ggVisibile = " + btnGeneraAvviso5ggVisibile);
    return btnGeneraAvviso5ggVisibile;
  }

  public static boolean checkVisibilitaBtnGeneraAvviso60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean visibilitaBtnGeneraAvviso60gg = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)) {
      if (!dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())
          && UtilVerbali.checkIfNotNull(dettaglioVerbale.getStatoPagamento())) {
        if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.INSUFFICIENTE.toString())
              && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagati())
              && !dettaglioVerbale.getImportiPagati().isEmpty()
              && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
              && UtilVerbali.checkDataEntro60gg(
                  dettaglioVerbale.getImportiPagabili().getDataEntro5GG(),
                  dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getPdfAvvisoIntegrazione())) {
            visibilitaBtnGeneraAvviso60gg = true;
          }
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.toString())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getIuvIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())
              && UtilVerbali.checkDataEntro60gg(
                  dettaglioVerbale.getImportiPagabili().getDataEntro5GG(),
                  dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
              && (!UtilVerbali.checkIfNotNull(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG())
                  || UtilVerbali.checkNotEmpty(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG()))) {
            visibilitaBtnGeneraAvviso60gg = true;
          }
        } else {
          if (dettaglioVerbale
                  .getStatoPagamento()
                  .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.toString())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getIuvIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())
              && !UtilVerbali.checkIfNotNull(
                  dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
              && (!UtilVerbali.checkIfNotNull(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG())
                  || UtilVerbali.checkNotEmpty(
                      dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG()))) {
            visibilitaBtnGeneraAvviso60gg = true;
          }
        }
      }
    }

    log.debug("CP visibilitaBtnGeneraAvviso60gg = " + visibilitaBtnGeneraAvviso60gg);

    return visibilitaBtnGeneraAvviso60gg;
  }

  public static boolean checkVisibilitaBtnGeneraAvvisoDopo60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente) {
    boolean visibilitaBtnGeneraAvvisoDopo60gg = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)) {
      if (!dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())
          && UtilVerbali.checkIfNotNull(dettaglioVerbale.getStatoPagamento())) {
        if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
          visibilitaBtnGeneraAvvisoDopo60gg =
              getGeneraAvvisoOltre60gg(dettaglioVerbale, visibilitaBtnGeneraAvvisoDopo60gg);
        } else {

          if (UtilVerbali.checkIfNull(
              UtilVerbaliDataNotifica.getAnagraficaUtente(dettaglioVerbale, utente))) {
            if (UtilVerbali.checkIfNotNull(
                UtilVerbaliDataNotifica.getDataNotificaSeProprietarioVeicoloNonInAnagrafiche(
                    dettaglioVerbale))) {

              visibilitaBtnGeneraAvvisoDopo60gg =
                  getGeneraAvvisoOltre60gg(dettaglioVerbale, visibilitaBtnGeneraAvvisoDopo60gg);
            }
          }
        }
      }
    }

    log.debug("CP visibilitaBtnGeneraAvvisoDopo60gg = " + visibilitaBtnGeneraAvvisoDopo60gg);

    return visibilitaBtnGeneraAvvisoDopo60gg;
  }

  private static boolean getGeneraAvvisoOltre60gg(
      DettaglioVerbale dettaglioVerbale, boolean visibilitaBtnGeneraAvvisoDopo60gg) {
    if ((dettaglioVerbale
                .getStatoPagamento()
                .equalsIgnoreCase(StatoPagamentoEnum.INSUFFICIENTE.toString())
            || dettaglioVerbale
                .getStatoPagamento()
                .equalsIgnoreCase(StatoPagamentoEnum.OLTRE_I_TERMINI.toString()))
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagati())
        && !dettaglioVerbale.getImportiPagati().isEmpty()
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkDataOltre60gg(dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
        && !UtilVerbali.checkIfNotNull(
            dettaglioVerbale.getImportiPagabili().getPdfAvvisoIntegrazione())) {
      visibilitaBtnGeneraAvvisoDopo60gg = true;
    }
    if (dettaglioVerbale
            .getStatoPagamento()
            .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.toString())
        && !UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getIuvIntegrazione())
        && !UtilVerbali.checkIfNotNull(
            dettaglioVerbale.getImportiPagabili().getCodiceAvvisoIntegrazione())
        && UtilVerbali.checkDataOltre60gg(dettaglioVerbale.getImportiPagabili().getDataEntro60GG())
        && (!UtilVerbali.checkIfNotNull(
                dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG())
            || UtilVerbali.checkNotEmpty(
                dettaglioVerbale.getImportiPagabili().getCodiceAvvisoEntro60GG()))) {
      visibilitaBtnGeneraAvvisoDopo60gg = true;
    }
    return visibilitaBtnGeneraAvvisoDopo60gg;
  }

  public static DatiGeneraAvviso datiGeneraAvviso(
      DettaglioVerbale dettaglioVerbale,
      String dataVerbale,
      BigInteger importoDaPagare,
      String indirizzoResidenza,
      String tipologiaAnagrafica,
      Utente utente) {
    DatiGeneraAvviso datiGeneraAvviso = new DatiGeneraAvviso();
    datiGeneraAvviso.setNumeroProtocollo(dettaglioVerbale.getNumeroProtocollo());
    datiGeneraAvviso.setNumeroAvviso(dettaglioVerbale.getNumeroAvviso());
    datiGeneraAvviso.setDataVerbale(dataVerbale);
    datiGeneraAvviso.setTarga(dettaglioVerbale.getTarga());
    datiGeneraAvviso.setImportoDaPagare(String.valueOf(importoDaPagare));
    datiGeneraAvviso.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
    datiGeneraAvviso.setCognome(utente.getCognome());
    datiGeneraAvviso.setNome(utente.getNome());
    datiGeneraAvviso.setIndirizzoResidenza(indirizzoResidenza);
    datiGeneraAvviso.setTipoFigura(tipologiaAnagrafica);
    return datiGeneraAvviso;
  }

  public static boolean checkVisibilitaIntegrazione5gg(
      DettaglioVerbale dettaglioVerbale, Utente utente, Object obj) {
    boolean codiceAvviso5gg = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(obj)) {

      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        LocalDate data5gg = dettaglioVerbale.getImportiPagabili().getDataEntro5GG();
        if (UtilVerbali.checkData5gg(data5gg)) {
          codiceAvviso5gg = true;
        }
      } else {
        codiceAvviso5gg = true;
      }
    }

    return codiceAvviso5gg;
  }

  public static boolean checkVisibilitaIntegrazioneEntro60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente, Object obj) {
    boolean codiceAvviso60gg = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(obj)) {
      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)) {
        if (UtilVerbali.checkDataEntro60gg(
            dettaglioVerbale.getImportiPagabili().getDataEntro5GG(),
            dettaglioVerbale.getImportiPagabili().getDataEntro60GG())) {
          codiceAvviso60gg = true;
        }
      } else {
        codiceAvviso60gg = true;
      }
    }

    return codiceAvviso60gg;
  }

  public static boolean checkVisibilitaIntegrazioneOltre60gg(
      DettaglioVerbale dettaglioVerbale, Utente utente, Object obj) {
    boolean codiceAvvisoOltre60gg = false;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale)
        && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && UtilVerbali.checkIfNotNull(obj)) {

      if (UtilVerbaliDataNotifica.checkDataNotificaPresente(dettaglioVerbale, utente)
          && UtilVerbali.checkDataOltre60gg(
              dettaglioVerbale.getImportiPagabili().getDataEntro60GG())) {
        codiceAvvisoOltre60gg = true;
      }
    }

    return codiceAvvisoOltre60gg;
  }

  public static ResourceLink downloadPdfAvvisoIntegrazione(
      DettaglioVerbale dettaglioVerbale, String wicketIdPdfAvvisoIntegrazione) {

    final AbstractResource fileResourceByte;

    ImportiPagabili importiPagabili = dettaglioVerbale.getImportiPagabili();

    FileAllegato pdfAvvisoIntegrazione = importiPagabili.getPdfAvvisoIntegrazione();

    String nomeFile = dettaglioVerbale.getNumeroProtocollo().concat("_").concat("Integrazione");

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = -2935920199932755344L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(pdfAvvisoIntegrazione.getFile().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(pdfAvvisoIntegrazione.getFile());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf avviso integrazione " + e.getMessage());
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink(wicketIdPdfAvvisoIntegrazione, fileResourceByte) {

          private static final long serialVersionUID = -1799261347673338473L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };

    return linkFile;
  }
}
