package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class BiblioMinoriPanel extends BasePanel {

  private static final long serialVersionUID = -8451987309994844252L;

  private static final String ICON_OMINO = "color-blue-sebina col-3 icon-studente-libro";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public BiblioMinoriPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<ComponenteNucleo> listaFigliUnder18 = (List<ComponenteNucleo>) dati;

    PageableListView<ComponenteNucleo> listView =
        new PageableListView<ComponenteNucleo>("box", listaFigliUnder18, 4) {

          private static final long serialVersionUID = 8842485594626804840L;

          @Override
          protected void populateItem(ListItem<ComponenteNucleo> item) {
            final ComponenteNucleo bambino = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaMaschioFemmina(bambino));
            item.addOrReplace(icona);

            Residente residente = bambino.getDatiCittadino();
            String nome = "";
            LocalDate data = null;
            if (LabelFdCUtil.checkIfNotNull(residente)) {
              if (LabelFdCUtil.checkIfNotNull(residente.getCpvGivenName())) {
                nome = residente.getCpvGivenName().concat(" ");
              }
              if (LabelFdCUtil.checkIfNotNull(residente.getCpvFamilyName())) {
                nome = nome.concat(residente.getCpvFamilyName());
              }

              if (LabelFdCUtil.checkIfNotNull(residente.getCpvDateOfBirth())) {
                data = residente.getCpvDateOfBirth();
              }
            }

            NotEmptyLabel nomeCognome = new NotEmptyLabel("nomeCognome", nome);
            item.addOrReplace(nomeCognome);

            LocalDateLabel dataNascita = new LocalDateLabel("dataNascita", data);
            item.addOrReplace(dataNascita);

            int calcoloEta = LocalDateUtil.getMesiEta(data);
            String etaFiglio = "";
            String mesi = "";
            if (calcoloEta < 1) {
              etaFiglio = calcoloEta + " ";
              mesi = getString("BiblioMinoriPanel.mese");
            } else if (calcoloEta < 12) {
              etaFiglio = calcoloEta + " ";
              mesi = getString("BiblioMinoriPanel.mesi");
            } else if (calcoloEta < 24) {
              etaFiglio = calcoloEta / 12 + " ";
              mesi = getString("BiblioMinoriPanel.anno");
            } else {
              etaFiglio = calcoloEta / 12 + " ";
              mesi = getString("BiblioMinoriPanel.anni");
            }
            NotEmptyLabel eta = new NotEmptyLabel("eta", etaFiglio);
            item.addOrReplace(eta);
            NotEmptyLabel mesiAnni = new NotEmptyLabel("mesiAnni", mesi);
            item.addOrReplace(mesiAnni);

            String iconaCssFunzione = "";
            String testo = "";
            Utente utenteSebina = null;

            try {
              List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente>
                  listaFindByDocIdentita =
                      ServiceLocator.getInstance()
                          .getServiziBiblioteche()
                          .getUtenteByCf(bambino.getCodiceFiscale());

              if (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
                  && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita)) {
                String nomeBambinoSpid = "";
                String cognomeBambinoSpid = "";

                if (LabelFdCUtil.checkIfNotNull(bambino.getDatiCittadino())) {
                  if (PageUtil.isStringValid(bambino.getDatiCittadino().getCpvGivenName())) {
                    nomeBambinoSpid = bambino.getDatiCittadino().getCpvGivenName().trim();
                  }
                  if (PageUtil.isStringValid(bambino.getDatiCittadino().getCpvFamilyName())) {
                    cognomeBambinoSpid = bambino.getDatiCittadino().getCpvFamilyName().trim();
                  }
                }

                utenteSebina = listaFindByDocIdentita.get(0);

                String nomeBambinoSebina = utenteSebina.getGivenName().trim();
                String cognomeBambinoSebina = utenteSebina.getSn().trim();

                boolean datiSpidSebinaOk =
                    nomeBambinoSpid.equalsIgnoreCase(nomeBambinoSebina)
                        && cognomeBambinoSpid.equalsIgnoreCase(cognomeBambinoSebina);

                if (!datiSpidSebinaOk) {
                  iconaCssFunzione = "color-blue-sebina col-auto icon-foglio-penna";
                  testo = "BiblioMinoriPanel.iscrivi";
                } else {
                  iconaCssFunzione = "color-blue-sebina col-auto icon-libri";
                  testo = "BiblioMinoriPanel.iscritto";
                }
              } else {
                iconaCssFunzione = "color-blue-sebina col-auto icon-foglio-penna";
                testo = "BiblioMinoriPanel.iscrivi";
              }

              LinkDinamicoLaddaFunzione<Object> btnIscrivizioneOMovimenti =
                  new LinkDinamicoLaddaFunzione<Object>(
                      "btnIscrivizioneOMovimenti",
                      LinkDinamicoFunzioneDataBuilder.getInstance()
                          .setWicketLabelKeyText(testo)
                          .setWicketClassName("BiblioMinoreLandingPage")
                          .setLinkTitleAdditionalText(bambino.getDatiCittadino().getCpvGivenName())
                          .build(),
                      bambino,
                      BiblioMinoriPanel.this,
                      iconaCssFunzione);

              item.addOrReplace(btnIscrivizioneOMovimenti);

              int etaBambino = LocalDateUtil.calcolaEta(data);
              boolean isVisibileBiblioInternet =
                  etaBambino < 14
                      && (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
                          && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita));

              LinkDinamicoLaddaFunzione<Object> btnBiblioInternet =
                  new LinkDinamicoLaddaFunzione<Object>(
                      "btnBiblioInternet",
                      LinkDinamicoFunzioneDataBuilder.getInstance()
                          .setWicketLabelKeyText("BiblioMinoriPanel.biblioInternet")
                          .setWicketClassName("BiblioInternetPage")
                          .setLinkTitleAdditionalText(bambino.getDatiCittadino().getCpvGivenName())
                          .build(),
                      utenteSebina,
                      BiblioMinoriPanel.this,
                      "color-blue-sebina col-auto icon-network",
                      isVisibileBiblioInternet);

              item.addOrReplace(btnBiblioInternet);

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore find bambino Sebina: " + e.getMessage());
            }
          }
        };
    listView.setVisible(checkPresenzaMinori(listaFigliUnder18));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationBiblioMinori", listView);
    paginazioneFascicolo.setVisible(
        checkPresenzaMinori(listaFigliUnder18) && listaFigliUnder18.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (listaFigliUnder18 == null || (listaFigliUnder18 != null && listaFigliUnder18.isEmpty())) {
      warn("Non risultano minori conviventi.");
    }
  }

  private boolean checkPresenzaMinori(List<ComponenteNucleo> listaFigli) {
    return LabelFdCUtil.checkIfNotNull(listaFigli) && !LabelFdCUtil.checkEmptyList(listaFigli);
  }

  private AttributeAppender getCssIconaMaschioFemmina(ComponenteNucleo bambino) {
    String css = ICON_OMINO;

    return new AttributeAppender("class", " " + css);
  }
}
