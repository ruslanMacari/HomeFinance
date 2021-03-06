package homefinance.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public abstract class CommonController<T> {

  public static final String REDIRECT = "redirect:";
  public static final String FLASH_MODEL_ATTRIBUTE_NAME = CommonController.class.getName() + '.' + "flashModel";
  private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

  public static String getRedirectURL(String URL) {
    return REDIRECT + URL;
  }

  // TODO: 20.04.2020 RMACARI: move to a separate class? ex: FlashModel
  public static void addModelToRedirectAttributes(Model model,
      RedirectAttributes redirectAttributes) {
    logger.debug("addModelToRedirectAttributes invoked");
    redirectAttributes.addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, model);
  }

  public static boolean isRedirectAndFlashModelMerged(Model model) {
    Object flashModel = model.asMap().get(FLASH_MODEL_ATTRIBUTE_NAME);
    if (flashModel == null) {
      return false;
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
      return true;
    }
  }

}
