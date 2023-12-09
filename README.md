TASK</br>

StockPortfolio is a CRUD application built in SpringBoot Java.

The home view summarized each investment, with a calculator that </br>
shows how much of a $10M investment remains. The user can add a fund,</br>
and filter buttons allow sorting by dollar amount (increasing / decreasing)</br>
or alphabetical order. The user can also edit the amount invested via </br>
an update button.</br>

TECHNOLOGY STACK</BR>
Java</br>
Spring Boot</br>
Maven</br>
Thymeleaf</br>
Bootstrap</br>
MySQL (and MySQL Workbench)</br>

DESIGN</br>
The following components create the CRUD capability:</br>
stockController: Defines the routes
Stock (model): Defines the variables/model methods, e.g. Stock, </br>
    ID, Amount Invested </br>
StockRepository:
StockService
StockPortfolioApplication: