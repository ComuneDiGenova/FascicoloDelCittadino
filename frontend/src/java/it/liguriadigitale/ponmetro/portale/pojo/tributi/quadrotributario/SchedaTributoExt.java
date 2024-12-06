package it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.tributi.model.DovutiVersati;
import it.liguriadigitale.ponmetro.tributi.model.Oggetti;
import it.liguriadigitale.ponmetro.tributi.model.SchedaTributo;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchedaTributoExt extends SchedaTributo {

  private static final long serialVersionUID = 1L;

  public SchedaTributoExt() {
    super();
  }

  public List<SchedaTributoDovutiVersatiExt> getDovutiVersatiExt() {
    if (super.getDovutiVersati() == null) return null;

    Comparator<DovutiVersati> comparator =
        Comparator.comparing(
            DovutiVersati::getDataScadenza, Comparator.nullsFirst(Comparator.naturalOrder()));

    Stream<DovutiVersati> stream = super.getDovutiVersati().stream();

    return stream
        .sorted(comparator)
        .map(parent -> PojoUtils.copyAndReturn(new SchedaTributoDovutiVersatiExt(), parent))
        .collect(Collectors.toList());
  }

  public List<SchedaTributoOggettiExt> getOggettiExt() {
    if (super.getOggetti() == null) return null;

    Comparator<Oggetti> comparator =
        Comparator.comparing(
            Oggetti::getDataInizioLegame, Comparator.nullsFirst(Comparator.reverseOrder()));

    Stream<Oggetti> stream = super.getOggetti().stream();

    return stream
        .sorted(comparator)
        .map(parent -> PojoUtils.copyAndReturn(new SchedaTributoOggettiExt(), parent))
        .collect(Collectors.toList());
  }
}
