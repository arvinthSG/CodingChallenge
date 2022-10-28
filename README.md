**Gorest.co.in client**

An app that works with the https://gorest.co.in/ website

List of task that are implemented

1. Retrieve page 3 of the list of all users.
2. Using a logger, log the total number of pages from the previous request.
3. Sort the retrieved user list by name.
4. After sorting, log the name of the last user.
5. Update that user's name to a new value and use the correct http method to save it.
6. Delete that user.
7. Attempt to retrieve a nonexistent user with ID 5555. Log the resulting http response code.
8. Write unit tests for all code, mocking out calls to the actual API service.


App is built using MVP architecture to keep it clean, easy to understand and easy to modify/scale.
Network interactions are implemented using a combination of RxJava & Retrofit.

Home page has 3 buttons with 3 edit texts that can be used to execute the tasks that are listed above. All the button actions are able to handle empty edit texts i.e., all the tasks are pre-loaded with default value(from the task list) in case the user doesn't provide any input.

<img width="198" alt="Screen Shot 2022-10-28 at 1 57 06 AM" src="https://user-images.githubusercontent.com/42759060/198547620-1e8ba579-6fa5-4e1b-979e-333250f250df.png">
To execute tasks 1 - 5, enter a page no in the page no edit text and click on the "LOAD USERS FROM PAGE" button. 
The resulst are updated in the Android logcat as per the requirement but I have also added a text box below these buttons to display the messages.

This is a running list of logs from the tasks that are executed so far and with the use of a NestedScrollView this can be scrolled vertically.
<img width="201" alt="Screen Shot 2022-10-28 at 1 58 12 AM" src="https://user-images.githubusercontent.com/42759060/198547877-7e44787c-25f1-4490-b658-6b0eebd9a217.png">

Task 6 can be executed using the second edittext and button combo. Enter an ID of a user in the edit text and click on the "DELETE USER" button. Result of the action will be then posted to the text box below.


Taks 7 can be executed using the third edittext and button combo. Enter a user ID in the edit text and click on the "RETRIEVE USER" button. If it is a valid user ID then the user details will be added to the text box or else if it is a non-existent user the error code is logged and printed in the textbox as well.

<img width="208" alt="Screen Shot 2022-10-28 at 2 02 06 AM" src="https://user-images.githubusercontent.com/42759060/198548716-0d9a38d4-2f6d-4625-8459-578e0e82fc39.png">
