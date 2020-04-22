package homefinance.common;

import homefinance.common.exception.PageNotFoundException;
import homefinance.common.util.PathSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public abstract class CommonController<T> {

  protected PathSelector pathSelector;
  public static final String REDIRECT = "redirect:";
  public static final String FLASH_MODEL_ATTRIBUTE_NAME =
      CommonController.class.getName() + '.' + "flashModel";

  public static String getRedirectURL(String URL) {
    return "redirect:" + URL;
  }

  @Autowired
  public void setPathSelector(PathSelector pathSelector) {
    this.pathSelector = pathSelector;
  }

  public void test(T t) {
    if (t == null) {
      throw new PageNotFoundException();
    }
  }

  // TODO: 20.04.2020 RMACARI: move to a separate class? ex: FlashModel
  public void addModelToRedirectAttributes(Model model, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, model);
  }

  public boolean isRedirectAndFlashModelMerged(Model model) {
    Object flashModel = model.asMap().get(FLASH_MODEL_ATTRIBUTE_NAME);
    if (flashModel == null) {
      return false;
    } else {
      model.mergeAttributes(((Model) flashModel).asMap());
      return true;
    }
  }
}
