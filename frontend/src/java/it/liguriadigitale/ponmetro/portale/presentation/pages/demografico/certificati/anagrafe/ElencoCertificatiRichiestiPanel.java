package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe;

import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoPersona;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class ElencoCertificatiRichiestiPanel extends BasePanel {

  private static final String E_MAIL = "E-MAIL";

  private static final String ON_LINE = "ON-LINE";

  private static final long serialVersionUID = -3021289773196275267L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ElencoCertificatiRichiestiPanel(String id) {
    super(id);
    List<CertificatoPersona> lista =
        ServiceLocator.getInstance()
            .getCertificatiAnagrafe()
            .elencoCertificatiRichiesti(getUtente());
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  public ElencoCertificatiRichiestiPanel(String id, List<CertificatoPersona> lista) {
    this(id);
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<CertificatoPersona> lista = (List<CertificatoPersona>) dati;

    mostraLista(lista);
  }

  private void mostraLista(List<CertificatoPersona> lista) {

    PageableListView<CertificatoPersona> listView =
        new PageableListView<CertificatoPersona>("lista", lista, 4) {

          private static final long serialVersionUID = -593847571381978380L;

          @Override
          protected void populateItem(ListItem<CertificatoPersona> item) {
            CertificatoPersona certificato = item.getModelObject();
            log.debug(
                "certificato= "
                    + certificato.getIdentificativoRichiesta()
                    + "--data: "
                    + certificato.getDataRichiesta());
            String cfIntestatario = "";
            String nomeIntestatario = "";
            String cognomeIntestatario = "";
            String cfRichiedente = "";
            String nomeRichiedente = "";
            String cognomeRichiedente = "";

            // intestazione
            item.addOrReplace(new NotEmptyLabel("stato", certificato.getStatoRichiesta()));
            // valori
            item.add(
                new CardLabel<>(
                    "dataRichiesta",
                    certificato.getDataRichiesta(),
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "tipo",
                    certificato.getTipoCertificato(),
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "idRichiesta",
                    certificato.getIdentificativoRichiesta(),
                    ElencoCertificatiRichiestiPanel.this));
            if (certificato.getEstremiIntestatario() != null) {
              cfIntestatario = certificato.getEstremiIntestatario().getCodiceFiscale();
              nomeIntestatario = certificato.getEstremiIntestatario().getNome();
              cognomeIntestatario = certificato.getEstremiIntestatario().getCognome();
            }
            if (certificato.getEstremiRichiedente() != null) {
              cfRichiedente = certificato.getEstremiRichiedente().getCodiceFiscale();
              nomeRichiedente = certificato.getEstremiRichiedente().getNome();
              cognomeRichiedente = certificato.getEstremiRichiedente().getCognome();
            }
            item.add(
                new CardLabel<>(
                    "cfIntestatario", cfIntestatario, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "nomeIntestatario", nomeIntestatario, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    cognomeIntestatario,
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cfRichiedente", cfRichiedente, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "nomeRichiedente", nomeRichiedente, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cognomeRichiedente",
                    cognomeRichiedente,
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "tipoConsegnaScelta",
                    decodificaTipoConsegna(certificato),
                    ElencoCertificatiRichiestiPanel.this));
          }
        };
    listView.setRenderBodyOnly(true);
    listView.setVisible(!lista.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(lista.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (lista.isEmpty()) {
      warn("Nessuna richiesta di certificati trovata");
    }
  }

  private String decodificaTipoConsegna(CertificatoPersona certificato) {
    return null;
    // return StringUtils.isBlank(certificato.getTipoConsegnaScelta())
    // || certificato.getTipoConsegnaScelta().equals("0") ? ON_LINE :
    // E_MAIL;
  }
}
