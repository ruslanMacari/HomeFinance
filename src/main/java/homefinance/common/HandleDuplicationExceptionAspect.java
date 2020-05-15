package homefinance.common;

import homefinance.common.exception.DuplicateFieldsException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Aspect
@Component
public class HandleDuplicationExceptionAspect {

  private static final Logger logger = LoggerFactory
      .getLogger(HandleDuplicationExceptionAspect.class);

  private final RequestBuffer requestBuffer;

  @Autowired
  public HandleDuplicationExceptionAspect(RequestBuffer requestBuffer) {
    this.requestBuffer = requestBuffer;
  }

  @Around("@annotation(homefinance.common.HandleDuplicationException)")
  public Object handleDuplication(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.debug("handleDuplication()");
    try {
      return joinPoint.proceed();
    } catch (DuplicateFieldsException e) {
      List<Object> args = this.getArgs(joinPoint);
      this.registerError(e, args);
      this.addModelToRedirectAttributes(args);
      return this.getRedirectUrl(joinPoint);
    }
  }

  private List<Object> getArgs(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    Assert.notNull(args, "method must have arguments");
    return Arrays.stream(args)
        .collect(Collectors.toList());
  }

  private void registerError(DuplicateFieldsException e, List<Object> args) {
    BindingResult errors = this
        .getArgument(args, arg -> arg instanceof BindingResult, BindingResult.class);
    errors.rejectValue(e.getField(), e.getErrorCode());
  }

  private <T> T getArgument(List<Object> args, Predicate<? super Object> condition, Class<T> type) {
    T arg = type.cast(args.stream()
        .filter(condition)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Missing argument of type: " + type)));
    this.secureFromReFinding(args, arg);
    return arg;
  }

  private <T> void secureFromReFinding(List<Object> args, T arg) {
    args.remove(arg);
  }

  private void addModelToRedirectAttributes(List<Object> args) {
    RedirectAttributes redirectAttributes = this
        .getArgument(args, arg -> arg instanceof RedirectAttributes, RedirectAttributes.class);
    Model model = this.getArgument(args, arg -> arg instanceof Model, Model.class);
    CommonController.addModelToRedirectAttributes(model, redirectAttributes);
  }

  private String getRedirectUrl(ProceedingJoinPoint joinPoint) {
    String url = this.getAnnotationUrl(joinPoint);
    return url.isEmpty() ? CommonController.getRedirectURL(this.requestBuffer.getUrl())
        : CommonController.getRedirectURL(url);
  }

  private String getAnnotationUrl(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    HandleDuplicationException annotation = signature.getMethod()
        .getAnnotation(HandleDuplicationException.class);
    return annotation.url();
  }

}
