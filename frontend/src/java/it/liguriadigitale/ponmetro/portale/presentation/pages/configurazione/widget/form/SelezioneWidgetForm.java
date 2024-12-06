package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget.form;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.portale.configurazione.WidgetSelezionati;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.DatiVisualizzazioneSezioneWidgetRenderer;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class SelezioneWidgetForm extends AbstracFrameworkForm<WidgetSelezionati> {

  private static final long serialVersionUID = -8935404967414978837L;
  private List<DatiVisualizzazioneSezioneWidget> listaWidget;

  public SelezioneWidgetForm(
      String id, WidgetSelezionati model, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    super(id, model);
    this.listaWidget = listaWidget;
    addElementiForm(this.listaWidget);
  }

  public void addElementiForm(List<DatiVisualizzazioneSezioneWidget> listaWidget) {

    WidgetSelezionati model = getModelObject();

    WidgetDropDownChoice comboSceltaBottom =
        creaDropDownChoice(
            listaWidget,
            "widgetBottom",
            new DatiVisualizzazioneSezioneWidgetRenderer(),
            new PropertyModel<>(model, "widgetBottom"));
    WidgetDropDownChoice comboSceltaTop =
        creaDropDownChoice(
            listaWidget,
            "widgetTop",
            new DatiVisualizzazioneSezioneWidgetRenderer(),
            new PropertyModel<>(model, "widgetTop"));
    comboSceltaTop.add(
        new AjaxFormComponentUpdatingBehavior("click") {

          private static final long serialVersionUID = -5640364250947256925L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            DatiVisualizzazioneSezioneWidget scelta1 =
                (DatiVisualizzazioneSezioneWidget) comboSceltaTop.getModelObject();
            List<DatiVisualizzazioneSezioneWidget> choices = removeSceltaDaLista(scelta1);
            comboSceltaBottom.setChoices(choices);
            comboSceltaBottom.setEnabled(true);
            target.add(comboSceltaBottom);
          }
        });
    comboSceltaTop.setOutputMarkupId(true);
    comboSceltaBottom.setOutputMarkupId(true);
    comboSceltaBottom.setEnabled(false);
    add(comboSceltaTop);
    add(comboSceltaBottom);
  }

  @Override
  public void addElementiForm() {
    // non utilizzato
  }

  private List<DatiVisualizzazioneSezioneWidget> removeSceltaDaLista(
      DatiVisualizzazioneSezioneWidget scelta1) {

    List<DatiVisualizzazioneSezioneWidget> lista = new ArrayList<>();
    for (DatiVisualizzazioneSezioneWidget dati : listaWidget) {
      if (!dati.getWidget().getIdWidget().equalsIgnoreCase(scelta1.getWidget().getIdWidget()))
        lista.add(dati);
    }
    return (lista);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected WidgetDropDownChoice creaDropDownChoice(
      List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {
    ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
    WidgetDropDownChoice combo = new WidgetDropDownChoice(idWicket, choices);
    combo.setChoiceRenderer(choiceRenderer);
    combo.setModel(modello);
    return combo;
  }
}
