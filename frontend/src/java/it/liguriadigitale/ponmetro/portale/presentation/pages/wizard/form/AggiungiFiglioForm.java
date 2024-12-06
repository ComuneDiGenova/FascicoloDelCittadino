package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AggiungiFiglio;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoMinoreRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CFAutodichiarazioneFiglioValidator;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.LetterValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class AggiungiFiglioForm extends AbstracFrameworkForm<AggiungiFiglio> {

  private static final long serialVersionUID = -209432177307114188L;

  private Utente utente;

  public AggiungiFiglioForm(String id, AggiungiFiglio model, Utente utente) {
    super(id, model);

    this.utente = utente;

    setMultiPart(true);

    addElementiForm();

    onError();
  }

  private List<AutocertificazioneTipoMinoreEnum> getLista() {
    List<AutocertificazioneTipoMinoreEnum> listaParentele =
        Arrays.asList(AutocertificazioneTipoMinoreEnum.values());

    List<AutocertificazioneTipoMinoreEnum> listaParenteleSenzaMinoreConvivente =
        new ArrayList<AutocertificazioneTipoMinoreEnum>();

    for (AutocertificazioneTipoMinoreEnum elem : listaParentele) {
      if (!elem.getDescription()
              .equalsIgnoreCase(AutocertificazioneTipoMinoreEnum.MC.getDescription())
          && !elem.getDescription()
              .equalsIgnoreCase(AutocertificazioneTipoMinoreEnum.NN.getDescription())) {
        listaParenteleSenzaMinoreConvivente.add(elem);
      }
    }

    return listaParenteleSenzaMinoreConvivente;
  }

  private DropDownChoice<?> creaComboParentela() {
    DropDownChoice<?> dropDownChoice =
        creaDropDownChoice(
            getLista(),
            "comboParentela",
            new TipoMinoreRenderer(),
            new PropertyModel<AggiungiFiglio>(getModel(), "tipoParentela"));
    dropDownChoice.setEscapeModelStrings(true);
    dropDownChoice.setRequired(true);
    dropDownChoice.setLabel(Model.of("Seleziona parentela"));
    return dropDownChoice;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void addElementiForm() {
    addOrReplace(creaComboParentela());

    TextField codiceFiscale =
        new TextField("codiceFiscale", new PropertyModel(getModelObject(), "codiceFiscale"));
    codiceFiscale.setRequired(true);
    codiceFiscale.setLabel(Model.of("Codice fiscale"));
    codiceFiscale.add(new CFAutodichiarazioneFiglioValidator(utente));
    codiceFiscale.add(StringValidator.exactLength(16));
    addOrReplace(codiceFiscale);

    TextField nome = new TextField("nome", new PropertyModel(getModelObject(), "nome"));
    nome.setRequired(true);
    nome.setLabel(Model.of("Nome"));
    nome.add(new LetterValidator());
    addOrReplace(nome);

    TextField cognome = new TextField("cognome", new PropertyModel(getModelObject(), "cognome"));
    cognome.setRequired(true);
    cognome.setLabel(Model.of("Cognome"));
    cognome.add(new LetterValidator());
    addOrReplace(cognome);
  }
}
