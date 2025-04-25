package homefinance.expense;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import homefinance.AbstractSpringIntegrationTest;
import homefinance.expense.entity.Expense;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class ExpenseRepositoryIntegrationTest extends AbstractSpringIntegrationTest {

  @Autowired
  private ExpenseRepository expenseRepository;

  @BeforeEach
  void setUp() {
    expenseRepository.deleteAll();
  }

  @Test
  void testSaveExpense() {
    // given:
    Expense expense = new Expense("Groceries");
    // when:
    Expense savedExpense = expenseRepository.save(expense);
    // then:
    then(savedExpense.getName()).isEqualTo("Groceries");
  }

  @Test
  void testFindByName() {
    // given:
    Expense expense = new Expense("Utilities");
    expenseRepository.save(expense);
    // when:
    Expense foundExpense = expenseRepository.findByName("Utilities");
    // then:
    assertNotNull(foundExpense);
    then(foundExpense.getName()).isEqualTo("Utilities");
  }

  @Test
  void testUniqueNameConstraint() {
    // given:
    Expense expense1 = new Expense("Rent");
    expenseRepository.save(expense1);
    // when:
    Expense expense2 = new Expense("Rent");
    // then:
    DataIntegrityViolationException exception = assertThrows(
        DataIntegrityViolationException.class,
        () -> expenseRepository.save(expense2)
    );
    then(exception.getMessage()).contains("unique_expenses_by_name");
    // Verify that the exception is due to a constraint violation
    Throwable cause = exception.getCause();
    then(cause.getClass()).isEqualTo(ConstraintViolationException.class);
    then(((ConstraintViolationException) cause).getConstraintName()).isEqualTo(Expense.UNIQUE_CONSTRAINT_NAME);
  }

  @Test
  void testFindAll() {
    // given:
    expenseRepository.save(new Expense("Food"));
    expenseRepository.save(new Expense("Transportation"));
    expenseRepository.save(new Expense("Entertainment"));
    // when:
    List<Expense> expenses = expenseRepository.findAll();
    // then:
    then(expenses.size()).isEqualTo(3);
  }
}
