package it.liguriadigitale.ponmetro.portale.presentation.components.tarinetribe;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.model.IModel;

public class TipologiaRichiedenteRimborsoTariNetribeDropDownChoice
    extends FdCDropDownChoice<TipologiaRichiedenteRimborsoEnum> {

  private static Log log =
      LogFactory.getLog(TipologiaRichiedenteRimborsoTariNetribeDropDownChoice.class);

  public TipologiaRichiedenteRimborsoTariNetribeDropDownChoice(
      String id, IModel<TipologiaRichiedenteRimborsoEnum> model, boolean isIntestatario) {
    super(id);

    setChoices(getListaTipologie(isIntestatario));
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<TipologiaRichiedenteRimborsoEnum> getListaTipologie(boolean isIntestatario) {

    if (isIntestatario) {
      return Arrays.asList(TipologiaRichiedenteRimborsoEnum.values());
    } else {
      List<TipologiaRichiedenteRimborsoEnum> listaSenzaIntestatario = new ArrayList<>();

      List<TipologiaRichiedenteRimborsoEnum> listaCompleta =
          Arrays.asList(TipologiaRichiedenteRimborsoEnum.values());

      if (LabelFdCUtil.checkIfNotNull(listaCompleta)
          && !LabelFdCUtil.checkEmptyList(listaCompleta)) {
        listaSenzaIntestatario =
            listaCompleta.stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem)
                            && !elem.value()
                                .equalsIgnoreCase(
                                    TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()))
                .collect(Collectors.toList());
      }

      return listaSenzaIntestatario;
    }
  }
}
