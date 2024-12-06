package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Allegabile;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.AllegatoPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DocumentiAllegati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class DocumentiAllegatiPermessiPersonalizzatiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;
  ListView<AllegatoPermessiPersonalizzati> listView;

  private FeedbackPanel feedbackPanel;

  protected int idDomanda;
  boolean attivo;

  public DocumentiAllegatiPermessiPersonalizzatiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      int idDomanda,
      boolean attivo) {
    super(id);
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    this.attivo = attivo;
    this.idDomanda = idDomanda;
    fillDati(richiestaPermessoPersonalizzatoModel);
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    List<AllegatoPermessiPersonalizzati> listaAllegatoPermessiPersonalizzati;
    if (richiestaPermessoPersonalizzatoModel.getObject().getDocumentiAllegati() == null
        || richiestaPermessoPersonalizzatoModel
                .getObject()
                .getDocumentiAllegati()
                .getListaAllegatoPermessiPersonalizzati()
            == null) {
      listaAllegatoPermessiPersonalizzati = new ArrayList<>();

      if (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null) {
        try {
          List<Allegabile> listaAllegabile =
              ServiceLocator.getInstance()
                  .getServiziPermessiPersonalizzati()
                  .getAllegabili(
                      richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice(),
                      richiestaPermessoPersonalizzatoModel
                          .getObject()
                          .getSoggettiCoinvolti()
                          .getDisabile()
                          .isResidenteInGenova());
          for (Iterator<Allegabile> iterator = listaAllegabile.iterator(); iterator.hasNext(); ) {
            Allegabile allegabile = iterator.next();

            AllegatoPermessiPersonalizzati allegatoPermessiPersonalizzati =
                new AllegatoPermessiPersonalizzati();
            allegatoPermessiPersonalizzati.setCodiceAllegato(allegabile.getCodiceAllegato());
            allegatoPermessiPersonalizzati.setDescrizioneAllegato(
                allegabile.getDescrizioneAllegato());
            listaAllegatoPermessiPersonalizzati.add(allegatoPermessiPersonalizzati);
          }

          if (richiestaPermessoPersonalizzatoModel.getObject().getDocumentiAllegati() == null)
            richiestaPermessoPersonalizzatoModel
                .getObject()
                .setDocumentiAllegati(new DocumentiAllegati());

          richiestaPermessoPersonalizzatoModel
              .getObject()
              .getDocumentiAllegati()
              .setListaAllegatoPermessiPersonalizzati(listaAllegatoPermessiPersonalizzati);

        } catch (BusinessException | ApiException | IOException e) {
          throw new RestartResponseAtInterceptPageException(
              new ErroreServiziPage("elenco tipologie allegati"));
        }
      }
    } else
      listaAllegatoPermessiPersonalizzati =
          richiestaPermessoPersonalizzatoModel
              .getObject()
              .getDocumentiAllegati()
              .getListaAllegatoPermessiPersonalizzati();

    listView =
        new ListView<AllegatoPermessiPersonalizzati>("lista", listaAllegatoPermessiPersonalizzati) {

          private static final long serialVersionUID = -6761858167284918287L;

          @Override
          protected void populateItem(ListItem<AllegatoPermessiPersonalizzati> item) {
            AllegatoPermessiPersonalizzatiPanel allegatoPermessiPersonalizzatiPanel =
                new AllegatoPermessiPersonalizzatiPanel(
                    "allegato",
                    new CompoundPropertyModel<>(item.getModelObject()),
                    attivo,
                    idDomanda);
            item.addOrReplace(allegatoPermessiPersonalizzatiPanel);
          }
        };

    addOrReplace(listView);
  }

  //	protected FeedbackPanel createFeedBackPPPanel() {
  //		NotificationPanel feedback = new NotificationPanel("feedbackDocumentiAllegati") {
  //
  //			private static final long serialVersionUID = -4144216848667276421L;
  //
  //			@Override
  //			protected boolean isCloseButtonVisible() {
  //				return false;
  //			}
  //		};
  //		feedback.setOutputMarkupId(true);
  //		this.addOrReplace(feedback);
  //		return feedback;
  //	}

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    if (listView == null || listView.getList().size() == 0)
      fillDati(richiestaPermessoPersonalizzatoModel);
  }
}
