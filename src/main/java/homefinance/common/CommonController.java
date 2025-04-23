package homefinance.common;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public abstract class CommonController<T> {

  public static final String REDIRECT = "redirect:";
  public static final String FLASH_MODEL_ATTRIBUTE_NAME = CommonController.class.getName() + '.' + "flashModel";

  public static String getRedirectURL(String url) {
    return REDIRECT + url;
  }

  // TODO: 20.04.2020 RMACARI: move to a separate class? ex: FlashModel
  public static void addModelToRedirectAttributes(Model model,
      RedirectAttributes redirectAttributes) {
    log.debug("addModelToRedirectAttributes invoked");
    redirectAttributes.addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, model);
  }

  public static boolean isRedirectAndFlashModelMerged(Model model) {
    Object redirectFlashModelAttribute = model.asMap().get(FLASH_MODEL_ATTRIBUTE_NAME);
    if (redirectFlashModelAttribute == null) {
      return false;
    }
    model.mergeAttributes(((Model) redirectFlashModelAttribute).asMap());
    return true;
  }

}
