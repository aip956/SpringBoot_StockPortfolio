TASK</br>

StockPortfolio is a CRUD application built in SpringBoot Java.

The home view summarized each investment, with a calculator that </br>
shows how much of a $10M investment remains. The user can add a fund,</br>
and filter buttons allow sorting by dollar amount (increasing / decreasing)</br>
or alphabetical order. The user can also edit the amount invested via </br>
an update button.</br>
</br>
TECHNOLOGY STACK</br>
* Java</br>
* Spring Boot</br>
* Maven</br>
* Thymeleaf</br>
* Bootstrap</br>
* MySQL (and MySQL Workbench)</br>
  </br>

DESIGN</br>
The following components create the CRUD capability:</br>
* stockController: Defines the routes
* Stock (model): Defines the variables/model methods, e.g. Stock, </br>
    ID, Amount Invested </br>
* StockRepository: Manages the persistence of Stock entities. </br>
    It acts as an interface between the application code and the underlying  </br>
    database.
* StockService: Acts as an intermediary between the controller and the </br>
    database, calculating the total investments, managing investment </br>
    limits.</br>
* StockPortfolioApplication: Entry point for the Spring Boot application. </br>
  </br>

TO RUN</br>
Locally
* In IntlliJ IDEA
* * In the StockPortfolioApplication, right click for the menu, then click run </br>

Web Application
* In Railway, link https://stockportfolio.up.railway.app/stock </br>
* This will be the main landing page, showing all stock
* 