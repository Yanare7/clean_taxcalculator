package taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    // Constants
    private static final double SOCIAL_SECURITY_TAX_RATE = 9.76;
    private static final double SOCIAL_HEALTH_SECURITY_TAX_RATE = 1.5;
    private static final double SOCIAL_SICK_SECURITY_TAX_RATE = 2.45;
    private static final double TAX_DEDUCTIBLE_EXPENSES_RATE = 20;
    private static final double ADVANCE_TAX_RATE = 18;
    private static final double SOCIAL_HEALTH1_RATE = 9;
    private static final double SOCIAL_HEALTH2_RATE = 7.75;

    // Instance variables
    private double income; // User's income
    private char contractType; // Type of contract (Employment or Civil)
    private double socSecurity; // Social security tax
    private double socHealthSecurity; // Health social security tax
    private double socSickSecurity; // Sickness social security tax
    private double taxDeductibleExpenses; // Tax deductible expenses
	private double taxedIncome; // Income taxed
	private double taxedIncomeRounded; // Income taxed and rounded
    private double advanceTax; // Calculated advance tax
    private double taxFreeIncome = 0; // Default tax-free income
	private double taxPaid; // paid tax
    private double socHealth1; // Health security tax at 9%
    private double socHealth2; // Health security tax at 7.75%
    private double advanceTaxPaid; // Paid advance tax
	private double advanceTaxRounded; // Rounded paid advance tax
	private double netIncome; // Net income
	private DecimalFormat df00 = new DecimalFormat("#.00"); // Decimal format for two decimal places
    private DecimalFormat df = new DecimalFormat("#"); // Decimal format for whole numbers

    // Main method
    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator(); 
        calculator.getInput(); 
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

        printChoice();
        calculateCommonTaxes();
        printSecurTaxes();

        if (contractType == 'E') {

			printNewHealthSocialSecurTax();
			
			printTaxedIncome();
            
			printCalculateTax();

			printAtpNi();

        } else if (contractType == 'C') {
            calculateIncome(); 
            System.out.println("Health social security tax: 9% = " + df00.format(socHealth1) + " 7.75% = " + df00.format(socHealth2));
            taxDeductibleExpenses = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100; 
            System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
            double taxedIncome = income - taxDeductibleExpenses; 
            double taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome));
            System.out.println("Taxed income: " + taxedIncome + " rounded: " + df.format(taxedIncomeRounded));
            System.out.println("Advance tax (18%): " + df00.format(advanceTax));
            System.out.println("Already paid tax: " + df00.format(advanceTaxPaid));
            double advanceTaxRounded = Double.parseDouble(df.format(advanceTaxPaid));
            System.out.println("Advance tax paid: " + df00.format(advanceTaxPaid) + " rounded: " + df.format(advanceTaxRounded));
            double netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxRounded); // Calculate net income
            System.out.println("Net income: " + df00.format(netIncome));
        } else {
            System.out.println("Unknown type of contract!"); // Handle unknown contract types
        }
    }

    //TODO diviser en 2 methodes simples
    // Method to calculate advance tax
    private void calculateAdvanceTax() 
    {
        advanceTaxPaid = advanceTax - socHealth2 - taxFreeIncome; // Adjust advance tax
		advanceTaxRounded = Double.parseDouble(df.format(advanceTaxPaid)); // Round the adjusted advance tax
		netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxRounded); // Calculate net income
    }


    private void calculateNetIncome() 
    {
        netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxRounded);
    }


    // Method to calculate advance tax and tax paid
    private void calculateTax() {
        advanceTax = (income * ADVANCE_TAX_RATE) / 100; // Calculate advance tax
		taxPaid = advanceTax - taxFreeIncome; // Calculate tax paid
    }

    // Method to calculate income after social security deductions
    private void calculateIncome() {
        socSecurity = (income * SOCIAL_SECURITY_TAX_RATE) / 100; // Calculate social security tax
        socHealthSecurity = (income * SOCIAL_HEALTH_SECURITY_TAX_RATE) / 100; // Calculate health security tax
        socSickSecurity = (income * SOCIAL_SICK_SECURITY_TAX_RATE) / 100; // Calculate sickness security tax
    }

    // Method to calculate additional health taxes
    private void calculateOtherTaxes() {
        socHealth1 = (income * SOCIAL_HEALTH1_RATE) / 100; // Calculate health security tax at 9%
        socHealth2 = (income * SOCIAL_HEALTH2_RATE) / 100; // Calculate health security tax at 7.75%
    }

	// Method to calculate taxDeductibleExpenses, taxedIncome, taxedIncomeRounded
	private void calculateTaxedIncomeRounded() {

		taxDeductibleExpenses = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100; // Calculate tax deductible expenses
		taxedIncome = income - taxDeductibleExpenses; // Calculate taxed income
        taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome)); // Rounded the taxed income
	}

    private void calculateCommonTaxes() {
        calculateIncome();
        calculateOtherTaxes();
        calculateTaxedIncomeRounded();
        calculateTax();
        calculateAdvanceTax();
    }

	//Method to print the social security taxes
	private void printSecurTaxes(){

		System.out.println("Social security tax: " + df00.format(socSecurity));
        System.out.println("Health social security tax: " + df00.format(socHealthSecurity));
        System.out.println("Sickness social security tax: " + df00.format(socSickSecurity));
        System.out.println("Income basis for health social security: " + income);
	}

	private void printNewHealthSocialSecurTax() {

		System.out.println("Health social security tax: 9% = " + df00.format(socHealth1) + " 7.75% = " + df00.format(socHealth2));
	}

	//Methode to print the tax deductible expenses and the taxed income
	private void printTaxedIncome() {

		System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
        System.out.println("Taxed income: " + taxedIncome + " rounded: " + df.format(taxedIncomeRounded));
	}

	//Method to print
	private void printCalculateTax() {

		System.out.println("Advance tax (18%): " + df00.format(advanceTax));
		System.out.println("Tax-free income: " + taxFreeIncome);
		System.out.println("Reduced tax: " + df00.format(taxPaid));
	}

	//Method to print the advance tax paid and the net income
	private void printAtpNi() {

		System.out.println("Advance tax paid: " + df00.format(advanceTaxPaid) + " rounded: " + df.format(advanceTaxRounded));
		System.out.println("Net income: " + df00.format(netIncome));
	}

	//Method to print the choice : Employment or Civil
	private void printChoice() {

		if (contractType == 'E')
			System.out.println("EMPLOYMENT CONTRACT");
		else
			System.out.println("CIVIL CONTRACT");

		System.out.println("Income: " + income);
	}
}
