package it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente.TipoEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.RedirectBaseLandingPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

@SuppressWarnings("rawtypes")
public class SalvaContattiPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -5886305978939947512L;

  private boolean isObbligatorioInserireContatti;

  public SalvaContattiPanel(
      String id, boolean isObbligatorioInserireContatti, List<ContattiUtente> listaContatti) {
    super(id, listaContatti);

    setObbligatorioInserireContatti(isObbligatorioInserireContatti);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(listaContatti);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    List<ContattiUtente> listaContatti = (List<ContattiUtente>) dati;

    log.debug("CP listaContatti = " + listaContatti);

    if (LabelFdCUtil.checkIfNull(listaContatti) || LabelFdCUtil.checkEmptyList(listaContatti)) {
      ContattiUtente contattoEmail = new ContattiUtente();
      contattoEmail.setTipo(TipoEnum.E);
      ContattiUtente contattoCellulare = new ContattiUtente();
      contattoCellulare.setTipo(TipoEnum.C);

      listaContatti.add(contattoCellulare);
      listaContatti.add(contattoEmail);
    } else {
      if (listaContatti.size() == 1) {

        boolean isNonPresenteEmail =
            listaContatti.stream()
                .anyMatch(elem -> !elem.getTipo().equalsIgnoreCase(TipoEnum.E.value()));
        boolean isNonPresenteCel =
            listaContatti.stream()
                .anyMatch(elem -> !elem.getTipo().equalsIgnoreCase(TipoEnum.C.value()));

        log.debug(
            "CP isNonPresenteEmail = "
                + isNonPresenteEmail
                + " isNonPresenteCel = "
                + isNonPresenteCel);

        if (isNonPresenteEmail) {
          ContattiUtente contattoEmail = new ContattiUtente();
          contattoEmail.setTipo(TipoEnum.E);

          listaContatti.add(contattoEmail);
        }

        if (isNonPresenteCel) {
          ContattiUtente contattoEmail = new ContattiUtente();
          contattoEmail.setTipo(TipoEnum.C);

          listaContatti.add(contattoEmail);
        }
      }
    }

    form.add(new FdCTitoloPanel("titolo", getString("SalvaContattiPanel.titolo")));

    ListView<ContattiUtente> listViewContatti =
        new ListView<ContattiUtente>("listaContatti", listaContatti) {

          private static final long serialVersionUID = 2504182463602824062L;

          @Override
          protected void populateItem(ListItem<ContattiUtente> item) {
            final ContattiUtente contatto = item.getModelObject();

            int indice = item.getIndex();

            if (contatto.getTipo().equalsIgnoreCase(TipoEnum.C.value())) {
              FdCPhoneNumberField cel =
                  new FdCPhoneNumberField(
                      "tipoContatto",
                      new PropertyModel(contatto, "contatto"),
                      SalvaContattiPanel.this) {

                    private static final long serialVersionUID = -3313399455809781017L;

                    @Override
                    protected void creaLabelEtichettaGenerandoResourceId(
                        Component pannello, String id) {
                      String nomePannello = pannello.getClass().getSimpleName();
                      String resourceId = nomePannello + "." + "cellulare";
                      creaLabelEtichetta(pannello, resourceId, "tipoContatto" + indice);
                    }
                  };

              cel.setMarkupId("tipoContatto" + indice);

              item.addOrReplace(cel);
            } else if (contatto.getTipo().equalsIgnoreCase(TipoEnum.E.value())) {
              FdCEmailTextField mail =
                  new FdCEmailTextField(
                      "tipoContatto",
                      new PropertyModel(contatto, "contatto"),
                      SalvaContattiPanel.this) {

                    private static final long serialVersionUID = -7802017110943427073L;

                    @Override
                    protected void creaLabelEtichettaGenerandoResourceId(
                        Component pannello, String id) {
                      String nomePannello = pannello.getClass().getSimpleName();
                      String resourceId = nomePannello + "." + "email";
                      creaLabelEtichetta(pannello, resourceId, "tipoContatto" + indice);
                    }
                  };

              mail.setMarkupId("tipoContatto" + indice);

              item.addOrReplace(mail);
            }
          }
        };
    form.addOrReplace(listViewContatti);

    form.addOrReplace(creaBtnSalva(listaContatti));
  }

  private FdcAjaxButton creaBtnSalva(List<ContattiUtente> listaAggiornata) {

    return new FdcAjaxButton("salvaContatti") {

      private static final long serialVersionUID = 6578449388334660104L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        log.debug(
            "CP salva contatti = "
                + listaAggiornata
                + " \n "
                + getUtente().getMail()
                + " \n "
                + getUtente().getMobile()
                + " \n "
                + isObbligatorioInserireContatti);

        boolean checkNonCiSonoContatti =
            listaAggiornata.stream()
                .allMatch(elemContatto -> !PageUtil.isStringValid(elemContatto.getContatto()));

        log.debug("CP checkNonCiSonoContatti = " + checkNonCiSonoContatti);

        try {

          if (checkNonCiSonoContatti) {
            log.debug("CP email e cel null");

            error("Attenzione, inserire cellulare o email");

            onError();

            target.add(SalvaContattiPanel.this);

          } else {

            for (ContattiUtente contattoInserito : listaAggiornata) {
              log.debug("CP contattoInserito = " + contattoInserito);

              if (PageUtil.isStringValid(contattoInserito.getTipo())) {
                if (contattoInserito.getTipo().equalsIgnoreCase(TipoEnum.C.value())) {
                  log.debug(
                      "CP checkContattoDaAggiornare Cel "
                          + contattoInserito.getContatto()
                          + " - "
                          + getUtente().getMobile());

                  if (checkContattoDaAggiornare(
                      contattoInserito.getContatto(), getUtente().getMobile())) {
                    contattoInserito.setTipo(TipoEnum.C);
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .upInsertContattiUtente(getUtente(), TipoEnum.C.value(), contattoInserito);

                    getUtente().setMobile(contattoInserito.getContatto());
                  }

                  if (checkContattoDaCancellare(
                      contattoInserito.getContatto(), getUtente().getMobile())) {
                    contattoInserito.setTipo(TipoEnum.C);
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .cancellaContattiUtente(getUtente(), TipoEnum.C.value());

                    getUtente().setMobile(contattoInserito.getContatto());
                  }

                } else if (contattoInserito.getTipo().equalsIgnoreCase(TipoEnum.E.value())) {
                  log.debug(
                      "CP checkContattoDaAggiornare Email "
                          + contattoInserito.getContatto()
                          + " - "
                          + getUtente().getMail());

                  if (checkContattoDaAggiornare(
                      contattoInserito.getContatto(), getUtente().getMail())) {
                    contattoInserito.setTipo(TipoEnum.E);
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .upInsertContattiUtente(getUtente(), TipoEnum.E.value(), contattoInserito);

                    getUtente().setMail(contattoInserito.getContatto());
                  }

                  if (checkContattoDaCancellare(
                      contattoInserito.getContatto(), getUtente().getMail())) {
                    contattoInserito.setTipo(TipoEnum.E);
                    ServiceLocator.getInstance()
                        .getServiziConfigurazione()
                        .cancellaContattiUtente(getUtente(), TipoEnum.E.value());

                    getUtente().setMail(contattoInserito.getContatto());
                  }
                }
              }
            }

            setResponsePage(RedirectBaseLandingPage.class);
          }

        } catch (BusinessException | ApiException | IOException e) {
          log.error("Errore aggiornamento contatti = " + e.getMessage());
        }
      }

      @Override
      protected void onError(AjaxRequestTarget target) {
        target.add(feedbackPanel);

        log.debug("Errore salva contatti");
      }
    };
  }

  public boolean isObbligatorioInserireContatti() {
    return isObbligatorioInserireContatti;
  }

  public void setObbligatorioInserireContatti(boolean isObbligatorioInserireContatti) {
    this.isObbligatorioInserireContatti = isObbligatorioInserireContatti;
  }

  private boolean checkContattoDaAggiornare(String contattoNuovo, String contattoVecchio) {
    return (PageUtil.isStringValid(contattoNuovo)
        && (!contattoNuovo.equalsIgnoreCase(contattoVecchio)
            || (contattoNuovo.equalsIgnoreCase(contattoVecchio)
                && isObbligatorioInserireContatti)));
  }

  private boolean checkContattoDaCancellare(String contattoNuovo, String contattoVecchio) {
    return !PageUtil.isStringValid(contattoNuovo) && PageUtil.isStringValid(contattoVecchio);
  }
}
