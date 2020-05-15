package homefinance.common;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.exception.DuplicateFieldsException;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class HandleDuplicationExceptionAspectTest {

  private HandleDuplicationExceptionAspect aspect;
  @Mock
  private RequestBuffer requestBufferMock;
  @Mock
  private ProceedingJoinPoint joinPointMock;
  @Mock
  private Model modelMock;
  @Mock
  private RedirectAttributes redirectAttributesMock;
  @Mock
  private BindingResult bindingResultMock;

  @Before
  public void setUp() {
    this.aspect = new HandleDuplicationExceptionAspect(this.requestBufferMock);
  }

  @Test
  public void handleDuplication_givenNoExceptionThrown_returnProceed() throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willReturn("/test");
    // when:
    Object actual = this.aspect.handleDuplication(this.joinPointMock);
    // then:
    then(actual).isEqualTo("/test");
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndMethodHasNoArgs_expectIllegalArgumentException()
      throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(DuplicateFieldsException.class);
    // when:
    Throwable throwable = catchThrowable(() -> this.aspect.handleDuplication(this.joinPointMock));
    // then:
    then(throwable).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("method must have arguments");
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndMethodHasNoModel_expectIllegalArgumentException()
      throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(DuplicateFieldsException.class);
    given(this.joinPointMock.getArgs())
        .willReturn(this.getArray(this.bindingResultMock, this.redirectAttributesMock));
    // when:
    Throwable throwable = catchThrowable(() -> this.aspect.handleDuplication(this.joinPointMock));
    // then:
    then(throwable).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(String.valueOf(Model.class));
  }

  private Object[] getArray(Object ... objects) {
    return objects;
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndMethodHasNoErrors_expectIllegalArgumentException()
      throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(DuplicateFieldsException.class);
    given(this.joinPointMock.getArgs())
        .willReturn(this.getArray(this.modelMock, this.redirectAttributesMock));
    // when:
    Throwable throwable = catchThrowable(() -> this.aspect.handleDuplication(this.joinPointMock));
    // then:
    then(throwable).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(String.valueOf(BindingResult.class));
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndMethodHasNoRedirectAttributes_expectIllegalArgumentException()
      throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(DuplicateFieldsException.class);
    given(this.joinPointMock.getArgs())
        .willReturn(this.getArray(this.modelMock, this.bindingResultMock));
    // when:
    Throwable throwable = catchThrowable(() -> this.aspect.handleDuplication(this.joinPointMock));
    // then:
    then(throwable).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(String.valueOf(RedirectAttributes.class));
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndUrlSetInAnnotation_returnRedirectUrlFromAnnotation() throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(new DuplicateFieldsException("field", "errorCode"));
    given(this.joinPointMock.getArgs())
        .willReturn(this.getArray(this.modelMock, this.bindingResultMock,
            this.redirectAttributesMock));
    this.mockJoinPointMethod("testMethod");
    // when:
    Object actual = this.aspect.handleDuplication(this.joinPointMock);
    // then:
    then(actual).isEqualTo("redirect:/urlAnnotation");
    BDDMockito.then(this.redirectAttributesMock).should()
        .addFlashAttribute(CommonController.FLASH_MODEL_ATTRIBUTE_NAME, this.modelMock);
    BDDMockito.then(this.bindingResultMock).should().rejectValue("field", "errorCode");
  }

  private void mockJoinPointMethod(String methodName) throws NoSuchMethodException {
    MethodSignature signature = mock(MethodSignature.class);
    Method method = this.getClass().getMethod(methodName, BindingResult.class,
        RedirectAttributes.class, Model.class);
    given(signature.getMethod()).willReturn(method);
    given(this.joinPointMock.getSignature()).willReturn(signature);
  }

  @HandleDuplicationException(url = "/urlAnnotation")
  public String testMethod(BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    return "test";
  }

  @Test
  public void handleDuplication_givenDuplicateExceptionThrownAndUrlNotSetInAnnotation_returnRedirectUrlFromRequestBuffer() throws Throwable {
    // given:
    given(this.joinPointMock.proceed()).willThrow(DuplicateFieldsException.class);
    given(this.joinPointMock.getArgs())
        .willReturn(this.getArray(this.modelMock, this.bindingResultMock,
            this.redirectAttributesMock));
    given(this.requestBufferMock.getUrl()).willReturn("/urlFromRequestBuffer");
    this.mockJoinPointMethod("testMethodUrlMissing");
    // when:
    Object actual = this.aspect.handleDuplication(this.joinPointMock);
    // then:
    then(actual).isEqualTo("redirect:/urlFromRequestBuffer");
  }

  @HandleDuplicationException()
  public String testMethodUrlMissing(BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
    return "test";
  }
}