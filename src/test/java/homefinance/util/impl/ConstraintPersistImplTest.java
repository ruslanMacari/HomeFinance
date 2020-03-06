package homefinance.util.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import homefinance.entity.ConstraintEntity;
import homefinance.web.exceptions.DuplicateFieldsException;
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
    assertEquals(result, this.entity);
    try {
      this.instance.add(this::getEntityError, map);
      fail("DuplicateFieldsException must be thrown");
    } catch (DuplicateFieldsException duplicateEx) {
      assertEquals(duplicateEx.getErrorCode(), this.errorMsg);
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
