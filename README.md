# Library Manager Console App

## 1. Project Overview
This is a console application that simulates a library manager. There are two types of users:
*Admins* who have the privilege to add, edit, and delete books in the library, and also they are the only way more users can be added and registered to the system.
*Regular Users* who can view the book catalog, and borrow and return books.

The app's source code is very modular, and anticipates any needs for further extension. Moreover it allows basic authentication through the login dialogue, and deploys smart logic for handling user's input errors.

The app is developed in **Java verision 22**, and connects to a **MySQL** database. The app was containerized and made ready for deployment through **Docker**.



## 2. Setup Instructions

### 2.1 Prerequisites
1. You need to have Docker installed on you machine. You can download it from the "Download Docker Desktop" button found at the top of the [official page](https://www.docker.com/products/docker-desktop/)

2. (Optional) You can install git on you machine too. It will be a valuable tool for cloning from online repos generally, but you can also not download it and everything will be setup perfectly.  


### 2.2 Step-by-step Instructions
After downloading and installing docker, do the following:

#### 2.2.1 Clone/Download the Repository to your Machine
If you have git, open any terminal and navigate to the directory on your machine where you want the app to be stored. Then execute:

```
git clone https://github.com/Ahmed-Aquarius/Library-Manager-App.git
```

If you chose not to download git, then:
1. click on _code button_
2. click on _download ZIP_
3. after download finishes, move the zip file to any directory you want
4. extract the zip file in that directory

#### 2.2.2 Create a .env File 
1. create a new file in the directory where you downloaded the repo in and name it ".env"
2. copy all the code from the ".env.example" file into the ".env" you created.
3. your _.env_ file should now look like this:

<pre>
```
> DB_HOST=mysql-db
> DB_PORT=3306
> DB_NAME=library_manager
> DB_USER=user
> DB_PASSWORD=pass
```
</pre>

4. replace "user" with your database username, and "pass" with your database password

### 2.2.3 Run the Docker Containers
1. open any terminal, and run:

```
docker-compose up -d mysql-db adminer 
```

2. After the processing happens and the terminal return to accepting prompts again, run:

```
docker-compose run --build --rm app
```

Note: The app may take a few seconds to run after executing the above command

## 3. Access Instructions
There are two points of access for this app, both of which can be accessed only after the full and successful setting up:
1. the terminal on which the app is run: that's the main method of accessing. You can view the meny and interact and use the features of the app from there

2. Adminer: This is a web database management tool that allows you to view the real-time contents of the database and directly manipulte it if you want. You an access it by going to [http://localhost:8080/](http://localhost:8080/)

You will be presented with a login-like form. Enter the values below:

<pre>
```
> System: MySQL/MariaDB
> Server: mysql-db
> Username: <your username>
> Password: <your password>
> Database: library_manager
```
</pre>

replacing the values surrrounded with <> with the values you entered for the same fields in the _.env_ file.