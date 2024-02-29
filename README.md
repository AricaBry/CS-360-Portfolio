# CS-360-Portfolio
# Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?
The application I made is an inventory tracking app. Its main purpose is to make the management of items much easier for companies/users. The app needed to have a login system that allowed users to log in and even create accounts if they had never done so before. The app would store these details inside of a SQLite database. This database would also be in charge of storing the inventory items. The database allows users to:
(Create) Add items to a database
(Read) View all of the items in the database as a grid
(Update) Edit details about items in the database such as changing the amount of each item and even changing the item name.
(Delete) Remove items from the database
There are also SMS notifications that the user can enable through the app. These notifications send out a text whenever an inventory item is close to running out of stock. The app still fully functions without this feature enabled.

# What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?
I made 5 screens that would give users access to all features. There is a login screen where users can enter their login credentials to gain access to the app. If they have never created an account before they will need to create an account on the Register screen. After logging in, the user is taken to their inventory list. From here they can add, edit, or delete an item. They can also navigate to the SMS notifications screen. 
![InventoryAppScreens](https://github.com/AricaBry/CS-360-Portfolio/assets/99919870/735761ff-ff35-41eb-8c3e-8628643da16e)

# How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?
My approach to coding my app was starting and working through one functionality at a time. For example, I would not start work on SMS notifications until I knew I had my database working.

# How did you test to ensure your code was functional? Why is this process important and what did it reveal?
I tested my code throughout the development process. I tested after any new code/functionality was added. This made sure bugs did not make it far and cause problems later in the project.

# Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge? 
Some ideas I had for the UI were too complicated for me to complete in the allotted time frame. I think it was important to recognize this and take a different route otherwise the project would not have been completed in time.

# In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?
I believe I am most proud of getting database items to display in RecyclerView. This took me the longest to work out and I am very proud of how it came out in the end. 
