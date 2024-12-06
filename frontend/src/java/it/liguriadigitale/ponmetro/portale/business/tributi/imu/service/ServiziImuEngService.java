package it.liguriadigitale.ponmetro.portale.business.tributi.imu.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.ProtocollazioneIstanzaRimborso;
import java.util.List;

public interface ServiziImuEngService {

  List<CategoriaCatastale> getCategorieCatastali() throws BusinessException;

  List<RimborsoImu> getPraticheRimborsoIMU(String codiceFiscale)
      throws BusinessException, ApiException;

  IstanzaRimborso praticaRimborsoImu(RimborsoImu praticaRimborso)
      throws BusinessException, ApiException;

  IstanzaRimborso inserisciAllegato(
      String fileName,
      Long praticaRimborsoId,
      String codiceFiscale,
      String tipoAllegato,
      String fileBase64)
      throws ApiException, BusinessException;

  IstanzaRimborso cancellaAllegato(Long idAllegato) throws ApiException, BusinessException;

  RimborsoImu dettaglioPraticaRimborsoImu(Integer praticaRimborsoImuId)
      throws ApiException, BusinessException;

  ProtocollazioneIstanzaRimborso praticaRimborsoIMUProtocollazione(
      String nome,
      String cognome,
      String personaFisicaOGiuridica,
      String codiceFiscale,
      String codiceContribuente,
      String descrizioneDocumento,
      Long progressivoIstanzaRimborso)
      throws BusinessException, ApiException;

  IstanzaRimborso praticaRimborsoImuAnnullamento(Long progressivoIstanzaRimborso)
      throws BusinessException, ApiException;

  List<RimborsoImuAllegato> getElencoAllegatiPraticaRimborso(
      String codiceFiscale, Long progressivoIstanzaRimborso) throws BusinessException, ApiException;

  String getLinkFromResourceDB(String chiave);
}
