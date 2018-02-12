package cn.choujiangba.server.app.vo;

/**
 * Created by hao on 2015/10/19.
 */
public class BalanceVO {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceVO{" +
                "balance=" + balance +
                '}';
    }
}
