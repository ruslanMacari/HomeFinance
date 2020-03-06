package homefinance.web.exceptionshandle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class GlobalAdviceTest {

  private GlobalAdvice advice;
  private Exception e;

  @Before
  public void init() {
    advice = new GlobalAdvice();
    e = mock(Exception.class);
  }

  @Test
  public void testException() {
    // TODO: 14.09.2019 fix test
    //assertEquals(advice.exception(e, mock(Model.class)), "exception");
  }

  @Test
  public void testNoHandlerFound() {
    assertEquals(advice.noHandlerFound(e), "resource-not-found");
  }

}
