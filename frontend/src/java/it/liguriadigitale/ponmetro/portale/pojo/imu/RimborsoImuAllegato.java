package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RimborsoImuAllegato implements Serializable, Cloneable {

  private static final long serialVersionUID = 1654651289765321L;

  private UUID id;
  private long idAllegato;
  private TipoAllegatoEnum tipoAllegato;
  private String altro;
  private byte[] documento;
  private String nomeFile;
  private long dimensione;
  private String fileBase64;

  private Log log = LogFactory.getLog(getClass());

  public RimborsoImuAllegato() {
    this.id = UUID.randomUUID();
  }

  public RimborsoImuAllegato(
      String idAllegato, String nomeFile, String tipoDocumento, String fileBase64) {
    // TODO Auto-generated constructor stub
    this.idAllegato = Long.valueOf(idAllegato);
    this.nomeFile = nomeFile;
    this.id = UUID.randomUUID();
    this.tipoAllegato = getFromTipoDocumentoEng(tipoDocumento);
    this.fileBase64 = fileBase64;
  }

  public String getFileBase64() {
    return fileBase64;
  }

  public void setFileBase64(String fileBase64) {
    this.fileBase64 = fileBase64;
  }

  public long getIdAllegato() {
    return idAllegato;
  }

  public void setIdAllegato(long idAllegato) {
    this.idAllegato = idAllegato;
  }

  public long getDimensione() {
    return dimensione;
  }

  public void setDimensione(long dimensione) {
    this.dimensione = dimensione;
  }

  public String getNomeFile() {
    return nomeFile;
  }

  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public String getAltro() {
    return altro;
  }

  public void setAltro(String altro) {
    this.altro = altro;
  }

  public TipoAllegatoEnum getAllegato() {
    return tipoAllegato;
  }

  public void setAllegato(TipoAllegatoEnum allegato) {
    this.tipoAllegato = allegato;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setDocumento(byte[] bytes) {
    // TODO Auto-generated method stub
    this.documento = bytes;
  }

  public byte[] getDocumento() {
    return this.documento;
  }

  private TipoAllegatoEnum getFromTipoDocumentoEng(String tipo) {
    try {
      log.debug(String.format("[TipoDocumento]: %s", tipo));
      String[] tc = tipo.split("_");

      String t = tc[0] + "_" + tc[1];

      return TipoAllegatoEnum.valueOf(t);
    } catch (Exception e) {
      return null;
    }
  }

  public RimborsoImuAllegato clone() throws CloneNotSupportedException {
    RimborsoImuAllegato copia = new RimborsoImuAllegato();
    copia.setAllegato(this.tipoAllegato);
    copia.setAltro(this.altro);
    copia.setNomeFile(this.nomeFile);

    return copia;
  }
}
