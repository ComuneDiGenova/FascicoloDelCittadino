package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.LinguaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

// public class LinguaDropDownChoice extends FdCDropDownChoice<LinguaZonaRossa> {

// private static final long serialVersionUID = 6889627681485651854L;

//	public LinguaDropDownChoice(String id, IModel<LinguaZonaRossa> model) {
//		super(id);
//
//		setChoiceRenderer(new LinguaZonaRossaRender());
//		setChoices(getListaLingua());
//		setModel(model);
//		setOutputMarkupId(true);
//		setOutputMarkupPlaceholderTag(true);
//		setRequired(true);
//
//	}
//
//	private static List<LinguaZonaRossa> getListaLingua() {
//
//
//		List<LinguaZonaRossa> listaLinguaZonaRossa= new ArrayList<>();
//		List<LinguaEnum> listaLinguaEnum = Arrays.asList(LinguaEnum.values());
//
//		for (Iterator<LinguaEnum> iterator = listaLinguaEnum.iterator(); iterator.hasNext();) {
//			LinguaEnum linguaEnum = (LinguaEnum) iterator.next();
//
//			LinguaZonaRossa linguaZonaRossa = new LinguaZonaRossa();
//
//			linguaZonaRossa.setIdLingua(linguaEnum);
//			if(linguaEnum.value().equalsIgnoreCase("BUONA")) {
//				linguaZonaRossa.setLabelLingua("BUONA SE IN LINGUA ITALIANA");
//			} else if(linguaEnum.value().equalsIgnoreCase("BUONA SOLO SE")) {
//				linguaZonaRossa.setLabelLingua("BUONA SOLO SE IN LINGUA STRANIERA (DA SELEZIONARE)");
//			} else if(linguaEnum.value().equalsIgnoreCase("AUDIOLESO o NON UDENTE")) {
//				linguaZonaRossa.setLabelLingua("AUDIOLESO O NON UDENTE");
//			}
//
//			listaLinguaZonaRossa.add(linguaZonaRossa);
//
//		}
//
//		return listaLinguaZonaRossa;
//	}

public class LinguaDropDownChoice extends FdCDropDownChoice<LinguaEnum> {

  private static final long serialVersionUID = 6889627681485651854L;

  public LinguaDropDownChoice(String id, IModel<LinguaEnum> model) {
    super(id);

    setChoices(getListaLingua());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setRequired(true);
  }

  private static List<LinguaEnum> getListaLingua() {
    return Arrays.asList(LinguaEnum.values());
  }
}
