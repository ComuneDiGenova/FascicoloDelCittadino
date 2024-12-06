package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public final class RichiestaDietaSpecialeHelper {

  public static FdCTextField<Component> createFdCTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired, Panel panello) {

    FdCTextField<Component> field = new FdCTextField<Component>(id, model, panello);
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);

    return field;
  }

  public static FdCEmailTextField<Component> createFdCEmailTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired, Panel panello) {
    FdCEmailTextField<Component> field = new FdCEmailTextField<Component>(id, model, panello);

    field.setEnabled(enabled);
    field.getTextField().setRequired(isRequired);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);
    return field;
  }

  public static TipoDietaDropDownChoise createFdcTipoDietaDropDown(
      String id, IModel<TipoDietaSpecialeEnum> model) {
    TipoDietaDropDownChoise field = new TipoDietaDropDownChoise(id, model, true);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);
    return field;
  }

  public static MenuMotiviReligiosiDropDownChoise createMenuMotiviReligiosiDropDownChoise(
      String id, IModel<DietaMotiviEticoReligiosi> model) {
    MenuMotiviReligiosiDropDownChoise field = new MenuMotiviReligiosiDropDownChoise(id, model);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);
    field.setLabel(Model.of("Tipo menu"));
    return field;
  }

  public static ScuolaDropDownChoise creaScuolaDropDownChoise(
      String id, PropertyModel<DatiIstituto> model) {
    ScuolaDropDownChoise field = new ScuolaDropDownChoise(id, model, true);

    field.setRequired(true);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);
    return field;
  }

  public static DirezioneTerritorialeDropDownChoise creaDirezioneTerritorialeDropDownChoise(
      String id, PropertyModel<DatiDirezioneTerritoriale> model) {
    DirezioneTerritorialeDropDownChoise field = new DirezioneTerritorialeDropDownChoise(id, model);

    field.setLabel(Model.of("Direzione Territoriale"));
    field.setRequired(true);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);

    return field;
  }

  public static AnnoScolasticoDropDownChoice creaAnnoScolasticoDropDownChoise(
      String id, PropertyModel<AnnoScolastico> model) {
    AnnoScolasticoDropDownChoice field = new AnnoScolasticoDropDownChoice(id, model);

    field.setLabel(Model.of("Anno scolastico"));
    field.setRequired(true);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);

    return field;
  }
}
