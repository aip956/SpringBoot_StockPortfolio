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
* In IntelliJ IDEA
* * In the StockPortfolioApplication, right click for the menu, then click run </br>
* * <img
    src="./ScreenCaps/LocalRunFromFile.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
</br>
</br>
* * Or run from icon
* * <img
    src="./ScreenCaps/LocalRunFromIcon.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
</br>
</br>
Web Application
* In Railway, link https://stockportfolio.up.railway.app/stock </br>
* This will be the main landing page, showing all stock
* Stock can be viewed by ascending or descending amount
* * <img
    src="./ScreenCaps/ListSortByAmt.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
</br>
</br>
* Stock can be sorted by Name, ascending or descending
* * <img
    src="./ScreenCaps/ListSortByName.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
</br>
</br>
* Stock can be added/edited
* * <img
    src="./ScreenCaps/AddInv.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
    </br>
* If investment exceeds limit, there will be an error
* * <img
    src="./ScreenCaps/AddStockExceedLimitError.png"
    alt="Run from file menu"
    title="Run from file menu"
    style="display: block; margin: 0 auto; max-width: 150px">
</br>
</br>
FUTURE FEATURES</br>
It was a great experience creating a CRUD application in Java. </br>
Some future improvements could be:
* Implement in AWS
* Add React for better front end functionality
* Improve sorting implementation and have single sort button
