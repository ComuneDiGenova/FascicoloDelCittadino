package it.liguriadigitale.ponmetro.portale.presentation.pages.step;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class StepPanel extends BasePanel {

  private static final long serialVersionUID = -4597203846396094476L;

  private Integer indexStep = 1;
  private Integer numeroStepDaMostrare = 0;

  public StepPanel(String id, Integer index, List<StepFdC> lista) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.indexStep = index;
    this.setNumeroStepDaMostrare(index);
    fillDati(lista);

    log.debug("costrutture step panel");
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<StepFdC> lista = (List<StepFdC>) dati;

    ListView<StepFdC> listView =
        new ListView<StepFdC>("lista", lista) {

          private static final long serialVersionUID = 6200460079524201264L;

          @Override
          protected void populateItem(ListItem<StepFdC> item) {

            final StepFdC stepEnum = item.getModelObject();

            Label number = new Label("number", stepEnum.getIndice());
            item.addOrReplace(number);

            Label step = new Label("step", stepEnum.getDescrizione());
            item.addOrReplace(step);

            Label only = new Label("only", stepEnum.getDescrizione());
            item.addOrReplace(only);

            AttributeAppender stepAttuale = new AttributeAppender("class", "active no-line");
            if (stepEnum.getIndice() == indexStep) {
              item.add(stepAttuale);
            }

            AttributeAppender stepEseguito = new AttributeAppender("class", "confirmed");
            if (stepEnum.getIndice() > indexStep) {
              item.add(stepEseguito);
            }

            item.setVisible(stepEnum.getShow());
          }
        };

    addOrReplace(listView);
  }

  public Integer getNumeroStepDaMostrare() {
    return numeroStepDaMostrare;
  }

  public void setNumeroStepDaMostrare(Integer numeroStepDaMostrare) {
    this.numeroStepDaMostrare = numeroStepDaMostrare;
  }

  public Integer getIndexStep() {
    return indexStep;
  }

  public void setIndexStep(Integer indexStep) {
    this.indexStep = indexStep;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    StringBuffer sb = new StringBuffer();

    sb.append("$(\"html,body\").scrollTop(300);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
