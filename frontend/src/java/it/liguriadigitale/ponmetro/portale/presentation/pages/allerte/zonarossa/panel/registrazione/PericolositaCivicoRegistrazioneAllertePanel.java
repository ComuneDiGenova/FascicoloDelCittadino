package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo.PosizioneEnum;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.PosizioneDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.TipoPersonaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.VulnerabilitaCivicoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class PericolositaCivicoRegistrazioneAllertePanel extends BasePanel {

  private static final long serialVersionUID = -7076316267059163424L;

  private int index;

  FdcAjaxButton avanti;

  PosizioneDropDownChoice posizione;

  private WebMarkupContainer wmkPericolositaNonSufficiente;

  public PericolositaCivicoRegistrazioneAllertePanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      int index,
      FdcAjaxButton avanti) {
    super(id);
    this.avanti = avanti;
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
    fillDati(datiCompleti);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa>) dati;

    FdCTextField amministratore =
        new FdCTextField(
            "amministratore",
            datiCompleti.bind("civicoZonaRossa.amministratore"),
            PericolositaCivicoRegistrazioneAllertePanel.this);
    amministratore.getTextField().setRequired(true);
    addOrReplace(amministratore);

    FdCTextField proprietario =
        new FdCTextField(
            "proprietario",
            datiCompleti.bind("civicoZonaRossa.proprietario"),
            PericolositaCivicoRegistrazioneAllertePanel.this);
    proprietario.getTextField().setRequired(true);
    addOrReplace(proprietario);

    VulnerabilitaCivicoDropDownChoice vulnerabilita =
        new VulnerabilitaCivicoDropDownChoice(
            "vulnerabilita", datiCompleti.bind("civicoZonaRossa.vulnerabilita"));
    vulnerabilita.setRequired(true);
    vulnerabilita.setLabel(Model.of("Vulnerabilità civico"));
    addOrReplace(vulnerabilita);

    TipoPersonaDropDownChoice tipo =
        new TipoPersonaDropDownChoice(
            "tipo", datiCompleti.bind("dettagliUtenteZonaRossa.tipoPersona"));
    tipo.setRequired(true);
    tipo.setLabel(Model.of("Tipo"));
    addOrReplace(tipo);

    posizione =
        new PosizioneDropDownChoice("posizione", datiCompleti.bind("civicoZonaRossa.posizione"));
    posizione.setRequired(true);
    posizione.setLabel(Model.of("Posizione"));
    addOrReplace(posizione);

    posizione.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            log.debug("onupdate indirizzi");

            wmkPericolositaNonSufficiente.setVisible(!isSottostradaOStrada());

            avanti.setVisible(isSottostradaOStrada());
            target.add(wmkPericolositaNonSufficiente, avanti);
          }
        });

    wmkPericolositaNonSufficiente = new WebMarkupContainer("wmkPericolositaNonSufficiente");

    FdCButtonBootstrapAjaxLink btnVaiACortesia =
        new FdCButtonBootstrapAjaxLink("btnVaiACortesia", Type.Primary) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    btnVaiACortesia.setLabel(
        Model.of(getString("PericolositaCivicoRegistrazioneAllertePanel.btnVaiACortesia")));

    IconType icon =
        new IconType("btnVaiACortesia") {

          private static final long serialVersionUID = -4364612481443783595L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnVaiACortesia.setIconType(icon);
    wmkPericolositaNonSufficiente.add(btnVaiACortesia);
    wmkPericolositaNonSufficiente.setOutputMarkupId(true);
    wmkPericolositaNonSufficiente.setOutputMarkupPlaceholderTag(true);
    add(wmkPericolositaNonSufficiente);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    wmkPericolositaNonSufficiente.setVisible(!isSottostradaOStrada());
    avanti.setVisible(isSottostradaOStrada());
  }

  private boolean isSottostradaOStrada() {
    if (posizione != null && posizione.getModelObject() != null) {
      return (PosizioneEnum.SOTTOSTRADA
              .toString()
              .equalsIgnoreCase(posizione.getModelObject().toString())
          || PosizioneEnum.STRADA
              .toString()
              .equalsIgnoreCase(posizione.getModelObject().toString()));
    } else {
      return false;
    }
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
