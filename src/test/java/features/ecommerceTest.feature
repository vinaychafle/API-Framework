Feature: Validating Ecommerce API

Scenario Outline: Verify if product is added 
	Given Login payload "<username>" "<password>"
	
	And verify product is added to "<name>" using "userID"
	
Examples:
	|username 				| password     |name |
	|superman@gmail.com     |  Karan@11    |new productNew|
	

	 


	

	
	
	
	
	
	

	
	
	