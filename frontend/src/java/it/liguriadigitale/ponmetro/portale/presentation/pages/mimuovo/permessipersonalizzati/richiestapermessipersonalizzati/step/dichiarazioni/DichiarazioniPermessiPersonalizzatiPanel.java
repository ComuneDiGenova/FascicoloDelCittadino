package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.dichiarazioni;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazionePermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazioniPermessiPersonalizzati;
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
import org.apache.wicket.model.CompoundPropertyModel;

public class DichiarazioniPermessiPersonalizzatiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;
  ListView<DichiarazionePermessiPersonalizzati> listView;
  DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel;

  public DichiarazioniPermessiPersonalizzatiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel) {
    super(id);
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    setOutputMarkupId(true);
    fillDati(richiestaPermessoPersonalizzatoModel);
    DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel = this;
  }

  @Override
  public void fillDati(Object dati) {

    List<DichiarazionePermessiPersonalizzati> listaDichiarazioniPermessiPersonalizzati;
    if (richiestaPermessoPersonalizzatoModel.getObject().getDichiarazioni() == null
        || richiestaPermessoPersonalizzatoModel
                .getObject()
                .getDichiarazioni()
                .getListaDichiarazioni()
            == null) {
      listaDichiarazioniPermessiPersonalizzati = new ArrayList<>();
      try {
        List<DichiarazioniResponse> listaDichiarazioni =
            ServiceLocator.getInstance().getServiziPermessiPersonalizzati().getDichiarazioni();

        for (Iterator<DichiarazioniResponse> iterator = listaDichiarazioni.iterator();
            iterator.hasNext(); ) {
          DichiarazioniResponse dichiarazioniResponse = iterator.next();

          DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati =
              new DichiarazionePermessiPersonalizzati();
          dichiarazionePermessiPersonalizzati.setCodiceDichiarazione(
              dichiarazioniResponse.getCodiceDichiarazione());
          dichiarazionePermessiPersonalizzati.setDescrizioneDichiarazione(
              dichiarazioniResponse.getDescrizioneDichiarazione());
          dichiarazionePermessiPersonalizzati.setDichiarazioneAlternativa(
              dichiarazioniResponse.getDichiarazioneAlternativa());

          listaDichiarazioniPermessiPersonalizzati.add(dichiarazionePermessiPersonalizzati);
        }

        richiestaPermessoPersonalizzatoModel
            .getObject()
            .setDichiarazioni(new DichiarazioniPermessiPersonalizzati());

        richiestaPermessoPersonalizzatoModel
            .getObject()
            .getDichiarazioni()
            .setListaDichiarazioni(listaDichiarazioniPermessiPersonalizzati);

      } catch (BusinessException | ApiException | IOException e) {
        throw new RestartResponseAtInterceptPageException(
            new ErroreServiziPage("elenco tipologie allegati"));
      }
    } else
      listaDichiarazioniPermessiPersonalizzati =
          richiestaPermessoPersonalizzatoModel
              .getObject()
              .getDichiarazioni()
              .getListaDichiarazioni();

    listView =
        new ListView<DichiarazionePermessiPersonalizzati>(
            "lista", listaDichiarazioniPermessiPersonalizzati) {

          private static final long serialVersionUID = -6761858167284918287L;

          @Override
          protected void populateItem(ListItem<DichiarazionePermessiPersonalizzati> item) {

            item.setOutputMarkupId(true);
            item.setOutputMarkupPlaceholderTag(true);
            item.addOrReplace(
                new DichiarazionePPPanel(
                    "dichiarazione",
                    new CompoundPropertyModel<>(item.getModelObject()),
                    richiestaPermessoPersonalizzatoModel,
                    dichiarazioniPermessiPersonalizzatiPanel));
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);

    addOrReplace(listView);
  }
}
