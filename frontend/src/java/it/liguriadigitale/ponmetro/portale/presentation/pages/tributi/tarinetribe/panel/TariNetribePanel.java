package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.TariNetribeDettagliPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.PagamentoAvviso;
import it.liguriadigitale.ponmetro.tarinetribe.model.PagamentoAvviso.IstanzaEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import it.liguriadigitale.ponmetro.tarinetribe.model.TestataAvviso;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class TariNetribePanel extends BasePanel {

  private static final long serialVersionUID = -4290451829762921L;

  protected PaginazioneFascicoloPanel pagination;

  public TariNetribePanel(String panelId, List<TARIResult> listaTariAnno) {
    super(panelId);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(listaTariAnno);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<TARIResult> listaTariAnno = (List<TARIResult>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkTari(listaTariAnno));
    addOrReplace(listaVuota);

    PageableListView<TARIResult> listViewDocumenti =
        new PageableListView<TARIResult>("documenti", listaTariAnno, 4) {

          private static final long serialVersionUID = -1986540581454322267L;

          @Override
          protected void populateItem(ListItem<TARIResult> itemDocumento) {
            final TARIResult documento = itemDocumento.getModelObject();

            NotEmptyLabel tipologia = new NotEmptyLabel("tipologia", documento.getTipologia());
            tipologia.setVisible(PageUtil.isStringValid(documento.getTipologia()));
            itemDocumento.addOrReplace(tipologia);

            NotEmptyLabel tipologiaUtenza =
                new NotEmptyLabel("tipologiaUtenza", documento.getTipologiaUtenza());
            tipologiaUtenza.setVisible(PageUtil.isStringValid(documento.getTipologiaUtenza()));
            itemDocumento.addOrReplace(tipologiaUtenza);

            TestataAvviso testataAvviso = documento.getTestataAvviso();

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "numAvviso", testataAvviso.getIdAvviso(), TariNetribePanel.this));

            PagamentoAvviso datiPagamento = documento.getDatiPagamento();

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "totaleImporto", datiPagamento.getTotaleImporto(), TariNetribePanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "totalePagamenti", datiPagamento.getTotalePagamenti(), TariNetribePanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("saldo", datiPagamento.getSaldo(), TariNetribePanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("istanza", datiPagamento.getIstanza(), TariNetribePanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "annoRiferimento", datiPagamento.getAnnoRiferimento(), TariNetribePanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "annoConguaglio", datiPagamento.getAnnoConguaglio(), TariNetribePanel.this));

            itemDocumento.addOrReplace(creaBtnDettagli(documento));

            boolean rimborsoRichiedibile =
                LabelFdCUtil.checkIfNotNull(datiPagamento.getIstanza())
                    && datiPagamento
                        .getIstanza()
                        .equalsIgnoreCase(IstanzaEnum.DA_RIMBORSARE.value());

            LinkDinamicoLaddaFunzione<TARIResult> btnRimborso =
                new LinkDinamicoLaddaFunzione<>(
                    "btnRimborso",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("TariNetribePanel.btnRimborso")
                        .setWicketClassName("RichiestaRimborsoTariNetribePage")
                        .build(),
                    documento,
                    TariNetribePanel.this,
                    "color-green col-auto icon-cassonetto",
                    rimborsoRichiedibile);

            itemDocumento.addOrReplace(btnRimborso);
          }
        };

    listViewDocumenti.setVisible(checkTari(listaTariAnno));

    addOrReplace(listViewDocumenti);

    pagination = new PaginazioneFascicoloPanel("pagination", listViewDocumenti);
    pagination.setVisible(checkTari(listaTariAnno) && listaTariAnno.size() > 4);
    addOrReplace(pagination);
  }

  private boolean checkTari(List<TARIResult> listaTariAnno) {
    return LabelFdCUtil.checkIfNotNull(listaTariAnno)
        && !LabelFdCUtil.checkEmptyList(listaTariAnno);
  }

  /*
  	private LaddaAjaxLink<Object> creaBottoneIndietro(Intestatario intestatario) {
  		LaddaAjaxLink<Object> indietro = new LaddaAjaxLink<Object>(
  				"indietro",
  				Type.Primary) {

  			private static final long serialVersionUID = 4638203351758954575L;

  			@Override
  			public void onClick(AjaxRequestTarget target) {
  				target.add(RichiestaCertificatoAnagraficoPageStep2.this);
  				log.debug("click indietro " + intestatario);
  				setResponsePage(new RichiestaCertificatoAnagraficoPage(intestatario));
  			}
  		};
  		indietro.setLabel(
  				Model.of(Application.get().getResourceSettings().getLocalizer()
  						.getString("RichiestaCertificatoAnagraficoPage.indietro",
  								RichiestaCertificatoAnagraficoPageStep2.this)));

  		return indietro;

  	}
  */

  private LaddaAjaxLink<Object> creaBtnDettagli(TARIResult documento) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = 4140121548889475031L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(TariNetribePanel.this);

            setResponsePage(new TariNetribeDettagliPage(documento));
          }
        };

    btnDettagli.setLabel(Model.of(getString("TariNetribePanel.dettagli")));

    return btnDettagli;
  }
}
