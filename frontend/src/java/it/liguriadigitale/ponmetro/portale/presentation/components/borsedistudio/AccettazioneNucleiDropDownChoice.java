package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class AccettazioneNucleiDropDownChoice
    extends FdCDropDownChoice<AccettazioneNucleoIseeAnagraficoEnum> {

  private static final long serialVersionUID = -6029039088092831023L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"rawtypes", "unchecked"})
  public AccettazioneNucleiDropDownChoice(String id, IModel accettazione) {
    super(id);

    setChoices(getListaAccettazione());

    setModel(accettazione);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    setRequired(true);
    setLabel(Model.of("Dichiarazione"));
  }

  private static List<AccettazioneNucleoIseeAnagraficoEnum> getListaAccettazione() {
    List<AccettazioneNucleoIseeAnagraficoEnum> lista =
        Arrays.asList(AccettazioneNucleoIseeAnagraficoEnum.values());
    List<AccettazioneNucleoIseeAnagraficoEnum> listaFiltrata =
        lista.stream()
            .filter(elem -> !elem.equals(AccettazioneNucleoIseeAnagraficoEnum.NUCLEI_COINCIDONO))
            .collect(Collectors.toList());
    return listaFiltrata;
  }
}
