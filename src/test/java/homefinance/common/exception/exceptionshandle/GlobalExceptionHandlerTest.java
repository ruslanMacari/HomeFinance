package homefinance.common.exception.exceptionshandle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import homefinance.common.RequestBuffer;
import org.junit.Before;
import org.junit.Test;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler advice;
  private Exception e;

  @Before
  public void init() {
    advice = new GlobalExceptionHandler(mock(RequestBuffer.class));
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
