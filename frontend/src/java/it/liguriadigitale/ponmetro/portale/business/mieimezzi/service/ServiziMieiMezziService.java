package it.liguriadigitale.ponmetro.portale.business.mieimezzi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiAvvioPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import it.liguriadigitale.ponmetro.tassaauto.model.EsitoAnnullamentoPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.PagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.io.IOException;
import java.util.List;

public interface ServiziMieiMezziService {

  List<Veicolo> getListaVeicoli(Utente utente)
      throws BusinessException, ApiException, IOException, RuntimeException;

  DettaglioVeicolo getDettagliVeicolo(Veicolo veicolo) throws BusinessException, ApiException;

  List<Bollo> getListaDettagliBolli(Veicolo veicolo) throws BusinessException, ApiException;

  DettaglioCalcoloBollo getCalcolaBollo(Veicolo veicolo, Bollo bollo)
      throws BusinessException, ApiException;

  List<PagamentoBollo> getPagamenti(Veicolo veicolo, Bollo bollo)
      throws BusinessException, ApiException;

  DatiAvvioPagamento creaRichiestaPagamentoPagoPa(Veicolo veicolo, Bollo bollo, Utente utente)
      throws BusinessException, ApiException;

  EsitoAnnullamentoPagamento annullaPrenotazionePagamentoPagoPa(
      DatiAvvioPagamento datiAvvioPagamento) throws BusinessException, ApiException;

  VerificaAssicurazioneVeicoli getAssicurazione(Veicolo veicolo)
      throws BusinessException, ApiException;
}
