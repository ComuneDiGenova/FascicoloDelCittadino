package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoMinoreRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

public class NucleoAnagraficoPanel extends BasePanel {

  private static final long serialVersionUID = -1642481424336076636L;

  public NucleoAnagraficoPanel(String id) {
    super(id);
    Residente residente = LoadData.caricaMieiDatiResidente(getSession());
    fillDati(residente);
    createFeedBackPanel();
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    List<MinoreConvivente> listaMinori = new ArrayList<>();

    try {
      listaMinori =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .getFigliPerAutodichiarazione(getUtente());
    } catch (BusinessException | ApiException e) {
      log.error("Errore API:", e);
    }

    ListView<MinoreConvivente> listView =
        new ListView<MinoreConvivente>("listaConviventi", listaMinori) {

          private static final long serialVersionUID = -675270110514582192L;

          @Override
          protected void populateItem(ListItem<MinoreConvivente> item) {

            MinoreConvivente minore = item.getModelObject();
            AbstracFrameworkForm<MinoreConvivente> form =
                new AbstracFrameworkForm<MinoreConvivente>("form", minore) {

                  private static final long serialVersionUID = 1811478912485612738L;

                  @Override
                  public void addElementiForm() {

                    add(
                        creaDropDownChoice(
                            getLista(),
                            "combo",
                            new TipoMinoreRenderer(),
                            new PropertyModel<MinoreConvivente>(getModel(), "tipoParentela")));
                  }

                  private List<AutocertificazioneTipoMinoreEnum> getLista() {

                    return Arrays.asList(AutocertificazioneTipoMinoreEnum.values());
                  }
                };
            item.add(form);

            form.add(
                new AjaxButton("salva", form) {

                  private static final long serialVersionUID = 1302165883054146653L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget target) {

                    MinoreConvivente minore = form.getModelObject();
                    try {
                      ServiceLocator.getInstance()
                          .getServiziConfigurazione()
                          .updateMinoreConviventePerDichiazioneGenitore(minore, getUtente());
                      log.debug("Modifiche salvate");
                      NucleoAnagraficoPanel.this.success("Modifiche salvate");
                    } catch (BusinessException e) {
                      NucleoAnagraficoPanel.this.error("Errore durante il salvataggio");
                      log.error("Errore:", e);
                    }
                    target.add(NucleoAnagraficoPanel.this);
                  }
                });
            Label nomeCognome = new Label("nome", minore.getNome() + " " + minore.getCognome());
            Label codiceFiscale = new Label("cf", minore.getCodiceFiscale());
            form.add(nomeCognome);
            form.add(codiceFiscale);
          }
        };
    listView.setRenderBodyOnly(true);
    add(listView);
  }
}
