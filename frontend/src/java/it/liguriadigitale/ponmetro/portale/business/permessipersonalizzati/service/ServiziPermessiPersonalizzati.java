package it.liguriadigitale.ponmetro.portale.business.permessipersonalizzati.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Allegabile;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaCompletaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.IdDomandaProtocolloBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.PostResult;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Protocollazione;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.StatoProcedimento;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import java.io.IOException;
import java.util.List;

public interface ServiziPermessiPersonalizzati {

  // List<InlineResponse2001> getListaDomande(Utente utente) throws BusinessException, ApiException,
  // IOException;

  PostResult putDomanda(DomandaBody domandaBody, Boolean isResidente)
      throws BusinessException, ApiException, IOException;

  Protocollazione putDomanda(int idDomanda, IdDomandaProtocolloBody idDomandaProtocolloBody)
      throws BusinessException, ApiException, IOException;

  DomandaCompletaResponse getDomanda(int idDomanda)
      throws BusinessException, ApiException, IOException;

  List<DomandaResponse> getDomande(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  AllegatoBody getDomanda(int idDomanda, String nomeFile)
      throws BusinessException, ApiException, IOException;

  List<Allegabile> getAllegabili() throws BusinessException, ApiException, IOException;

  List<Allegabile> getAllegabili(String codice, Boolean isResidente)
      throws BusinessException, ApiException, IOException;

  List<TipologiaProcedimento> getTipologieProcedimento()
      throws BusinessException, ApiException, IOException;

  List<DichiarazioniResponse> getDichiarazioni()
      throws BusinessException, ApiException, IOException;

  List<MessaggiInformativi> popolaListaMessaggi()
      throws BusinessException, ApiException, IOException;

  List<Legenda> getListaLegenda();

  List<StatoProcedimento> getStatiProcedimento()
      throws BusinessException, ApiException, IOException;
}
