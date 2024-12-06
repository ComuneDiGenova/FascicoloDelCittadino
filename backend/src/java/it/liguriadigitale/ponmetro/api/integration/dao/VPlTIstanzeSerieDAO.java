package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VPlTIstanzeSerie
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-08-10T12:52:52.342
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeSerie;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VPlTIstanzeSerieDAO extends AbstractSearchDAO {

  private Boolean mettiliInAnd;
  private Boolean wantEqual;
  private List<String> serie;
  private List<BigDecimal> articoli;
  private String codiceHermes;

  public VPlTIstanzeSerieDAO(
      Boolean mettiliInAnd,
      Boolean wantEqual,
      List<String> serie,
      List<BigDecimal> articoli,
      String codiceHermes) {
    super();
    this.mettiliInAnd = mettiliInAnd;
    this.wantEqual = wantEqual;
    this.serie = serie;
    this.articoli = articoli;
    this.codiceHermes = codiceHermes;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    log.error("VPlTIstanzeSerieDAO getSqlRetrieveObjectByWhere: ");
    String sql = "SELECT * ";

    sql += " FROM LDFDCADM.V_PL_T_ISTANZE_SERIE " + " WHERE 1=1 ";
    sql += getCommonFilter();
    log.error("Query generata: " + sql);
    return sql;
  }

  private String getCommonFilter() {
    String sql = "";
    String andOr = " AND ";
    String stringEqual = " = ";
    // if (!wantEqual) {
    // stringEqual = " <> ";
    // }
    if (serie != null && !serie.isEmpty()) {
      // if (!wantEqual) {
      // stringEqual = " <> ";
      // }
      sql += andOr + " ( SERIE " + stringEqual + " ? ";
      for (int i = 1; i < serie.size(); i++) {
        sql += " OR SERIE " + stringEqual + " ? ";
      }
      if (!mettiliInAnd) andOr = " OR ";
      sql += ")";
    }
    if (articoli != null && !articoli.isEmpty()) {
      sql += andOr + " ( ( ARTICOLO " + stringEqual + " ? ";
      if (!wantEqual) {
        sql += " AND TIPO_RELAZIONE_SERIE = 2 ";
      } else {
        sql += " AND TIPO_RELAZIONE_SERIE = 1 ";
      }

      sql += " ) ";
      for (int i = 1; i < articoli.size(); i++) {
        sql += " OR ( ARTICOLO " + stringEqual + " ? ";
        if (!wantEqual) {
          sql += " AND TIPO_RELAZIONE_SERIE = 2 ";
        } else {
          sql += " AND TIPO_RELAZIONE_SERIE = 1 ";
        }

        sql += " ) ";
      }
      if (!mettiliInAnd) andOr = " OR ";
      sql += ")";
    }
    // wantEqual non per il codiceHermes
    if (codiceHermes != null) {
      sql += andOr + " CODICE_HERMES = ? ";
    }

    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;

    if (serie != null && !serie.isEmpty()) {
      st.setString(index++, serie.get(0));
      for (int i = 1; i < serie.size(); i++) {
        st.setString(index++, serie.get(i));
      }
    }
    if (articoli != null && !articoli.isEmpty()) {
      st.setBigDecimal(index++, articoli.get(0));
      for (int i = 1; i < articoli.size(); i++) {
        st.setBigDecimal(index++, articoli.get(i));
      }
    }
    if (codiceHermes != null) {
      st.setString(index++, codiceHermes);
      log.error("Query generata codiceHermes [" + index + "]: " + codiceHermes);
    }
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VPlTIstanzeSerie obj = new VPlTIstanzeSerie();

    obj.setCodiceHermes(r.getString("CODICE_HERMES"));
    obj.setTipologia(r.getString("TIPOLOGIA"));
    obj.setRiferimentoLegge(r.getString("RIFERIMENTO_LEGGE"));
    obj.setMinDoc(r.getBigDecimal("MIN_DOC"));
    obj.setMaxDoc(r.getBigDecimal("MAX_DOC"));
    obj.setFlgIntegrazioni(r.getString("FLG_INTEGRAZIONI"));
    obj.setTipoRelazioneSerie(r.getBigDecimal("TIPO_RELAZIONE_SERIE"));
    obj.setCodice(r.getString("CODICE"));
    obj.setDescrizioneSerie(r.getString("DESCRIZIONE_SERIE"));
    obj.setTipo(r.getBigDecimal("TIPO"));
    obj.setTipoDescrizione(r.getString("TIPO_DESCRIZIONE"));
    obj.setArticolo(r.getBigDecimal("ARTICOLO"));
    obj.setSerie(r.getString("SERIE"));
    return obj;
  }
}
