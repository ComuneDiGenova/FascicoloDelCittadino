package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.model.IModel;

public class TipologiaRichiedenteRimborsoTariEngDropDownChoice
    extends FdCDropDownChoice<TipologiaRichiedenteRimborsoEnum> {

  private static final long serialVersionUID = 1293389823051757623L;

  private static Log log =
      LogFactory.getLog(TipologiaRichiedenteRimborsoTariEngDropDownChoice.class);

  public TipologiaRichiedenteRimborsoTariEngDropDownChoice(
      String id, IModel<TipologiaRichiedenteRimborsoEnum> model) {
    super(id);

    setChoices(getListaTipologie());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<TipologiaRichiedenteRimborsoEnum> getListaTipologie() {
    List<TipologiaRichiedenteRimborsoEnum> listaSenzaIntestatario = new ArrayList<>();

    List<TipologiaRichiedenteRimborsoEnum> listaCompleta =
        Arrays.asList(TipologiaRichiedenteRimborsoEnum.values());

    log.debug("listaCompleta " + listaCompleta);

    if (LabelFdCUtil.checkIfNotNull(listaCompleta) && !LabelFdCUtil.checkEmptyList(listaCompleta)) {
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
