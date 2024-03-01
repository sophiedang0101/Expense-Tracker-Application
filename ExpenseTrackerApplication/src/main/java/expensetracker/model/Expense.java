package expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

//Model for expense.
public class Expense {
    private int expenseId;
    private String expenseDescription;
    private BigDecimal cost;
    private LocalDate expenseDate;
    private String vendor;
    private String notes;
    private int categoryId;

    public Expense() {
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    //Methods for testing purposes.
    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return getExpenseId() == expense.getExpenseId() && getCategoryId() == expense.getCategoryId() && Objects.equals(getExpenseDescription(), expense.getExpenseDescription()) && Objects.equals(getCost(), expense.getCost()) && Objects.equals(getExpenseDate(), expense.getExpenseDate()) && Objects.equals(getVendor(), expense.getVendor()) && Objects.equals(getNotes(), expense.getNotes());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpenseId(), getExpenseDescription(), getCost(), getExpenseDate(), getVendor(), getNotes(), getCategoryId());
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expenseDescription='" + expenseDescription + '\'' +
                ", cost=" + cost +
                ", expenseDate=" + expenseDate +
                ", vendor='" + vendor + '\'' +
                ", notes='" + notes + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
