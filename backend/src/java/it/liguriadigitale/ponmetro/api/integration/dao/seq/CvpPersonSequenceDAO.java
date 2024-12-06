package it.liguriadigitale.ponmetro.api.integration.dao.seq;

import it.liguriadigitale.framework.integration.dao.SequenceDAO;

public class CvpPersonSequenceDAO extends SequenceDAO {

  private static final String CPV_PERSON_SEQ = "CPV_PERSON_SEQ";

  public CvpPersonSequenceDAO() {
    super(CPV_PERSON_SEQ);
  }
}
