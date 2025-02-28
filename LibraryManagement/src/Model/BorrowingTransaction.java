package Model;

import java.util.Date;

public class BorrowingTransaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private Date issueDate;
    private Date returnDate;
    private boolean isReturned;
    private double feesAmount;
    private boolean isPaid;

    public BorrowingTransaction(int transactionId, int userId, int bookId, Date issueDate, Date returnDate, boolean isReturned, double feesAmount, boolean isPaid) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.feesAmount = feesAmount;
        this.isPaid = isPaid;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public double getFeesAmount() {
        return feesAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    public void setFeesAmount(double feesAmount) {
        this.feesAmount = feesAmount;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public String toString() {
        return "Transaction ID: " + transactionId +
               ", User ID: " + userId +
               ", Book ID: " + bookId +
               ", Issue Date: " + issueDate +
               ", Return Date: " + returnDate +
               ", Returned: " + isReturned +
               ", Fees: " + feesAmount +
               ", Paid: " + isPaid;
    }
}
