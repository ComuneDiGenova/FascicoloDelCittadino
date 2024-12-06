package it.liguriadigitale.ponmetro.api.presentation.rest.teleriscaldamento;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrClienti;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrContratti;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.teleriscaldamento.apiclient.TeleriscaldamentoApi;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Cliente;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.ClientiTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.ContrattiTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Contratto;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DatiRicerca;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DatiRicerca.TipologiaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandeTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Esito;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Protocollo;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.RicercaDato;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DomandeResource implements TeleriscaldamentoApi {

  private static Log log = LogFactory.getLog(DomandeResource.class);

  @Override
  public DomandeTeleriscaldamento getDatiCittadino(String codiceFiscale) {

    DomandeTeleriscaldamento domande = new DomandeTeleriscaldamento();
    domande.setCodiceFiscale(codiceFiscale);
    Esito esito = new Esito();

    try {
      List<DomandaTeleriscaldamento> lista =
          ServiceLocator.getInstance()
              .getServiziTeleriscaldamento()
              .getDatiCittadino(codiceFiscale);
      domande.setDomande(lista);
      esito.setEsito(true);
    } catch (BusinessException e) {
      log.error("Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }
    domande.setEsito(esito);
    return domande;
  }

  @Override
  public Protocollo getProtocollo() {

    Protocollo protocollo = new Protocollo();
    Esito esito = new Esito();
    try {
      BigDecimal sequence =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getProtocollo();
      protocollo.setProtocollo(sequence);
      esito.setEsito(true);
    } catch (BusinessException e) {
      log.error("Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }
    protocollo.setEsito(esito);
    return protocollo;
  }

  @Override
  public Esito setDatiCittadino(DomandaTeleriscaldamento domanda) {

    Esito esito = new Esito();
    try {
      ServiceLocator.getInstance().getServiziTeleriscaldamento().setDatiCittadino(domanda);
      esito.setEsito(true);
    } catch (BusinessException e) {
      log.error("Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }
    return esito;
  }

  @Override
  public ClientiTeleriscaldamento getClienti() {
    ClientiTeleriscaldamento clienti = new ClientiTeleriscaldamento();
    Esito esito = new Esito();

    try {
      List<Cliente> lista =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getListaClienti();
      clienti.setClienti(lista);
      esito.setEsito(true);
    } catch (BusinessException e) {
      log.error("[DomandeResource] getClienti Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }
    clienti.setEsito(esito);
    return clienti;
  }

  @Override
  public ContrattiTeleriscaldamento getContratti() {
    ContrattiTeleriscaldamento contratti = new ContrattiTeleriscaldamento();
    Esito esito = new Esito();

    try {
      List<Contratto> lista =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getListaContratti();
      contratti.setContratti(lista);
      esito.setEsito(true);
    } catch (BusinessException e) {
      log.error("[DomandeResource] getContratti Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }
    contratti.setEsito(esito);
    return contratti;
  }

  @Override
  public RicercaDato isClientePresenteInLista(String numeroCliente) {
    RicercaDato ricercaDato = new RicercaDato();
    Esito esito = new Esito();

    try {
      TrClienti cliente =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getCliente(numeroCliente);

      DatiRicerca datiRicerca = new DatiRicerca();
      datiRicerca.setNumeroInserito(numeroCliente);
      datiRicerca.setTipologia(TipologiaEnum.CLIENTE);

      if (cliente != null) {
        datiRicerca.setPresente(true);
      } else {
        datiRicerca.setPresente(false);
      }

      ricercaDato.setDatiRicerca(datiRicerca);

      esito.setEsito(true);

    } catch (BusinessException e) {
      log.error("[DomandeResource] isClientePresenteInLista Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }

    ricercaDato.setEsito(esito);

    return ricercaDato;
  }

  @Override
  public RicercaDato isContrattoPresenteInLista(String numeroContratto) {
    RicercaDato ricercaDato = new RicercaDato();
    Esito esito = new Esito();

    try {
      TrContratti contratto =
          ServiceLocator.getInstance().getServiziTeleriscaldamento().getContratto(numeroContratto);

      DatiRicerca datiRicerca = new DatiRicerca();
      datiRicerca.setNumeroInserito(numeroContratto);
      datiRicerca.setTipologia(TipologiaEnum.CONTRATTO);

      if (contratto != null) {
        datiRicerca.setPresente(true);
      } else {
        datiRicerca.setPresente(false);
      }

      ricercaDato.setDatiRicerca(datiRicerca);

      esito.setEsito(true);

    } catch (BusinessException e) {
      log.error(
          "[DomandeResource] isContrattoPresenteInLista Errore durante la lettura dal db: ", e);
      esito.setEsito(false);
    }

    ricercaDato.setEsito(esito);

    return ricercaDato;
  }
}
