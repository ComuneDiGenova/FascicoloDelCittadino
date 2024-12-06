package it.liguriadigitale.ponmetro.portale.business.biblioteche.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject1;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteAbil;
import java.io.IOException;
import java.util.List;

public interface ServiziBibliotecheService {

  List<Movimento> getMovimentiCorrentiUtenteById(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  void cancellaMovimentoAnnullabile(String codiceFiscale, Movimento movimento)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaBiblio(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaLingua(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaPaeseRecord> getListaTabellaPaese()
      throws BusinessException, ApiException, IOException;

  List<TabellaPaeseRecord> getPaesi(String input)
      throws BusinessException, ApiException, IOException;

  List<TabellaPaeseRecord> getPaeseNazionalita(String nazionalitaDemografico)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaProv(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaTDocIdentita(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaTpv(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaTts(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaTut() throws BusinessException, ApiException, IOException;

  String getCodiceTipoUtente() throws BusinessException, ApiException, IOException;

  List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> getUtenteByDocIdentita(
      String codiceFiscale) throws BusinessException, ApiException, IOException;

  Long getIdUtenteSebina(String codiceFiscale) throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaTmov(Utente utente)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneTipoMovimento(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaStam(Utente utente)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneStatoMovimento(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException;

  List<TabellaRecord> getListaTabellaBiblio(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneBiblioteca(Utente utente, Movimento movimento)
      throws BusinessException, ApiException, IOException;

  void iscriviBibliotecheSebina(Utente utente, BibliotecheIscrizione bibliotecheIscrizione)
      throws BusinessException, ApiException, IOException;

  List<String> getListaCodiciPrestiti() throws ApiException, IOException;

  List<Movimento> getListaPrestiti(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  List<String> getListaCodiciPrenotazioni() throws BusinessException, ApiException, IOException;

  List<Movimento> getListaPrenotazioni(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  List<String> getStatiVerde() throws BusinessException, ApiException, IOException;

  List<String> getStatiGiallo() throws BusinessException, ApiException, IOException;

  List<String> getStatiRosso() throws BusinessException, ApiException, IOException;

  List<String> getDigitale() throws BusinessException, ApiException, IOException;

  List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> getUtenteByCf(
      String codiceFiscale) throws BusinessException, ApiException, IOException;

  List<UtenteAbil> findUtenteAbil(Long utenteId, String cdAbil, String cdBib)
      throws BusinessException, ApiException, IOException;

  void patchUtenteAbil(
      Long utenteId, String cdAbil, InlineObject1 inlineObject1, List<String> cdBib)
      throws BusinessException, ApiException, IOException;

  List<BreadcrumbFdC> getListaBreadcrumbIscrizione();

  List<BreadcrumbFdC> getListaBreadcrumbIscrizioneBambini();

  List<BreadcrumbFdC> getListaBreadcrumbMovimenti();

  List<BreadcrumbFdC> getListaBreadcrumbMovimentiBambini();

  List<BreadcrumbFdC> getListaBreadcrumbServiziPerMinori();

  List<BreadcrumbFdC> getListaBreadcrumbInternet();

  List<BreadcrumbFdC> getListaBreadcrumbRiepilogo();

  List<BreadcrumbFdC> getListaBreadcrumbModificaDati();

  List<Legenda> getListaLegenda();

  void patchUtenteById(Long utenteId, InlineObject inLineObject)
      throws BusinessException, ApiException, IOException;

  public boolean inviaMailSegnalazioneBambino(
      ComponenteNucleo componenteNucleo,
      it.liguriadigitale.ponmetro.portale.pojo.login.Utente utente,
      List<Long> listaIdSebina);

  public boolean inviaMailSegnalazione(
      it.liguriadigitale.ponmetro.portale.pojo.login.Utente utente, List<Long> listaIdSebina);
}
