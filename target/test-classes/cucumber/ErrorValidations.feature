Feature: Error validation

Background:
	Given I landed on Ecommerce Page

@ErrorValidation
Scenario Outline: Positive Test of Submitting the order
	Given I landed on Ecommerce Page
	When Logged in with username <username> and password <password>
	Then "Incorrect email or password." message is displayed
	
	Examples:
		| username 			  | password   	  |
		| marharyta@gmail.com | MyTest1212123 | 