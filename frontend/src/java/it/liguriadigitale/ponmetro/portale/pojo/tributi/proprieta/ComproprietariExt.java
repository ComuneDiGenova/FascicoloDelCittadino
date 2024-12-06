package it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Comproprietari;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ComproprietariExt extends Comproprietari {

  @SuppressWarnings("unused")
  private static Log log = LogFactory.getLog(ComproprietariExt.class);

  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unused")
  private HashMap<String, String> hashTipoSoggetto;

  // private static final String PU_CO_P = "Privato???";
  // private static final String PU_CO_D = "Demaniale???";

  public ComproprietariExt() {
    super();
  }

  @Override
  public String getDenominazione() {
    return super.getDenominazione().replace("/", " ");
  }

  public Integer getAnnoDataInizioPossesso() {
    return PageUtil.getYearFromAAAAminusMMminusDDOrActualYear(super.getDataInizioPossesso());
  }

  public Integer getAnnoDataFinePossesso() {
    return PageUtil.getYearFromAAAAminusMMminusDDOrActualYear(super.getDataFinePossesso());
  }

  @Override
  public String getDataInizioPossesso() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataInizioPossesso());
  }

  @Override
  public String getDataFinePossesso() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataFinePossesso());
  }

  // Devo ridefinire la set perche la copy properties usa la get del parent
  // che ritorna una stringa, e la set del parent la sua enum
  public void setTipoSoggetto(String tipoSoggetto) {
    super.setTipoSoggetto(TipoSoggettoEnum.fromValue(tipoSoggetto));
  }
}
