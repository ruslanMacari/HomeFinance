package homefinance.common.exception.exceptionshandle;

import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import homefinance.common.beans.JsoupAdapter;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler advice;
  @Mock
  private JsoupAdapter jsoupMock;
  @Mock
  private Document documentMock;
  @Mock
  private Model modelMock;
  @Mock
  private HttpServletRequest requestMock;

  @BeforeEach
  public void init() {
    advice = new GlobalExceptionHandler(jsoupMock);
  }

  @Test
  public void exception_internalServerError() {
    // given:
    String message = "exception message";
    given(jsoupMock.parse(message)).willReturn(documentMock);
    String error = "error";
    given(documentMock.text()).willReturn(error);
    // when:
    String actual = advice.exception(requestMock, new RuntimeException(message), modelMock);
    // then:
    then(actual).isEqualTo("exception");
    BDDMockito.then(modelMock).should().addAttribute("errorMessage", error);
    BDDMockito.then(modelMock).should().addAttribute("statusCode", INTERNAL_SERVER_ERROR);
  }

  @Test
  public void exception_status403() {
    // given:
    String message = "exception message";
    given(jsoupMock.parse(message)).willReturn(documentMock);
    given(requestMock.getAttribute("jakarta.servlet.error.status_code")).willReturn(403);
    // when:
    String actual = advice.exception(requestMock, new RuntimeException(message), modelMock);
    // then:
    then(actual).isEqualTo("exception");
    BDDMockito.then(modelMock).should().addAttribute("statusCode", HttpStatus.valueOf(403));
  }

  @Test
  public void testNoHandlerFound() {
    assertEquals(advice.noHandlerFound(new RuntimeException()), "resource-not-found");
  }

}
