package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunzioniInEvidenzaDAO extends AbstractSearchDAO {

  private FunzioneChiaveValore funzioneChiaveValore;

  public FunzioniInEvidenzaDAO(FunzioneChiaveValore funzioneChiaveValore) {
    super();
    this.funzioneChiaveValore = funzioneChiaveValore;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        "SELECT VCF.* , KV.* "
            + "FROM CFG_KEY_VALUE KV INNER JOIN V_CFG_T_CAT_FUNZ VCF ON KV.CFG_VALUE = VCF.CLASSE_PAGINA_STD "
            + "WHERE 1=1";
    if (funzioneChiaveValore != null
        && funzioneChiaveValore.getChiaveValore() != null
        && funzioneChiaveValore.getChiaveValore().getChiave() != null) {
      sql += " AND KV.CFG_KEY LIKE ? ";
    }

    log.debug("CP sql query in evidenza = " + sql);

    return sql;
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    FunzioneChiaveValore obj = new FunzioneChiaveValore();

    ChiaveValore chiaveValore = new ChiaveValore();
    chiaveValore.setChiave(r.getString("CFG_KEY"));
    chiaveValore.setValore(r.getString("CLASSE_PAGINA_STD"));
    obj.setChiaveValore(chiaveValore);

    FunzioniDisponibili funzioniDisponibili = new FunzioniDisponibili();
    funzioniDisponibili.setIdSez(r.getLong("ID_SEZ"));
    if (r.wasNull()) {
      funzioniDisponibili.setIdSez(null);
    }

    funzioniDisponibili.setOrdinamentoS(r.getLong("ORDINAMENTO_S"));
    if (r.wasNull()) {
      funzioniDisponibili.setOrdinamentoS(null);
    }

    funzioniDisponibili.setOrdinamentoC(r.getLong("ORDINAMENTO_C"));
    if (r.wasNull()) {
      funzioniDisponibili.setOrdinamentoC(null);
    }

    funzioniDisponibili.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    funzioniDisponibili.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    funzioniDisponibili.setUriSez(r.getString("URI_SEZ"));
    funzioniDisponibili.setFlagAbilitazioneSez(r.getBoolean("FLAG_ABILITAZIONE_SEZ"));
    funzioniDisponibili.setIdComp(r.getLong("ID_COMP"));
    if (r.wasNull()) {
      funzioniDisponibili.setIdComp(null);
    }

    funzioniDisponibili.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    funzioniDisponibili.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    funzioniDisponibili.setUriComp(r.getString("URI_COMP"));
    funzioniDisponibili.setFlagAbilitazioneComp(r.getBoolean("FLAG_ABILITAZIONE_COMP"));
    funzioniDisponibili.setIdFunz(r.getLong("ID_FUNZ"));
    if (r.wasNull()) {
      funzioniDisponibili.setIdFunz(null);
    }

    funzioniDisponibili.setDenominazioneFunz(r.getString("DENOMINAZIONE_FUNZ"));
    funzioniDisponibili.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    funzioniDisponibili.setWicketLabelIdStd(r.getString("WICKET_LABEL_ID_STD"));
    funzioniDisponibili.setWicketLabelIdAlt(r.getString("WICKET_LABEL_ID_ALT"));
    funzioniDisponibili.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    funzioniDisponibili.setClassePaginaAlt(r.getString("CLASSE_PAGINA_ALT"));
    funzioniDisponibili.setWicketTitleStd(r.getString("WICKET_TITLE_STD"));
    funzioniDisponibili.setWicketTitleAlt(r.getString("WICKET_TITLE_ALT"));
    funzioniDisponibili.setFlagAbilitazioneFunz(r.getBoolean("FLAG_ABILITAZIONE_FUNZ"));
    funzioniDisponibili.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    funzioniDisponibili.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    funzioniDisponibili.setFlagNonResidente(r.getBoolean("FLAG_NON_RESIDENTE"));
    funzioniDisponibili.setIconaCss(r.getString("ICONA_CSS"));

    obj.setFunzioniDisponibili(funzioniDisponibili);

    return obj;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (funzioneChiaveValore.getChiaveValore().getChiave() != null) {
      st.setString(index++, funzioneChiaveValore.getChiaveValore().getChiave());
    }
  }
}
