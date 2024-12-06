package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheInternet;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.BiblioInternetPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.form.BiblioInternetForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject1;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteAbil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class BiblioInternetPanel extends BasePanel {

  private static final long serialVersionUID = 1736040378432391717L;

  private BiblioInternetForm form = null;

  private BibliotecheInternet bibliotecheInternet;

  private List<UtenteAbil> abilitazioni;

  private Utente utenteSebina;

  public BiblioInternetPanel(String id, Utente utenteSebina, List<UtenteAbil> abilitazioni) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.abilitazioni = abilitazioni;
    this.utenteSebina = utenteSebina;

    fillDati(abilitazioni);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<UtenteAbil> listaAbilitazioni = (List<UtenteAbil>) dati;

    UtenteAbil abilitazioneBerio =
        listaAbilitazioni.stream()
            .filter(elem -> elem.getCdBib().equalsIgnoreCase("BE"))
            .findAny()
            .orElse(null);

    String decodificaAbilitazione = "";
    if (LabelFdCUtil.checkIfNotNull(abilitazioneBerio)) {
      boolean flAbil = abilitazioneBerio.getFlAbil();
      if (flAbil) {
        decodificaAbilitazione = "abilitati per ";
      } else {
        decodificaAbilitazione = "disabilitati per ";
      }
    }

    String info = decodificaAbilitazione.concat(" ").concat(utenteSebina.getGivenName());

    log.debug(
        "CP fill dati internet = " + utenteSebina.getDisplayName() + " " + utenteSebina.getId());

    List<String> listaCodiciBiblioteche = new ArrayList<String>();
    for (UtenteAbil elem : listaAbilitazioni) {
      listaCodiciBiblioteche.add(elem.getCdBib());
    }

    log.debug("CP lista biblio = " + listaCodiciBiblioteche.size());

    NotEmptyLabel nome =
        new NotEmptyLabel(
            "nome", new StringResourceModel("BiblioInternetPanel.nome", this).setParameters(info));
    addOrReplace(nome);

    form = new BiblioInternetForm("form", bibliotecheInternet);

    form.addOrReplace(creaBottoneAvanti(listaCodiciBiblioteche, abilitazioneBerio));
    form.addOrReplace(creaBottoneAnnulla());
    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti(
      List<String> listaCodiciBiblioteche, UtenteAbil abilitazioneBerio) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -2603150298500006243L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(BiblioInternetPanel.this);

            log.debug("CP click abilita disabilita internet " + utenteSebina);

            String codiceInternet = "M_INTERNET";

            try {

              InlineObject1 inlineObject1 = new InlineObject1();
              inlineObject1.setDtFineAbil(LocalDate.of(2099, 1, 1));

              boolean flAbil = abilitazioneBerio.getFlAbil();
              if (flAbil) {
                inlineObject1.setFlAbil(false);
              } else {
                inlineObject1.setFlAbil(true);
              }

              inlineObject1.setNota(null);

              ServiceLocator.getInstance()
                  .getServiziBiblioteche()
                  .patchUtenteAbil(
                      utenteSebina.getId(), codiceInternet, inlineObject1, listaCodiciBiblioteche);

              success("Modifica effettuata");

              setResponsePage(new BiblioInternetPage(utenteSebina));

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore PATCH Internet " + e.getMessage(), e);

              error(
                  "Errore durante abilitazione servizio, si prega di riprovare più tardi. Grazie.");
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(BiblioInternetPanel.this);

            log.error("Errore biblio internt");
          }
        };

    if (LabelFdCUtil.checkIfNotNull(abilitazioneBerio)) {
      boolean flAbil = abilitazioneBerio.getFlAbil();
      if (flAbil) {
        avanti.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString("BiblioInternetPanel.disabilita", BiblioInternetPanel.this)));
      } else {
        avanti.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString("BiblioInternetPanel.abilita", BiblioInternetPanel.this)));
      }
    }

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 1451969096587156745L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(BiblioInternetPanel.this);

            form.clearInput();

            setResponsePage(new BiblioInternetPage(utenteSebina));
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("BiblioInternetPanel.annulla", BiblioInternetPanel.this)));

    return annulla;
  }
}
