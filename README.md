# A simple basket pricer in Java

My implementation of the *popular* interview pre-screening assignment.

## Instructions

- You are building a checkout system for a shop which only sells apples and bananas.

  - Apples cost 60p and bananas cost 30p.

- Build a checkout system which takes a list of items scanned at the till and outputs the total cost

   - For example: [ Apple, Apple, Banana, Apple] => £2.10


### Simple offers

- The shop decides to introduce two new offers

  - Buy one, get one free on Apples

  - 3 for the price of 2 on Bananas


## Some implementation notes

- There are two implementations of the pricing algorithm :
 
 - A simple pricer: The pricer takes in a list of promotions and applies them in strict order. The pricer take the fist promotion
   and repeatedly applies it to the basket until no more items qualifying for this offer are left in the basket. It will then move on to the next promotion and so on. After each application, the creates the copy of the basket minus the items that have been discounted to prevent discounts being applied twice to same item. 
 
 - A discount maximizing pricer: This pricer takes in a list of promotions and tries to find an order which maximizes the discount available to the customer.
For example, given the following basket : *[Apple, Apple, Apple, Apple]* and the following promotions *[BuyTwoGetOneFree, BuyOneGetOneFree]* the pricer will choose to apply the *BuyOneGetOneFree* promotion (twice) as that maximizes the discount available to the customers.
 
- Basket is currently not thread safe. All other classes are thread safe.


