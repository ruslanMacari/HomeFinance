package homefinance.money.currency;

import static homefinance.common.CommonController.FLASH_MODEL_ATTRIBUTE_NAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import homefinance.common.RequestBuffer;
import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CurrencyControllerTest {

  @Mock
  private CurrencyService currencyServiceMock;
  private CurrencyController controller;
  @Mock
  private Model model;
  @Mock
  private Currency currency;
  @Mock
  private CurrencyDto currencyDto;
  @Mock
  private BindingResult errors;
  @Mock
  private CurrencyFacade currencyFacadeMock;
  @Mock
  private RedirectAttributes redirectAttributesMock;
  @Mock
  private RequestBuffer requestBufferMock;

  @Before
  public void setUp() {
    this.controller = new CurrencyController(this.currencyServiceMock, this.currencyFacadeMock,
        this.requestBufferMock);
    PathSelector pathSelector = new PathSelectorTest();
    this.controller.setPathSelector(pathSelector);
  }

  @Test
  public void list_shouldReturnListTemplateAndAddCurrenciesAttributeWithFilledCurrencyDtoList() {
    //given
    List<CurrencyDto> currencyDtoList = Arrays.asList(mock(CurrencyDto.class), null);
    given(this.currencyFacadeMock.getAllCurrenciesDto()).willReturn(currencyDtoList);
    //when
    String actual = this.controller.list(this.model);
    //then
    then(actual).isEqualTo("currencies/list");
    BDDMockito.then(this.model).should().addAttribute("currencies", currencyDtoList);
  }

  @Test
  public void openNew_givenModelHasNoFlashModel_shouldReturnNewTemplateAndAddCurrencyAttributeWithNewCurrencyDto() {
    // given:
    Map<String, Object> map = new HashMap<>();
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.openNew(this.model);
    // then:
    then(actual).isEqualTo("currencies/new");
    BDDMockito.then(this.model)
        .should().addAttribute(BDDMockito.eq("currency"), BDDMockito.refEq(new CurrencyDto()));
  }

  @Test
  public void openNew_givenModelHasFlashModel_shouldReturnNewTemplateAndMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.openNew(this.model);
    // then:
    then(actual).isEqualTo("currencies/new");
    BDDMockito.then(this.model).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void saveNew_givenHasValidationErrors_shouldReturnRedirectToNewUrl() {
    // given:
    given(this.errors.hasErrors()).willReturn(true);
    // when:
    String actual = this.controller
        .saveNew(this.currencyDto, this.errors, this.redirectAttributesMock, this.model);
    // then:
    then(actual).isEqualTo("redirect:/currencies/new");
    BDDMockito.then(this.redirectAttributesMock).should()
        .addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, this.model);
  }

  @Test
  public void saveNew_givenHasNoValidationErrors_shouldReturnRedirectUrl() {
    given(this.errors.hasErrors()).willReturn(false);
    // when:
    String actual = this.controller
        .saveNew(this.currencyDto, this.errors, this.redirectAttributesMock, this.model);
    // then:
    then(actual).isEqualTo("redirect:/currencies");
    BDDMockito.then(this.currencyFacadeMock).should().saveNew(this.currencyDto);
  }

  @Test
  public void view_givenModelHasNoFlashModel_thenAddAttribute() {
    // given:
    CurrencyDto currencyDto = mock(CurrencyDto.class);
    given(this.currencyFacadeMock.getCurrencyDtoById(5)).willReturn(currencyDto);
    Map<String, Object> map = new HashMap<>();
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.model);
    // then:
    then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.model).should().addAttribute("currency", currencyDto);
  }

  @Test
  public void view_givenModelHasFlashModel_thenMergeAttributesFromFlashModel() {
    // given:
    Map<String, Object> map = new HashMap<>();
    Model flashModel = mock(Model.class);
    map.put(FLASH_MODEL_ATTRIBUTE_NAME, flashModel);
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.model);
    // then:
    then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.model).should().mergeAttributes(flashModel.asMap());
  }

  @Test
  public void update_givenValidationHasErrors_thenShouldBeEqualToRedirectCurrenciesView() {
    // given:
    given(this.errors.hasErrors()).willReturn(true);
    given(this.currencyDto.getId()).willReturn(555);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actualResult = this.controller.update(this.currencyDto, this.errors, redirectAttributes,
        this.model);
    // then:
    then(actualResult).isEqualTo("redirect:/currencies/555");
    BDDMockito.then(redirectAttributes)
        .should().addFlashAttribute(FLASH_MODEL_ATTRIBUTE_NAME, this.model);
  }

  @Test
  public void update_givenValidationHasNoErrors_thenReturnRedirectToCurrencies() {
    given(this.errors.hasErrors()).willReturn(false);
    given(this.currencyDto.getId()).willReturn(78);
    String actualResult = this.controller.update(this.currencyDto, this.errors, null, this.model);
    then(actualResult).isEqualTo("redirect:/currencies/78");
  }

  @Test
  public void testDeleteUser() {
    assertThat(this.controller.deleteUser(1), is("redirect:/currencies"));
  }

  @Test
  public void fillCurrencies_shouldReturnRedirectUrl() {
    // when:
    String actual = this.controller.fillCurrencies();
    // then:
    then(actual).isEqualTo("redirect:/currencies");
    BDDMockito.then(this.currencyServiceMock).should().fillDistinctCurrencies();
  }

}
