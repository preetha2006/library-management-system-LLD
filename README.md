# Library Management System — Low Level Design

## Overview

This repository contains the Low Level Design (LLD) for a Library Management System. The design models the internal structure and components required to manage books, members, borrowing activities, and fines within a library environment.

The system is represented through UML Class Diagrams and Entity Relationship (ER) Diagrams, describing entity relationships, system responsibilities, and the data structures necessary for implementation.

---

## Objectives

The design provides a structured model for managing core library operations, with support for the following:

- Managing books in the library catalog
- Maintaining member records
- Tracking borrowing and return activities
- Enforcing borrowing limits and loan durations
- Handling overdue fines
- Supporting concurrent access to records
- Allowing future system extensibility

---

## System Components

**Librarian**

Responsible for managing books and member records within the system.

| Attribute | Description |
|---|---|
| librarianId | Unique identifier |
| name | Full name |
| mail | Email address |
| role | Assigned role |

Responsibilities: Add, remove, and update book records. Register new members. Process borrowing and return transactions.

---

**Book**

Represents a book available in the library catalog.

| Attribute | Description |
|---|---|
| ISBN | Unique book identifier |
| title | Book title |
| author | Author name |
| publicationYear | Year of publication |
| availability | Current availability status |

Responsibilities: Check and update availability status.

---

**Member**

Represents a registered library user.

| Attribute | Description |
|---|---|
| memberId | Unique identifier |
| name | Full name |
| phoneNo | Contact number |
| mail | Email address |
| borrowingHistory | List of past borrow records |
| borrowLimit | Maximum books allowed at a time |

Responsibilities: Borrow books, return books, and view borrowing history.

---

**BorrowRecord**

Stores the details of each borrowing transaction.

| Attribute | Description |
|---|---|
| recordId | Unique record identifier |
| librarianId | Librarian who processed the transaction |
| memberId | Member who borrowed the book |
| ISBN | Book reference |
| borrowDate | Date of borrowing |
| dueDate | Expected return date |
| returnDate | Actual return date |
| status | Current record status |

Responsibilities: Create and close borrow records. Track borrowing status.

---

**Fine**

Represents a penalty issued for overdue returns.

| Attribute | Description |
|---|---|
| fineId | Unique fine identifier |
| recordId | Associated borrow record |
| overdueDays | Number of days past due date |
| perDayCharge | Charge applied per overdue day |
| totalFineAmount | Calculated total fine |
| status | Payment status |
| issuedDate | Date the fine was issued |
| paidDate | Date the fine was settled |

Responsibilities: Calculate fine amounts and process fine payments.

---

## Diagrams

**UML Class Diagram**

The class diagram illustrates the structural design of the system, including classes, attributes, methods, and relationships between Librarian, Book, Member, BorrowRecord, and Fine. It visualizes how responsibilities are distributed across system components.

**ER Diagram**

The entity relationship diagram describes the database structure required to implement the system.

Key relationships:
- A member can have multiple borrow records.
- A book can appear across multiple borrow records.
- A borrow record may generate a fine if the book is returned after the due date.
- A librarian processes all borrowing and return transactions.

---

## Repository Structure

```
Library-Management-System-LLD
│
├── UML-Diagram
│   └── Library_Class_Diagram.png
│
├── ER-Diagram
│   └── Library_ER_Diagram.png
│
└── README.md
```

---

## Design Principles

- Separation of Concerns
- Encapsulation of Responsibilities
- Modular System Structure
- Extensibility for Future Features

---

## Future Enhancements

- Book reservation functionality
- Multi-branch library management
- Automated notifications for approaching due dates
- Role-based access control
- Online catalog search

---

## About

This repository is a system design exercise focused on modeling a Library Management System using UML Class Diagrams and ER Diagrams, applying core software design principles throughout.
