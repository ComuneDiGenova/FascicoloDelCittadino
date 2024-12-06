package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DichiarazioniBorseStudioPanel extends BasePanel {

  private static final long serialVersionUID = 1618781692612458514L;

  private int index;

  public DichiarazioniBorseStudioPanel(String id, PraticaBorseStudioExtend datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    PraticaBorseStudioExtend datiDomanda = (PraticaBorseStudioExtend) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("DichiarazioniBorseStudioPanel.titolo")));

    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziBorseDiStudio()
            .popolaListaMessaggiDichiarazioniBorseDiStudio();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    SiNoDropDownChoice dichiarazioneDiEssereAConoscenza =
        new SiNoDropDownChoice(
            "dichiarazioneDiEssereAConoscenzaString",
            new PropertyModel<>(datiDomanda, "dichiarazioneDiEssereAConoscenzaString"));
    dichiarazioneDiEssereAConoscenza.setRequired(true);
    dichiarazioneDiEssereAConoscenza.setLabel(Model.of("Dichiaro di essere a conoscenza"));

    dichiarazioneDiEssereAConoscenza.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 745372595300984530L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(dichiarazioneDiEssereAConoscenza)
                && PageUtil.isStringValid(dichiarazioneDiEssereAConoscenza.getValue())) {
              if (dichiarazioneDiEssereAConoscenza.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setDichiarazioneDiEssereAConoscenza(true);
              } else {
                datiDomanda.setDichiarazioneDiEssereAConoscenza(false);
              }
            }
          }
        });

    addOrReplace(dichiarazioneDiEssereAConoscenza);

    WebMarkupContainer lblDichiarazioneDiEssereAConoscenza =
        new WebMarkupContainer("lblDichiarazioneDiEssereAConoscenza");
    lblDichiarazioneDiEssereAConoscenza.add(
        new AttributeAppender("for", dichiarazioneDiEssereAConoscenza.getMarkupId()));
    addOrReplace(lblDichiarazioneDiEssereAConoscenza);
  }
}
