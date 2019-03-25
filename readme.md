# SpringMVCContactManager

SpringMVCContactManager is a Spring Framework REST application for storing contacts and sending contact notifications. 
It is a kind of address book with the possibility of sending notifications. 
As a standard, we have two options to send a notification: 

1. By email
 
2. Cellular SMS. In order to send an SMS, the application uses the REST SMS gateway running on a mobile phone.

If application can't send an SMS or email then it 
goes into standby mode and retransmits the message after a specified 
time until it has not exhausted the maximum number of attempts it can perform.

All configuration parameters are available in the contactmanager.properties file.

One of the goals of the application was to see and practice how the Spring 5 Framework 
works without using springboot in the basic configuration with SpringSecurity and JPA (H2 file).

## Getting Started

### Prerequisites

To run application you need maven 3.5, java 1.8, tomcat 8.5.38

### Installing

To build application run command from SpringMVC directory

```
mvn package
```

To deploy application please type 

```
mvn tomcat7:deploy - needs change in pom.xml in org.apache.tomcat.maven section
                 
                 <url>https://yourhost:port/manager/text</url>
                 <server>TomcatServerName</server>

You can also deploy war.
```

To better understand how the back-end website works, I include JavaScript / JQuery front-end available
at https://github.com/PiotrOlchawa/SpringMVCContactManagerFront


## Running the tests

To run application test just run mvn test.

