package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.tributi.model.ElencoVersamenti;
import it.liguriadigitale.ponmetro.tributi.model.Versamenti;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElencoVersamentiExt extends ElencoVersamenti {

  private static final long serialVersionUID = 1L;

  private Integer annoDaTenere;

  public ElencoVersamentiExt() {
    super();
    annoDaTenere = null;
  }

  public List<TributiVersamentiExt> getVersamentiExt() {
    if (super.getElencoVersamentiIMU() == null) return new ArrayList<TributiVersamentiExt>();

    Comparator<Versamenti> comparator =
        Comparator.comparing(
            Versamenti::getDataScadenzaRata, Comparator.nullsFirst(Comparator.naturalOrder()));

    Stream<Versamenti> stream = super.getElencoVersamentiIMU().stream();

    if (annoDaTenere != null) {
      stream = stream.filter(a -> a.getAnnoRiferimento().equals(new Long(annoDaTenere)));
    }

    // ordino dopo eventuale filtro anno
    return stream
        .sorted(comparator)
        .map(parent -> PojoUtils.copyAndReturn(new TributiVersamentiExt(), parent))
        .collect(Collectors.toList());
  }

  public Integer getAnnoDaTenere() {
    return annoDaTenere;
  }

  public void setAnnoDaTenere(Integer annoDaTenere) {
    this.annoDaTenere = annoDaTenere;
  }
}
