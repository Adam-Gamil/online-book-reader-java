# 📚 Online Book Reader

A C++ console-based online book reader application that allows users to sign up, log in, read books, and save their reading progress. Admins can also add new books to the library. The app simulates a basic e-reader experience, with user management and book management features.

## 🚀 Features

- **User Roles:**
  - **Admin:** Admin users can add books to the system and view all registered users.
  - **Customer:** Customers can browse, read books, and save their reading progress (last page and date).
  
- **Books:**
  - Add new books with a title, author, and total pages (admin only).
  - Browse the complete list of books.
  - Track reading progress: last page read and last time opened.
  - Save progress and resume reading later.

- **User Management:**
  - User sign-up with name, email, username, and password.
  - User login with authentication.
  - View all users (admin only).
  
## 🛠️ How It Works

### For Admins:
1. **Sign Up/Login:** Admins can sign up or log in as admin users by selecting the admin role during registration.
2. **Add Books:** Once logged in, admins can add books by providing the book title, author, and total pages.
3. **View Users:** Admins can see a list of all registered users in the system.

### For Customers:
1. **Sign Up/Login:** Customers can sign up or log in to access their reading dashboard.
2. **Browse Books:** Customers can browse the entire collection of available books.
3. **Read Books:** Once a book is selected, the customer can read it and the app will track their reading progress.
4. **Track Reading Progress:** The app remembers the last page a customer read and the date of the last session.
5. **Continue Reading:** Customers can resume from where they left off during their next session.

## 💻 Installation and Usage

### Prerequisites

Make sure you have a C++ compiler installed on your system (e.g., GCC for Linux/Mac or MinGW for Windows).

