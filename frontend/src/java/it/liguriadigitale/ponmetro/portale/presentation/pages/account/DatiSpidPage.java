package it.liguriadigitale.ponmetro.portale.presentation.pages.account;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente.TipoEnum;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.account.panel.breadcrumb.BreadcrumbDatiSpidPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti.SalvaContattiPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class DatiSpidPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7135026543304712805L;

  public DatiSpidPage() {
    super();
    mostraDati();
  }

  private void mostraDati() {

    BreadcrumbDatiSpidPanel breadcrumbDatiSpidPanel =
        new BreadcrumbDatiSpidPanel("breadcrumbPanel");
    addOrReplace(breadcrumbDatiSpidPanel);

    Utente utente = getUtente();

    addOrReplace(
        new Label("cf", utente.getCodiceFiscaleOperatore())
            .setVisible(utente.getCodiceFiscaleOperatore() != null));
    addOrReplace(
        new Label("nome", utente.getNome() + " " + utente.getCognome())
            .setVisible(utente.getNome() != null && utente.getCognome() != null));
    addOrReplace(
        new Label(
                "luogoNascita",
                getLuogoNascitaNotNull(utente).toLowerCase()
                    + " "
                    + getProvinciaNotNull(utente).toLowerCase())
            .setVisible(
                getLuogoNascitaNotNull(utente) != null
                    && !getLuogoNascitaNotNull(utente).isEmpty()));
    addOrReplace(
        new LocalDateLabel("dataNascita", utente.getDataDiNascita())
            .setVisible(utente.getDataDiNascita() != null));

    addOrReplace(new Label("emailUtente", utente.getMail()).setVisible(utente.getMail() != null));
    addOrReplace(
        new Label("cellulareUtente", utente.getMobile()).setVisible(utente.getMobile() != null));

    addOrReplace(
        new Label("domicilio", utente.getDomicilio()).setVisible(utente.getDomicilio() != null));

    addOrReplace(new Label("pec", utente.getPec()).setVisible(false));
    addOrReplace(new Label("sesso", utente.getSesso()).setVisible(false));
    addOrReplace(new Label("idSpid", utente.getCodiceIdentificativoSpid()).setVisible(false));

    addOrReplace(new Label("build_version", BaseServiceImpl.BUILD_VERSION));

    addOrReplace(creaBtnModificaContatti());
  }

  private LaddaAjaxLink<Object> creaBtnModificaContatti() {
    LaddaAjaxLink<Object> btnModificaContatti =
        new LaddaAjaxLink<Object>("btnModificaContatti", Type.Primary) {

          private static final long serialVersionUID = 2803927276265666355L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiSpidPage.this);

            List<ContattiUtente> contattiUtente = new ArrayList<>();

            if (PageUtil.isStringValid(getUtente().getMail())) {
              ContattiUtente contattoEmail = new ContattiUtente();
              contattoEmail.setContatto(getUtente().getMail());
              contattoEmail.setTipo(TipoEnum.E);
              contattiUtente.add(contattoEmail);
            }

            if (PageUtil.isStringValid(getUtente().getMobile())) {
              ContattiUtente contattoCellulare = new ContattiUtente();
              contattoCellulare.setContatto(getUtente().getMobile());
              contattoCellulare.setTipo(TipoEnum.C);
              contattiUtente.add(contattoCellulare);
            }

            setResponsePage(new SalvaContattiPage(true, contattiUtente));
          }
        };
    btnModificaContatti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DatiSpidPage.btnModificaContatti", DatiSpidPage.this)));

    // btnModificaContatti.setVisible(Constants.DEPLOY != TIPO_DEPLOY.MANUTENZIONE);
    btnModificaContatti.setVisible(
        BaseServiceImpl.SALVATAGGIO_CONTATTI_ACCESO.equalsIgnoreCase("on"));

    return btnModificaContatti;
  }

  private String getLuogoNascitaNotNull(Utente utente) {
    if (utente.getLuogoNascita() != null) return utente.getLuogoNascita();
    else return "";
  }

  private String getProvinciaNotNull(Utente utente) {
    if (utente.getProvinciaNascita() != null) return utente.getProvinciaNascita();
    else return "";
  }
}
