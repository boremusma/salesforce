##############################################################
      								 Author
#############################################################
Bo Ma

#######################################################################################
                                     Summary
#######################################################################################
 
This project covers the salesforce SDET interview assessment provide by Jordan Dapper.
#################################################################################################
						          	  Design
#################################################################################################

com.salesforce.base --> package contains the abstract superclass for page objects and tests.
com.salesforce.pages --> package contains all the page objects include: Login, Home, Leads, Campaigns.
com.salesforce.tests --> package contains all the test cases and test suites.
com.salesforce.utils --> package contains help classes include WebDriverGenerator(Singleton) and so on.

config --> folder has all the property files for page/tests, please use the same name for the property
file and its corresponding class. To modify test data, please change variable in config/test/MainTest.
jars  -->  folder contains selenium, junit, log4j JAR files.
logs  -->  folder store log4j generated logs.
build.xml --> the ant build file for the project.

#################################################################################################################################
								    Requirement
#################################################################################################################################

URL: login.salesforce.com
User name: demo_user@salesforce.com
Temporary password: jVpGt4qiPjVpGt4qiP


1. Create appropriate Classes/Methods/Locators to handle the following activities:

A. Login to salesforce using the credentials above. Check for and gracefully handle a login error
B. Select from the dropdown in the top right corner (you want to be able to select 'Sales')
C. Select an 'Object' from the top tab bar. Example objects are: 'Leads', 'Accounts', 'Contacts', 'Opportunities'
D. Set the View Filter to 'All Leads'
E. Checkmarkthe record 'Mui, Rick'
F. On the Leads Home Page, select a record from the Leads table
G. On the Leads Home Page, checkmark a specific record in the table as well as a method to click 'Del'
H. A method to accept/cancel 'Alerts', such as those that pop up when you click 'Del' as mentioned above.

Note:  All methods should have some form of logging, appropriate wait conditions, and some form of error handling. All locators should be By objects. Furthermore, all methods should be structured in classes following the page-object model convention. If you are not familiar with this approach, please research this before starting.

Note: All Locators should be defined using well-formed XPATH's. We suggest using Firebug/Firepath to investigate the DOM, but do not Firepaths' generated xpath. Create your own that does not break from run to run. ID's in Salesforce DO NOT persist. 

Bonus: When on the Leads Home Page, checkmark a passed in users record, and click 'Add to Campaign'. On the resulting page, there will be a field 'Campaign' with an associated button that performs a lookup. Using the term 'Automation Campaign', create a method to use the lookup and resulting popout window to search for and successfully select the term 'Automation Campaign'



2. Create a test using the above classes. The test should be in a JUnit format. Create the following workflow. 

A. Login
B. Select the app 'Sales' from the top right dropdown
C. Click on the tab 'Leads'
D. Set the View Filter to 'Hot Leads'
E. Checkmark the record 'Bell, Ken'
F. Click 'Del' as associated with that record
E. Accept Alert
F. Assert that the record no longer exists within the table after deleting



3. Upon completion, upload all class contents, including imports, to PasteBin or Git, and email me the link.

Bonus: Compile using ANT and include the ANT script with the source

