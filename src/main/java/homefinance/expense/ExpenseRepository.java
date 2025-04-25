package homefinance.expense;

import homefinance.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

  Expense findByName(String name);
}
