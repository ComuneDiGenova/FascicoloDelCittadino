package it.liguriadigitale.ponmetro.portale.pojo.gestore;

import it.liguriadigitale.framework.pojo.common.BasePojo;
import java.util.Calendar;

/**
 * @author scaldaferri
 */
public class Anno extends BasePojo {

  /** Classe base per la gestione degli anni */
  private static final long INTORNO = 5L;

  private static final long serialVersionUID = 7132583664770872687L;

  public static final Long LIMITE_INFERIORE = 2017L;

  private Long annoSelezionato;
  private Long limiteSuperiore;

  public Anno(Long annoSelezionato) {
    this();
    this.annoSelezionato = annoSelezionato;
  }

  public Anno() {
    super();
    annoSelezionato = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    limiteSuperiore = annoSelezionato + INTORNO;
  }

  public static Long getLimiteInferiore() {
    return LIMITE_INFERIORE;
  }

  public Long getAnnoSelezionato() {
    return annoSelezionato;
  }

  public void setAnnoSelezionato(final Long annoSelezionatoParameter) {
    annoSelezionato = annoSelezionatoParameter;
  }

  public Long getLimiteSuperiore() {
    return limiteSuperiore;
  }

  public void setLimiteSuperiore(final Long limiteSuperioreParameter) {
    limiteSuperiore = limiteSuperioreParameter;
  }

  @Override
  public String toString() {
    return "Anno [annoSelezionato="
        + annoSelezionato
        + ", limiteSuperiore="
        + limiteSuperiore
        + "]";
  }
}
