package it.liguriadigitale.ponmetro.portale.business.anagrafici.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.DocumentoCaricato;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCittadinanzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceComuneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceStatoResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDichiarazionePrecompilataResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDocumentiPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoIndividuiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoParenteleResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoPraticheResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoStatoCivileResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTipologiaFamigliaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTitolaritaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProfessioniResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProvenienzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoIscrizioneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTitoliStudioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetToponomasticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetViarioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Professione;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PutCambioStatoPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.StringGenericResponse;
import java.io.IOException;
import java.util.List;

public interface ServiziAnagraficiService {

  GetTipoIscrizioneResponseGenericResponse getTipologiaIscrizione(Integer tipoPratica)
      throws BusinessException, ApiException, IOException;

  GetViarioResponseGenericResponse getViario(Integer idVia, String descrizioneVia)
      throws BusinessException, ApiException, IOException;

  GetElencoTitolaritaResponseGenericResponse getTipoOccupazione()
      throws BusinessException, ApiException, IOException;

  GetCodiceComuneResponseGenericResponse getTuttiComuniApkappa(Integer codice, String descrizione)
      throws BusinessException, ApiException, IOException;

  GetCodiceStatoResponseGenericResponse getStati(String codice, String descrizione)
      throws BusinessException, ApiException, IOException;

  GetElencoTipologiaFamigliaResponseGenericResponse getTipologiaFamiglia()
      throws BusinessException, ApiException, IOException;

  GetDichiarazionePrecompilataResponseGenericResponse getDichiarazionePrecompilata(
      Integer idPratica, String codiceFiscaleRichiedente)
      throws BusinessException, ApiException, IOException;

  PostInserimentoPraticaSospesaResponseGenericResponse inserimentoPraticaSospesa(
      VariazioniResidenza variazioniResidenza) throws BusinessException, ApiException, IOException;

  GetCittadinanzaResponseGenericResponse getCittadinanza(String codice, String descrizione)
      throws BusinessException, ApiException, IOException;

  GetElencoStatoCivileResponseGenericResponse getStatoCivile()
      throws BusinessException, ApiException, IOException;

  GetElencoParenteleResponseGenericResponse getElencoParentele()
      throws BusinessException, ApiException, IOException;

  GetProfessioniResponseGenericResponse getProfessioni()
      throws BusinessException, ApiException, IOException;

  GetTitoliStudioResponseGenericResponse getTitoliDiStudio()
      throws BusinessException, ApiException, IOException;

  GetElencoPraticheResponseGenericResponse getElencoPratiche(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  PostInserimentoAllegatiResponseGenericResponse inserimentoAllegato(
      Integer idPratica, DocumentoCaricato documento)
      throws BusinessException, ApiException, IOException;

  GetDocumentiPraticaResponseGenericResponse getDocumentiPratica(Integer idPratica, boolean attivo)
      throws BusinessException, ApiException, IOException;

  PutCambioStatoPraticaResponseGenericResponse inviaPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException;

  StringGenericResponse cancellaAllegatoCaricato(Integer idAllegato)
      throws BusinessException, ApiException, IOException;

  GetDatiGeneraliPraticaResponseGenericResponse getDatiGeneraliPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException;

  GetElencoIndividuiResponseGenericResponse getElencoIndividuiCollegati(Integer idPratica)
      throws BusinessException, ApiException, IOException;

  StringGenericResponse cancellaPratica(Integer idPratica)
      throws BusinessException, ApiException, IOException;

  GetProvenienzaResponseGenericResponse getProvenienza()
      throws BusinessException, ApiException, IOException;

  Residente getDatiResidentePerApk(String codiceFiscale) throws BusinessException, ApiException;

  <T> boolean isCardMinorenniVisibile(List<T> listaIndividui)
      throws BusinessException, ApiException;

  List<Professione> getProfessioniFiltrate(String input);

  Professione getProfessioniFiltrateByCodice(String descrizioneProfessione);

  GetToponomasticaResponseGenericResponse getToponomasticaConSelect2(
      FeaturesGeoserver featuresGeoserver) throws BusinessException, ApiException, IOException;
}
