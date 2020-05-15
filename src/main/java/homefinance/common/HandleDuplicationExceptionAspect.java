package homefinance.common;

import homefinance.common.exception.DuplicateFieldsException;
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
  public HandleDuplicationExceptionAspect(
      RequestBuffer requestBuffer) {
    this.requestBuffer = requestBuffer;
  }

  @Around("@annotation(homefinance.common.HandleDuplicationException)")
  public Object handleDuplication(ProceedingJoinPoint joinPoint) throws Throwable {

    try {
      return joinPoint.proceed();
    } catch (DuplicateFieldsException e) {
      Object[] args = joinPoint.getArgs();
      Assert.notNull(args, "method must have arguments");
      RedirectAttributes redirectAttributes = null;
      Model model = null;
      BindingResult bindingResult = null;
      for (Object arg : args) {
        if (arg instanceof BindingResult) {
          bindingResult = (BindingResult) arg;
        } else if (arg instanceof RedirectAttributes) {
          redirectAttributes = (RedirectAttributes) arg;
        } else if (arg instanceof Model) {
          model = (Model) arg;
        }
      }
      Assert.notNull(bindingResult, "Missing argument of type: " + BindingResult.class);
      Assert.notNull(model, "Missing argument of type: " + Model.class);
      Assert.notNull(redirectAttributes, "Missing argument of type: " + RedirectAttributes.class);
      bindingResult.rejectValue(e.getField(), e.getErrorCode());
      CommonController.addModelToRedirectAttributes(model, redirectAttributes);
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      HandleDuplicationException annotation = signature.getMethod()
          .getAnnotation(HandleDuplicationException.class);
      if (annotation.url().isEmpty()) {
        return CommonController.getRedirectURL(this.requestBuffer.getUrl());
      }
      return CommonController.getRedirectURL(annotation.url());

    }
  }

}
