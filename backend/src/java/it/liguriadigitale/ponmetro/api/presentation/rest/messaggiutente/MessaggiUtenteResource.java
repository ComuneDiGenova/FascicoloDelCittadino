package it.liguriadigitale.ponmetro.api.presentation.rest.messaggiutente;

import it.liguriadigitale.ponmetro.api.business.messaggi.impl.MessaggiUtenteImpl;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.VCfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.builder.DatiMessaggioUtenteBuilder;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.messaggi.utente.apiclient.MessaggiUtenteApi;
import it.liguriadigitale.ponmetro.messaggi.utente.model.BodyCambiaStato;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumMessaggiUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.NumeroMessaggiUtente;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessaggiUtenteResource implements MessaggiUtenteApi {

  private static Log log = LogFactory.getLog(MessaggiUtenteResource.class);

  @Override
  public void messaggiUtenteCambiaStatoPut(Long arg0, BodyCambiaStato arg1) {
    log.debug("Inizio -- MessaggiUtenteResource.messaggiUtenteCambiaStatoPut");
    try {
      MessaggiUtenteImpl messaggiUtenteImpl = new MessaggiUtenteImpl();
      messaggiUtenteImpl.setStatoMessaggi(arg0, arg1);
    } catch (Exception e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    }
  }

  @Override
  public EnumMessaggiUtente messaggiUtenteEnumsGet() {
    return null;
  }

  @Override
  public List<DatiMessaggioUtente> messaggiUtenteListaGet(
      Long arg0, EnumFiltroTipologia arg1, Long arg2) {
    log.debug("Inizio -- MessaggiUtenteResource.messaggiUtenteListaGet");
    List<DatiMessaggioUtente> datiToRet = new ArrayList<>();
    try {
      MessaggiUtenteImpl messaggiUtenteImpl = new MessaggiUtenteImpl();
      List<VCfgRFcittNotifiche> lista = messaggiUtenteImpl.getListaMessaggi(arg0, arg1, arg2);
      datiToRet = buildBusinessList(lista);
    } catch (Exception e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    }
    return datiToRet;
  }

  @Override
  public NumeroMessaggiUtente messaggiUtenteNumeroGet(
      Long arg0, EnumFiltroTipologia arg1, Long arg2) {
    log.debug("Inizio -- MessaggiUtenteResource.messaggiUtenteNumeroGet");
    NumeroMessaggiUtente numeroMessaggiUtente = new NumeroMessaggiUtente();
    try {
      MessaggiUtenteImpl messaggiUtenteImpl = new MessaggiUtenteImpl();
      Long numeroTrovato = messaggiUtenteImpl.messaggiUtenteNumeroGet(arg0, arg1, arg2);
      numeroMessaggiUtente.setNumeroMessaggi(numeroTrovato);
    } catch (Exception e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    }
    return numeroMessaggiUtente;
  }

  private List<DatiMessaggioUtente> buildBusinessList(List<VCfgRFcittNotifiche> lista) {
    log.debug("Inizio -- MessaggiUtenteResource.buildBusinessList:::");
    if (lista != null && !lista.isEmpty()) {
      log.debug("helper.cercaOggetti().size: " + lista.size());
      log.debug("helper.cercaOggetti().lista: " + lista);
      ZoneOffset offset = OffsetDateTime.now().getOffset();
      List<DatiMessaggioUtente> datiToRet =
          lista.stream()
              .filter(ithitem -> ithitem != null)
              .map(
                  ithitem ->
                      new DatiMessaggioUtenteBuilder()
                          .setCompartoDataCatalogazione(
                              ithitem.getDataCatalogazioneComp() != null
                                  ? ithitem.getDataCatalogazioneComp().atOffset(offset)
                                  : null)
                          .setCompartoDenominazione(ithitem.getDenominazioneComp())
                          .setCompartoDescrizione(ithitem.getDescrizioneComp())
                          .setCompartoUri(ithitem.getUriComp())
                          .setIdFcittNotifiche(
                              ithitem.getIdFcittNotifiche() != null
                                  ? ithitem.getIdFcittNotifiche().longValue()
                                  : null)
                          .setNotificaData(
                              ithitem.getDataNotifica() != null
                                  ? ithitem.getDataNotifica().atOffset(offset)
                                  : null)
                          .setNotificaTesto(ithitem.getTestoNotifica())
                          .setNotificaUri(ithitem.getUriNotifica())
                          .setOrdinamento(ithitem.getOrdinamento())
                          .setSezioneDenominazione(ithitem.getDenominazioneSez())
                          .setSezioneDescrizione(ithitem.getDescrizioneSez())
                          .setSezioneUri(ithitem.getUriSez())
                          .setVisualizzazioneData(
                              ithitem.getDataPresaVisione() != null
                                  ? ithitem.getDataPresaVisione().atOffset(offset)
                                  : null)
                          .setStatoRecord(ithitem.getIdStatoRec())
                          .build())
              .collect(Collectors.toList());

      log.debug("helper.cercaOggetti().datiToRet: " + datiToRet);
      log.debug("datiToRet: " + datiToRet.size());
      return datiToRet;
    }
    log.debug("buildBusinessList lista null ");
    return new ArrayList<>();
  }
}
