package homefinance.money.currency;

import static homefinance.money.currency.CurrencyController.REDIRECT_URL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.util.PathSelector;
import homefinance.common.util.impl.PathSelectorTest;
import homefinance.money.currency.dto.CurrencyDto;
import homefinance.money.currency.entity.Currency;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.BDDAssertions;
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
  private BindingResult result;
  @Mock
  private CurrencyFacade currencyFacadeMock;

  @Before
  public void setUp() {
    this.controller = new CurrencyController(this.currencyServiceMock, this.currencyFacadeMock);
    PathSelector pathSelector = new PathSelectorTest();
    this.controller.setPathSelector(pathSelector);
  }

  @Test
  public void list_shouldReturnListTemplateAndAddCurrenciesAttributeWithFilledCurrencyDtoList() {
    //given
    List<CurrencyDto> currencyDtoList = Arrays.asList(mock(CurrencyDto.class), null);
    given(this.currencyFacadeMock.getAllCurrenciesDto())
        .willReturn(currencyDtoList);
    //when
    String actual = this.controller.list(this.model);
    //then
    BDDAssertions.then(actual)
        .isEqualTo("currencies/list");
    BDDMockito.then(this.model)
        .should()
        .addAttribute("currencies", currencyDtoList);
  }

  @Test
  public void openNew_shouldReturnNewTemplateAndAddCurrencyAttributeWithNewCurrencyDto() {
    // when:
    String actual = this.controller.openNew(this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("currencies/new");
    BDDMockito.then(this.model)
        .should().addAttribute(BDDMockito.eq("currency"), BDDMockito.refEq(new CurrencyDto()));
  }

  @Test
  public void testSave() {
    when(this.result.hasErrors()).thenReturn(true);
    assertThat(this.controller.saveNew(this.currency, this.result), is("currencies/new"));
    when(this.result.hasErrors()).thenReturn(false);
    assertThat(this.controller.saveNew(this.currency, this.result),
        is(REDIRECT_URL));
  }

  @Test
  public void view_givenModelHasNoFlashModel_thenAddAttribute() {
    // given:
    given(this.currencyServiceMock.getByID(5)).willReturn(this.currency);
    Map<String, Object> map = new HashMap<>();
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.model)
        .should().addAttribute("currency", this.currency);
  }

  @Test
  public void view_givenModelHasFlashModel_thenMergeAttributesFromFlashModel() {
    // given:
    given(this.currencyServiceMock.getByID(5)).willReturn(this.currency);
    Map<String, Object> map = new HashMap<>();
    Object flashModel = mock(Model.class);
    map.put("flashModel", flashModel);
    given(this.model.asMap()).willReturn(map);
    // when:
    String actual = this.controller.view(5, this.model);
    // then:
    BDDAssertions.then(actual).isEqualTo("currencies/view");
    BDDMockito.then(this.model)
        .should().mergeAttributes(((Model) flashModel).asMap());
  }

  @Test
  public void update_givenValidationHasErrors_thenShouldBeEqualToRedirectCurrenciesView() {
    // given:
    given(this.result.hasErrors()).willReturn(true);
    given(this.currency.getId()).willReturn(555);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    // when:
    String actualResult = this.controller.update(this.currency, this.result, redirectAttributes,
        this.model);
    // then:
    BDDAssertions.then(actualResult).isEqualTo("redirect:/currencies/555");
    BDDMockito.then(redirectAttributes)
        .should().addFlashAttribute("flashModel", this.model);
  }

  @Test
  public void update_givenValidationHasNoErrorsAndGetPathWorkedOk_thenShouldBeEqualToRedirectUrl() {
    given(this.result.hasErrors()).willReturn(false);
    String actualResult = this.controller.update(this.currency, this.result, null, this.model);
    BDDAssertions.then(actualResult).isEqualTo(REDIRECT_URL);
  }

  @Test
  public void update_givenValidationHasNoErrorsAndUpdateThrownDuplicateFieldsException_thenShouldBeEqualToRedirectCurrenciesView() {
    // given:
    given(this.result.hasErrors()).willReturn(false);
    given(this.currency.getId()).willReturn(556);
    doThrow(DuplicateFieldsException.class).when(this.currencyServiceMock).update(this.currency);
    // when:
    String actualResult = this.controller.update(this.currency, this.result, null, this.model);
    // then:
    BDDAssertions.then(actualResult).isEqualTo("redirect:/currencies/556");
  }

  @Test
  public void testDeleteUser() {
    assertThat(this.controller.deleteUser(1), is(REDIRECT_URL));
  }

  @Test
  public void fillCurrencies_shouldReturnRedirectUrl() {
    String actual = this.controller.fillCurrencies();
    BDDAssertions.then(actual)
        .isEqualTo(REDIRECT_URL);
    BDDMockito.then(this.currencyServiceMock)
        .should()
        .fillDistinctCurrencies();
  }
}
