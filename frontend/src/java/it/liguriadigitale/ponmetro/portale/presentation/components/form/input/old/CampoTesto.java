package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

public class CampoTesto extends CampoGenerico<String> {

  private static final long serialVersionUID = 7782183585968704857L;

  public CampoTesto(String nomeCampo) {
    super(nomeCampo);
  }

  public CampoTesto(String nomeCampo, IModel<String> model) {
    super(nomeCampo, model);
  }

  public CampoTesto(String nomeCampo, Integer lunghezzaFissa) {
    super(nomeCampo, lunghezzaFissa);
    this.add(new StringValidator(0, lunghezzaFissa));
  }

  public CampoTesto(String nomeCampo, Integer lunghezzaMinima, Integer lunghezzaMassima) {
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

}
