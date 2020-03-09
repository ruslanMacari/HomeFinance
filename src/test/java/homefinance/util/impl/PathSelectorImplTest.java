package homefinance.util.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import homefinance.web.exceptions.DuplicateFieldsException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

public class PathSelectorImplTest {

  private PathSelectorImpl pathSelector;

  @Before
  public void setUp() {
    this.pathSelector = new PathSelectorImpl();
  }

  @Test
  public void testSetActionOk() {
    assertEquals(this.pathSelector.setActionOk(() -> System.out.println("test")), this.pathSelector);
  }

  @Test
  public void testSetActionError() {
    assertEquals(this.pathSelector.setActionError(() -> System.out.println("test")),
        this.pathSelector);
  }

  @Test
  public void testSetPaths() {
    assertEquals(this.pathSelector.setPaths("test1", "test2"), this.pathSelector);
  }

  @Test
  public void testSetErrors() {
    assertEquals(this.pathSelector.setErrors(mock(Errors.class)), this.pathSelector);
  }

  @Test
  public void testGetPath() {
    String pathOk = "ok";
    String pathError = "error";
    this.pathSelector.setActionOk(() -> System.out.println("ok")).setPaths(pathOk, pathError);
    assertEquals(this.pathSelector.getPath(), pathOk);
    this.pathSelector.setActionOk(() -> {
      throw mock(DuplicateFieldsException.class);
    });
    this.pathSelector.setErrors(mock(Errors.class)).setActionError(() -> System.out.println("error"));
    assertEquals(this.pathSelector.getPath(), pathError);
  }

}
