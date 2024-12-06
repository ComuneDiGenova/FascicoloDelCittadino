package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.tributi.model.Scadenze;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// non sono manco in grado di dare nomi coerenti varsamenti / scadenze invertiti
// singolo ed elenco
public class TributiScadenzeExt extends Scadenze {

  private static final long serialVersionUID = 1L;
  protected Log log = LogFactory.getLog(getClass());

  private Integer annoDaTenere;

  public TributiScadenzeExt() {
    super();
    annoDaTenere = null;
  }

  public List<ElencoScadenzeExt> getElencoScadenzeExt() {
    if (super.getElenco() == null) return null;
    /*
     * Comparator<ElencoScadenze> comparator = Comparator
     * .comparing(ElencoScadenze::getDataScadenzaRata,
     * Comparator.nullsFirst(Comparator.reverseOrder()));
     */
    Stream<ElencoScadenzeExt> stream =
        super.getElenco().stream()
            .map(parent -> PojoUtils.copyAndReturn(new ElencoScadenzeExt(), parent));
    log.error("TributiScadenzeExt stream"); // .sorted(comparator)

    if (annoDaTenere != null) {
      stream = stream.filter(a -> a.getAnnoRiferimento().equals(annoDaTenere));
    }

    log.error("prima di return stream");
    // ordino dopo eventuale filtro anno
    return stream.collect(Collectors.toList());
  }

  public Integer getAnnoDaTenere() {
    return annoDaTenere;
  }

  public void setAnnoDaTenere(Integer annoDaTenere) {
    this.annoDaTenere = annoDaTenere;
  }

  // In attesa di istruzioni / legende, in realta da spostare nel singolo
  // elemento della list, cioe' ElencoScadenzeExt
  public String getColorForIcon() {
    return BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS;
  }
}
