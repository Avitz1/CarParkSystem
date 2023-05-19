# Car Park System - Parking Network Project

## A system for managing parking lots, staff, customers, orders and memberships for an automatic parking lot chain

In this project we, a group of 4 members, planned, designed and implemented a client-server desktop app for an automatic parking lot chain including GUIs for customers and staff. 

## Project's Structure:
**Client** - the client is built using JavaFX and OCSF. We use EventBus in order to pass events between classes.<br />
**Server** - the server is built using OCSF. All the communication to the MySql DB occurs by the server.<br />
**Entities** - shared module where all the entities of the project live.

- The project is written in Java using OCSF
- Hibernate ORM with MySQL
- An event-driven UI using JavaFX
- Maven as a dependency tool


## Running:
- Run Maven install in the parent project.
- Run the server using the exec:java goal in the server module.
- Run the client using the javafx:run goal in the client module.

## Functionalities:
**_Customers' functionalities are:_**
- Entering and leaving a parking lot through the interface and charging
- Signing up as registered customers 
- Logging in to their accounts
- Creating an order for a specific parking lot 
- Creating a membership for a one parking lot or for the entire chain 
- Filing a complaint
- View all their details (orders, memberships, complaints) in an interactive GUI and managing them (canceling, modifying)

**_Some of the staff's functionalities are:_**
- They have an interactive map of allparking slots in the lot with which they can check and midify the state of certain slots
- Managers can change prices of verious sevices awaiting the approval of the CEO
- Customer Service workers can view complaints, handle them and compensate client's account if they see fit
- Managers can request and view statistics and reports

For planning and designing the software we made UC, sequence and activity diagrams using *UML*.
##

## Project Presentation
### Main Window:
<img width="1021" alt="Screenshot 2023-03-15 at 18 28 27" src="https://user-images.githubusercontent.com/103389828/225376548-c6687c44-5cc6-4ae3-9cea-24d55698e29a.png">

## Customer UI:
### Sign Up Window:
<img width="1020" alt="Screenshot 2023-03-21 at 19 03 51" src="https://user-images.githubusercontent.com/103389828/226686527-eea2fba4-1d0a-467b-8f0e-4d46a0ee9b7c.png">
<img width="1020" alt="Screenshot 2023-03-21 at 19 04 07" src="https://user-images.githubusercontent.com/103389828/226686598-be67c884-59a4-458b-9f2d-8b0ea1796f39.png">

### Customer's Window (after sign-up):
<img width="1019" alt="Screenshot 2023-03-21 at 19 05 54" src="https://user-images.githubusercontent.com/103389828/226687113-751a4f59-adf2-41d2-94f8-93a2c92093a3.png">

### New Order Window:
<img width="1020" alt="Screenshot 2023-03-21 at 19 08 38" src="https://user-images.githubusercontent.com/103389828/226687849-67b0a74d-ccc8-4a70-87f8-990dcb4d4c7c.png">

### New Membership Window:
<img width="1019" alt="Screenshot 2023-03-21 at 19 10 47" src="https://user-images.githubusercontent.com/103389828/226688100-eecafee0-31a7-4c60-82e5-e639a351e1cb.png">
<img width="1020" alt="Screenshot 2023-03-21 at 19 12 10" src="https://user-images.githubusercontent.com/103389828/226688537-bd593d71-b55e-45d6-b14e-95705d5c6d50.png">

### Customer's Orders View Window:
Organized in a table view with an option of order cancelation and making refunds if needed.

<img width="1021" alt="Screenshot 2023-03-21 at 19 12 52" src="https://user-images.githubusercontent.com/103389828/226688837-5dc6918d-d2d4-40a6-b6ec-1ffde3bf520e.png">

### Customer's Memberships View Window:
<img width="1020" alt="Screenshot 2023-03-21 at 19 16 16" src="https://user-images.githubusercontent.com/103389828/226689615-2646c83a-328c-4056-942d-5529f7325a20.png">

### New Complaint Submition Window: 
<img width="1019" alt="Screenshot 2023-03-21 at 19 17 54" src="https://user-images.githubusercontent.com/103389828/226690023-ac6eb20a-cc13-44fc-806b-23d86ea683ef.png">

### Customer's Complaint View Window:
Organized in a table view with the column of the compaint status. 
The Customer Service workers should give a response to a new complaint within 24 hours, only then the status will change.

<img width="1020" alt="Screenshot 2023-03-21 at 19 19 20" src="https://user-images.githubusercontent.com/103389828/226690494-da3ea591-2493-41df-b087-956e2f14a886.png">

##
## Employee UI:
### Parking Lot Worker's Window:
<img width="1020" alt="Screenshot 2023-03-21 at 19 36 05" src="https://user-images.githubusercontent.com/103389828/226694478-2cf2a2ba-70a9-4a8f-a35c-32f4c8faf48c.png">

### Worker's Parking Lot Map View Window:
Every parking lot worker can adjust the parking lot he/she works at. 
By clicking at a slot they can change it's status: Empty (grey), Reserved (blue), Restricted (red). 
If the slot is green it means that the slot is Occupied by a car.

<img width="1020" alt="Screenshot 2023-03-21 at 19 40 23" src="https://user-images.githubusercontent.com/103389828/226695513-edcdce85-7873-4c41-bbba-98d8a55efec4.png">

### Parking Lot Manager's Window:
<img width="1020" alt="Screenshot 2023-03-21 at 19 46 36" src="https://user-images.githubusercontent.com/103389828/226697199-31b74151-df49-4234-ad37-f393af57e5e4.png">

### Manager's Prices View
Every manager can change the prices at his/her parking lot. The changes have to be approved by the CEO in order to be applied.
<img width="1021" alt="Screenshot 2023-03-21 at 19 50 14" src="https://user-images.githubusercontent.com/103389828/226697995-00a513a6-3a17-4c69-be47-67f08a1c724c.png">

### CEO's Window
<img width="1021" alt="Screenshot 2023-03-21 at 19 53 57" src="https://user-images.githubusercontent.com/103389828/226698683-d70cb572-d332-4aa9-8f57-e7ccf5dd51df.png">

### CEO's Prices View
In this window we display all the prices changes the CEO gets from the entire parking lot chain. He/She has to approve the changes in prder them to be applied. 
<img width="1021" alt="Screenshot 2023-03-21 at 19 54 31" src="https://user-images.githubusercontent.com/103389828/226698812-38281dd6-e264-4707-a76a-314699d8ee98.png">

### CEO's Reports Window:
<img width="1014" alt="Screenshot 2023-03-21 at 20 01 35" src="https://user-images.githubusercontent.com/103389828/226700615-76a17957-1ed4-4bc9-9a28-6e22a9be6fa0.png">


