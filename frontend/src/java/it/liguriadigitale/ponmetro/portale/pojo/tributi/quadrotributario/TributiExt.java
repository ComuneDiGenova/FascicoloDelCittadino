package it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.tributi.model.QuadroTributario;
import it.liguriadigitale.ponmetro.tributi.model.Tributi;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TributiExt extends Tributi {

  private static final long serialVersionUID = 1L;

  public TributiExt() {
    super();
  }

  public String getTitolo() {
    return super.getTributo();
  }

  // Devo ridefinire la set perche la copy properties usa la get del parent
  // che ritorna una stringa, e la set del parent la sua enum
  public void setTributo(String tributo) {
    super.setTributo(TributoEnum.fromValue(tributo));
  }

  public void addDettaglio(QuadroTributario dettaglio) {
    if (getDettaglio() == null) {
      setDettaglio(new ArrayList<QuadroTributario>());
    }
    addDettaglioItem(dettaglio);
  }

  public List<QuadroTributarioExt> getDettaglioExt() {
    if (super.getDettaglio() == null) return null;

    Comparator<QuadroTributario> comparator =
        Comparator.comparing(
            QuadroTributario::getUri, Comparator.nullsFirst(Comparator.reverseOrder()));

    Stream<QuadroTributario> stream = super.getDettaglio().stream();

    return stream
        .sorted(comparator)
        .map(parent -> PojoUtils.copyAndReturn(new QuadroTributarioExt(), parent))
        .collect(Collectors.toList());
  }
}
