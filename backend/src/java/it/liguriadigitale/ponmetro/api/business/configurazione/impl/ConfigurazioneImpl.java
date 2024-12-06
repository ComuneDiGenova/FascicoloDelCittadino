package it.liguriadigitale.ponmetro.api.business.configurazione.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.common.datasource.AvatarDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTAvatarDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTAvatarDefDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.FunzioniDisponibiliDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.ScOperatoriDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgTAvatarSequenceDAO;
import it.liguriadigitale.ponmetro.api.pojo.avatar.CfgTAvatar;
import it.liguriadigitale.ponmetro.api.pojo.avatar.CfgTAvatarDef;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.operatori.ScOperatori;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurazioneImpl implements ConfigurazioneInterface {
  private static Log log = LogFactory.getLog(ConfigurazioneImpl.class);

  @Override
  public ImagineCaricata getAvatar(BigDecimal idAnagrafica) throws BusinessException {
    log.debug("bj - getAvatar inizio");
    ImagineCaricata response = new ImagineCaricata();
    Long size = 0L;
    try {
      CfgTAvatar avatar = new CfgTAvatar();
      avatar.setIdFcitt(idAnagrafica.longValue());
      CfgTAvatarDAO dao = new CfgTAvatarDAO(avatar);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      CfgTAvatar avatarOutput = (CfgTAvatar) helper.cercaOggetto();
      if (avatarOutput != null && avatarOutput.getAvatarFile() != null) {
        size = avatarOutput.getAvatarFile().length();
        byte[] targetArray = getByteArray(avatarOutput.getAvatarFile());
        String stringFile = Base64.getEncoder().encodeToString(targetArray);
        response.setFileImage(stringFile);
        return response;
      } else {
        CfgTAvatarDef avatarDef = new CfgTAvatarDef();
        CfgTAvatarDefDAO daoDef = new CfgTAvatarDefDAO(avatarDef);
        helper = new PonMetroBusinessHelper(daoDef);
        CfgTAvatarDef avatarDefOutput = (CfgTAvatarDef) helper.cercaOggetto();
        if (avatarDefOutput != null && avatarDefOutput.getAvatarFile() != null) {
          size = avatarDefOutput.getAvatarFile().length();
          byte[] targetArray = getByteArray(avatarDefOutput.getAvatarFile());
          String stringFile = Base64.getEncoder().encodeToString(targetArray);
          response.setFileImage(stringFile);
          log.debug("Restituisco l'immagine Avatar di Default");
          return response;
        } else {
          String erroreImmagineDefaultNonPresente = "Immagine di default non presente";
          log.error(erroreImmagineDefaultNonPresente);
          throw new BusinessException(erroreImmagineDefaultNonPresente);
        }
      }
    } catch (SerialException | IOException e) {
      log.error("Errore durante il caricamento dell'immagine: ", e);
      throw new BusinessException(e.getMessage());
    }
  }

  private byte[] getByteArray(SerialBlob blob) throws SerialException, IOException {
    InputStream binaryStream = blob.getBinaryStream();
    byte[] targetArray = new byte[binaryStream.available()];
    binaryStream.read(targetArray);
    return targetArray;
  }

  @Override
  public ImagineCaricata uploadAvatar(BigDecimal idAnagrafica, File imageAvatar)
      throws BusinessException {
    ImagineCaricata imagineCaricata = new ImagineCaricata();
    String utenteAgg = "UtenteSimulato";

    if (idAnagrafica == null || imageAvatar == null)
      throw new BusinessException("avatar o id Anagrafica non puo essere null o vuota");

    CfgTAvatar avatar = new CfgTAvatar();
    avatar.setIdFcitt(idAnagrafica.longValue());
    CfgTAvatarDAO dao = new CfgTAvatarDAO(avatar);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    CfgTAvatar avatarDaAggiornare = (CfgTAvatar) helper.cercaOggetto();

    if (avatarDaAggiornare == null) {

      AvatarDatasourceTransactionManager manager =
          new AvatarDatasourceTransactionManager() {

            @Override
            protected void execute(Connection connection) throws Exception {
              Long idAvatarNuovo = null;

              CfgTAvatarSequenceDAO daoSeq = new CfgTAvatarSequenceDAO();
              @SuppressWarnings("unchecked")
              List<Long> lista = daoSeq.retrieveWhere(connection);
              if (!lista.isEmpty()) {
                idAvatarNuovo = lista.get(0);
              }
              byte[] fileAvatar;
              fileAvatar = FileUtils.readFileToByteArray(new File(imageAvatar.toString()));
              Blob b = new SerialBlob(fileAvatar);

              CfgTAvatar avatarDaInserire = new CfgTAvatar();
              avatarDaInserire.setIdAvatar(idAvatarNuovo);
              avatarDaInserire.setAvatarFile((SerialBlob) b);
              avatarDaInserire.setDataIns(LocalDateTime.now());
              avatarDaInserire.setUtenteIns(utenteAgg);
              avatarDaInserire.setIdFcitt(idAnagrafica.longValue());
              avatarDaInserire.setNomeFile("avatar_utente");

              CfgTAvatarDAO daoInsert = new CfgTAvatarDAO(avatarDaInserire);

              int nRecord = daoInsert.insertPrepared(connection);
              connection.commit();
            }
          };

      manager.executeTransaction();

    } else {

      byte[] fileAvatar;
      try {
        fileAvatar = FileUtils.readFileToByteArray(new File(imageAvatar.toString()));
        Blob b = new SerialBlob(fileAvatar);

        avatarDaAggiornare.setAvatarFile((SerialBlob) b);
        avatarDaAggiornare.setDataAgg(LocalDateTime.now());
        avatarDaAggiornare.setUtenteAgg(utenteAgg);

        CfgTAvatarDAO daoDaSalavare = new CfgTAvatarDAO(avatarDaAggiornare);
        PonMetroBusinessHelper helperIns = new PonMetroBusinessHelper(daoDaSalavare);
        helperIns.aggiornaOggetto();
        log.debug(" aggiornamento avatar eseguito correttamente");
      } catch (IOException | SQLException e) {
        log.error("Errore durante l'aggiornamento dell'immagine avatar: ", e);
        throw new BusinessException(e.getMessage());
      }
    }

    try {
      CfgTAvatar avatarFiltro = new CfgTAvatar();
      avatarFiltro.setIdFcitt(idAnagrafica.longValue());
      CfgTAvatarDAO daoFiltro = new CfgTAvatarDAO(avatarFiltro);
      PonMetroBusinessHelper helperFiltro = new PonMetroBusinessHelper(daoFiltro);
      CfgTAvatar avatarRisultato = (CfgTAvatar) helperFiltro.cercaOggetto();

      if (avatarRisultato != null && avatarRisultato.getAvatarFile() != null) {
        long size = avatarRisultato.getAvatarFile().length();
        byte[] targetArray = getByteArray(avatarRisultato.getAvatarFile());
        String stringFile = Base64.getEncoder().encodeToString(targetArray);
        imagineCaricata.setFileImage(stringFile);
      } else {
        String erroreLetturaAvatarSalvato = "Errore durante lettura avatar salvato";
        log.error(erroreLetturaAvatarSalvato);
        throw new BusinessException(erroreLetturaAvatarSalvato);
      }

    } catch (SerialException | IOException e) {

      log.error("Errore ricerca immagine avatar: ", e);
      throw new BusinessException(e.getMessage());
    }
    return imagineCaricata;
  }

  @Override
  public List getListaFunzioni() throws BusinessException {

    FunzioniDisponibili ricerca = new FunzioniDisponibili();
    FunzioniDisponibiliDAO dao = new FunzioniDisponibiliDAO(ricerca);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    return helper.cercaOggetti();
  }

  @Override
  public Boolean isAccessoAutorizzato(String codiceFiscale) throws BusinessException {

    ScOperatori scoperatori = new ScOperatori();
    scoperatori.setOpCf(codiceFiscale.toUpperCase());
    ScOperatoriDAO dao = new ScOperatoriDAO(scoperatori);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    ScOperatori scoperatore = (ScOperatori) helper.cercaOggetto();
    if ((scoperatore != null) && (codiceFiscale.equalsIgnoreCase(scoperatore.getOpCf()))) {
      return true;
    } else {
      return false;
    }
  }
}
