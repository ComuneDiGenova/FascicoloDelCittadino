package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.tributi.model.Accertato;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioScadenze;
import it.liguriadigitale.ponmetro.tributi.model.Ordinario;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DettaglioScadenzeExt extends DettaglioScadenze {

  private static final long serialVersionUID = 1L;

  public List<OrdinarioScadenzeExt> getOrdinarioScadenzeExt() {
    if (super.getOrdinario() == null) return null;

    Stream<Ordinario> stream = super.getOrdinario().stream();

    return stream
        .map(parent -> PojoUtils.copyAndReturn(new OrdinarioScadenzeExt(), parent))
        .collect(Collectors.toList());
  }

  public List<AccertatoScadenzeExt> getAccertatoScadenzeExt() {
    if (super.getAccertato() == null) return null;

    Stream<Accertato> stream = super.getAccertato().stream();

    return stream
        .map(parent -> PojoUtils.copyAndReturn(new AccertatoScadenzeExt(), parent))
        .collect(Collectors.toList());
  }

  public Integer getAnnoRiferimento() {
    if (getOrdinarioScadenzeExt() == null || getOrdinarioScadenzeExt().isEmpty()) {
      return null;
    }
    // supponendo che nel dettaglio ci sianoo ordinari tutti dello stesso
    // anno
    return getOrdinarioScadenzeExt().get(0).getAnnoDataScadenzaRata();
  }
}
