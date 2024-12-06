package it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.tributi.model.ProprietaUtenze;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProprietaUtenzeExt extends ProprietaUtenze {

  @SuppressWarnings("unused")
  private static Log log = LogFactory.getLog(ProprietaUtenzeExt.class);

  private static final long serialVersionUID = 1L;

  private HashMap<String, String> hashTipoOggetto;

  private static final String PUE_FABBRICATO = "FABBRICATO";
  private static final String PUE_TERRENO = "TERRENO";

  // La copyProperties usa la parent get (return string) e la set qui
  // private String tipoOggetto;

  public ProprietaUtenzeExt() {
    super();

    // init mapping
    hashTipoOggetto = new HashMap<>();
    hashTipoOggetto.put(TipoOggettoEnum.F.value(), PUE_FABBRICATO);
    hashTipoOggetto.put(TipoOggettoEnum.T.value(), PUE_TERRENO);
  }

  public ProprietaUtenzeExt copia(ProprietaUtenze aa) {
    return PojoUtils.copyAndReturn(this, aa);
  }

  public String getTitolo() {
    return StringUtils.isNotBlank(getTipoOggettoGenerico())
        ? getTipoOggettoGenerico()
        : StringUtils.isNotBlank(getUtilizzo())
            ? getUtilizzo()
            : StringUtils.isNotBlank(getTipoOggetto()) ? getTipoOggetto() : "PROPRIETA";
  }

  @Override
  public String getTipoOggetto() {
    return hashTipoOggetto.get(super.getTipoOggetto());
  }

  public boolean isTerreno() {
    return super.getTipoOggetto().equals(TipoOggettoEnum.T.value());
  }

  public boolean isAbitazione() {
    return super.getTipoOggetto().equals(TipoOggettoEnum.F.value())
        && "ABITAZIONE".equalsIgnoreCase(super.getTipoOggettoGenerico());
  }

  public boolean isGarage() {
    return super.getTipoOggetto().equals(TipoOggettoEnum.F.value())
        && "RIMESSA".equalsIgnoreCase(super.getTipoOggettoGenerico());
  }

  public boolean isNegozio() {
    return super.getTipoOggetto().equals(TipoOggettoEnum.F.value())
        && (super.getTipoOggettoGenerico().toUpperCase().contains("NEGOZIO")
            || super.getTipoOggettoGenerico().toUpperCase().contains("PUB")
            || super.getTipoOggettoGenerico().toUpperCase().contains("PIZZERIE")
            || super.getTipoOggettoGenerico().toUpperCase().contains("RISTORANTI")
            || super.getTipoOggettoGenerico().toUpperCase().contains("BAR"));
  }

  // Devo ridefinire la set perche la copy properties usa la get del parent
  // che ritorna una stringa, e la set del parent la sua enum
  public void setTipoOggetto(String tipoOggetto) {
    super.setTipoOggetto(TipoOggettoEnum.fromValue(tipoOggetto));
  }

  public String getResidenza() {
    return super.getFlagResidenza() ? "SI" : "NO";
  }

  @Override
  public String toString() {
    return "ProprietaUtenzeExt [hashTipoOggetto="
        + hashTipoOggetto
        + "], super: "
        + super.toString();
  }
}
