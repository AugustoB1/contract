package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, int months ){

        double basicQuota = contract.getTotalValue() / months;

        for(int i = 1; i<=months; i++){

            LocalDate duoDate = contract.getDate().plusMonths(i);

            double interest =  onlinePaymentService.interest(basicQuota, i);
            double paymentFee = onlinePaymentService.paymentFee(interest + basicQuota);

            double totalValue = basicQuota + interest + paymentFee;

            contract.getInstallments().add(new Installment(duoDate, totalValue));

        }
    }
}
