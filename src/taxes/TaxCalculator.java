package taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    // Constants in %
    private static final double SOCIAL_SECURITY_TAX_RATE = 9.76;
    private static final double SOCIAL_HEALTH_SECURITY_TAX_RATE = 1.5;
    private static final double SOCIAL_SICK_SECURITY_TAX_RATE = 2.45;
    private static final double SOCIAL_HEALTH1_RATE = 9;
    private static final double SOCIAL_HEALTH2_RATE = 7.75;
    private static final double TAX_DEDUCTIBLE_EXPENSES_RATE = 20;
    private static final double ADVANCE_TAX_RATE = 18;


    // Instance variables
    private double income = 0; 
    private char contractType = ' ';
    
    // social security taxes
    private double socSecurity; 
    private double socHealthSecurity; 
    private double socSickSecurity; 


    private double taxDeductibleExpenses; 
	private double taxedIncome; 
	private double taxedIncomeRounded; 
    private double advanceTax; 
    private double taxFreeIncome = 46.33; 
	private double taxPaid; 
    private double advanceTaxPaidRounded; 
    private double socHealth1; 
    private double socHealth2; 
    private double advanceTaxPaid; 
	private double netIncome; 
	private DecimalFormat df00 = new DecimalFormat("#.00"); // Decimal format for two decimal places
    private DecimalFormat df = new DecimalFormat("#"); // Decimal format for whole numbers


    // Main method
    public static void main(String[] args) 
    {
        TaxCalculator calculator = new TaxCalculator(); 
        calculator.getInput(); 
        calculator.calculateTaxes(); 
        calculator.printAll();
    }


    // Method to get user input for income and contract type
    private void getInput() 
    {
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



    private void calculateTaxes() {

        calculateCommonTaxes();

        if (contractType == 'E') {
            processEmploymentContract();
        } 
        else if (contractType == 'C') {
            processCivilContract();
        } 
        else {
            System.out.println("Unknown type of contract!"); // Handle unknown contract types
        }
    }



    private void processCivilContract() 
    {


    }

    private void processEmploymentContract()
    {

    
    }


    //Calculate methods


    private void calculateIncome() 
    {
        socSecurity = (income * SOCIAL_SECURITY_TAX_RATE) / 100; 
        socHealthSecurity = (income * SOCIAL_HEALTH_SECURITY_TAX_RATE) / 100; 
        socSickSecurity = (income * SOCIAL_SICK_SECURITY_TAX_RATE) / 100; 
    }    



    private void calculateOtherTaxes() 
    {
        socHealth1 = (income * SOCIAL_HEALTH1_RATE) / 100; 
        socHealth2 = (income * SOCIAL_HEALTH2_RATE) / 100; 
    }    


    private void CalculateTaxDeductibleExpenses()
    {
        if (contractType == 'E') {

        }
		else if (contractType == 'C') {
            taxFreeIncome = 0;
        }
        taxDeductibleExpenses  = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100;
    }


    
	private void calculateTaxedIncome() 
    {
		taxedIncome = income - taxDeductibleExpenses; 
        taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome)); 
	}



    private void calculateTax() 
    {
        advanceTax = (taxedIncomeRounded * ADVANCE_TAX_RATE) / 100; 
    }
    


    private void calculateAdvanceTax() 
    {
        taxPaid = advanceTax - taxFreeIncome; 
        advanceTaxPaid = advanceTax - socHealth2 - taxFreeIncome; 
		advanceTaxPaidRounded = Double.parseDouble(df.format(advanceTaxPaid)); 
    }



    private void calculateNetIncome() 
    {
        netIncome = income - (socSecurity + socHealthSecurity + socSickSecurity + socHealth1 + advanceTaxPaidRounded);
    }
    





    private void calculateCommonTaxes() 
    {
        calculateIncome();
        calculateOtherTaxes();
        CalculateTaxDeductibleExpenses();
        calculateTaxedIncome();
        calculateTax();
        calculateAdvanceTax();
        calculateNetIncome();
    }






    //Print methods 



	private void printChoice() 
    {
		if (contractType == 'E') {
        	System.out.println("EMPLOYMENT CONTRACT");
        }
		else {
			System.out.println("CIVIL CONTRACT");
        }
		System.out.println("Income: " + income);
	}



	private void printSecurTaxes()
    {
		System.out.println("Social security tax: " + df00.format(socSecurity));
        System.out.println("Health social security tax: " + df00.format(socHealthSecurity));
        System.out.println("Sickness social security tax: " + df00.format(socSickSecurity));
        System.out.println("Income basis for health social security: " + income);
	}



	private void printNewHealthSocialSecurTax() 
    {
		System.out.println("Health social security tax: 9% = " + df00.format(socHealth1) + " 7.75% = " + df00.format(socHealth2));
	
    }



    private void printTaxedDeductible() 
    {
		System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
    }



	private void printTaxedIncome() 
    {
        System.out.println("income to be taxed = " + taxedIncome + " rounded = " + df.format(taxedIncomeRounded));
	}


	private void printCalculateTax1() 
    {
        System.out.println("Advance tax 18 % = " + advanceTax);

        if (contractType == 'E') {

            System.out.println("Tax free income = " + taxFreeIncome);
        }
	}



    private void printAlreadyPaidTax() 
    {
        System.out.println("Already paid tax = " + df00.format(taxPaid));
    }



	private void printAdvancedTax() 
    {
		System.out.println("Advance tax paid: " + df00.format(advanceTaxPaid) + " rounded: " + df.format(advanceTaxPaidRounded));
	}



    private void printNetIncome() 
    {
        System.out.println();
        System.out.println("Net income = " + df00.format(netIncome));
    }




    private void printAll() 
    {
        printChoice();
        printSecurTaxes();
        printNewHealthSocialSecurTax();
        printTaxedDeductible();
        printTaxedIncome();
        printCalculateTax1();
        printAlreadyPaidTax();
        printAdvancedTax();
        printNetIncome();

    }


}
