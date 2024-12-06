package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CAPValidator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

public class CampoCAP extends CampoGenerico<String> {

  private static final long serialVersionUID = 7782183585968704857L;

  public CampoCAP(String nomeCampo) {
    super(nomeCampo);
    inizializza();
  }

  public CampoCAP(String nomeCampo, IModel<String> model) {
    super(nomeCampo, model);
    inizializza();
  }

  public CampoCAP(String nomeCampo, Integer lunghezzaMinima, Integer lunghezzaMassima) {
    super(nomeCampo, lunghezzaMinima, lunghezzaMassima);
    if (lunghezzaMinima != null) this.add(StringValidator.minimumLength(lunghezzaMinima));
    if (lunghezzaMassima != null) this.add(StringValidator.maximumLength(lunghezzaMassima));
  }

  /*
  @Override
  public void renderHead(IHeaderResponse response) {
  	super.renderHead(response);

  	StringBuilder sb = new StringBuilder();
  	if (isRequired()) {
  		sb.append("$(document).ready(function() { \r\n"
  				+ "$('#"+ getMarkupId() +"').rules( \"add\", { required: true}) ;});");


  	}
  	response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }*/

  private void inizializza() {
    this.add(new CAPValidator());
    this.add(
        new AttributeModifier(
            "oninput",
            "this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');"));
  }
}
