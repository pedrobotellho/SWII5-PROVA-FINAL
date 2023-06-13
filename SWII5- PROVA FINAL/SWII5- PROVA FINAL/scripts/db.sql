CREATE DATABASE Inventario;
USE Inventario;
 
CREATE TABLE `salesman` (
  `salesman_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `city` varchar(15) NOT NULL,
  `commission` decimal(5, 2) NOT NULL,
  PRIMARY KEY (`salesman_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `customer` (
  `customer_id` int(5) NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(30) NOT NULL,
  `city` varchar(15) NOT NULL,
  `grade` int(3) NOT NULL,
  `salesman_id` int(5) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `orders` (
  `ord_no` int(5) NOT NULL AUTO_INCREMENT,
  `purch_amt` decimal(8, 2) NOT NULL,
  `ord_date` date NOT NULL,
  `customer_id` int(5) NOT NULL,
  `salesman_id` int(5) NOT NULL,
  PRIMARY KEY (`ord_no`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer`(`customer_id`),
  FOREIGN KEY (`salesman_id`) REFERENCES `salesman`(`salesman_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
