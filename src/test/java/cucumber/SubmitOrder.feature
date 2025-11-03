Feature: Purchase the order from Ecommerse Website

Background:
	Given I landed on Ecommerce Page

@Regression
Scenario Outline: Positive Test of Submitting the order
	Given Logged in with username <username> and password <password>
	When I add product <productName> to Cart
	And Checkout <productName> and submit the order
	Then "Thankyou for the order." message is displayed on Confirmation page
	
	Examples:
		| username 			  | password   | productName	 |
		| marharyta@gmail.com | MyTest123  | ZARA COAT 3     |
		| margo@gmail.com 	  | MyTest1234 | ADIDAS ORIGINAL |