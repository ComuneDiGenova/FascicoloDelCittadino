package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.CodiceFiscaleUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public final class RichiestaRimborsoImuHelper {
  public static FdCTextField<Component> createFdCTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired, Panel panello) {

    FdCTextField<Component> field = new FdCTextField<Component>(id, model, panello);
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);

    return field;
  }

  public static FdCNumberField<Component> createFdCNumberTextField(
      String id, IModel<Integer> model, boolean enabled, boolean isRequired, Panel panello) {

    FdCNumberField<Component> field = new FdCNumberField<Component>(id, model, panello);
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

  public static FdCPhoneNumberField<?> createFdCPhoneNumberField(
      String id, IModel<String> model, boolean enabled, boolean isRequired, Panel panello) {
    FdCPhoneNumberField<?> cellulare = new FdCPhoneNumberField<Object>(id, model, panello);

    cellulare.setEnabled(enabled);
    cellulare.setRequired(isRequired);
    cellulare.getTextField().add(new AttributeAppender("maxlength", "15"));

    return cellulare;
  }

  public static FdCLocalDateField<Component> createFdCLocalDateTextfield(
      String id, IModel<LocalDate> model, boolean enabled, boolean isRequired, Panel panello) {
    FdCLocalDateField<Component> field = new FdCLocalDateField<Component>(id, model, panello);
    field.setEnabled(enabled);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);

    return field;
  }

  public static FdCTextFieldCurrency<Component> createFdCTextFieldCurrency(
      String id, IModel<Double> model, boolean enabled, boolean isRequired, Panel panello) {
    FdCTextFieldCurrency<Component> field =
        new FdCTextFieldCurrency<Component>(id, model, panello, " €");
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    field.setOutputMarkupId(true);
    field.setOutputMarkupPlaceholderTag(true);
    return field;
  }

  public static String checkImmobili(RimborsoImu rimborso) {
    String key = "";

    if (rimborso.getImmobili().isEmpty()) {
      key = "RichiestaRimborsoImuPanel.immobiliEmpty";
    }

    if (rimborso.getImmobili().stream()
        .anyMatch(
            x ->
                (x.getPercentualePossesso() == null
                        || x.getPercentualePossesso().compareTo(BigDecimal.ZERO) <= 0)
                    || x.getUtilizzo() == null)) {
      key = "RichiestaRimborsoImuPanel.percentualePossessoNonIndicata";
    }

    return key;
  }

  public static String checkAnnualita(RimborsoImu rimborso) {
    return rimborso.getVersamenti().isEmpty() ? "RichiestaRimborsoImuPanel.annualitaEmpty" : "";
  }

  public static String checkConoscenza(RimborsoImu rimborso) {
    // TODO Auto-generated method stub
    List<Boolean> list = new ArrayList<Boolean>();
    list.add(rimborso.isCk1());
    list.add(rimborso.isCk2());
    list.add(rimborso.isCk3());
    list.add(rimborso.isCk4());

    return list.stream().anyMatch(x -> x == false)
        ? "RichiestaRimborsoImuPanel.checkAllCondition"
        : "";
  }

  public static ExternalLink createExtenalLink(String id, String url, String label) {
    return new ExternalLink(id, url, label) {
      private static final long serialVersionUID = -8010560272317354356L;

      @Override
      protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("target", "_blank");
      }
    };
  }

  public static String getUrl(String chiave) {
    return ServiceLocator.getInstance().getServiziImuEng().getLinkFromResourceDB(chiave);
  }

  public static String checkCFErede(
      String nome,
      String cognome,
      LocalDate dataNascita,
      char sesso,
      String codiceLuogoNascita,
      String codiceFiscaleEredeDaVerificare)
      throws Exception {

    CodiceFiscaleUtil cfUtil = new CodiceFiscaleUtil();
    String cfEredeCalcolato =
        cfUtil.generaCodiceFiscale(
            nome,
            cognome,
            dataNascita.getDayOfMonth(),
            dataNascita.getMonthValue(),
            dataNascita.getYear(),
            sesso,
            codiceLuogoNascita);

    return !codiceFiscaleEredeDaVerificare.equals(cfEredeCalcolato)
        ? "RichiestaRimborsoImuPanel.checkCfEredeErrato"
        : "";
  }

  public static List<CategoriaCatastale> getCategorieCatastali() {
    try {
      return ServiceLocator.getInstance().getServiziImuEng().getCategorieCatastali();
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      return new ArrayList<>();
    }
  }

  public static CategoriaCatastale getCategoriaCatastaleByCodice(String codice) {
    List<CategoriaCatastale> lista = getCategorieCatastali();

    try {
      return lista.stream().filter(x -> x.getCodice().equals(codice)).findFirst().get();
    } catch (NoSuchElementException e) {
      return null;
    }
  }
}
