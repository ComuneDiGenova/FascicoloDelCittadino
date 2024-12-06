package it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.util.CommonUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Comproprietari;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioProprietaUtenza;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DettaglioProprietaUtenzaExt extends DettaglioProprietaUtenza {

  @SuppressWarnings("unused")
  private static Log log = LogFactory.getLog(DettaglioProprietaUtenzaExt.class);

  private static final long serialVersionUID = 1L;

  public DettaglioProprietaUtenzaExt() {
    super();
  }

  public List<ComproprietariExt> getComproprietariExt(Boolean soloAttuali) {
    Comparator<Comproprietari> comparator =
        Comparator.comparing(
                Comproprietari::getDataFinePossesso,
                Comparator.nullsFirst(Comparator.reverseOrder()))
            .thenComparing(Comproprietari::getDataInizioPossesso);

    if (super.getComproprietari() == null) return null;

    Stream<Comproprietari> stream = super.getComproprietari().stream();

    if (soloAttuali) {
      stream =
          stream.filter(item -> (soloAttuali && StringUtils.isEmpty(item.getDataFinePossesso())));
    }
    // leva doppioni codice fiscale
    stream = stream.filter(CommonUtil.distinctByField(p -> p.getCodiceFiscale()));

    return stream
        .sorted(comparator)
        .map(parent -> PojoUtils.copyAndReturn(new ComproprietariExt(), parent))
        .collect(Collectors.toList());
  }

  public List<ComproprietariExt> getComproprietariExtFiltered(
      String cfUtente, Integer annoSelezionato) {

    // anno corrente?
    // Si > mostro solo proprietari attuali
    if (LocalDate.now().getYear() == annoSelezionato) {
      return getComproprietariExt(true);
    }
    // No > controllo utente e' sono tra i proprietari?
    // non mi interessa, mostro i proprietari di quell'anno

    // SONO TRA I PROPRIETARI?
    // se non sono tra i proprietari devo controllare se sono residente
    // se flag residenza e' false allora faccio vdere solo quelli con anno
    // fine null, cioe' i proprietari attuali
    List<ComproprietariExt> listaTuttiDaFiltrare = getComproprietariExt(false);
    List<ComproprietariExt> listaToRet = new ArrayList<ComproprietariExt>();

    if (LabelFdCUtil.checkIfNotNull(listaTuttiDaFiltrare)) {
      for (ComproprietariExt comproprietario : listaTuttiDaFiltrare) {
        // controlla se anno selezionato e' nell'intervallo
        Integer annoInizio = comproprietario.getAnnoDataInizioPossesso();
        Integer annoFine = comproprietario.getAnnoDataFinePossesso();

        if (annoSelezionato <= annoFine && annoSelezionato >= annoInizio) {
          listaToRet.add(comproprietario);
        }
      }
    }

    return listaToRet;
  }

  public String getTitolo() {
    return getIndirizzoCompleto();
  }

  @Override
  public String getConsistenza() {
    return super.getConsistenza().equals("0") ? null : super.getConsistenza(); // levato
    // perche'
    // dipende
    // dal
    // tipo
    // di
    // immobile/proprieta + " vani";
  }

  public String getSuperficieL() {
    return (super.getSuperficie().equals(0) || super.getSuperficie().equals(0.0))
        ? null
        : super.getSuperficie() + " mq";
  }

  public String getRenditaL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getRendita());
  }
}
