package homefinance.money.currency;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.RequestBuffer;
import homefinance.money.currency.dto.CurrencyDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class CurrencyControllerTest {

  private CurrencyController controller;
  @Mock
  private Model modelMock;
  @Mock
  private CurrencyDto currencyDtoMock;
  @Mock
  private BindingResult errorsMock;
  @Mock
  private CurrencyFacade currencyFacadeMock;
  @Mock
  private RedirectAttributes redirectAttributesMock;
  @Mock
  private RequestBuffer requestBufferMock;

  @BeforeEach
  public void setUp() {
    this.controller = new CurrencyController(this.currencyFacadeMock, this.requestBufferMock);
  }

  @Test
  public void list_shouldReturnListTemplateAndAddCurrenciesAttributeWithFilledCurrencyDtoList() {
    //given
    List<CurrencyDto> currencyDtoList = Arrays.asList(mock(CurrencyDto.class), null);
    given(this.currencyFacadeMock.getAllCurrenciesDto()).willReturn(currencyDtoList);
    //when
    String actual = this.controller.list(this.modelMock);
    //then
    then(actual).isEqualTo("currencies/list");
    BDDMockito.then(this.modelMock).should().addAttribute("currencies", currencyDtoList);
  }

  @Test
  public void openNew_givenModelHasNoFlashModel_shouldReturnNewTemplateAndAddCurrencyAttributeWithNewCurrencyDto() {
    // given:
    Map<String, Object> map = new HashMap<>();
    given(this.modelMock.asMap()).willReturn(map);
    // when:
    String actual = this.controller.openNew(this.modelMock);
    // then:
    then(actual).isEqualTo("currencies/new");
    BDDMockito.then(this.modelMock)
        .should().addAttribute(BDDMockito.eq("currency"), BDDMockito.refEq(new CurrencyDto()));
  }

  @Test
  public void openNew_givenModelHasFlashModel_shouldReturnNewTemplateAndMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(this.modelMock.asMap()).willReturn(map);
    // when:
    String actual = this.controller.openNew(this.modelMock);
    // then:
    then(actual).isEqualTo("currencies/new");
    BDDMockito.then(this.modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void saveNew_givenHasValidationErrors_shouldReturnRedirectToNewUrl() {
    // given:
    given(this.errorsMock.hasErrors()).willReturn(true);
    // when:
    String actual = this.controller
        .saveNew(this.currencyDtoMock, this.errorsMock, this.redirectAttributesMock,
            this.modelMock);
    // then:
    then(actual).isEqualTo("redirect:/currencies/new");
    BDDMockito.then(this.redirectAttributesMock).should()
        .addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, this.modelMock);
  }

  @Test
  public void saveNew_givenHasNoValidationErrors_shouldReturnRedirectUrl() {
    given(this.errorsMock.hasErrors()).willReturn(false);
    // when:
    String actual = this.controller
        .saveNew(this.currencyDtoMock, this.errorsMock, this.redirectAttributesMock,
            this.modelMock);
    // then:
    then(actual).isEqualTo("redirect:/currencies");
    BDDMockito.then(this.currencyFacadeMock).should().saveNew(this.currencyDtoMock);
  }

  @Test
  public void view_givenModelHasNoFlashModel_thenAddAttribute() {
    // given:
    CurrencyDto currencyDto = mock(CurrencyDto.class);
    given(this.currencyFacadeMock.getCurrencyDtoById(5)).willReturn(currencyDto);
    Map<String, Object> map = new HashMap<>();
    given(this.modelMock.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.modelMock);
    // then:
    then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.modelMock).should().addAttribute("currency", currencyDto);
  }

  @Test
  public void view_givenModelHasFlashModel_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(this.modelMock.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.modelMock);
    // then:
    then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.modelMock).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenValidationHasErrors_thenShouldBeEqualToRedirectCurrenciesView() {
    // given:
    given(this.errorsMock.hasErrors()).willReturn(true);
    given(this.currencyDtoMock.getId()).willReturn(555);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actualResult = this.controller
        .update(this.currencyDtoMock, this.errorsMock, redirectAttributes,
            this.modelMock);
    // then:
    then(actualResult).isEqualTo("redirect:/currencies/555");
    BDDMockito.then(redirectAttributes)
        .should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, this.modelMock);
  }

  @Test
  public void update_givenValidationHasNoErrors_thenReturnRedirectToCurrencies() {
    given(this.errorsMock.hasErrors()).willReturn(false);
    given(this.currencyDtoMock.getId()).willReturn(78);
    String actualResult = this.controller
        .update(this.currencyDtoMock, this.errorsMock, null, this.modelMock);
    then(actualResult).isEqualTo("redirect:/currencies/78");
  }

  @Test
  public void delete_shouldReturnRedirectToCurrenciesAndInvokeDelete() {
    // when:
    String actual = this.controller.delete(1);
    // then:
    then(actual).isEqualTo("redirect:/currencies");
    BDDMockito.then(this.currencyFacadeMock).should().delete(1);
  }

  @Test
  public void fillCurrencies_shouldReturnRedirectUrl() {
    // when:
    String actual = this.controller.fillCurrencies();
    // then:
    then(actual).isEqualTo("redirect:/currencies");
    BDDMockito.then(this.currencyFacadeMock).should().fillDistinctCurrencies();
  }

}
