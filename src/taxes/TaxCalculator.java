package taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    // Constants
    private static final double SOC_SECURITY_RATE = 9.76; // Social security tax rate
    private static final double SOC_HEALTH_SECURITY_RATE = 1.5; // Health security tax rate
    private static final double SOC_SICK_SECURITY_RATE = 2.45; // Sickness security tax rate
    private static final double TAX_DEDUCTIBLE_EXPENSES_RATE = 20; // Tax deductible expenses rate
    private static final double ADVANCE_TAX_RATE = 18; // Advance tax rate
    private static final double SOC_HEALTH1_RATE = 9; // Health social security rate 1
    private static final double SOC_HEALTH2_RATE = 7.75; // Health social security rate 2

    // Instance variables
    private double income; // User's income
    private char contractType; // Type of contract (Employment or Civil)
    private double socSecurity; // Social security tax
    private double socHealthSecurity; // Health social security tax
    private double socSickSecurity; // Sickness social security tax
    private double taxDeductibleExpenses; // Tax deductible expenses
    private double advanceTax; // Calculated advance tax
    private double taxFreeIncome = 0; // Default tax-free income
    private double socHealth1; // Health security tax at 9%
    private double socHealth2; // Health security tax at 7.75%
    private double advanceTaxPaid; // Paid advance tax

    // Main method
    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator(); // Create an instance of TaxCalculator
        calculator.getInput(); // Get user input
        calculator.calculateTaxes(); // Calculate taxes based on the contract type
    }

    // Method to get user input for income and contract type
    private void getInput() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            System.out.print("Enter income: ");
            income = Double.parseDouble(br.readLine());

            System.out.print("Contract Type: (E)mployment, (C)ivil: ");
            contractType = br.readLine().charAt(0);

        } catch (Exception ex) {
            System.out.println("Incorrect input");
            System.err.println(ex);
        }
    }

    // Method to calculate taxes based on the contract type
    private void calculateTaxes() {
        DecimalFormat df00 = new DecimalFormat("#.00"); // Decimal format for two decimal places
        DecimalFormat df = new DecimalFormat("#"); // Decimal format for whole numbers

        if (contractType == 'E') {
            System.out.println("EMPLOYMENT");
            System.out.println("Income: " + income);
            calculateIncome(income); // Calculate income and relevant taxes
            System.out.println("Social security tax: " + df00.format(socSecurity));
            System.out.println("Health social security tax: " + df00.format(socHealthSecurity));
            System.out.println("Sickness social security tax: " + df00.format(socSickSecurity));
            System.out.println("Income basis for health social security: " + income);
            calculateOtherTaxes(income); // Calculate additional health taxes
            System.out.println("Health social security tax: 9% = " + df00.format(socHealth1) + " 7.75% = " + df00.format(socHealth2));
            taxDeductibleExpenses = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100; // Calculate tax deductible expenses
            System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
            double taxedIncome = income - taxDeductibleExpenses; // Calculate taxed income
            double taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome));
            System.out.println("Taxed income: " + taxedIncome + " rounded: " + df.format(taxedIncomeRounded));
            calculateTax(taxedIncomeRounded); // Calculate advance tax
            System.out.println("Advance tax (18%): " + df00.format(advanceTax));
            System.out.println("Tax-free income: " + taxFreeIncome);
            double taxPaid = advanceTax - taxFreeIncome; // Calculate tax paid
            System.out.println("Reduced tax: " + df00.format(taxPaid));
            calculateAdvanceTax(); // Calculate advance tax paid
            double advanceTaxRounded = Double.parseDouble(df.format(advanceTaxPaid));
            System.out.println("Advance tax paid: " + df00.format(advanceTaxPaid) + " rounded: " + df.format(advanceTaxRounded));
            double netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxRounded); // Calculate net income
            System.out.println("Net income: " + df00.format(netIncome));
        } else if (contractType == 'C') {
            System.out.println("CIVIL CONTRACT");
            System.out.println("Income: " + income);
            calculateIncome(income); // Calculate income and relevant taxes
            System.out.println("Social security tax: " + df00.format(socSecurity));
            System.out.println("Health social security tax: " + df00.format(socHealthSecurity));
            System.out.println("Sickness social security tax: " + df00.format(socSickSecurity));
            System.out.println("Income for calculating health social security tax: " + income);
            calculateOtherTaxes(income); // Calculate additional health taxes
            System.out.println("Health social security tax: 9% = " + df00.format(socHealth1) + " 7.75% = " + df00.format(socHealth2));
            taxDeductibleExpenses = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100; // Calculate tax deductible expenses
            System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
            double taxedIncome = income - taxDeductibleExpenses; // Calculate taxed income
            double taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome));
            System.out.println("Taxed income: " + taxedIncome + " rounded: " + df.format(taxedIncomeRounded));
            calculateTax(taxedIncomeRounded); // Calculate advance tax
            System.out.println("Advance tax (18%): " + df00.format(advanceTax));
            System.out.println("Already paid tax: " + df00.format(advanceTaxPaid));
            calculateAdvanceTax(); // Calculate advance tax paid
            double advanceTaxRounded = Double.parseDouble(df.format(advanceTaxPaid));
            System.out.println("Advance tax paid: " + df00.format(advanceTaxPaid) + " rounded: " + df.format(advanceTaxRounded));
            double netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxRounded); // Calculate net income
            System.out.println("Net income: " + df00.format(netIncome));
        } else {
            System.out.println("Unknown type of contract!"); // Handle unknown contract types
        }
    }

    // Method to calculate advance tax
    private void calculateAdvanceTax() {
        advanceTaxPaid = advanceTax - socHealth2 - taxFreeIncome; // Adjust advance tax
    }

    // Method to calculate advance tax
    private void calculateTax(double income) {
        advanceTax = (income * ADVANCE_TAX_RATE) / 100; // Calculate advance tax
    }

    // Method to calculate income after social security deductions
    private void calculateIncome(double income) {
        socSecurity = (income * SOC_SECURITY_RATE) / 100; // Calculate social security tax
        socHealthSecurity = (income * SOC_HEALTH_SECURITY_RATE) / 100; // Calculate health security tax
        socSickSecurity = (income * SOC_SICK_SECURITY_RATE) / 100; // Calculate sickness security tax
    }

    // Method to calculate additional health taxes
    private void calculateOtherTaxes(double income) {
        socHealth1 = (income * SOC_HEALTH1_RATE) / 100; // Calculate health security tax at 9%
        socHealth2 = (income * SOC_HEALTH2_RATE) / 100; // Calculate health security tax at 7.75%
    }
}
