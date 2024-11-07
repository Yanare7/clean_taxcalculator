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
    private double income = 0; //income in PLN 
    private static char contractType = ' ';
        
    // social security taxes
    private double socialSecurity; 
    private double socialHealthSecurity; 
    private double socialSickSecurity; 


    private double taxDeductibleExpenses; 
    private double taxedIncome; 
    private double taxedIncomeRounded; 
    private double advanceTax; 
    private double taxFreeIncome = 46.33; // tax-free income monthly 46,33 PLN
    private double taxPaid; 
    private double advanceTaxPaid; 
    private double advanceTaxPaidRounded; 
    private double socialHealth1; 
    private double socialHealth2; 
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
    private void getInput() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
    
            System.out.print("Enter income: ");
            income = Double.parseDouble(br.readLine());
    
            System.out.print("Contract Type: (E)mployment, (C)ivil: ");
            try {
                contractType = br.readLine().charAt(0);
                if (contractType != 'E' && contractType != 'C') {
                    throw new IllegalArgumentException("Valid contract type. Choose 'E' or 'C'.");
                }
            } catch (Exception e) {
                System.out.println("Unknown type of contract!"); // Handle unknown contract types
                System.out.println("Error : " + e.getMessage());
            }
    
        } catch (Exception ex) {
            System.out.println("Incorrect input.");
            System.err.println(ex);
        }
    }
    

    
    //Calculate methods

    private void calculateSocialSecurity() 
    {
        socialSecurity = (income * SOCIAL_SECURITY_TAX_RATE) / 100; 
        socialHealthSecurity = (income * SOCIAL_HEALTH_SECURITY_TAX_RATE) / 100; 
        socialSickSecurity = (income * SOCIAL_SICK_SECURITY_TAX_RATE) / 100; 
    }    



    private void calculateHealthTaxes() 
    {
        socialHealth1 = (income * SOCIAL_HEALTH1_RATE) / 100; 
        socialHealth2 = (income * SOCIAL_HEALTH2_RATE) / 100; 
    }    


    private void calculateTaxDeductibleExpenses()
    {
        if (contractType == 'E') {
            taxDeductibleExpenses = 111.25;
        }
		else if (contractType == 'C') {
            taxFreeIncome = 0;
            taxDeductibleExpenses  = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100;
        }
    }



    private void calculateTax() 
    {
        taxedIncome = income - taxDeductibleExpenses; 
        taxedIncomeRounded = Double.parseDouble(df.format(taxedIncome)); 
        advanceTax = (taxedIncomeRounded * ADVANCE_TAX_RATE) / 100; 
    }
    


    private void calculateAdvanceTax() 
    {
        taxPaid = advanceTax - taxFreeIncome; 
        advanceTaxPaid = advanceTax - socialHealth2 - taxFreeIncome; 
		advanceTaxPaidRounded = Double.parseDouble(df.format(advanceTaxPaid)); 
    }



    private void calculateNetIncome() 
    {
        netIncome = income - (socialSecurity + socialHealthSecurity + socialSickSecurity + socialHealth1 + advanceTaxPaidRounded);
    }
    

    private void processCivilCalculateTaxes()
    {
        calculateSocialSecurity();
        calculateHealthTaxes();
        calculateTaxDeductibleExpenses();
        calculateTax();
        calculateAdvanceTax();
        calculateNetIncome();
    }

    private void processEmploymentCalculateTaxes()
    {
        calculateSocialSecurity();
        calculateHealthTaxes();
        calculateTaxDeductibleExpenses();
        calculateTax();
        calculateAdvanceTax();
        calculateNetIncome();
    }

    private void calculateTaxes() 
    {
        switch (contractType ){
            case ('C'):
                processCivilCalculateTaxes();
                break;
            case ('E'):
                processEmploymentCalculateTaxes();
                break;
        }
        
    }



    //Print methods 

	private void printDetailsContract() 
    {
		if (contractType == 'E') {
        	System.out.println("EMPLOYMENT CONTRACT");
        }
		else {
			System.out.println("CIVIL CONTRACT");
        }
		System.out.println("Income: " + income);
	}



	private void printSecurityTaxes()
    {
		System.out.println("Social security tax: " + df00.format(socialSecurity));
        System.out.println("Health social security tax: " + df00.format(socialHealthSecurity));
        System.out.println("Sickness social security tax: " + df00.format(socialSickSecurity));
        System.out.println("Income basis for health social security: " + income);
	}



	private void printNewHealthSocialSecurTax() 
    {
		System.out.println("Health social security tax: 9% = " + df00.format(socialHealth1) + " 7.75% = " + df00.format(socialHealth2));
	
    }



    private void printTaxedDeductible() 
    {
		System.out.println("Tax deductible expenses: " + df00.format(taxDeductibleExpenses));
    }



	private void printTaxedIncome() 
    {
        System.out.println("income to be taxed = " + taxedIncome + " rounded = " + df.format(taxedIncomeRounded));
	}


	private void printCalculateTax() 
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
        printDetailsContract();
        printSecurityTaxes();
        printNewHealthSocialSecurTax();
        printTaxedDeductible();
        printTaxedIncome();
        printCalculateTax();
        printAlreadyPaidTax();
        printAdvancedTax();
        printNetIncome();

    }

}
