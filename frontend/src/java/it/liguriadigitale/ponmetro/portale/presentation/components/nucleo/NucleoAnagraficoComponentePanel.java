package it.liguriadigitale.ponmetro.portale.presentation.components.nucleo;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.amt.AbbonamentoSanzioneAmt;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.AbbonamentiAmtPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.NucleoFamiglia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.panel.AbbonamentiAmtPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.sanzioniamt.SanzioniAMTPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.sanzioniamt.panel.SanzioniAMTPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

public class NucleoAnagraficoComponentePanel<T extends Component> extends BasePanel {

  private static final long serialVersionUID = 5761763310508403577L;

  private FeedbackPanel feedbackPanel;

  private AbbonamentoSanzioneAmt abbonamentoSanzioneAmt;

  public NucleoAnagraficoComponentePanel(String id, AbbonamentoSanzioneAmt abbonamentoSanzioneAmt) {
    super(id);

    this.abbonamentoSanzioneAmt = abbonamentoSanzioneAmt;

    setOutputMarkupId(true);
    feedbackPanel = createFeedBackPanelNucleo();
  }

  @Override
  public void fillDati(Object dati) {
    NucleoFamiglia abbonamentiAmt = (NucleoFamiglia) dati;
    creaForm(abbonamentiAmt);
  }

  private void creaForm(NucleoFamiglia abbonamentiAmt) {

    AbstracFrameworkForm<NucleoFamiglia> form =
        new AbstracFrameworkForm<NucleoFamiglia>("form", abbonamentiAmt) {

          private static final long serialVersionUID = -617291987966666525L;

          @Override
          public void addElementiForm() {
            List<ComponenteNucleo> listaNucleoFamiliare = new ArrayList();
            listaNucleoFamiliare.addAll(abbonamentiAmt.getListaCfNucleo());

            String titoloComboNucleoAmt = "";
            if (abbonamentoSanzioneAmt
                .getDescrizione()
                .equalsIgnoreCase(AbbonamentoSanzioneAmt.ABBONAMENTO.getDescrizione())) {
              titoloComboNucleoAmt =
                  "Scegli per quale componente del nucleo familiare vuoi visualizzare l'abbonamento AMT:";
            } else if (abbonamentoSanzioneAmt
                .getDescrizione()
                .equalsIgnoreCase(AbbonamentoSanzioneAmt.SANZIONE.getDescrizione())) {
              titoloComboNucleoAmt =
                  "Scegli per quale componente del nucleo familiare vuoi visualizzare le sanzioni AMT:";
            }
            Label titoloComboNucleo = new Label("titoloComboNucleo", titoloComboNucleoAmt);
            addOrReplace(titoloComboNucleo);

            List<ComponenteNucleoAmt> listaNucleoFamiliareAMT =
                new ArrayList<ComponenteNucleoAmt>();

            if (LabelFdCUtil.checkIfNotNull(abbonamentiAmt.getListaCfNucleo())
                && abbonamentiAmt.getListaCfNucleo().size() > 1) {
              ComponenteNucleoAmt tuttiComponenti = new ComponenteNucleoAmt();
              tuttiComponenti.setTutti("TUTTI");
              listaNucleoFamiliareAMT.add(tuttiComponenti);
            }

            for (ComponenteNucleo elemComponente : abbonamentiAmt.getListaCfNucleo()) {
              ComponenteNucleoAmt elemAmt = new ComponenteNucleoAmt();
              elemAmt.setComponenteNucleo(elemComponente);
              listaNucleoFamiliareAMT.add(elemAmt);
            }

            ComponenteNucleoDropDownChoice selectComboNucleo =
                new ComponenteNucleoDropDownChoice<>(
                    "comboNucleo",
                    new PropertyModel<>(getModelObject(), "comboNucleo"),
                    listaNucleoFamiliareAMT);

            ComponenteNucleoAmt tuttiComponenti = new ComponenteNucleoAmt();
            tuttiComponenti.setTutti("TUTTI");
            selectComboNucleo.setDefaultModelObject(tuttiComponenti);

            selectComboNucleo.add(
                new OnChangeAjaxBehavior() {

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    log.debug("CP selectComboNucleo amt onUpdate ");

                    ComponenteNucleoAmt componenteNucleoAMT =
                        (ComponenteNucleoAmt) selectComboNucleo.getModelObject();

                    NucleoFamiglia nucleoSelezionato = new NucleoFamiglia();

                    if (LabelFdCUtil.checkIfNotNull(componenteNucleoAMT)) {
                      if (LabelFdCUtil.checkIfNotNull(componenteNucleoAMT.getComponenteNucleo())) {
                        List<ComponenteNucleo> listaComponenteDelNucleo =
                            new ArrayList<ComponenteNucleo>();
                        listaComponenteDelNucleo.add(componenteNucleoAMT.getComponenteNucleo());
                        nucleoSelezionato.setListaCfNucleo(listaComponenteDelNucleo);

                      } else {
                        nucleoSelezionato.setListaCfNucleo(abbonamentiAmt.getListaCfNucleo());
                      }
                    }

                    if (abbonamentoSanzioneAmt
                        .getDescrizione()
                        .equalsIgnoreCase(AbbonamentoSanzioneAmt.ABBONAMENTO.getDescrizione())) {
                      AbbonamentiAmtPage page = (AbbonamentiAmtPage) getPage();
                      AbbonamentiAmtPanel panel =
                          page.creaPannelloAbbonamentiAMT(nucleoSelezionato);

                      target.add(panel, feedbackPanel);

                    } else if (abbonamentoSanzioneAmt
                        .getDescrizione()
                        .equalsIgnoreCase(AbbonamentoSanzioneAmt.SANZIONE.getDescrizione())) {
                      SanzioniAMTPage page = (SanzioniAMTPage) getPage();
                      SanzioniAMTPanel panel = page.creaPannelloSanzioniAMT(nucleoSelezionato);

                      target.add(panel, feedbackPanel);
                    }
                  }
                });

            addOrReplace(selectComboNucleo);
          }
        };

    addOrReplace(form);
  }

  protected FeedbackPanel createFeedBackPanelNucleo() {

    NotificationPanel feedback =
        new NotificationPanel("feedbackNucleo") {

          private static final long serialVersionUID = 5374513024437798726L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }
}
