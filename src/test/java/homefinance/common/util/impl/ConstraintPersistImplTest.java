package homefinance.common.util.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import homefinance.common.entity.ConstraintEntity;
import homefinance.common.exception.DuplicateFieldsException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

public class ConstraintPersistImplTest {

  private ConstraintPersistImpl instance = new ConstraintPersistImpl();
  private ConstraintEntity entity = mock(ConstraintEntity.class);
  private String errorMsg = "exception";

  @Test
  public void testAdd() {
    Map<String, String> map = new HashMap<>();
    map.put(this.errorMsg, this.errorMsg);
    ConstraintEntity result = this.instance.add(this::getEntityOk, map);
    assertThat(result, is(this.entity));
    try {
      this.instance.add(this::getEntityError, map);
      fail("DuplicateFieldsException must be thrown");
    } catch (DuplicateFieldsException duplicateEx) {
      assertThat(duplicateEx.getErrorCode(), is(this.errorMsg));
      try {
        map.clear();
        this.instance.add(this::getEntityError, map);
        fail("DataIntegrityViolationException must be thrown");
      } catch (DataIntegrityViolationException e) {
        String msg = "Test Error";
        assertTrue(e.getMessage().contains(msg));
      }
    }
  }

  private ConstraintEntity getEntityOk() {
    return this.entity;
  }

  private ConstraintEntity getEntityError() {
    throw new DataIntegrityViolationException("Test Error", new Exception(this.errorMsg));
  }

}
