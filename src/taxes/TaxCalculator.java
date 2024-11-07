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
    private double socialHealthTax1; 
    private double socialHealthTax2; 
    private double netIncome; 
    private DecimalFormat decimalFormatTwoDigits = new DecimalFormat("#.00"); // Decimal format for two decimal places
    private DecimalFormat decimalFormatRounded = new DecimalFormat("#"); // Decimal format for whole numbers

    
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
            
            do{
                System.out.print("Enter income: ");
                income = Double.parseDouble(br.readLine());
                if (income <= 0) {
                    System.err.println("The income has to be postive.");                    
                }
            }while(income <= 0);
            
            do {
                System.out.print("Contract Type: (E)mployment, (C)ivil: ");
                contractType = br.readLine().charAt(0);
                
                if (contractType != 'E' && contractType != 'C') {
                    System.err.println("Unknown type of contract!");  
                }
            } while (contractType != 'E' && contractType != 'C');
            
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
        socialHealthTax1 = (income * SOCIAL_HEALTH1_RATE) / 100; 
        socialHealthTax2 = (income * SOCIAL_HEALTH2_RATE) / 100; 
    }    


    private void calculateTaxDeductibleExpensesForContractE()
    {
        taxDeductibleExpenses = 111.25;
    }

    private void calculateTaxDeductibleExpensesForContractC()
    {
        taxFreeIncome = 0;
        taxDeductibleExpenses  = (income * TAX_DEDUCTIBLE_EXPENSES_RATE) / 100;
    }



    private void calculateTax() 
    {
        taxedIncome = income - taxDeductibleExpenses; 
        taxedIncomeRounded = Double.parseDouble(decimalFormatRounded.format(taxedIncome)); 
        advanceTax = (taxedIncomeRounded * ADVANCE_TAX_RATE) / 100; 
    }
    


    private void calculateAdvanceTax() 
    {
        taxPaid = advanceTax - taxFreeIncome; 
        advanceTaxPaid = advanceTax - socialHealthTax2 - taxFreeIncome; 
		advanceTaxPaidRounded = Double.parseDouble(decimalFormatRounded.format(advanceTaxPaid)); 
    }



    private void calculateNetIncome() 
    {
        netIncome = income - (socialSecurity + socialHealthSecurity + socialSickSecurity + socialHealthTax1 + advanceTaxPaidRounded);
    }
    

    private void processCivilCalculateTaxes()
    {
        calculateSocialSecurity();
        calculateHealthTaxes();
        calculateTaxDeductibleExpensesForContractC();
        calculateTax();
        calculateAdvanceTax();
        calculateNetIncome();
    }

    private void processEmploymentCalculateTaxes()
    {
        calculateSocialSecurity();
        calculateHealthTaxes();
        calculateTaxDeductibleExpensesForContractE();
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
		System.out.println("Social security tax: " + decimalFormatTwoDigits.format(socialSecurity));
        System.out.println("Health social security tax: " + decimalFormatTwoDigits.format(socialHealthSecurity));
        System.out.println("Sickness social security tax: " + decimalFormatTwoDigits.format(socialSickSecurity));
        System.out.println("Income basis for health social security: " + income);
	}



	private void printNewHealthSocialSecurTax() 
    {
		System.out.println("Health social security tax: 9% = " + decimalFormatTwoDigits.format(socialHealthTax1) + " 7.75% = " + decimalFormatTwoDigits.format(socialHealthTax2));
	
    }



    private void printTaxedDeductible() 
    {
		System.out.println("Tax deductible expenses: " + decimalFormatTwoDigits.format(taxDeductibleExpenses));
    }



	private void printTaxedIncome() 
    {
        System.out.println("income to be taxed = " + taxedIncome + " rounded = " + decimalFormatRounded.format(taxedIncomeRounded));
	}


	private void printCalculateTax() 
    {
        System.out.println("Advance tax 18 % = " + advanceTax);
	}


    private void printTaxFreeIncome()
    {
        System.out.println("Tax free income = " + taxFreeIncome);

    }

    private void printAlreadyPaidTax() 
    {
        System.out.println("Already paid tax = " + decimalFormatTwoDigits.format(taxPaid));
    }



	private void printAdvancedTax() 
    {
		System.out.println("Advance tax paid: " + decimalFormatTwoDigits.format(advanceTaxPaid) + " rounded: " + decimalFormatRounded.format(advanceTaxPaidRounded));
	}



    private void printNetIncome() 
    {
        System.out.println();
        System.out.println("Net income = " + decimalFormatTwoDigits.format(netIncome));
    }


    private void processEmploymentPrintAll()
    {
        printDetailsContract();
        printSecurityTaxes();
        printNewHealthSocialSecurTax();
        printTaxedDeductible();
        printTaxedIncome();
        printCalculateTax();
        printTaxFreeIncome()
        printAlreadyPaidTax();
        printAdvancedTax();
        printNetIncome();
    }

    private void processCivilPrintAll()
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

    private void printAll() 
    {
        switch (contractType){
            case ('C'):
                processCivilPrintAll();
                break;
            case ('E'):
                processEmploymentPrintAll();
                break;
        }

    }

}
