## Vending Machine in Two Ways

Please review the [License](LICENSE.md)

##### NOTE: I do not care to contribute to other students finding and using any part of this solution as their own. GitHub provides _private_ repositories if you would like to save it for later study.

This project contains the vending machine in two forms based upon _what we've learned_. 

1. The CLI application required from the mini-capstone
2. A GUI version that uses the exact same classes without modification

   - I stated that I could write a GUI version by only replacing one controller and the view while we discussed the CLI design. So I did.
   - It is simple and nothing special, it is simply designed to show how loosely coupled classes are awesome.
   - To produce the sales report, first buy a product and then hit CTRL-S

This implementation breaks the requirements into Models (objects with state), Views (methods that "show" something), Controllers (direct actions that form display output) and Services (in this application these are utility classes). Each class (and almost every method) adheres to the Single Responsibility principle. 

The project uses three configuration files:

1. The vending machine inventory file

2. The menu configuration file

3. The actions (route) configuration file

Each has a corresponding service that loads and parses the file contents into the correct data structure. These are good examples of reading and parsing a CSV file into a List and or a Map.

There is an Enum type that has the "Action Names" that can occur in the CLI. The main controller (front controller)  for the CLI uses these in order to delegate actions to the respective classes that have the responsibility.

Everything else is directly out of the instructions. You'll find extensive comments in each class.

![](/Users/randy/workspace/VendingMachine/readme-assets/package_core.png)
# vendingmachine
